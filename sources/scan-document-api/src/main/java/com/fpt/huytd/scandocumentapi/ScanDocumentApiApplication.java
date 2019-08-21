package com.fpt.huytd.scandocumentapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ScanDocumentApiApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ScanDocumentApiApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
//        SpringApplication.run(ScanDocumentApiApplication.class, args);
    }

}
