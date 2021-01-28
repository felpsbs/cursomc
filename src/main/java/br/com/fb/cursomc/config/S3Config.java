package br.com.fb.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
@PropertySource(value = { "file://${HOME}/.cursomc-s3.properties " }, ignoreResourceNotFound = true)
public class S3Config {

	// Somente para o Ninja S3
	private static final String ENDPOINT = "http://localhost:9444/s3";

	@Autowired
	private Environment env;
	
	@Bean
	public AmazonS3 s3client() {
		AWSCredentials credentials = new BasicAWSCredentials(env.getProperty("aws.access_key_id"), env.getProperty("aws.secret_access_key"));
		AmazonS3 amazonS3 =	AmazonS3ClientBuilder.standard()
				//.withRegion(Regions.SA_EAST_1) 
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(ENDPOINT, Regions.SA_EAST_1.getName()))// Somente para o Ninja S3
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	
		return amazonS3;
	}
}
