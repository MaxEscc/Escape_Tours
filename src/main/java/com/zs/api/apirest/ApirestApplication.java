package com.zs.api.apirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.zs.api.apirest"})
public class ApirestApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApirestApplication.class, args);
  }
}
