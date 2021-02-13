package net.colinjohnson.chess.core.pieces;

import net.colinjohnson.chess.core.*;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(ChessColor color, ChessPosition position) {
        super(color, position);
    }

    @Override
    public int getValue() {
        return 500;
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board) {
        // TODO: Test this

        if (!board.containsPiece(this)) {
            throw new IllegalArgumentException("Piece not on the given board.");
        }

        List<ChessMove> moves = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ChessBoard rotatedBoard = new ChessBoard(board).rotateCW(i);
            ChessPosition rotatedPosition = new ChessPosition(getPosition()).rotateCW(i);
            List<ChessPosition> rotatedDestinations = new ArrayList<>();

            for (int j = 0; j < ChessBoard.BOARD_SIZE - rotatedPosition.getFile(); j++) {
                ChessPosition candidate = new ChessPosition(rotatedPosition, 0, j);
                ChessPiece occupant = rotatedBoard.getPieceAt(candidate);

                // can't move past a piece of the same color
                if (occupant.getColor() == getColor()) break;

                rotatedDestinations.add(candidate);

                // can't move past a piece without taking it
                if (occupant != null) break;
            }

            for (ChessPosition position : rotatedDestinations) {
                moves.add(new ChessMove(position.rotateCCW(i), getPosition()));
            }
        }

        return null;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.ROOK;
    }

    @Override
    public ChessPiece clone() {
        return new Rook(getColor(), getPosition());
    }
}
