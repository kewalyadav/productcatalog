package com.product.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.product.catalog", "com.product.catalog.**.**"})
public class ProductCatalogApplication {

    public static void main(String... args) {
        SpringApplication.run(ProductCatalogApplication.class, args);
    }
}
