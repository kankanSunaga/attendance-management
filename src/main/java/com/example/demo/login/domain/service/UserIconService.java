package com.example.demo.login.domain.service;

import java.nio.file.Files;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.login.domain.service.util.PathUtil;

@Service
public class UserIconService {

	@Autowired
	PathUtil pathUtil;

	@Autowired
	ResourceLoader resourceLoader;

	public boolean setImage(MultipartFile file, int userId) throws IOException {

		Path path = Paths.get("image");
		if (Files.notExists(path)) {
			try {
				Files.createDirectory(path);
			} catch (NoSuchFileException ex) {
				System.err.println(ex);
			} catch (IOException ex) {
				System.err.println(ex);
			}
		}

		int dot = file.getOriginalFilename().lastIndexOf(".");
		String extention = "";
		if (dot > 0) {
			extention = file.getOriginalFilename().substring(dot).toLowerCase();
		}

		Path uploadfile = Paths.get(path.toAbsolutePath().toString(), userId + extention);

		try (OutputStream os = Files.newOutputStream(uploadfile, StandardOpenOption.CREATE)) {
			byte[] bytes = file.getBytes();
			os.write(bytes);
		} catch (IOException ex) {
			System.err.println(ex);
		}

		return true;
	}

	public String uploadImage(int userId) throws IOException {

		Path path = Paths.get("image", userId + ".jpg");

		byte[] byteData = Files.readAllBytes(pathExists(path));

		Charset charset = StandardCharsets.UTF_8;
		byte[] a = Base64.getEncoder().encode(byteData);
		String base64 = new String(a, charset);

		StringBuffer data = new StringBuffer();
		data.append("data:image/jpeg;base64,");
		data.append(base64);

		return data.toString();
	}

	public Path pathExists(Path path) {

		if (Files.notExists(path)) {
			return Paths.get("image", "sample.jpg");
		} else {
			return path;
		}
	}
 
	public String uploadLogoImage() throws IOException {

		Path path = Paths.get("image", "logo.jpg");

		byte[] byteData = Files.readAllBytes(pathExists(path));

		Charset charset = StandardCharsets.UTF_8;
		byte[] a = Base64.getEncoder().encode(byteData);
		String base64 = new String(a, charset);

		StringBuffer data = new StringBuffer();
		data.append("data:image/jpeg;base64,");
		data.append(base64);

		return data.toString();
	}
}