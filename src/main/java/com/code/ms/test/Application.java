package com.code.ms.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Application.
 *
 * @author Jorge Codelia
 */
@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"cl.code.ms.test"})
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
