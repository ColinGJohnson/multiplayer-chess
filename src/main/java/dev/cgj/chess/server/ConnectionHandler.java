package dev.cgj.chess.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

public class ConnectionHandler implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionHandler.class);

    private final ChessServer chessServer;
    private final ServerSocket socket;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();

    public ConnectionHandler(ChessServer chessServer, int serverPort) {
        this.chessServer = chessServer;
        try {
            socket = new ServerSocket(serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Failed to start server socket.");
        }
        LOG.info("Opened TCP port {}", serverPort);

        Timer connectionMonitor = new Timer();
        connectionMonitor.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                checkConnections();
            }
        }, new Date(), 1000);
    }

    @Override
    public void run() {
        try {
            while (true) {
                clients.add(new ClientHandler(socket.accept()));
                chessServer.broadcast("Client at %s connected, current connections: %d.".formatted(
                    clients.getLast().address.substring(1), clients.size()));
            }
        } catch (IOException e) {
            LOG.error("Failed to add client", e);
        } finally {
            close();
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
                chessServer.broadcast("Client at " + client.address + " has disconnected.");
                LOG.info("Current connections: {}", clients.size());
            }
        }
    }

    public ArrayList<ClientHandler> getClients() {
        return clients;
    }
}
