package com.venta.grapheditor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {
    public static void main(final String[] args) {
        JavaFxApplication.launchApp(new SpringApplicationBuilder(Application.class)
                .headless(false)
                .run(args));
    }
}