package UI.SQL.drop;


import Networking.Socket.SocketConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by mauri on 15-Jun-17.
 */
public class DropUI implements Initializable {

    @FXML Button btnReady;
    @FXML TextField drop_name;
    @FXML Button cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnReady.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(!isEmpty(drop_name.getText())) {
                    String table_drop_name = String.valueOf(drop_name.getText());
                    SocketConnection.getInstance().sendMessage(table_drop_name);
                    //Close popup window
                    Stage stage = (Stage) btnReady.getScene().getWindow();
                    stage.close();
                }else{
                    JOptionPane.showMessageDialog(null, "You need to fill all spaces" , "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
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

    private boolean isEmpty(String str){
        if(!str.matches("") && !str.matches(" ") && !str.matches("-")){
            return false;
        }
        return true;
    }
}
