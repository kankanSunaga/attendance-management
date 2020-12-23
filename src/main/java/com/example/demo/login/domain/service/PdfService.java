package com.example.demo.login.domain.service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.util.PathUtil;
import com.example.demo.login.domain.service.util.SessionUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

@Service
public class PdfService {

	@Autowired
	UserService userService;

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	DayOfWeekService dayOfWeekService;

	@Autowired
	SessionUtil sessionUtil;

	@Autowired
	PathUtil pathUtil;

	public String createPdf(int userId, List<WorkTime> workTimes, TemplateEngine engine, IContext ctx) throws IOException {

		String htmlPath = pathUtil.createPath("output", userId, "html");
		Writer writer = new FileWriter(htmlPath);

		engine.process("pdf/pdf", ctx, writer);
		writer.close();

		byte[] encoded = Files.readAllBytes(Paths.get(htmlPath));
		String htmlStr = new String(encoded);

		String pdfPath = pathUtil.createPath("output", userId, "pdf");
		try (OutputStream os = new FileOutputStream(pdfPath)) {
			PdfWriter pdfWriter = new PdfWriter(os);
			ConverterProperties converterProperties = new ConverterProperties();

			PdfDocument pdfDocument = new PdfDocument(pdfWriter);
			pdfDocument.setDefaultPageSize(new PageSize(PageSize.A4));

			Document document = HtmlConverter.convertToDocument(htmlStr, pdfDocument, converterProperties);
			document.close();
		}
		return null;
	}

	public String pdfDownload(int userId, HttpServletResponse response) {

		Path data = Paths.get("output/" + userId + ".pdf");

		if (data == null) {
			return "download_error";
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline");
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "");

		try (OutputStream out = response.getOutputStream()) {
			byte[] bytes = Files.readAllBytes(data);
			out.write(bytes);
			out.flush();
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	public TemplateEngine initializeTemplateEngine() {

		final TemplateEngine templateEngine = new TemplateEngine();
		final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		resolver.setTemplateMode("XHTML");
		resolver.setPrefix("templates/");
		resolver.setSuffix(".html");
		templateEngine.setTemplateResolver(resolver);

		return templateEngine;
	}

	public IContext makeContext(int userId, String strYearMonth, List<WorkTime> workTimes,
			LinkedHashMap<String, Object> calender, LinkedHashMap<String, Object> setCalenderObject) {

		final IContext ctx = new Context();

		((Context) ctx).setVariable("userName", userService.selectOne(userId).getUserName());
		((Context) ctx).setVariable("weekFormatter", DateTimeFormatter.ofPattern("E", Locale.JAPANESE));
		((Context) ctx).setVariable("dayOfWeekService", dayOfWeekService);
		((Context) ctx).setVariable("yearMonth", strYearMonth);
		((Context) ctx).setVariable("contract", setCalenderObject);
		((Context) ctx).setVariable("totalTime", workTimeService.samWorkTimeMinute(workTimes));
		((Context) ctx).setVariable("totalDay", workTimes.size());

		return ctx;
	}
}