package dev.cgj.chess.engine;

import java.util.ArrayList;
import java.util.List;

import static dev.cgj.chess.engine.PieceColor.BLACK;
import static dev.cgj.chess.engine.PieceColor.WHITE;

public class Board {
    public static final int SIZE = 8;
    public static final Board EMPTY = new Board(empty());
    public static final Board STANDARD = new Board(standard());

    /**
     * row-major array representation of chess board
     */
    private final Piece[][] pieces;

    private Board(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public Piece get(Coordinate coordinate) {
        return pieces[coordinate.rank()][coordinate.file()];
    }

    /**
     * Creates a new board containing the result of applying a move to this board.
     *
     * @param move The move to apply to this board.
     * @return The new board if the move was legal, or an empty optional otherwise.
     */
    public Board move(Move move) {
        Piece[][] piecesCopy = clonePieces();

        piecesCopy[move.finish().rank()][move.finish().file()] = pieces[move.start().rank()][move.start().file()];
        piecesCopy[move.start().rank()][move.start().file()] = Piece.EMPTY;

        // if method has proceeded to this point, the move must have been successful
        return new Board(piecesCopy);
    }

    public List<Coordinate> getPiecesOfColor(PieceColor color) {
        List<Coordinate> locations = new ArrayList<>();
        for (int r = 0; r < this.pieces.length; r++) {
            for (int c = 0; c < this.pieces[0].length; c++) {
                if (this.pieces[r][c].color() == color) {
                    locations.add(new Coordinate(r, c));
                }
            }
        }
        return locations;
    }

    /**
     * Fills the game board with empty tiles.
     */
    private static Piece[][] empty() {
        Piece[][] pieces = new Piece[SIZE][SIZE];
        for (int r = 0; r < pieces.length; r++) {
            for (int c = 0; c < pieces[0].length; c++) {
                pieces[r][c] = Piece.EMPTY;
            }
        }
        return pieces;
    }

    /**
     * Resets the game board to its initial state with all pieces in the correct positions
     */
    public static Piece[][] standard() {
        Piece[][] pieces = empty();

        for (int c = 0; c < pieces[1].length; c++) {
            pieces[1][c] = new Piece(PieceType.PAWN, BLACK);
            pieces[6][c] = new Piece(PieceType.PAWN, WHITE);
        }

        pieces[0] = new Piece[]{
            new Piece(PieceType.ROOK, BLACK),
            new Piece(PieceType.KNIGHT, BLACK),
            new Piece(PieceType.BISHOP, BLACK),
            new Piece(PieceType.KING, BLACK),
            new Piece(PieceType.QUEEN, BLACK),
            new Piece(PieceType.BISHOP, BLACK),
            new Piece(PieceType.KNIGHT, BLACK),
            new Piece(PieceType.ROOK, BLACK)
        };

        pieces[7] = new Piece[]{
            new Piece(PieceType.ROOK, WHITE),
            new Piece(PieceType.KNIGHT, WHITE),
            new Piece(PieceType.BISHOP, WHITE),
            new Piece(PieceType.KING, WHITE),
            new Piece(PieceType.QUEEN, WHITE),
            new Piece(PieceType.BISHOP, WHITE),
            new Piece(PieceType.KNIGHT, WHITE),
            new Piece(PieceType.ROOK, WHITE)
        };

        return pieces;
    }

    private Piece[][] clonePieces() {
        Piece[][] clone = new Piece[SIZE][SIZE];
        for (int r = 0; r < pieces.length; r++) {
            System.arraycopy(pieces[r], 0, clone[r], 0, pieces[0].length);
        }
        return clone;
    }
}
