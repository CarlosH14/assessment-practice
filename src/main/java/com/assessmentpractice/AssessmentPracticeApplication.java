package com.assessmentpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = "com.assessmentpractice")
public class AssessmentPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssessmentPracticeApplication.class, args);
    }
}
