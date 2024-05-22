package com.nimesa.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nimesa.demo.entity.Job;
import com.nimesa.demo.repository.JobRepository;
import com.nimesa.demo.repository.S3ObjectRepository;

import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class JobService {
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private S3ObjectRepository s3ObjectRepository;
	@Autowired
	private AWSService awsService;

	@Async
	public List<String> discoverServices(List<String> services) {
		List<String> result = new ArrayList<>();
		try {
			for (String service : services) {
				if ("EC2".equalsIgnoreCase(service)) {
					List<String> instances = awsService.listEC2Instances();

					result.addAll(instances);

				} else if ("S3".equalsIgnoreCase(service)) {
					List<String> buckets = awsService.listS3Buckets();
					result.addAll(buckets);
				}
			}

		} catch (Exception e) {
			System.err.println("Exception occured while discovering services.");
		}
		return result;
	}

	public String getJobStatus(Long jobId) {
		return jobRepository.findById(jobId).map(Job::getStatus).orElse("Not Found");
	}

	public List<String> getS3BucketObjects(String bucketName, String jobId) {
		List<String> objects = awsService.listS3Objects(bucketName);

		return objects;
	}

	public Job save(Job job) {
		jobRepository.save(job);
		return null;
	}

	public String getJobIdForS3Bucket(String bucketName) {
		List<S3Object> s3Objects = s3ObjectRepository.findByBucketName(bucketName);
		return s3Objects.isEmpty() ? s3Objects.get(0).key() : null;
	}

	public long getS3ObjectCount(String bucketName) {
		List<S3Object> s3Objects = s3ObjectRepository.findByBucketName(bucketName);
		return s3Objects.isEmpty() ? s3Objects.size() : 0;
	}

	public List<String> getS3ObjectsLike(String bucketName, String pattern) {
		// TODO Auto-generated method stub
		return null;
	}
}
