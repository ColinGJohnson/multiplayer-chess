package dev.cgj.chess.engine;

import java.awt.*;

import static dev.cgj.chess.engine.PieceColor.BLACK;
import static dev.cgj.chess.engine.PieceColor.WHITE;

public class Board {

    /**
     * row-major array representation of chess board
     */
    private final Piece[][] board = new Piece[8][8];

    /**
     * Handles a request to move a piece from a given Player.
     *
     * @param source      The Player attempting to make this move
     * @param moveCode    String representation of the attempted move
     * @return true if the move was legal and was executed, false otherwise.
     */
    public boolean move(PieceColor source, String moveCode) {

        // remove whitespace from moveCode
        moveCode = moveCode.replaceAll("[\\s]", "");

        // reject requests with invalid moveCode
        if (moveCode.length() != 4 || !moveCode.matches("(([a-hA-H][1-8]){2})")) {
            return false;
        }

        // specify and validate the requested move
        Move move = new Move(getBoardCoordinate(moveCode.substring(0, 2)), getBoardCoordinate(moveCode.substring(2)));

        // if the move is valid, do it
        if (move.valid(board)) {
            board[move.finish.x][move.finish.y] = board[move.start.x][move.start.y];
            board[move.start.x][move.start.y] = Piece.EMPTY;
        }

        // if method has proceeded to this point, the move must have been successful
        return true;
    }

    /**
     * Gets a coordinate on the two-dimensional board from algebraic chess notation.
     *
     * @param moveCode The moveCode to translate. Eg. "A2A3"
     * @return The point (r,c), accessed (x,y), on the chess board represented by the given moveCode, null if the moveCode is invalid.
     */
    private Point getBoardCoordinate(String moveCode) {
        int r = (board.length - 1) - (moveCode.charAt(1) - 49);
        int c = (moveCode.substring(0, 1).toLowerCase().toCharArray()[0] - 97);
        return new Point(r, c);
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
