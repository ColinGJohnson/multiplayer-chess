package dev.cgj.chess.server;

import dev.cgj.chess.engine.ChessGame;
import dev.cgj.chess.ui.BoardUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ChessServer {
    private static final Logger LOG = LoggerFactory.getLogger(ChessServer.class);
    private final ConnectionHandler connectionHandler;

    boolean serverOpen;

    public ChessServer(int serverPort) {

        // start a new thread to handle incoming client connections
        connectionHandler = new ConnectionHandler(this, serverPort);
        new Thread(connectionHandler).start();

        // the server is now open
        serverOpen = true;

        // keep starting new games until the server is closed
        while (serverOpen) {
            playGame();
        }
    }

    public void playGame() {
        ArrayList<ClientHandler> clients = connectionHandler.getClients();

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
        LOG.info("[Broadcast] \"{}\"", message);
        for (ClientHandler client : connectionHandler.getClients()) {
            client.sendMessage(message, false);
        }
    }
}
