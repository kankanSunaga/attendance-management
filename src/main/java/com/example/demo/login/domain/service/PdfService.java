package com.example.demo.login.domain.service;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.demo.login.domain.model.Pdf;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.repository.PdfDao;
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
	PdfDao dao;

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

	public List<Pdf> selectMany() {
		return dao.selectMany();
	}

	public String createPdf(int userId, int contractId, String yearMonth, LocalDate minWorkDay, LocalDate maxWorkDay,
			String strYearMonth, HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		final TemplateEngine engine = initializeTemplateEngine();

		final IContext ctx = makeContext(userId, contractId, yearMonth, minWorkDay, maxWorkDay, strYearMonth);

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

	public String pdfDownload(HttpServletResponse response) {

		Path data = Paths.get("output/report.pdf");

		if (data == null) {
			return "download_error";
		}

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline");
		response.setHeader("Cache-Control", "private");
		response.setHeader("Pragma", "");

		try (OutputStream out = response.getOutputStream()) {
			// Files.readAllBytes() 引数:path, 戻り値:byte
			byte[] bytes = Files.readAllBytes(data);
			out.write(bytes);
			out.flush();
		} catch (Exception e) {
			System.err.println(e);
		}
		return null;
	}

	public TemplateEngine initializeTemplateEngine() {
		// エンジンをインスタンス化
		final TemplateEngine templateEngine = new TemplateEngine();
		// テンプレート解決子をインスタンス化（今回はクラスパスからテンプレートをロードする）
		final ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
		// テンプレートモードはXHTML
		resolver.setTemplateMode("XHTML");
		// クラスパスのtemplatesディレクトリ配下にテンプレートファイルを置くことにする
		resolver.setPrefix("templates/");
		// テンプレートの拡張子はhtml
		resolver.setSuffix(".html");
		// テンプレート解決子をエンジンに設定
		templateEngine.setTemplateResolver(resolver);

		return templateEngine;
	}

	public IContext makeContext(int userId, int contractId, String yearMonth, LocalDate minWorkDay,
			LocalDate maxWorkDay, String strYearMonth) {

		final IContext ctx = new Context();
		List<WorkTime> workTimes = workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay);

		// 空のカレンダー作成
		LinkedHashMap<String, Object> calender = workTimeService.calender(yearMonth);
		// 空のカレンダーにデータを追加
		LinkedHashMap<String, Object> setCalenderObject = workTimeService.setCalenderObject(calender, contractId,
				yearMonth);

		// PDFに書き込む用のデータ
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