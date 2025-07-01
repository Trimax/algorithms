package com.venta.grapheditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

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
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        primaryStage.setTitle("Visibility graph test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }
}