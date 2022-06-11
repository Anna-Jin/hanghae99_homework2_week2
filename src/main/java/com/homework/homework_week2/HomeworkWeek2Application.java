package com.homework.homework_week2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HomeworkWeek2Application {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkWeek2Application.class, args);
    }

}
