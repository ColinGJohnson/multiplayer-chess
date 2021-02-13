package net.colinjohnson.chess.gui;

import javafx.scene.layout.GridPane;
import net.colinjohnson.chess.core.ChessBoard;
import net.colinjohnson.chess.core.ChessBoardObserver;
import net.colinjohnson.chess.core.ChessColor;


/**
 * View for the chessboard model.
 */
public class ChessPane extends GridPane implements ChessBoardObserver {

    public ChessPane (ChessBoard board) {

        setStyle("-fx-border-color: black");
        setPrefHeight(400);
        setPrefWidth(400);

        // build 8x8 grid of ChessButtons
        for (int rank = 0; rank < ChessBoard.BOARD_SIZE; rank++) {
            for (int file = 0; file < ChessBoard.BOARD_SIZE; file++) {
                ChessColor squareColor;
                if ((rank * (ChessBoard.BOARD_SIZE - 1) + file) % 2 == 0) {
                    squareColor = ChessColor.WHITE;
                } else {
                    squareColor = ChessColor.BLACK;
                }
                add(new ChessSquare(squareColor), file, rank);
            }
        }

        // register this ChessPane as an observer of the ChessBoard
        board.registerObserver(this);
    }

    @Override
    public void onBoardUpdate(ChessBoard board) {

    }
}
