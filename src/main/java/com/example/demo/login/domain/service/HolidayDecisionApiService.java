package com.example.demo.login.domain.service;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


@Service
public class HolidayDecisionApiService {
	public String holidaydecisionapi(){
		String a = "2020-10-30";
		final String API_URL = "http://s-proj.com/utils/checkHoliday.php?kind=h&date=" + a;

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

		} catch (Exception e) {
			e.printStackTrace();

		}
		System.out.println(writeContent);

		return writeContent;

	}
	String result = holidaydecisionapi();
}
