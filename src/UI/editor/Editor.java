package UI.editor;

import Networking.Socket.SocketConnection;
import UI.custom.CustomStructurePane;
import UI.custom.NodesPane;
import UI.custom.SQLTable;
import UI.custom.ZoomablePane;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import main.Main;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by josea on 6/2/2017.
 */
public class Editor implements Initializable {


    @FXML TreeView<String> leftmenu;
    @FXML Pane StructPane;
    @FXML Slider zoomSlider;
    @FXML ScrollPane nombrebonito;
    CustomStructurePane structurePane;
    //Tables tab
    @FXML TableView<List<String>> tvTable;
    @FXML Label lbTableTitle;
    @FXML ListView<SQLTable> lvTables;
    @FXML TabPane tabPane;

    NodesPane nPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Circle icon = new Circle(3);
        icon.setFill(Color.BLACK);

        Circle icon2 = new Circle(3);
        icon.setFill(Color.BLACK);

        Circle icon3 = new Circle(3);
        icon.setFill(Color.BLACK);

        Circle icon4 = new Circle(3);
        icon.setFill(Color.BLACK);



        /*DBTable dbTable = new DBTable("mierda");
        dbTable.addTableAttribute("Mierda Aguada");
        StructPane.getChildren().add(dbTable);*/

        structurePane = new CustomStructurePane();
        structurePane.setScaleX(0.5);
        structurePane.setScaleY(0.5);


        //structurePane.connect();
        ZoomablePane zoomablePane = new ZoomablePane(structurePane);
        StructPane.getChildren().addAll(zoomablePane);

        zoomablePane.zoomFactorProperty().bind(zoomSlider.valueProperty());

        nPane = new NodesPane();
        nombrebonito.setContent(nPane);
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

        TreeItem<String> Simple = new TreeItem<>("Simple", icon3);
        TreeItem<String> Join = new TreeItem<>("Join", icon4);

        root_structure.getChildren().addAll(create, drop, select, insert, delete, update);

        create.getChildren().addAll(table, index);

        select.getChildren().addAll(Simple,Join);

        leftmenu.setRoot(root_structure);
        leftmenu.setShowRoot(false);

        leftmenu.setOnMouseClicked(event -> {
            if (leftmenu.getSelectionModel().getSelectedItem() != null) {
                if (event.getClickCount() == 2) {
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
                        case "Insert": {
                            leftmenu.getSelectionModel().clearSelection();
                            try {
                                Main.showInsertUI();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "Drop": {
                            leftmenu.getSelectionModel().clearSelection();
                            try {
                                Main.dropUI();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "Update":{
                            leftmenu.getSelectionModel().clearSelection();
                            try {
                                Main.showUpdateUI();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "Delete": {
                            leftmenu.getSelectionModel().clearSelection();
                            try {
                                Main.showDeleteUI();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "Simple":{
                            leftmenu.getSelectionModel().clearSelection();
                            try {
                                Main.selectUI();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                        case "Join": {
                            leftmenu.getSelectionModel().clearSelection();
                            try {
                                Main.select_join();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
        });

        initializeListView();
        updateMetadata();

        tabPane.setOnMouseClicked(event -> {
            if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Tables")){
                initializeListView();
            }
            if (tabPane.getSelectionModel().getSelectedItem().getText().equals("Structure")){
                updateMetadata();
            }
        });


        //Tables tab
        /*List<String> cols = Arrays.asList("Fname","Pene","Cancer");
        List<List<String>> vals = Arrays.asList(
                Arrays.asList("Tetito","penecito","mucho"),
                Arrays.asList("ASDAS","fhdhd","hfdj"),
                Arrays.asList("Andrecito","chimadito","negro"),
                Arrays.asList("fjvadas","vfdssdhjf","vdvdfv"),
                Arrays.asList("dfdf","iifv","4df54"));
        SQLTable sqlTable = new SQLTable("Penecidad",cols,vals);
        updateTableTree(sqlTable);*/
    }

    @FXML
    public void onListViewClick(){
        if (lvTables.getSelectionModel().getSelectedItem() != null){
            updateTableTree(lvTables.getSelectionModel().getSelectedItem());
        }
    }

    public void updateMetadata(){
        String responce = SocketConnection.getInstance().request("RequestAllMetadata");
        System.out.println("RESPONSE: "+responce);
        if(responce.contains("Error")){
            JOptionPane.showMessageDialog(null, responce, "Error",
                    JOptionPane.PLAIN_MESSAGE);
        }else{
            structurePane.getChildren().clear();
            String[] strArr = responce.split("\\^");
            for(String str : strArr){
                //System.out.println(str);
                structurePane.addTable(str);
            }
            nPane.getChildren().clear();
            nPane.addNodes(responce);
        }
    }

    public void updateTableTree(SQLTable sqlTable){
        lbTableTitle.setText(sqlTable.getTitle());
        tvTable.getColumns().clear();
        tvTable.getItems().clear();
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

    public void initializeListView(){
        lvTables.getItems().clear();
        String strTables = SocketConnection.getInstance().request("RequestAllTables");
        System.out.println(strTables);

        String[] Tables = strTables.split(",");
        for (String table : Tables){
            String[] all = table.split("\\.");
            List<String> columns = new ArrayList<>();
            List<List<String>> values = new ArrayList<>();
            if (all.length != 1) {
                System.out.println("ESTO ES TABLE " + table);
                String[] rows = all[1].split("-");
                String[] temp = rows[0].split(";");
                for (String tempcol : temp) {
                    columns.add(tempcol.split(":")[0]);
                }
                for (String row : rows) {
                    String[] cols = row.split(";");
                    List<String> value = new ArrayList<>();
                    for (String col : cols) {
                        value.add(col.split(":")[1]);
                    }
                    values.add(value);
                }
            }
            lvTables.getItems().add(new SQLTable(all[0], columns, values));
        }
    }

    @FXML
    public void onTreeClick(){

    }
}
