package net.colinjohnson.chess.core.pieces;

import net.colinjohnson.chess.core.*;

import java.util.List;

public class King extends ChessPiece {

    public King(ChessColor color, ChessPosition position) {
        super(color, position);
    }

    @Override
    public int getValue() {
        return 10000;
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board) {
        return null;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public ChessPiece clone() {
        return new King(getColor(), getPosition());
    }
}
