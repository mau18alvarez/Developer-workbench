package LogIn;


import Networking.Socket.SocketConnection;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.Main;
import java.io.IOException;

/**
 * Created by mauri on 6/2/2017.
 */

public class LogInController {

    @FXML private TextField login_ip;
    @FXML private TextField login_port;

    public void onclickevent () throws IOException{

        Main.ip = login_ip.getText();
        Main.puerto = login_port.getText();

        SocketConnection.getInstance().initialize(login_ip.getText(),Integer.valueOf(login_port.getText()));

        Main.showMenu();

    }
}
