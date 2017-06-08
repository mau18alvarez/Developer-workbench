package UI.editor;

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

    @FXML TreeView<String> leftmenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Circle icon = new Circle(3);
        icon.setFill(Color.BLACK);

        Circle icon2 = new Circle(3);
        icon.setFill(Color.BLACK);


        TreeItem<String> root = new TreeItem<>("Root",icon);
        TreeItem<String> create = new TreeItem<>("Create", new Rectangle(4,4));
        TreeItem<String> drop = new TreeItem<>("Drop", new Rectangle(4,4));
        TreeItem<String> select = new TreeItem<>("Select", new Rectangle(4,4));
        TreeItem<String> insert = new TreeItem<>("Insert", new Rectangle(4,4));
        TreeItem<String> delete = new TreeItem<>("Delete", new Rectangle(4,4));
        TreeItem<String> update = new TreeItem<>("Update", new Rectangle(4,4));

        TreeItem<String> table = new TreeItem<>("Table", icon);
        TreeItem<String> index = new TreeItem<>("Index", icon2);
        root.getChildren().addAll(create,drop,select,insert,delete,update);

        create.getChildren().addAll(table,index);

        leftmenu.setRoot(root);
        leftmenu.setShowRoot(false);
        leftmenu.getSelectionModel().selectedItemProperty()
                .addListener((v,oldValue,newValue) -> {
                    switch (newValue.getValue()){
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

    }

}