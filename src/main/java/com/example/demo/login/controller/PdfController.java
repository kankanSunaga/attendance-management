package com.example.demo.login.controller;

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
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.DayOfWeekService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.WorkTimeService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

@Controller
public class PdfController {

	@Autowired
	UserService userService;

	@Autowired
	ContractService contractService;

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	DayOfWeekService dayOfWeekService;

	// yyyy年MM月
	private String setStrYearMonth;

	@GetMapping("/contract/{contractId}/{yearMonth}/pdfDownload")
	public String getPdfDownload(@ModelAttribute WorkTime form, Model model, HttpServletRequest request,
			HttpServletResponse response, @PathVariable("contractId") int contractId,
			@PathVariable("yearMonth") String yearMonth
	) throws IOException {

		model.addAttribute("yearMonthUrl", yearMonth);
		
		// yyyy-MM-01(月初のString)の作成
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(yearMonth);
		stringBuilder.insert(4, "-");
		stringBuilder.append("-01");
		String strMinWorkDay = stringBuilder.toString();

		// 引数のminWorkDayとmaxWorkDayの値を代入
		LocalDate minWorkDay = LocalDate.parse(strMinWorkDay, DateTimeFormatter.ISO_DATE);
		LocalDate maxWorkDay = minWorkDay.with(TemporalAdjusters.lastDayOfMonth());

		List<WorkTime> contractDayList = workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay);
		model.addAttribute("contractDay", contractDayList);

		// yyyy年MM月の作成
		String strMonth = strMinWorkDay.replace("-01", "月");
		String strYearMonth = strMonth.replace("-", "年");

		// PDFで使いたいのでFieldにset
		this.setStrYearMonth = strYearMonth;

		model.addAttribute("contractId", contractId);
		model.addAttribute("yearMonth", strYearMonth);
		model.addAttribute("yearMonthUrl", yearMonth);

		// セッション取得(userId)
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		// PDF作成処理
		// テンプレートエンジンを初期化する
		final TemplateEngine engine = initializeTemplateEngine();

		// コンテキストを生成する
		final IContext ctx = makeContext(model, userId, contractId, yearMonth, minWorkDay, maxWorkDay);

		// 今回はWriter経由で結果を出力するのでWriterも初期化
		final Writer writer = new FileWriter("output/sample.html");

		// テンプレート名とコンテキストとWriterを引数としてprocessメソッドをコール
		engine.process("pdf/pdf", ctx, writer);

		// Writerをクローズ
		writer.close();

		// ここからがpdf出力用
		byte[] encoded = Files.readAllBytes(Paths.get("output/sample.html"));
		String htmlStr = new String(encoded);

		String outputFile = "output/report.pdf";
		try (OutputStream os = new FileOutputStream(outputFile)) {
			PdfWriter pdfWriter = new PdfWriter(os);
			ConverterProperties converterProperties = new ConverterProperties();
			PdfDocument pdfDocument = new PdfDocument(pdfWriter);

			// For setting the PAGE SIZE
			pdfDocument.setDefaultPageSize(new PageSize(PageSize.A4));

			Document document = HtmlConverter.convertToDocument(htmlStr, pdfDocument, converterProperties);
			document.close();
		}

		// PDFダウンロード処理
		Path data = Paths.get("output/report.pdf");

		// ダウンロード対象のファイルデータがnullの場合はエラー画面に遷移
		if (data == null) {

			return "download_error";
		}

		// PDFプレビューの設定を実施
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "inline;");

		// その他の設定を実施
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

	private TemplateEngine initializeTemplateEngine() {
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

	// PDFに書き込む処理
	private IContext makeContext(@ModelAttribute Model model, int userId, @PathVariable("contractId") int contractId,
			@PathVariable("yearMonth") String yearMonth, LocalDate minWorkDay, LocalDate maxWorkDay) {

		final IContext ctx = new Context();
		List<WorkTime> workTimes = workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay);

		// 空のカレンダー作成
		LinkedHashMap<String, Object> calender = workTimeService.calender(yearMonth);
		// 空のカレンダーにデータを追加
		LinkedHashMap<String, Object> setCalenderObject = workTimeService.setCalenderObject(calender, contractId, yearMonth);

		// PDFに書き込む用のデータ
		((Context) ctx).setVariable("userName", userService.selectOne(userId).getUserName());
		((Context) ctx).setVariable("weekFormatter", DateTimeFormatter.ofPattern("E", Locale.JAPANESE));
		((Context) ctx).setVariable("dayOfWeek", dayOfWeekService);
		((Context) ctx).setVariable("yearMonth", setStrYearMonth);
		((Context) ctx).setVariable("contract", setCalenderObject);
		((Context) ctx).setVariable("totalTime", workTimeService.samWorkTimeMinute(workTimes));
		((Context) ctx).setVariable("totalDay", workTimes.size());

		return ctx;
	}
}