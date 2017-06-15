package UI.editor;

import UI.custom.CustomStructurePane;
import UI.custom.ZoomablePane;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.Main;

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
    @FXML
    Pane StructPane;
    @FXML
    Slider zoomSlider;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Circle icon = new Circle(3);
        icon.setFill(Color.BLACK);

        Circle icon2 = new Circle(3);
        icon.setFill(Color.BLACK);


        CustomStructurePane structurePane = new CustomStructurePane();
        structurePane.setScaleX(0.5);
        structurePane.setScaleY(0.5);

        structurePane.addTable("MIERDA,Fname-!VARCHAR(15),Pene-VARCHAR(20),Panochito-!VARCHAR(10)");
        structurePane.addTable("Mierdota,Fname-!VARCHAR(15),Pene-VARCHAR(20):FUCK.ID,Panochito-!VARCHAR(10)");
        structurePane.addTable("FUCK,Fname-!VARCHAR(15),Pene-VARCHAR(20),Panochito-!VARCHAR(10)");

        //structurePane.connect();
        ZoomablePane zoomablePane = new ZoomablePane(structurePane);
        StructPane.getChildren().addAll(zoomablePane);

        zoomablePane.zoomFactorProperty().bind(zoomSlider.valueProperty());

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


        leftmenu.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (leftmenu.getSelectionModel().getSelectedItem() != null) {
                    if (event.getClickCount() == 2){
                        switch (leftmenu.getSelectionModel().getSelectedItem().getValue()) {
                            case "Table": {
                                try {
                                    leftmenu.getSelectionModel().clearSelection();
                                    Main.createTableStage();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                            }
                        }
                    }
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

    @FXML
    public void onTreeClick(){

    }
}
