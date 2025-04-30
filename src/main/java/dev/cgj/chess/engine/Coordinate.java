package dev.cgj.chess.engine;

public record Coordinate(int rank, int file) {
    public Coordinate {
        if (rank < 0 || rank > 7) {
            throw new IllegalArgumentException("Rank must be between 0 and 7");
        }
        if (file < 0 || file > 7) {
            throw new IllegalArgumentException("File must be between 0 and 7");
        }
    }
}
