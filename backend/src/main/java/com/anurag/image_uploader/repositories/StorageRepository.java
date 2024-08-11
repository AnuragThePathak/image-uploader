package com.anurag.image_uploader.repositories;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface StorageRepository {

	String upload(String container, String name, MultipartFile file);

	byte[] download(String container, String name);

	List<String> list(String container);

	String presignedURL(String container, String name);

	boolean exists(String container, String name);
}
