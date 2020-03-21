package com.leaf.jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.leaf.jobs.dao.mapper")
public class JobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobsApplication.class, args);
	}

}
