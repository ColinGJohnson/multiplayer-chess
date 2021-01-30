package net.colinjohnson.chess.core.pieces;

import net.colinjohnson.chess.core.*;

import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(ChessColor color, ChessPosition position) {
        super(color, position);
    }

    @Override
    public int getValue() {
        return 100;
    }

    @Override
    public List<ChessMove> getMoves(ChessBoard board) {

        // pawns can always move forward if there is an unoccupied square in front of them
//        if (color == ChessColor.white && position.getRank() + 1 < ChessBoard.SIZE
//                && board.getPieceAt(position.getRank(), position.getFile()) == null) {
//            validMoves.add(new ChessMove(position, position.getRank() + 1,
//                    position.getFile()));
//        } else {
//            if (position.getRank() > 0) {
//                validMoves.add(new ChessMove(position, position.getRank() - 1,
//                        position.getFile()));
//            }
//        }

        // taking a piece

        // en passent

        return null;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public ChessPiece clone() {
        return new Pawn(getColor(), getPosition());
    }
}
