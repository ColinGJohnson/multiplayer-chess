package net.colinjohnson.chess.core.pieces;

import net.colinjohnson.chess.core.*;

import java.util.List;

public class Queen extends ChessPiece {

    public Queen(ChessColor color, ChessPosition position) {
        super(color, position);
    }

    @Override
    public int getValue() {
        return 1000;
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board) {
        return null;
    }


    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public ChessPiece clone() {
        return new Queen(getColor(), getPosition());
    }
}
