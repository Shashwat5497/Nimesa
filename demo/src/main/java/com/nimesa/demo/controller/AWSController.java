package com.nimesa.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nimesa.demo.service.JobService;

@RestController
@RequestMapping("/api")
public class AWSController {
	@Autowired
	private JobService jobService;

	@PostMapping("/discover-services")
	public ResponseEntity<List<String>> discoverServices(@RequestBody List<String> services) {

		return ResponseEntity.ok(jobService.discoverServices(services));
	}

	@GetMapping("/job-status/{jobId}")
	public ResponseEntity<String> getJobStatus(@PathVariable Long jobId) {
		return ResponseEntity.ok(jobService.getJobStatus(jobId));
	}

	@GetMapping("/s3-objects/{bucketName}")
	public ResponseEntity<List<String>> getS3BucketObjects(@PathVariable String bucketName) {
		String jobId = jobService.getJobIdForS3Bucket(bucketName);
		List<String> objects = jobService.getS3BucketObjects(bucketName, jobId);
		return ResponseEntity.ok(objects);
	}

	@GetMapping("/s3-objects/count/{bucketName}")
	public ResponseEntity<Long> getS3BucketObjectCount(@PathVariable String bucketName) {
		long count = jobService.getS3ObjectCount(bucketName);
		return ResponseEntity.ok(count);
	}

	@GetMapping("/s3-objects/{bucketName}/{pattern}")
	public ResponseEntity<List<String>> getS3BucketObjectsLike(@PathVariable String bucketName,
			@PathVariable String pattern) {
		List<String> objects = jobService.getS3ObjectsLike(bucketName, pattern);
		return ResponseEntity.ok(objects);
	}
}
