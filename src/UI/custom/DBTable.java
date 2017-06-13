package UI.custom;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by josea on 6/13/2017.
 */
public class DBTable extends VBox {

    private ListView<String> listView;
    private ObservableList<String> data;
    private Label lbl;

    public DBTable(String title) {
        this.setPrefSize(200, 250);
        lbl = new Label(title);
        this.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        this.data = FXCollections.observableArrayList();

        this.listView  = new ListView<String>(data);
        listView.setPrefSize(200, 250);
        listView.setEditable(true);
        this.listView.setItems(this.data);

        this.getChildren().addAll(lbl,listView);

        this.setOnMouseDragged(event -> {
            setManaged(false);
            this.setTranslateX(event.getX() + this.getTranslateX()-50);
            this.setTranslateY(event.getY() + this.getTranslateY()-50);
            event.consume();
        });
    }

    public void addTableAttribute(String attribute){
        this.data.add(attribute);
    }

}
