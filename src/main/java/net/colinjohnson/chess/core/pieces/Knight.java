package net.colinjohnson.chess.core.pieces;

import net.colinjohnson.chess.core.*;

import java.util.List;

public class Knight extends ChessPiece {

    public Knight(ChessColor color, ChessPosition position) {
        super(color, position);
    }

    @Override
    public int getValue() {
        return 300;
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board) {

        // up-left

        // up-right

        // right-up

        // right-down

        // down-right

        // down-left

        // left-down

        // left-up

        return null;
    }


    @Override
    public PieceType getPieceType() {
        return PieceType.KNIGHT;
    }

    @Override
    public ChessPiece clone() {
        return new Knight(getColor(), getPosition());
    }
}
