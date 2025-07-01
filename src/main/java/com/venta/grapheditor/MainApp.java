package com.venta.grapheditor;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MainApp {
    public static void main(final String[] args) {
        JavaFxApplication.launchApp(new SpringApplicationBuilder(MainApp.class)
                .headless(false)
                .run(args));
    }
}