package UI.SQL.insert;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by josea on 6/15/2017.
 */
public class InsertUI implements Initializable{

    @FXML
    VBox insertVBox;
    @FXML
    Pane insertPane;
    @FXML
    Button btnReady;
    @FXML
    ComboBox tablesDropDown;

    private TextField name;
    final BooleanProperty firstTime = new SimpleBooleanProperty(true);

    ObservableList<String> tables;
    Map<String,String> tablesMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name = new TextField();
        name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                insertVBox.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        name.setPromptText("Name");

        tablesDropDown.setPromptText("Choose Table");
        tablesDropDown.setPrefSize(350,25);
        tables = FXCollections.observableArrayList();
        tablesMap = new HashMap<>();
        getTables();
        tablesDropDown.setItems(tables);
        tablesDropDown.valueProperty().addListener((obs, oldItem, newItem) -> {
            if(newItem != null){
                insertVBox.getChildren().clear();
                String[] attrsArr = tablesMap.get(newItem.toString()).split(",");
                for(String str : attrsArr){
                    if(!str.matches("")){
                        String[] strArr = str.split("-");
                        HBox hBox = new HBox();
                        Label label = new Label(strArr[0]+": ");
                        label.setPrefSize(150,25);
                        TextField textField = new TextField();
                        textField.setPrefSize(350,25);
                        hBox.getChildren().addAll(label,textField);
                        insertVBox.getChildren().addAll(hBox);
                    }
                }
            }
        });

        EventHandler<ActionEvent> btnReadyHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {
                        String msg = "INSERT INTO " + tablesDropDown.valueProperty().getValue()+
                                " (";
                        String[] attrsArr = tablesMap.get(tablesDropDown.valueProperty().getValue()).split(",");
                        for(int i = 0; i < attrsArr.length ; i++){
                            System.out.println("PENE: "+attrsArr[i]);
                            if(!attrsArr[i].matches("")){
                                String[] strArr = attrsArr[i].split("-");
                                msg += strArr[0];
                                if(i < attrsArr.length-1){
                                    msg += ",";
                                }
                            }
                        }
                        msg += ") VALUES (";
                        for(Node node : insertVBox.getChildren()){
                            TextField textField = new TextField("");
                            boolean nullTexField = false;
                            try{
                                HBox hBox = (HBox)node;
                                for(Node node1 : hBox.getChildren()){
                                    try{
                                        textField = (TextField)node1;
                                        nullTexField = true;
                                    }catch (Exception e){}
                                }
                            }catch (Exception e){}
                            if(!textField.getText().matches("")){
                                System.out.println("Txt: "+textField.getText());
                                msg += "'"+textField.getText()+"',";
                            }else{
                                if(nullTexField){
                                    msg += "!,";
                                }
                            }
                        }
                        msg = msg.substring(0, msg.length() - 1);
                        msg += ");";
                        System.out.println(msg);
                    }
                };
        btnReady.setOnAction(btnReadyHandler);

    }

    private void getTables(){
        String tablesStr = "MIERDAGUADA,Fname-!VARCHAR(15),Panochito-!VARCHAR(10)|MIERDA,Fname-!VARCHAR(15),Pene-VARCHAR(20),Panochito-!VARCHAR(10)";
        String[] tablesArr = tablesStr.split("\\|");
        for(String table : tablesArr){
            String[] tableArr = table.split(",");
            String Key = tableArr[0];
            String tab = "";
            for(int i = 1; i < tableArr.length ; i++){
                tab += tableArr[i]+",";
            }
            tablesMap.put(Key,tab);
            tables.add(Key);
        }
    }

}
