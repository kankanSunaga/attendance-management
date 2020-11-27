package com.example.demo.login.domain.service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserIconService {
	public boolean uploadImage(MultipartFile file, int userId) throws IOException {
		String resourcePath = new ClassPathResource("").getPath();
		Path path = Paths.get(resourcePath, "image");

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
}
