package net.colinjohnson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ChessClient {
    private String serverAddress = "0.0.0.0";
    private int serverPort = 1500;
    private boolean connected = false;
    Socket clientSocket = null;
    BufferedReader input = null;
    PrintWriter output = null;
    ArrayList<String> messageQueue = new ArrayList<String>();
    Thread listen = new Thread();

    private void play() {
        if (!connected) {
            System.out.println("No connection to play on.");
            return;
        }
    } // play

    private void connect(String serverAddress, int serverPort) {
        try {
            clientSocket = new Socket(serverAddress, serverPort);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[!] Connection to Server failed.");
            return;
        }

        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[!] Server communication error.");
            return;
        }

        System.out.println(String.format("connected to server at %s:%s", serverAddress, serverPort));
        this.serverPort = serverPort;
        this.serverAddress = serverAddress;
        connected = true;

        listen = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String line = "";
                        line = input.readLine();
                        if (line != "" && line != null && line != "\n") {
                            messageQueue.add(line);
                        }
                    } catch (IOException e) {
                        //e.printStackTrace();
                        System.out.println("[!] Failed to read from server.");
                        disconnect();
                    }
                    if (!messageQueue.isEmpty()) {
                        System.out.println("[S] " + messageQueue.get(0));
                        messageQueue.remove(0);
                    }
                }
            }
        };
        listen.start();

        Timer serverCheckIn = new Timer();
        serverCheckIn.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                output.println("!" + System.currentTimeMillis());
            }
        }, new Date(), 1000);
    } // connect

    private void disconnect() {
        if (output == null || input == null || clientSocket == null) {
            System.out.println("[!] No connection from which to disconnect.");
            return;
        }

        try {
            output.close();
            input.close();
            clientSocket.close();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("[!] Could not disconnect from server");
            return;
        }
        System.out.println("Disconnected from server.");
        connected = false;
    } // disconnect

    public static void main(String[] args) {
        ConsoleWindow window = new ConsoleWindow();

        ChessClient client = new ChessClient();
        client.connect("localhost", 1500);
        client.play();
    }
} // ChessClient