package dev.cgj.chess;

import dev.cgj.chess.client.ChessClient;
import dev.cgj.chess.server.ChessServer;

import java.util.Scanner;

public class ChessLauncher {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        if (args.length != 1){
            System.out.println("Incorrect number of arguments. Provide arguments \"-c\" for Client or \"-s\" for Server.");
            System.exit(-1);

        } else if (args[0].equals("-c")){
            System.out.println("Starting as Client.");
            System.out.print("Enter server address:");
            String address = scanner.nextLine();
            System.out.print("Enter server port:");
            int port = Integer.parseInt(scanner.nextLine());
            new ChessClient(address, port);

        } else if (args[0].equals("-s")){
            System.out.println("Starting as Server.");
            System.out.print("Enter port:");
            int port = Integer.parseInt(scanner.nextLine());
            new ChessServer(port);
        }
    }
}
