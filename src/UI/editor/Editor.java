package UI.editor;

import UI.custom.DBTable;
import UI.custom.SQLTable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by josea on 6/2/2017.
 */
public class Editor implements Initializable {

    @FXML TreeView<String> leftmenu;
    @FXML Pane StructPane;

    //Tables tab
    @FXML TableView<List<String>> tvTable;
    @FXML Label lbTableTitle;
    @FXML ListView<SQLTable> lvTables;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Circle icon = new Circle(3);
        icon.setFill(Color.BLACK);

        Circle icon2 = new Circle(3);
        icon.setFill(Color.BLACK);

        DBTable dbTable = new DBTable("mierda");
        dbTable.addTableAttribute("Mierda Aguada");
        StructPane.getChildren().add(dbTable);

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

        leftmenu.setOnMouseClicked(event -> {
            if (leftmenu.getSelectionModel().getSelectedItem() != null) {
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
        });

        //Tables tab
        List<String> cols = Arrays.asList("Fname","Pene","Cancer");
        List<List<String>> vals = Arrays.asList(
                Arrays.asList("Tetito","penecito","mucho"),
                Arrays.asList("ASDAS","fhdhd","hfdj"),
                Arrays.asList("Andrecito","chimadito","negro"),
                Arrays.asList("fjvadas","vfdssdhjf","vdvdfv"),
                Arrays.asList("dfdf","iifv","4df54"));
        SQLTable sqlTable = new SQLTable("Penecidad",cols,vals);
        updateTableTree(sqlTable);
    }

    public void updateTableTree(SQLTable sqlTable){
        lbTableTitle.setText(sqlTable.getTitle());
        tvTable.getColumns().clear();
        int c = 0;
        for (String column : sqlTable.getColumns()){
            TableColumn<List<String>,String> col = new TableColumn<>(column);
            final int num =c;
            col.setCellValueFactory(data -> {
                List<String> rowValues = data.getValue();
                String cellValue ;
                if (num < rowValues.size()) {
                    cellValue = rowValues.get(num);
                } else {
                    cellValue = "" ;
                }
                return new ReadOnlyStringWrapper(cellValue);
            });
            tvTable.getColumns().add(col);
            c++;
        }
        for (List<String> values : sqlTable.getValues()) {
            tvTable.getItems().add(values);
        }
    }

    @FXML
    public void onTreeClick(){

    }
}