package com.example.ecommerce;

import com.example.ecommerce.controller.ProductController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@SpringBootApplication

public class EcommerceApplication {

  public static void main(String[] args) {

    SpringApplication.run(EcommerceApplication.class, args);
  }

}
