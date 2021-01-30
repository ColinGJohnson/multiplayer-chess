package net.colinjohnson.chess.core.pieces;

import net.colinjohnson.chess.core.*;

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
        // moving up

        // moving down

        // moving left

        // moving right

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
