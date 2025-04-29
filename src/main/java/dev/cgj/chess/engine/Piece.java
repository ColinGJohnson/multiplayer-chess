package dev.cgj.chess.engine;

import static dev.cgj.chess.engine.PieceColor.WHITE;

public record Piece(PieceType piece, PieceColor color) {
    public static final Piece EMPTY = new Piece(PieceType.EMPTY, null);

    /**
     * Generates a string representation of a given Piece object.
     * White pieces are lowercase and black pieces are uppercase.
     *
     * @return A symbol for the given piece.
     */
    public String symbol() {
        String conversion = switch (piece) {
            case PAWN -> "P";
            case ROOK -> "R";
            case KNIGHT -> "N";
            case BISHOP -> "B";
            case KING -> "K";
            case QUEEN -> "Q";
            case EMPTY -> "_";
        };

        return color == WHITE ? conversion : conversion.toLowerCase();
    }
}
