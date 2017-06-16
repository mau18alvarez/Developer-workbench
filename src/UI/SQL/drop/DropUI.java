package UI.SQL.drop;


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
public class DropUI implements Initializable {

    @FXML Button btnReady;
    @FXML TextField drop_name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnReady.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                String table_drop_name = String.valueOf(drop_name.getText());
                System.out.println(table_drop_name);

                //Close popup window
                Stage stage = (Stage) btnReady.getScene().getWindow();
                stage.close();

            }
        });
    }
}
