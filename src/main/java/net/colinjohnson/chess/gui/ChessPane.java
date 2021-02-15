package net.colinjohnson.chess.gui;

import javafx.scene.layout.GridPane;
import net.colinjohnson.chess.core.ChessBoard;
import net.colinjohnson.chess.core.ChessBoardObserver;
import net.colinjohnson.chess.core.ChessColor;
import net.colinjohnson.chess.core.pieces.ChessPiece;

/**
 * View for the chessboard model.
 */
public class ChessPane extends GridPane implements ChessBoardObserver {

    // references to all of the chess squares for easy access
    private ChessSquare[][] chessSquares;

    public ChessPane (ChessBoard board) {

        //setStyle("-fx-border-color: black");
        setPrefHeight(400);
        setPrefWidth(400);

        // build 8x8 grid of ChessSquare controls
        chessSquares = new ChessSquare[ChessBoard.BOARD_SIZE][ChessBoard.BOARD_SIZE];
        for (int rank = 0; rank < ChessBoard.BOARD_SIZE; rank++) {
            for (int file = 0; file < ChessBoard.BOARD_SIZE; file++) {
                ChessColor squareColor;
                if ((rank * (ChessBoard.BOARD_SIZE - 1) + file) % 2 == 0) {
                    squareColor = ChessColor.WHITE;
                } else {
                    squareColor = ChessColor.BLACK;
                }
                ChessSquare square = new ChessSquare(squareColor);
                add(square, file, rank);
                chessSquares[rank][file] = square;
            }
        }

        // register this ChessPane as an observer of the ChessBoard
        board.registerObserver(this);

        // force an initial board update
        onBoardUpdate(board);
    }

    @Override
    public void onBoardUpdate(ChessBoard board) {
        for (int rank = 0; rank < ChessBoard.BOARD_SIZE; rank++) {
            for (int file = 0; file < ChessBoard.BOARD_SIZE; file++) {
                ChessPiece piece = board.getPieceAt(rank, file);
                if (piece != null) {
                    chessSquares[rank][file].setDisplayedPiece(piece);
                }
            }
        }
    }
}
