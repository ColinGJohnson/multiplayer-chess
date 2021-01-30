package net.colinjohnson.chess.core.pieces;

import net.colinjohnson.chess.core.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a chess piece. Each chess piece is owned by a player and has a position on the
 * chess board.
 */
public abstract class ChessPiece implements Cloneable {

    private ChessColor color;
    private ChessPosition position;

    public enum PieceType {
        BISHOP,
        KING,
        KNIGHT,
        PAWN,
        QUEEN,
        ROOK
    }

    public ChessPiece(ChessColor color, ChessPosition position) {
        this.color = color;
        this.position = position;
    }

    public ChessPiece(ChessPiece chessPiece) {
        this.color = chessPiece.color;
        this.position = chessPiece.position;
    }

    public ChessPosition getPosition() {
        return position;
    }

    public void setPosition(ChessPosition position) {
        this.position = position;
    }

    public ChessColor getColor() {
        return color;
    }

    /**
     * Gets the material value of this piece for use in board evaluation.
     * @return A positive integer indicating this piece's value.
     */
    public abstract int getValue();

    /**
     * Get the legal moves this piece can make on the given board.
     *
     * @param board The board to find moves on.
     * @return A list of moves
     */
    public abstract List<ChessMove> getMoves(ChessBoard board);

    public abstract PieceType getPieceType();

    public abstract ChessPiece clone();
}
