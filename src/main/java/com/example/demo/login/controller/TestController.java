package com.example.demo.login.controller;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.demo.login.domain.model.TestModel;
import com.example.demo.login.domain.repository.jdbc.TestJdbc;
import com.example.demo.login.domain.service.TestService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

@Controller
public class TestController {

	@Autowired
	TestService testService;

	@Autowired
	TestJdbc testJdbc;

	@GetMapping("pdf")
	public String getSample(@ModelAttribute TestModel testModel, Model model) throws IOException {

		// テンプレートエンジンを初期化する
		final TemplateEngine engine = initializeTemplateEngine();

		// コンテキストを生成する
		final IContext ctx = makeContext(model);

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
		
		return null;
	}

	@GetMapping("/download")
	public String getDownload(HttpServletResponse response) {
		
		// ダウンロード対象のファイルデータを取得
		Path data = Paths.get("/Users/yuenee/Documents/workspace-sts-3.9.9.RELEASE/PdfTest-5/output/report.pdf");

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

		// 画面遷移先はnullを指定
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


	private IContext makeContext(@ModelAttribute Model model) {
		final IContext ctx = new Context();
		// 変数マップにテンプレート変数を設定

		List<TestModel> testList = testService.selectMany();

		model.addAttribute("testList", testList);
		model.addAttribute("contents", "sample::sample_contents");

		((Context) ctx).setVariable("args", testJdbc.selectMany());


		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		Calendar cal = Calendar.getInstance();
		((Context) ctx).setVariable("today", dateFormat.format(cal.getTime()));

		return ctx;
	}
}