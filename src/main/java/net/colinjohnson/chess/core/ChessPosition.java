package net.colinjohnson.chess.core;

import net.colinjohnson.chess.core.pieces.ChessPiece;

import java.util.regex.Pattern;

/**
 * Class representing the position of a single chess piece.
 */
public class ChessPosition {

    // the 0-indexed column of the chess board
    private int file;

    // the 0-indexed row of the chess board
    private int rank;

    public ChessPosition(int rank, int file) {
        setRank(rank);
        setFile(file);
    }

    public ChessPosition(String algebraic) {
        setAlgebraic(algebraic);
    }

    public ChessPosition(ChessPosition chessPosition) {
        setRank(chessPosition.getRank());
        setFile(chessPosition.getFile());
    }

    public ChessPosition(ChessPosition chessPosition, int rankChange, int fileChange) {
        setRank(chessPosition.getRank() + rankChange);
        setFile(chessPosition.getFile() + fileChange);
    }

    /**
     * Encode this position in FIDE standard algebraic chess notation.
     * @return a string containing a chess board in algebraic notation
     */
    public String getAlgebraic() {
        return new String(new char[]{ (char)(file + 97), (char)(rank + 49) });
    }

    /**
     * Set the position using FIDE standard algebraic chess notation.
     * @param algebraic a 2-character string containing algebraic chess notation e.g. "d2"
     */
    public void setAlgebraic(String algebraic) {
        algebraic = algebraic.toLowerCase();
        if (!Pattern.matches("^[a-h][1-8]$", algebraic)) {
            throw new IllegalArgumentException("Invalid algebraic chess notation");
        }

        // decimal values of 'a' and '1' are 97 and 47 respectively.
        file = ((int)algebraic.charAt(0) - 97);
        rank = ((int)algebraic.charAt(1) - 49);
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        if (rank < 0 || rank > 7) {
            throw new IllegalArgumentException("Rank must be in the range [0, 7]");
        } else {
            this.rank = rank;
        }
    }

    public int getFile() {
        return file;
    }

    public void setFile(int file) {
        if (file < 0 || file > 7) {
            throw new IllegalArgumentException("Rank must be in the range [0, 7]");
        } else {
            this.file = file;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChessPosition chessPosition = (ChessPosition)o;
        return file == chessPosition.getFile() && rank == chessPosition.getRank();
    }

    public ChessPosition rotateCW(int n) {
        for (int i = 0; i < n; i++) {
            int rotatedRank = file;
            int rotatedFile = ChessBoard.BOARD_SIZE - 1 - rank;
            setRank(rotatedRank);
            setFile(rotatedFile);
        }
        return this;
    }

    public ChessPosition rotateCCW(int n) {
        // TODO: This is lazy and inefficient
        for (int i = 0; i < 3; i++) {
            rotateCW(n);
        }
        return this;
    }
}
