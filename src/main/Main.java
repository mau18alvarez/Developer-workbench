package main;

import Tables.Metada_Parser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.text.TableView;
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
        Metada_Parser.parse();


    }

    public void showMainPane() throws IOException {
        mainLayout = FXMLLoader.load(getClass().getResource("/main/MainPane.fxml"));
        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }

    public void showLogInPane() throws IOException {
        Pane logInPane = FXMLLoader.load(getClass().getResource("/LogIn/login_panel.fxml"));
        mainLayout.setCenter(logInPane);
        primaryStage.setResizable(false);
    }

    public static void showMenu() throws IOException {
        BorderPane logInPane = FXMLLoader.load(Main.class.getResource("../UI/editor/editor.fxml"));
        mainLayout.setCenter(logInPane);
        primaryStage.setResizable(true);
    }

    public static void createTableStage() throws IOException {
        Group groupTable = FXMLLoader.load(Main.class.getResource("../Tables/table.fxml"));
        Stage tableStage = new Stage();
        tableStage.setTitle("Create New Table");
        tableStage.initModality(Modality.NONE);
        tableStage.initOwner(primaryStage);
        Scene scene = new Scene(groupTable,430,600);
        tableStage.setScene(scene);
        tableStage.setResizable(false);
        tableStage.showAndWait();
    }

    public static void createMetadata() throws IOException {
        BorderPane metadata = FXMLLoader.load(Main.class.getResource("../Tables/dynamic_table.fxml"));
        Stage tableStage = new Stage();
        tableStage.setTitle("Metadata");
        tableStage.initModality(Modality.NONE);
        tableStage.initOwner(primaryStage);
        Scene scene = new Scene(metadata,300,200);
        tableStage.setScene(scene);
        tableStage.setResizable(true);
        tableStage.showAndWait();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
