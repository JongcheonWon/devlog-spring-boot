package com.jcwon.devlog.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// spring-integration 에서 제공하는 Adaptor 를 사용할 경우 아래 Annotation 을 선언.
//@IntegrationComponentScan
//@EnableIntegration
public class SftpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SftpApplication.class, args);
    }
}
