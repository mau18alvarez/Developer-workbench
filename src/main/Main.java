package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    private static Stage primaryStage;
    private static BorderPane mainLayout;

    public static String ip = null;
    public static String puerto = null;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.primaryStage = primaryStage;
        Main.primaryStage.setTitle("Developer Workbench");

        showMainPane();
        showLogInPane();


    }

    public void showMainPane() throws IOException {
        mainLayout = FXMLLoader.load(getClass().getResource("/main/MainPane.fxml"));
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showLogInPane() throws IOException {
        Pane logInPane = FXMLLoader.load(getClass().getResource("/LogIn/login_panel.fxml"));
        mainLayout.setCenter(logInPane);
    }

    public static void showMenu() throws IOException {
        BorderPane logInPane = FXMLLoader.load(Main.class.getResource("../UI/editor/editor.fxml"));
        mainLayout.setCenter(logInPane);
    }


    public static void main(String[] args) {
        launch(args);
    }
}