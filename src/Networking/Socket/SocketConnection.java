package Networking.Socket;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by josea on 6/8/2017.
 */

public class SocketConnection {

    private static SocketConnection instance;

    private String serverAddress;
    private int portNumber;
    private Socket socket;


    protected SocketConnection() {

    }

    public static SocketConnection getInstance() {
        if (instance == null) {
            instance = new SocketConnection();
        }
        return instance;
    }

    public void initialize(String ipAddress, int port) {
        this.serverAddress = ipAddress;
        this.portNumber = port;
        try {
            this.socket = new Socket(serverAddress, portNumber);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }


    public void sendMessage(String msg) {
        try {
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(msg);
            out.flush();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public String request(String msg){
        try {
            PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
            out.println(msg);
            out.flush();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        String answer = "";
        try {
            BufferedReader input =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));
            answer = input.readLine();
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, e.toString(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return answer;
    }

}
