package UI.SQL.delete;

import Networking.Socket.SocketConnection;
import Tables.JavaFXDynTable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by josea on 6/15/2017.
 */
public class DeleteUI implements Initializable{

    @FXML
    Pane delPane;
    @FXML
    TextField tableName;
    @FXML
    TextField condAttr;
    @FXML
    TextField condVal;
    @FXML
    Button readyBtn;
    @FXML
    Button cancelBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EventHandler<ActionEvent> readyBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {
                        String msg = "DELETE FROM ";
                        if(!isEmpty(tableName.getText())){
                            msg += tableName.getText();
                        }else{
                            JOptionPane.showMessageDialog(null, "The Table's Name is required." , "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        if(!isEmpty(condAttr.getText()) && !isEmpty(condVal.getText())){
                            System.out.println("mierda: "+condAttr.getText()+" "+condVal.getText());
                            msg += " WHERE "+condAttr.getText() +"=";
                            try{
                                Float.valueOf(condVal.getText());
                                msg += condVal.getText()+";";
                            }catch (Exception e){
                                msg += "'"+condVal.getText()+"';";
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "A condition is required." , "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        System.out.println("Delete:"+msg);
                        String response = SocketConnection.getInstance().request(msg);
                        JOptionPane.showMessageDialog(null, response , "Response",
                                JOptionPane.PLAIN_MESSAGE);
                    }
                };
                readyBtn.setOnAction(readyBtnHandler);

        EventHandler<ActionEvent> cancelBtnHandler =
                new EventHandler<ActionEvent>(){

                    @Override
                    public void handle(ActionEvent t) {
                        Stage stage = (Stage) delPane.getScene().getWindow();
                        stage.close();
                    }
                };
                cancelBtn.setOnAction(cancelBtnHandler);

    }

    private boolean isEmpty(String str){
        if(!str.matches("") && !str.matches(" ")){
            return false;
        }
        return true;
    }
}
