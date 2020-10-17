package com.example.demo.login.domain.service;


import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;


@Service
public class DayOfWeekService {
	public static boolean hasHoliday(LocalDate termination) throws IOException{
		
		//LocalDate型の変数を曜日にして、String型に変換
		String holiday = termination.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
		//System.out.println(holiday);
		
		//土日だったらtrueを返す
		if (holiday.equals("Sunday")||holiday.equals("Saturday")) {
			return true;
		}
		
		//祝日判定APIを叩いている
		final String API_URL = "http://s-proj.com/utils/checkHoliday.php?kind=h&date=" + termination;

		URL url = null;
		BufferedReader br = null;
		InputStreamReader isr = null;
		String readLine = "";
		String writeContent = "";

		try {
			url = new URL(API_URL);

			/* ここから読み込むための定型文 */
			isr = new InputStreamReader(url.openStream());
			br = new BufferedReader(isr);
			while ((readLine = br.readLine()) != null)
				writeContent = writeContent + readLine;// ここで読み込んだ内容を変数に保存している

		} catch (IOException e) {
			e.printStackTrace();

		}finally {
			isr.close();
			br.close();
		}
		
		//祝日かどうかの判定
		if (writeContent.equals("holiday")) {
			return true;
		}else {
			return false;
		}
	}	
}
