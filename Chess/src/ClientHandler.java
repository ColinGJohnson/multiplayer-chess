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
    private String name;

    int timeout = 1500;
    long lastCheckIn = -1;
    private Thread listen;
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

        // thread to listen for client messages separately from other tasks
        listen = new Thread() {
            @Override
            public void run() {

                // continue getting input from the client as long as it is connected
                while (connected) {

                    // try to get read a line from the client
                    try {

                        // read a line from the connected client
                        String line = "";
                        line = input.readLine();

                        // if we are here the read succeeded
                        readFail = false;

                        // handle input
                        if (line != null && line.length() != 0) {

                            // check if line matches regex for move codes
                            if(line.matches("(([a-hA-H][1-8]){2})")){
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
                        if (commandQueue.get(0).length() >= 1 && commandQueue.get(0).charAt(0) == '!') {
                            lastCheckIn = System.currentTimeMillis();
                        } else {
                            System.out.println("[Client " + name + "] Command \"" + commandQueue.get(0) + "\" unknown.");
                        }
                        commandQueue.remove(0);
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

    public String nextCommand(){
        if(!commandQueue.isEmpty()){
            String s = commandQueue.get(0);
            commandQueue.remove(0);
            return s;
        }
        return "";
    }

    public String nextMove(){
        if(moveQueue.size() > 0){
            String s = moveQueue.get(0);
            moveQueue.remove(0);
            return s;
        }
        return null;
    }

    /**
     * Sends a message to this client if it is currently connected.
     * @param whisper true if this message is being sent only to this client, false if this message is a broadcast to
     *                multiple users.
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

    public void setName(String name) {
        this.name = name;
    }
} // Client
