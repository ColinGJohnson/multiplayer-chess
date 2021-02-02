package net.colinjohnson.chess.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.colinjohnson.chess.core.ChessGame;

public class ChessClient {
    ChessGame gameState;
    String serverIP;
    String serverPort;
    Socket clientSocket;

    public ChessClient(String serverIP, String serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    /**
     * Fetches the current game state from the server.
     */
    public void updateGameState() {

    }

    /**
     * Requests to the server to make a move.
     */
    public void executeMove() {

    }

    public void connect(String serverAddress, int serverPort) throws IOException {
        clientSocket = new Socket(serverAddress, serverPort);
    }

    public void disconnect() {

    }
}
