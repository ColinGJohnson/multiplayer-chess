package net.colinjohnson.chess.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.colinjohnson.chess.core.ChessBoard;
import net.colinjohnson.chess.networking.ChessClient;
import net.colinjohnson.chess.networking.ChessServer;

import java.io.IOException;

public class ChessApp extends Application {
    private static final int MIN_WIDTH = 600;
    private static final int MIN_HEIGHT = 400;

    ChessClient client = null;
    ChessServer server = null;

    public boolean isHost() {
        return server != null;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = FXMLLoader.load(getClass().getResource("/javafx/scene.fxml"));

        root.setCenter(new ChessPane(new ChessBoard()));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/javafx/styles.css").toExternalForm());

        stage.setTitle("Multiplayer Chess");
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setScene(scene);
        stage.show();
    }
}
