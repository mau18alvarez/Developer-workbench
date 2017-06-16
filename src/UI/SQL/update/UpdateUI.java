package UI.SQL.update;

import Tables.JavaFXDynTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by josea on 6/15/2017.
 */
public class UpdateUI implements Initializable {

    @FXML
    VBox updateVbox;
    @FXML
    Button addBtn;
    @FXML
    Button readyBtn;
    @FXML
    TextField tableName;
    @FXML
    TextField condAttr;
    @FXML
    TextField condVal;
    @FXML
    Button cancelBtn;

    private TableView tableView = new TableView();

    static final String Day[] = {
            "Attribute's Name",
            "Attribute's New Value"};

    public static class Record<T> {
        private T id;
        private T value_0;
        private T value_1;

        Record(T i, T v0, T v1) {
            this.id = i;
            this.value_0 = v0;
            this.value_1 = v1;
        }

        public void set(int i, T val){
            switch (i){
                case 0:{
                    setValue_0(val);
                    break;
                }
                case 1:{
                    setValue_1(val);
                    break;
                }
                default:{
                    break;
                }

            }
        }

        public T getId() {
            return id;
        }

        public void setId(T v) {
            id = v;
        }

        public T getValue_0() {
            return value_0;
        }

        public void setValue_0(T v) {
            value_0 = v;
        }

        public T getValue_1() {
            return value_1;
        }

        public void setValue_1(T v) {
            value_1 = v;
        }

    };

    ObservableList<UpdateUI.Record<String>> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setEditable(true);

        EventHandler<ActionEvent> addBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {

                        //generate new Record with random number
                        UpdateUI.Record<String> newRec = new UpdateUI.Record("-", "-","-");
                        data.add(newRec);

                    }
                };
        addBtn.setOnAction(addBtnHandler);

        EventHandler<ActionEvent> readyBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {
                        String msg = "UPDATE " + tableName.getText() +" SET ";
                        for(Record record : data){
                            msg += record.getValue_0()+"=";
                            try{
                                Float.valueOf(record.getValue_1().toString());
                                msg += record.getValue_1() +",";
                            }catch (Exception e){
                                msg += "'"+record.getValue_1()+"',";
                            }
                        }
                        msg = msg.substring(0, msg.length() - 1);
                        msg += " WHERE " + condAttr.getText() + "=";
                        try{
                            Float.valueOf(condVal.getText());
                            msg += condVal.getText();
                        }catch (Exception e){
                            msg += "'"+condVal.getText()+"';";
                        }
                        System.out.println("UPDATE MSG: "+msg);
                    }
                };
        readyBtn.setOnAction(readyBtnHandler);

        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        return new UpdateUI.EditingCell();
                    }
                };

        //Editable columns
        for(int i=0; i<Day.length; i++){
            TableColumn col = new TableColumn(Day[i]);
            col.setCellValueFactory(
                    new PropertyValueFactory<JavaFXDynTable.Record, String>(
                            "value_" + String.valueOf(i)));
            col.impl_setReorderable(false);
            col.setPrefWidth(273);
            col.setResizable(false);
            tableView.getColumns().add(col);
            col.setCellFactory(cellFactory);
        }
        tableView.setItems(data);

        updateVbox.getChildren().addAll(tableView);

        EventHandler<ActionEvent> cancelBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {
                        Stage stage = (Stage) updateVbox.getScene().getWindow();
                        stage.close();
                    }
                };
        cancelBtn.setOnAction(cancelBtnHandler);

    }

    class EditingCell<T> extends TableCell<XYChart.Data, T> {

        private TextField textField;

        public EditingCell() {}

        @Override
        public void startEdit() {

            super.startEdit();
            if (textField == null) {
                createTextField();
            }
            setGraphic(textField);
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            textField.selectAll();
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            if (textField.getText() == "") {
                setText(String.valueOf(getItem()));
                data.get(getIndex()).set(getTableView().getColumns().indexOf(getTableColumn()),(String)getItem());
            }else{
                setText(textField.getText());
                data.get(getIndex()).set(getTableView().getColumns().indexOf(getTableColumn()),textField.getText());
            }
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null) {
                setText(null);
                setGraphic(null);
            } else {
                System.out.println("String:" +getString());
                if (isEditing()) {
                    System.out.println("Editing");
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setGraphic(textField);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                } else {
                    setText(getString());
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit((T)textField.getText());
                        data.get(getIndex()).set(getTableView().getColumns().indexOf(getTableColumn()),textField.getText());
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
}
