package dev.cgj.chess;

import dev.cgj.chess.client.ChessClient;
import dev.cgj.chess.server.ChessServer;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "Multiplayer Chess", mixinStandardHelpOptions = true)
public class ChessLauncher implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new ChessLauncher()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Use '-h' or '--help' to see usage details.");
    }

    @Command(name = "client", description = "Launch the Chess application as a client.")
    void client(@Option(names = {"-a", "--address"}, required = true) String address,
                @Option(names = {"-p", "--port"}, required = true) int port) {
        System.out.println("Starting as Client.");
        System.out.println("Connecting to server at " + address + ":" + port);
        new ChessClient(address, port);
    }

    @Command(name = "server", description = "Launch the Chess application as a server.")
    void server(@Option(names = {"-p", "--port"}, required = true) int port) {
        System.out.println("Starting as Server.");
        new ChessServer(port);
    }
}
