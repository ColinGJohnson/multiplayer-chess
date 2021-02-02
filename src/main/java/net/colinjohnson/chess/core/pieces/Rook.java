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
        int currentRank = getPosition().getRank();
        int currentFile = getPosition().getFile();
        List<ChessMove> moves = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ChessBoard rotatedBoard = new ChessBoard(board).rotateCW(i);
            ChessPosition rotatedPosition = new ChessPosition(getPosition()).rotateCW(i);
            List<ChessMove> rotatedMoves = new ArrayList<>();

            for (int j = 0; j < ChessBoard.BOARD_SIZE - currentFile; j++) {
                ChessMove candidate = new ChessMove(getPosition(), 0, j);
                ChessPiece occupant = board.getPieceAt(candidate.to);

                // can't move past a piece of the same color
                if (occupant.getColor() == getColor()) {
                    break;
                }

                rotatedMoves.add(candidate);

                // can't move past a piece without taking it
                if (occupant != null) {
                    break;
                }
            }

            for (ChessMove move : rotatedMoves) {
                moves.add(move.rotateCCW(i));
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
