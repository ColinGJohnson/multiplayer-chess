package dev.cgj.chess.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dev.cgj.chess.engine.PieceColor.BLACK;
import static dev.cgj.chess.engine.PieceColor.WHITE;

public class Board {

    /**
     * row-major array representation of chess board
     */
    private final Piece[][] board = new Piece[8][8];

    public Piece get(Coordinate coordinate) {
        return board[coordinate.rank()][coordinate.file()];
    }

    /**
     * Creates a new board containing the result of applying a move to this board.
     *
     * @param move The move to apply to this board.
     * @return The new board if the move was legal, or an empty optional otherwise.
     */
    public Optional<Board> move(Move move) {

        // TODO: make board immutable
        board[move.finish().rank()][move.finish().file()] = board[move.start().rank()][move.start().file()];
        board[move.start().rank()][move.start().file()] = Piece.EMPTY;

        // if method has proceeded to this point, the move must have been successful
        return Optional.of(this);
    }

    public List<Coordinate> getPiecesOfColor(PieceColor color) {
        List<Coordinate> pieces = new ArrayList<>();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                if (board[r][c].color() == color) {
                    pieces.add(new Coordinate(r, c));
                }
            }
        }
        return pieces;
    }

    public String textBoard() {
        StringBuilder s = new StringBuilder();

        // top legend
        s.append("   ");
        for (int c = 0; c < board[0].length; c++) {
            s.append((char) ('a' + c)).append(" ");
        }
        s.append(" \n /-----------------\\\n");
        for (int r = 0; r < board.length; r++) {

            // left numbers
            s.append((board.length - r)).append("| ");

            // chess squares
            for (int c = 0; c < board[0].length; c++) {
                s.append(board[r][c].symbol()).append(" ");
            }

            // right numbers
            s.append("|").append(board.length - r);

            // jump to next row
            s.append("\n");
        }

        // bottom legend
        s.append(" \\-----------------/\n   ");
        for (int c = 0; c < board[0].length; c++) {
            s.append((char) ('a' + c)).append(" ");
        }
        s.append(" \n");
        return s.toString();
    }

    /**
     * Fills the game board with empty tiles.
     */
    private void clear() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = Piece.EMPTY;
            }
        }
    }

    /**
     * Resets the game board to its initial state with all pieces in the correct positions
     */
    public void reset() {
        clear();

        for (int c = 0; c < board[1].length; c++) {
            board[1][c] = new Piece(PieceType.PAWN, BLACK);
            board[6][c] = new Piece(PieceType.PAWN, WHITE);
        }

        board[0] = new Piece[]{
            new Piece(PieceType.ROOK, BLACK),
            new Piece(PieceType.KNIGHT, BLACK),
            new Piece(PieceType.BISHOP, BLACK),
            new Piece(PieceType.KING, BLACK),
            new Piece(PieceType.QUEEN, BLACK),
            new Piece(PieceType.BISHOP, BLACK),
            new Piece(PieceType.KNIGHT, BLACK),
            new Piece(PieceType.ROOK, BLACK)
        };

        board[7] = new Piece[]{
            new Piece(PieceType.ROOK, WHITE),
            new Piece(PieceType.KNIGHT, WHITE),
            new Piece(PieceType.BISHOP, WHITE),
            new Piece(PieceType.KING, WHITE),
            new Piece(PieceType.QUEEN, WHITE),
            new Piece(PieceType.BISHOP, WHITE),
            new Piece(PieceType.KNIGHT, WHITE),
            new Piece(PieceType.ROOK, WHITE)
        };
    }

    /**
     * Prints to the console a text representation of the board.
     */
    public void print() {
        System.out.println(textBoard());
    }

    /**
     * Prints to the console a vertically and horizontally reversed text representation of the board.
     */
    public void printInverted() {
        String[] split = textBoard().split("[\n]");
        String[] inverted = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            inverted[(split.length - 1) - i] = new StringBuilder(split[i]).reverse() + "\n";
        }
        System.out.println(String.join("", inverted));
    }
}
