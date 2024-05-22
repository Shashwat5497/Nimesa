package com.nimesa.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class AWSService {
	private final Ec2Client ec2Client;
	private final S3Client s3Client;

	@Autowired
	public AWSService() {
		Region region = Region.AP_SOUTH_1;
		this.ec2Client = Ec2Client.builder().region(region).build();
		this.s3Client = S3Client.builder().region(region).build();
	}

	public List<String> listEC2Instances() {
		return ec2Client.describeInstances().reservations().stream()
				.flatMap(reservation -> reservation.instances().stream())
				.map(software.amazon.awssdk.services.ec2.model.Instance::instanceId).collect(Collectors.toList());
	}

	public List<String> listS3Buckets() {
		return s3Client.listBuckets().buckets().stream().map(Bucket::name).collect(Collectors.toList());
	}

	public List<String> listS3Objects(String bucketName) {
		return s3Client.listObjectsV2(ListObjectsV2Request.builder().bucket(bucketName).build()).contents().stream()
				.map(S3Object::key).collect(Collectors.toList());
	}
}
