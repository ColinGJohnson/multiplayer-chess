package net.colinjohnson.chess.core;

/**
 * Class representing a single piece's movement from once position to another.
 */
public class ChessMove {
    public ChessPosition to;
    public ChessPosition from;

    public ChessMove(ChessPosition to, ChessPosition from) {
        this.to = to;
        this.from = from;
    }

    public ChessMove(ChessPosition from, int rankChange, int fileChange) {
        this.to = new ChessPosition(from.getRank() + rankChange, from.getFile() + fileChange);
        this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChessMove chessMove = (ChessMove)o;
        return to == chessMove.to && from == chessMove.from;
    }

    public ChessMove rotateCCW(int i) {
        // TODO: method stub
        return this;
    }
}
