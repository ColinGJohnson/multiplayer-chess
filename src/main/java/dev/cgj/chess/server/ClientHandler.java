package dev.cgj.chess.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {
    boolean connected;
    boolean readFail = false;
    Socket socket;
    BufferedReader input = null;
    PrintWriter output = null;
    String address = "";
    private String name;

    int timeout = 1500;
    long lastCheckIn;
    ArrayList<String> commandQueue = new ArrayList<String>();
    ArrayList<String> moveQueue = new ArrayList<String>();

    public ClientHandler(Socket socket) {
        this.socket = socket;
        connected = true;
        address = socket.getRemoteSocketAddress().toString();
        name = address;
        lastCheckIn = System.currentTimeMillis();

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread listen = new Thread(() -> {

            // continue getting input from the client as long as it is connected
            while (connected) {

                // try to get read a line from the client
                try {

                    // read a line from the connected client
                    String line;
                    line = input.readLine();

                    // if we are here the read succeeded
                    readFail = false;

                    // handle input
                    if (line != null && !line.isEmpty()) {

                        // check if line matches regex for move codes
                        if (line.matches("(([a-hA-H][1-8]){2})")) {
                            System.out.println("[Client " + name + "] " + line);
                            moveQueue.add(line);
                            System.out.println(moveQueue.size());

                        // if this message is not a move, it must be a server command
                        } else {
                            commandQueue.add(line);
                        }
                    }

                // if reading from client failed eg. if the client disconnected, print a message and record failure
                } catch (IOException e) {
                    if (!readFail) {
                        System.out.println("[!] Failed to read from client.");
                        readFail = true;
                    }
                }

                // handle server commands
                if (!commandQueue.isEmpty()) {

                    // check in commands begin with '!'
                    if (!commandQueue.getFirst().isEmpty() && commandQueue.getFirst().charAt(0) == '!') {
                        lastCheckIn = System.currentTimeMillis();
                    } else {
                        System.out.println("[Client " + name + "] Command \"" + commandQueue.getFirst() + "\" unknown.");
                    }
                    commandQueue.removeFirst();
                }
            }
        });
        listen.start();
    }

    public void close() {
        try {
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        long timeSinceCheckIn = System.currentTimeMillis() - lastCheckIn;
        if (timeSinceCheckIn > timeout) {
            System.out.println("Client at " + address + " timed out (" + timeSinceCheckIn + "ms).");
            connected = false;
        } else {
            connected = true;
        }
    }

    public String nextMove() {
        if (!moveQueue.isEmpty()) {
            String s = moveQueue.getFirst();
            moveQueue.removeFirst();
            return s;
        }
        return null;
    }

    /**
     * Sends a message to this client if it is currently connected.
     *
     * @param whisper true if this message is being sent only to this client, false if this message
     *                is a broadcast to multiple users.
     */
    public void sendMessage(String message, boolean whisper) {
        if (connected) {
            if (whisper) {
                output.println("[W] \"" + message + "\"");
            } else {
                output.println("[B] \"" + message + "\"");
            }
        }
    }

    public String getName() {
        return name;
    }
}
