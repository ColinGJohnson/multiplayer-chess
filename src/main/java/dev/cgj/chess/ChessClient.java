package dev.cgj.chess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
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
    Scanner scanner = new Scanner(System.in);

    public ChessClient (String serverAddress, int serverPort) {
        connect(serverAddress, serverPort);
        play();
    } // ChessClient constructor

    public ChessClient () {
        connect("localhost", 1500);
        play();
    } // default ChessClient constructor

    /**
     * Sends scanner input to the server until the current connection is terminated, if one exists.
     */
    private void play() {
        if (!connected) {
            System.out.println("No connection to play on.");
            return;
        } else {
            while (connected) {
                output.println(scanner.nextLine());
            }
            System.out.println("No connection to play on.");
        }
    } // play

    public void connect(String serverAddress, int serverPort) {
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

        System.out.println(String.format("Connected to server at %s:%s", serverAddress, serverPort));
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

                    // if the client has received messages, handle them.
                    if (!messageQueue.isEmpty()) {

                        // messages starting with '!' regard connection status
                        if (messageQueue.get(0).length() >= 1 && messageQueue.get(0).charAt(0) == '!') {
                            System.out.println("Connection Closed By Server. Reason: " + messageQueue.get(0).substring(1));
                            disconnect();

                            // other messages are meant to be displayed to the client eg. board appearance
                        } else {
                            System.out.println("[S] " + messageQueue.get(0));
                        }

                        // remove handled message
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
    }

    /**
     * Terminates the current connection to a chess server, if one exists.
     */
    private void disconnect() {
        System.out.println("Disconnecting from server...");
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
        System.exit(0);
    }
}