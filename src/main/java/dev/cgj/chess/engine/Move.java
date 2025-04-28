package dev.cgj.chess.engine;

import java.awt.Point;

class Move {
    Point start;
    Point finish;

    public Move(Point start, Point finish) {
        this.start = start;
        this.finish = finish;
    }

    /**
     * @param targetBoard the board to test the validity of this move on.
     * @return if the proposed move is valid on the given board
     */
    public boolean valid(Piece[][] targetBoard) {
        return true;
    }

    /**
     * @param targetBoard the board to test the effectiveness of this move on.
     * @return The value of this move, -1 if the move is invalid.
     */
    public int getScore(Piece[][] targetBoard) {
        if (!valid(targetBoard)) {
            return -1;
        } else {
            return 0;
        }
    }
}
