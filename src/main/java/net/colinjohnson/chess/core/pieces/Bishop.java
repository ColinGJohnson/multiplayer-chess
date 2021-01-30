package net.colinjohnson.chess.core.pieces;

import net.colinjohnson.chess.core.*;

import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(ChessColor color, ChessPosition position) {
        super(color, position);
    }

    @Override
    public int getValue() {
        return 350;
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board) {
        return null;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public ChessPiece clone() {
        return new Bishop(getColor(), getPosition());
    }
}
