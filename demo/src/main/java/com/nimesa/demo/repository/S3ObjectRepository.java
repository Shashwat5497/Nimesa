package com.nimesa.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import software.amazon.awssdk.services.s3.model.S3Object;

public interface S3ObjectRepository extends JpaRepository<S3Object, Long> {
	List<S3Object> findByBucketName(String bucketName);

	List<S3Object> findByBucketNameAndObjectNameContaining(String bucketName, String pattern);

	long countByBucketName(String bucketName);
}
