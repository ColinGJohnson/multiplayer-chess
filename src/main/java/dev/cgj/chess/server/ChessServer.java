package dev.cgj.chess.server;

import dev.cgj.chess.engine.ChessGame;
import dev.cgj.chess.ui.BoardUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ChessServer {
    boolean serverOpen;
    private ServerSocket socket = null;
    private final ArrayList<ClientHandler> clients = new ArrayList<>();

    public ChessServer(int ServerPort) {
        try {
            socket = new ServerSocket(ServerPort);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error starting server socket.");
            System.exit(0);
        }
        System.out.println("Server started on port " + ServerPort);

        // start a new thread to handle incoming client connections
        new Thread(() -> {
            try {
                while (true) {
                    clients.add(new ClientHandler(socket.accept()));
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
        }).start();

        // start a new thread to watch for server console input
        new Thread(() -> {
            String s;
            Scanner scanner = new Scanner(System.in);

            while (true) {
                s = scanner.nextLine();
                if (!s.isEmpty() && s.toLowerCase().trim().startsWith("\\broadcast")) {
                    broadcast(s.replace("\\broadcast", "").trim());
                }
            }
        }).start();

        Timer connectionMonitor = new Timer();
        connectionMonitor.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                checkConnections();
            }
        }, new Date(), 1000);

        // the server is now open
        serverOpen = true;

        // keep starting new games until the server is closed
        while (serverOpen) {
            playGame();
        }

        // end the program when the server is closed
        System.exit(0);
    }

    public void playGame() {

        // wait for players
        broadcast("Waiting for players... " + clients.size() + "/2");
        ClientHandler whiteClient;
        ClientHandler blackClient;

        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // start a game between the first two clients connected
            if (clients.size() >= 2) {
                whiteClient = clients.get(0);
                blackClient = clients.get(1);
                break;
            }
        }

        // start a new chess game
        ChessGame game = new ChessGame(clients.get(0).getName(), clients.get(1).getName());
        whiteClient.sendMessage("Your pieces are white (lowercase)", true);
        blackClient.sendMessage("Your pieces are black (uppercase)", true);

        // boolean value to track whose move it is
        boolean whiteMove = true;

        // game loop
        while (serverOpen && !game.over) {

            // send board to players
            if (whiteMove) {
                broadcast(BoardUtils.textBoard(game.board) + "\nWhite's move.");
            } else {
                broadcast(BoardUtils.textBoard(game.board) + "\nBlack's move.");
            }

            // get input from player until valid move is provided
            String message = "";
            boolean moveSuccess = false;
            do {

                // end game if one of the clients ends their connection
                if (!whiteClient.connected || !blackClient.connected) {
                    game.over = true;
                    break;
                }

                // wait a while before trying again
                // TODO: Find out why things don't work if wait is removed. (race condition?)
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // get next move from client, restart loop if no pending move
                if (whiteMove) {
                    message = whiteClient.nextMove();
                } else {
                    message = blackClient.nextMove();
                }
                if (message == null) continue;

                // attempt to execute move
                moveSuccess = game.move(whiteMove, message);
            } while (!moveSuccess);

            // change active player
            whiteMove = !whiteMove;
        }

        broadcast("The game has ended, returning to lobby!");
    }

    public void broadcast(String message) {
        System.out.println("[Broadcast] \"" + message + "\"");
        for (ClientHandler client : clients) {
            client.sendMessage(message, false);
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
                broadcast("Client at " + client.address + " has disconnected.");
                System.out.println("Current connections: " + clients.size());
            }
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
