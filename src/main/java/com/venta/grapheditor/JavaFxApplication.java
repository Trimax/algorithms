package com.venta.grapheditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;

public final class JavaFxApplication extends Application {
    private static ConfigurableApplicationContext springContext;
    private FXMLLoader fxmlLoader;

    public static void launchApp(ConfigurableApplicationContext ctx) {
        springContext = ctx;
        Application.launch(JavaFxApplication.class);
    }

    @Override
    public void init() {
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlUrl = getClass().getResource("/fxml/main.fxml");
        System.out.println("FXML URL: " + fxmlUrl); // Должен быть не null

        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX + Spring Boot + FXML");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }
}