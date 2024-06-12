package com.example.todoapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.GetParameterResponse;

@SpringBootApplication
public class TodoappApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoappApplication.class, args);
    }

    // parameter store의 값을 읽어 출력하기
    @Bean
    public CommandLineRunner run() {
        return args -> {
            SsmClient ssmClient = SsmClient.builder()
                    .region(Region.AP_NORTHEAST_2)
                    .build();

            System.out.println("username: " + getParameterValue(ssmClient, "/todo/config/DB_USERNAME"));
            System.out.println("password: " + getParameterValue(ssmClient, "/todo/config/DB_PASSWORD"));
        };
    }

    private String getParameterValue(SsmClient ssmClient, String parameterName){
        GetParameterRequest parameterRequest = GetParameterRequest.builder()
                .name(parameterName)
                .withDecryption(true)
                .build();

        GetParameterResponse parameterResponse = ssmClient.getParameter(parameterRequest);
        return parameterResponse.parameter().value();
    }
}
