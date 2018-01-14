package net.colinjohnson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {
    boolean connected = false;
    boolean readFail = false;
    Socket socket;
    BufferedReader input = null;
    PrintWriter output = null;
    String address = "";

    int timeout = 1500;
    long lastCheckIn = -1;
    private Thread listen;
    ArrayList<String> messageQueue = new ArrayList<String>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
        connected = true;
        address = socket.getRemoteSocketAddress().toString();
        lastCheckIn = System.currentTimeMillis();

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        listen = new Thread() {
            @Override
            public void run() {
                while (connected) {
                    try {
                        String line = "";
                        line = input.readLine();
                        if (line != "" && line != null && line != "/n") {
                            messageQueue.add(line);
                        }
                        readFail = false;
                    } catch (IOException e) {
                        if (!readFail) {
                            System.out.println("[!] Failed to read from client.");
                            readFail = true;
                        }
                    }

                    if (!messageQueue.isEmpty()) {
                        if (messageQueue.get(0).charAt(0) == '!') {
                            lastCheckIn = System.currentTimeMillis();
                        }
                        messageQueue.remove(0);
                    }
                }
            }
        };
        listen.start();
    } // Client constructor

    public void close() {
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // close

    public boolean update() {
        long timeSinceCheckIn = System.currentTimeMillis() - lastCheckIn;
        if (timeSinceCheckIn > timeout) {
            System.out.println("Client at " + address + " timed out (" + timeSinceCheckIn + "ms).");
            connected = false;
        } else {
            connected = true;
        }
        return connected;
    } // update
} // Client
