import java.util.Scanner;

public class ConsoleWindow {
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);

        if (args.length != 1){
            System.out.println("Incorrect number of arguments.");
            System.exit(-1);
        } else if (args[0].equals("-c")){
            System.out.println("Starting as Client.");
            System.out.print("Enter server address:");
            String address = scanner.nextLine();
            System.out.print("Enter server port:");
            int port = Integer.parseInt(scanner.nextLine());
            ChessClient client = new ChessClient(address, port);
        } else if (args[0].equals("-s")){
            System.out.println("Starting as Server.");
            System.out.print("Enter port:");
            int port = Integer.parseInt(scanner.nextLine());
            ChessServer server = new ChessServer(port);
        } else {
            System.out.println("Provide arguments \"-c\" for Client \"-s\" for Server.");
        }
    }
} // ConsoleWindow