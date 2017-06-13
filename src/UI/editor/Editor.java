package UI.editor;

import Tables.DynamicTableView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import sun.reflect.generics.tree.Tree;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by josea on 6/2/2017.
 */
public class Editor implements Initializable {

    @FXML
    TreeView<String> leftmenu;
    @FXML
    TreeView<String> menu_tables;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Circle icon = new Circle(3);
        icon.setFill(Color.BLACK);

        Circle icon2 = new Circle(3);
        icon.setFill(Color.BLACK);

        //Para los de Structure

        TreeItem<String> root_structure = new TreeItem<>("Root", icon);
        TreeItem<String> create = new TreeItem<>("Create", new Rectangle(4, 4));
        TreeItem<String> drop = new TreeItem<>("Drop", new Rectangle(4, 4));
        TreeItem<String> select = new TreeItem<>("Select", new Rectangle(4, 4));
        TreeItem<String> insert = new TreeItem<>("Insert", new Rectangle(4, 4));
        TreeItem<String> delete = new TreeItem<>("Delete", new Rectangle(4, 4));
        TreeItem<String> update = new TreeItem<>("Update", new Rectangle(4, 4));


        TreeItem<String> table = new TreeItem<>("Table", icon);
        TreeItem<String> index = new TreeItem<>("Index", icon2);

        root_structure.getChildren().addAll(create, drop, select, insert, delete, update);

        create.getChildren().addAll(table, index);

        leftmenu.setRoot(root_structure);
        leftmenu.setShowRoot(false);

        leftmenu.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    switch (newValue.getValue()) {
                        case "Table": {
                            try {
                                Main.createTableStage();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                });

        //Para los de Structure
        TreeItem<String> root_table = new TreeItem<>("Root", icon);
        TreeItem<String> Metadata = new TreeItem<>("Metadata", new Rectangle(4, 4));


        root_table.getChildren().addAll(Metadata);

        menu_tables.setRoot(root_table);
        menu_tables.setShowRoot(false);

        menu_tables.getSelectionModel().selectedItemProperty()
                .addListener((v, oldValue, newValue) -> {
                    switch (newValue.getValue()) {
                        case "Metadata": {
                            try {
                                Main.createMetadata();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                });


    }
}