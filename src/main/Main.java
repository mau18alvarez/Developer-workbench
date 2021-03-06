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
        primaryStage.setHeight(720);
        primaryStage.setWidth(1080);
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
        Scene scene = new Scene(metadata);
        tableStage.setScene(scene);
        tableStage.setResizable(true);

        tableStage.showAndWait();

    }

    public static void showInsertUI() throws IOException{
        Pane  pane = FXMLLoader.load(Main.class.getResource("../UI/SQL/insert/InsertMenu.fxml"));
        Stage tableStage = new Stage();
        tableStage.setTitle("Insert");
        tableStage.initModality(Modality.NONE);
        tableStage.initOwner(primaryStage);
        Scene scene = new Scene(pane,600,500);
        tableStage.setScene(scene);
        tableStage.setResizable(false);
        tableStage.showAndWait();
    }

    public static void dropUI() throws IOException{
        Pane  pane = FXMLLoader.load(Main.class.getResource("../UI/SQL/drop/DropMenu.fxml"));
        Stage tableStage = new Stage();
        tableStage.setTitle("Drop");
        tableStage.initModality(Modality.NONE);
        tableStage.initOwner(primaryStage);
        Scene scene = new Scene(pane);
        tableStage.setScene(scene);
        tableStage.setResizable(true);
        tableStage.showAndWait();
    }

    public static void showUpdateUI() throws IOException{
        Pane  pane = FXMLLoader.load(Main.class.getResource("../UI/SQL/update/UpdateMenu.fxml"));
        Stage tableStage = new Stage();
        tableStage.setTitle("Update");
        tableStage.initModality(Modality.NONE);
        tableStage.initOwner(primaryStage);
        Scene scene = new Scene(pane,600,400);
        tableStage.setScene(scene);
        tableStage.setResizable(false);
        tableStage.showAndWait();
    }

    public static void selectUI() throws IOException {
        Pane pane = FXMLLoader.load(Main.class.getResource("../UI/SQL/Select/Select_Simple.fxml"));
        Stage tableStage = new Stage();
        tableStage.setTitle("Select Simple");
        tableStage.initModality(Modality.NONE);
        tableStage.initOwner(primaryStage);
        Scene scene = new Scene(pane);
        tableStage.setScene(scene);
        tableStage.setResizable(false);
        tableStage.showAndWait();
    }

    public static void select_join() throws IOException{
        Pane  pane = FXMLLoader.load(Main.class.getResource("../UI/SQL/Select/Select_Join.fxml"));
        Stage tableStage = new Stage();
        tableStage.setTitle("Select Join");
        tableStage.initModality(Modality.NONE);
        tableStage.initOwner(primaryStage);
        Scene scene = new Scene(pane,600,370);
        tableStage.setScene(scene);
        tableStage.setResizable(true);
        tableStage.showAndWait();
    }


    public static void showDeleteUI() throws IOException{
        Pane  pane = FXMLLoader.load(Main.class.getResource("../UI/SQL/delete/DeleteMenu.fxml"));
        Stage tableStage = new Stage();
        tableStage.setTitle("Delete");
        tableStage.initModality(Modality.NONE);
        tableStage.initOwner(primaryStage);
        Scene scene = new Scene(pane,600,268);
        tableStage.setScene(scene);
        tableStage.setResizable(false);
        tableStage.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
