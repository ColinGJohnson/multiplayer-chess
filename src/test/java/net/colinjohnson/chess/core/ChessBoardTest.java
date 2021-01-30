package net.colinjohnson.chess.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

class ChessBoardTest {
    ChessBoard chessBoard;

    @BeforeEach
    void setUp() {
        chessBoard = new ChessBoard();
    }

    @Test
    void toJSON() throws IOException {
        String fileName = "test_layout_1.json";
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(fileName);
        assert url != null;
        File file = new File(url.getFile());
        String content = new String(Files.readAllBytes(file.toPath()));
        System.out.println(content);
        ChessBoard board = ChessBoard.fromJSON(content);
    }
}
