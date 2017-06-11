package Tables;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import Networking.Socket.SocketConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;


public class JavaFXDynTable implements Initializable {

    private TableView tableView = new TableView();
    private Button btnNew = new Button("Add Table Attribute");
    private Button btnReady = new Button("Ready");
    private TextField tableName = new TextField();
    final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load

    @FXML VBox tableVbox;
    static final String Day[] = {
            "Name",
            "Data Type",
            "IsRequired",
            "Referencing"};

    public static class Record<T> {
        private T id;
        private T value_0;
        private T value_1;
        private T value_2;
        private T value_3;
        private T value_4;

        Record(T i, T v0, T v1, T v2, T v3, T v4) {
            this.id = i;
            this.value_0 = v0;
            this.value_1 = v1;
            this.value_2 = v2;
            this.value_3 = v3;
            this.value_4 = v4;
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
                case 2:{
                    setValue_2(val);
                    break;
                }
                case 3:{
                    setValue_3(val);
                    break;
                }
                case 4:{
                    setValue_4(val);
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

        public T getValue_2() {
            return value_2;
        }

        public void setValue_2(T v) {
            value_2 = v;
        }

        public T getValue_3() {
            return value_3;
        }

        public void setValue_3(T v) {
            value_3 = v;
        }

        public T getValue_4() {
            return value_4;
        }

        public void setValue_4(T v) {
            value_4 = v;
        }

    };

    ObservableList<Record<String>> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableView.setEditable(true);
        tableName.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                tableVbox.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        tableName.setPromptText("Table Name");

        Callback<TableColumn, TableCell> cellFactory =
                new Callback<TableColumn, TableCell>() {

                    @Override
                    public TableCell call(TableColumn p) {
                        return new EditingCell();
                    }
                };

        btnNew.setOnAction(btnNewHandler);
        btnReady.setOnAction(btnReadyHandler);


        //Editable columns
        for(int i=0; i<Day.length; i++){
            TableColumn col = new TableColumn(Day[i]);
            col.setCellValueFactory(
                    new PropertyValueFactory<Record, String>(
                            "value_" + String.valueOf(i)));
            col.impl_setReorderable(false);
            col.setPrefWidth(100);
            col.setResizable(false);
            tableView.getColumns().add(col);
            col.setCellFactory(cellFactory);
        }
        tableView.setItems(data);

        tableVbox.setSpacing(10);
        Insets insets = new Insets(10,20,10,20);
        tableVbox.setMargin(tableView,insets);
        tableVbox.setMargin(tableName,insets);
        tableVbox.setMargin(btnNew,insets);
        tableVbox.setMargin(btnReady,insets);
        tableVbox.getChildren().addAll(tableName,btnNew, tableView,btnReady);
    }

    EventHandler<ActionEvent> btnNewHandler =
            new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {

                    //generate new Record with random number
                    Record<String> newRec = new Record("-", "-", "-", "-", "-", "-");
                    data.add(newRec);

                }
            };

    EventHandler<ActionEvent> btnReadyHandler =
            new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    String str = "CREATE TABLE "+tableName.getText()+" (";
                    for(Record record : data){
                        str += record.getValue_0();
                        str += record.getValue_1();
                        str += record.getValue_2();
                        str += record.getValue_3();
                        str += record.getValue_4();
                        str += ");";
                    }
                    SocketConnection.getInstance().sendMessage(str);
                }
            };

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