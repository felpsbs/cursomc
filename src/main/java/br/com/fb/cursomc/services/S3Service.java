package br.com.fb.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import br.com.fb.cursomc.services.exceptions.FileException;

@Service
public class S3Service {

	private static final String URL = "http://localhost:9444/ui/"; // Somente para o Ninja S3
	private static Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();
			return uploadFile(is, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO: " + e.getMessage());
		}
	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(contentType);
			LOG.info("Iniciando upload...");
			s3client.putObject(bucketName, fileName, is, meta);
			LOG.info("Upload finalizado");
			return new URI(getUrl(fileName)); // Somente para o Ninja S3
			// return s3client.getUrl(bucketName, fileName).toURI(); // para o AmazonS3
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter de URL para URI");
		}
	}
	
	// Somente para o Ninja S3
	public String getUrl(String fileName) {
		if(!StringUtils.isEmpty(fileName)) {
			return  URL + bucketName + "/" + fileName;
		}
		return null;
	}

}
