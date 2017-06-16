package UI.SQL.Select;

import Tables.JavaFXDynTable;
import UI.SQL.update.UpdateUI;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mauri on 15-Jun-17.
 */
public class SelectUI implements Initializable {


    @FXML VBox updateVbox;
    @FXML Button addBtn;
    @FXML Button readyBtn;
    @FXML TextField tableName;
    @FXML TextField where_id;
    @FXML TextField equals_id;
    @FXML Button cancelBtn;

    private TableView tableView = new TableView();

    static final String Day[] = {
            "Attribute's Name"};

    public static class Record<T> {
        private T id;
        private T value_0;

        Record(T i, T v0) {
            this.id = i;
            this.value_0 = v0;
        }

        public void set(int i, T val){
            switch (i){
                case 0:{
                    setValue_0(val);
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


    };

    ObservableList<SelectUI.Record<String>> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setEditable(true);

        EventHandler<ActionEvent> addBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {

                        //generate new Record with random number
                        SelectUI.Record<String> newRec = new SelectUI.Record("-","-");
                        data.add(newRec);

                    }
                };
        addBtn.setOnAction(addBtnHandler);

        EventHandler<ActionEvent> readyBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {
                        String msg = "UPDATE ";
                        if(!isEmpty(tableName.getText())) {
                            if (!isEmpty(where_id.getText()) & !isEmpty(equals_id.getText())) {
                                for (Record record : data) {
                                    msg += record.getValue_0().toString();
                                    msg += ", ";
                                }
                                msg += "FROM " + tableName.getText() + " WHERE " + where_id.getText() + "=" + equals_id.getText();
                                Stage stage = (Stage) readyBtn.getScene().getWindow();
                                stage.close();
                                System.out.println(msg);

                            }else{
                                JOptionPane.showMessageDialog(null, "You need to fill all spaces" , "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "The table's name is required." , "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                };
        readyBtn.setOnAction(readyBtnHandler);

        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        return new SelectUI.EditingCell();
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
                        data.get(getIndex()).set(getTableView().getColumns().indexOf(getTableColumn()),textField.getText());
                        cancelEdit();
                    }
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

    private boolean isEmpty(String str){
        if(!str.matches("") && !str.matches(" ") && !str.matches("-")){
            return false;
        }
        return true;
    }
}
