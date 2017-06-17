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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mauri on 16-Jun-17.
 */
public class SelectUI_Join implements Initializable{
    @FXML VBox updateVbox;
    @FXML Button addBtn1;
    @FXML Button addBtn2;
    @FXML Button readyBtn;
    @FXML TextField table1;
    @FXML TextField table2;
    @FXML TextField condAttr;
    @FXML TextField condVal;
    @FXML Button cancelBtn;


    private TableView tableView = new TableView();
    private TableView tableView2 = new TableView();

    static final String Day[] = {
            "Table #1",
            "Table #2"};

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

    ObservableList<SelectUI_Join.Record<String>> data = FXCollections.observableArrayList();
    ObservableList<SelectUI_Join.Record<String>> data2 = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setEditable(true);
        tableView2.setEditable(true);

        EventHandler<ActionEvent> addBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {

                        //generate new Record with random number
                        SelectUI_Join.Record<String> newRec = new SelectUI_Join.Record("-", "-");
                        data.add(newRec);

                    }
                };
        addBtn1.setOnAction(addBtnHandler);

        EventHandler<ActionEvent> addBtnHandler2 =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {

                        //generate new Record with random number
                        SelectUI_Join.Record<String> newRec = new SelectUI_Join.Record("-", "-");
                        data2.add(newRec);

                    }
                };
        addBtn2.setOnAction(addBtnHandler2);

        EventHandler<ActionEvent> readyBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {

                        String msg = "SELECT ";
                        if(!isEmpty(table1.getText()) && !isEmpty(table2.getText())) {
                            if (!isEmpty(condAttr.getText()) & !isEmpty(condVal.getText())) {

                                for (Record record : data) {
                                    if(!isEmpty(record.getValue_0().toString())) {
                                        msg += table1.getText()+"."+record.getValue_0().toString()+ ", ";
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Null Attributes are not Allowed" , "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }

                                for (Record record : data2) {
                                    if(!isEmpty(record.getValue_0().toString())) {
                                        msg += table2.getText()+"."+record.getValue_0().toString()+ ", ";
                                    }else{
                                        JOptionPane.showMessageDialog(null, "Null Attributes are not Allowed" , "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                        return;
                                    }
                                }

                                msg += "FROM " + table1.getText() + " JOIN " + table2.getText() + " ON " + condAttr.getText() + "=" + condVal.getText() + ";";
                                System.out.println(msg);
                                Stage stage = (Stage) readyBtn.getScene().getWindow();
                                stage.close();

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
                        return new SelectUI_Join.EditingCell();
                    }
                };

        Callback<TableColumn, TableCell> cellFactory2 =
                new Callback<TableColumn, TableCell>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        return new SelectUI_Join.EditingCell2();
                    }
                };

        //Editable columns
        TableColumn col = new TableColumn(Day[0]);
        col.setCellValueFactory(
                new PropertyValueFactory<JavaFXDynTable.Record, String>(
                        "value_" + String.valueOf(0)));
        col.impl_setReorderable(false);
        col.setPrefWidth(246);
        col.setResizable(false);
        tableView.getColumns().add(col);
        col.setCellFactory(cellFactory);

        TableColumn col2 = new TableColumn(Day[1]);
        col2.setCellValueFactory(
                new PropertyValueFactory<JavaFXDynTable.Record, String>(
                        "value_" + String.valueOf(0)));
        col2.impl_setReorderable(false);
        col2.setPrefWidth(246);
        col2.setResizable(false);
        tableView2.getColumns().add(col2);
        col2.setCellFactory(cellFactory2);


        tableView.setItems(data);
        tableView2.setItems(data2);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(tableView,tableView2);
        updateVbox.getChildren().addAll(hBox);

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

    class EditingCell2<T> extends TableCell<XYChart.Data, T> {

        private TextField textField;

        public EditingCell2() {}

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
                data2.get(getIndex()).set(getTableView().getColumns().indexOf(getTableColumn()),(String)getItem());
            }else{
                setText(textField.getText());
                data2.get(getIndex()).set(getTableView().getColumns().indexOf(getTableColumn()),textField.getText());
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
                        data2.get(getIndex()).set(getTableView().getColumns().indexOf(getTableColumn()),textField.getText());
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

    private boolean isEmpty(String str){
        if(!str.matches("") && !str.matches(" ") && !str.matches("-")){
            return false;
        }
        return true;
    }
}

