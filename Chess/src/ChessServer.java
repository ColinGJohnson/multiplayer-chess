import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class ChessServer {
    private ServerSocket chessService = null;
    private ArrayList<ClientHandler> clients = new ArrayList<>();
    private Thread clientAdd;

    public ChessServer(int ServerPort) {
        try {
            chessService = new ServerSocket(ServerPort);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error starting server socket.");
            System.exit(0);
        }
        System.out.println("Server started on port " + ServerPort);

        // start a new thread to handle incoming client connections
        clientAdd = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        clients.add(new ClientHandler(chessService.accept()));
                        broadcast("Client at "
                                + clients.get(clients.size() - 1).address.substring(1)
                                + " connected, current connections: "
                                + clients.size()
                                + ".");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error adding client");
                } finally {
                    close();
                    System.exit(0);
                }
            }
        };
        clientAdd.start();

        Timer connectionMonitor = new Timer();
        connectionMonitor.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                checkConnections();
            }
        }, new Date(), 1000);
    } // ChessServer Constructor

    public void broadcast(String message) {
        System.out.println("[Broadcast] " + message);
        for (ClientHandler client : clients) {
            client.output.println(message);
        }
    } // broadcast

    public void checkConnections() {

        // update the clients' connection status
        for (ClientHandler client : clients) {
            client.update();
        }

        // iterate through this server's clients and remove disconnected clients
        Iterator<ClientHandler> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ClientHandler client = iterator.next();

            // if client is not connected, disconnect it
            if (!client.connected) {
                client.close();
                iterator.remove();
                broadcast("Client at " + client.address + " has disconnected.");
                System.out.println("Current connections: " + clients.size());
            }
        }
    } // checkConnection

    public void close() {
        try {
            chessService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // close

    public static void main(String[] args) {
        ChessServer server = new ChessServer(1500);
    }
} // ChessServer
