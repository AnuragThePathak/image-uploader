package com.anurag.image_uploader.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anurag.image_uploader.exceptions.FileNotFoundException;
import com.anurag.image_uploader.repositories.StorageRepository;

@Service
public class StorageService {

	private StorageRepository repository;

	@Value("${azure.storage.container}")
	private String container;

	StorageService(StorageRepository repository) {
		this.repository = repository;
	}

	public String upload(MultipartFile file) {
		String originalName = file.getOriginalFilename();
		String name;
		String uuid = UUID.randomUUID().toString();
		int idx;

		if (originalName != null && (idx = originalName.lastIndexOf(".")) != -1) {
			String extension = originalName.substring(idx + 1);
			name = uuid + "." + extension;
		} else {
			name = uuid;
		}

		return repository.presignedURL(container, repository.upload(container, name, file));
	}

	public byte[] download(String name) {
		if (!repository.exists(container, name)) {
			throw new FileNotFoundException("File not found: " + name);
		}
		return repository.download(container, name);
	}

	public List<String> list() {
		return repository.list(container).stream().map(
			name -> repository.presignedURL(container, name)).toList();
	}

	public String presignedURL(String name) {
		if (!repository.exists(container, name)) {
			throw new FileNotFoundException("File not found: " + name);
		}
		return repository.presignedURL(container, name);
	}

}
