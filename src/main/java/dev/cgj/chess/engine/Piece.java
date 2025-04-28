package dev.cgj.chess.engine;

import java.awt.Point;

/**
 * Inner class representing a Piece owned by a specific player
 */
public class Piece {
    Player player;
    PieceType piece;
    Point location; // TODO: does not update

    public Piece() {
        this.player = new Player(false, "placeHolder");
        this.piece = PieceType.EMPTY;
    }

    public Piece(Player player, PieceType piece) {
        this.player = player;
        this.piece = piece;
    }
}
