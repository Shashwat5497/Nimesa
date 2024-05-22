package com.nimesa.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nimesa.demo.entity.Job;

public interface JobRepository extends JpaRepository<Job, Long> {

}
