package com.anurag.image_uploader.repositories;

import java.io.IOException;
import java.util.List;
import java.time.OffsetDateTime;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.anurag.image_uploader.exceptions.FileReadException;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.sas.SasProtocol;

@Repository
public class AzureBlobRepository implements StorageRepository {

	private BlobServiceClient client;

	AzureBlobRepository(BlobServiceClient client) {
		this.client = client;
	}

	@Override
	public String upload(String container, String blob, MultipartFile file) {
		BlobContainerClient containerClient = client.getBlobContainerClient(container);
		if (!containerClient.exists()) {
			containerClient.create();
		}

		try {
			containerClient.getBlobClient(blob).upload(file.getInputStream(),
					file.getSize(), true);
		} catch (IOException e) {
			throw new FileReadException("Access error: " + blob, e);
		}
		return blob;
	}

	@Override
	public byte[] download(String container, String blob) {
		return client.getBlobContainerClient(container).getBlobClient(blob)
				.downloadContent().toBytes();
	}

	@Override
	public List<String> list(String container) {
		return client.getBlobContainerClient(container).listBlobs().stream()
				.map(blob -> blob.getName()).toList();
	}

	@Override
	public String presignedURL(String container, String blob) {
		BlobClient blobClient = client.getBlobContainerClient(container)
				.getBlobClient(blob);

		String sasToken = blobClient.generateSas(
				new BlobServiceSasSignatureValues(
						OffsetDateTime.now().plusHours(1),
						new BlobSasPermission().setReadPermission(true))
						.setProtocol(SasProtocol.HTTPS_ONLY));

		return blobClient.getBlobUrl() + "?" + sasToken;
	}

	@Override
	public boolean exists(String container, String blob) {
		return client.getBlobContainerClient(container).getBlobClient(blob)
				.exists();
	}

}
