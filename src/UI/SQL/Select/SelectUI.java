package UI.SQL.Select;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mauri on 15-Jun-17.
 */
public class SelectUI implements Initializable {

    @FXML TextField select_txtfield;
    @FXML TextField from_txtfield;
    @FXML TextField where_txtfield;
    @FXML Button btnReady;
    @FXML Button cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnReady.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String select_name = String.valueOf(select_txtfield.getText());
                String from_name = String.valueOf(from_txtfield.getText());
                String where_name = String.valueOf(where_txtfield.getText());
                System.out.println(select_name + from_name + where_name);

                // //char* sql = "SELECT ID, NOMBRE FROM ESTUDIANTES WHERE ESTUDIANTES.ID=1;";
                // //char* sql2 ="SELECT Orders.OrderID, Customers.CustomerName, Orders.OrderDate FROM Orders JOIN Customers ON

                //Close popup window
                Stage stage = (Stage) btnReady.getScene().getWindow();
                stage.close();

            }
        });

        EventHandler<ActionEvent> cancelBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {
                        Stage stage = (Stage) btnReady.getScene().getWindow();
                        stage.close();
                    }
                };
        cancelBtn.setOnAction(cancelBtnHandler);
    }
}