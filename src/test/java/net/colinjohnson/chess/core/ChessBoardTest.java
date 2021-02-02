package net.colinjohnson.chess.core;

import net.colinjohnson.chess.core.pieces.ChessPiece;
import net.colinjohnson.chess.core.pieces.Pawn;
import org.junit.jupiter.api.Assertions;
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
    void testJsonConversion() throws IOException {

        // load sample layout
        String fileName = "layouts/test_layout_1.json";
        URL url = getClass().getClassLoader().getResource(fileName);
        String json = new String(Files.readAllBytes(new File(url.getFile()).toPath()));

        // convert json to a board
        ChessBoard board = ChessBoard.fromJSON(json);

        // convert the board back to json
        // TODO: make sure this is the same as the original JSON
        System.out.println(board.toJSON());
    }

    @Test
    void testRotation() {
        chessBoard.addPiece(new Pawn(ChessColor.BLACK, new ChessPosition(0, 0)));
        Assertions.assertNotNull(chessBoard.getPieceAt(0, 0));
        chessBoard.rotateCW(1);
        Assertions.assertNull(chessBoard.getPieceAt(0, 0));
        ChessPiece rotatedPiece = chessBoard.getPieceAt(0, 7);
        Assertions.assertNotNull(rotatedPiece);
        Assertions.assertEquals(0, rotatedPiece.getPosition().getRank());
        Assertions.assertEquals(7, rotatedPiece.getPosition().getFile());
        chessBoard.clearBoard();
    }
}
