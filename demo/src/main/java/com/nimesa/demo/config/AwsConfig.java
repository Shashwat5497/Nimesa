package com.nimesa.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AwsConfig {

	@Autowired
	private Environment env;

	@Bean
	public Ec2Client ec2Client() {
		return Ec2Client.builder().region(Region.AP_SOUTH_1).credentialsProvider(StaticCredentialsProvider.create(
				AwsBasicCredentials.create(env.getProperty("aws.access-key"), env.getProperty("aws.secret-key"))))
				.build();
	}

	@Bean
	public S3Client s3Client() {
		return S3Client.builder().region(Region.AP_SOUTH_1).credentialsProvider(StaticCredentialsProvider.create(
				AwsBasicCredentials.create(env.getProperty("aws.access-key"), env.getProperty("aws.secret-key"))))
				.build();
	}
}
