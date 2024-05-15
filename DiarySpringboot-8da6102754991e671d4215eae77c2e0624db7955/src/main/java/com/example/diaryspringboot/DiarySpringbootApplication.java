package com.example.diaryspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.example.diaryspringboot"})
@EnableJpaRepositories(basePackages="com.example.diaryspringboot.repository")
@EnableTransactionManagement
@EntityScan(basePackages="com.example.diaryspringboot.entity")
public class DiarySpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiarySpringbootApplication.class, args);
    }

}
