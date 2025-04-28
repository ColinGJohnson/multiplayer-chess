package dev.cgj.chess.engine;

import java.awt.Point;

public class ChessGame {
    public boolean over = false;

    /**
     * row-major array representation of chess board
     */
    private final Piece[][] board = new Piece[8][8];

    public Player white;
    public Player black;

    /**
     * ChessGame constructor
     *
     * @param whiteName Name for the player with white pieces.
     * @param blackName Name for the player with black pieces.
     */
    public ChessGame(String whiteName, String blackName) {
        white = new Player(true, whiteName);
        black = new Player(false, blackName);
        resetBoard();
    }

    /**
     * Generates a string representation of a given Piece object. White pieces are lowercase while black pieces are uppercase.
     *
     * @param toConvert The Piece object to convert.
     * @return A symbol for the given piece.
     */
    public static String getSymbol(Piece toConvert) {

        // select an appropriate character for each type of game piece
        String conversion = switch (toConvert.piece) {
            case PAWN -> "P";
            case ROOK -> "R";
            case KNIGHT -> "N";
            case BISHOP -> "B";
            case KING -> "K";
            case QUEEN -> "Q";
            case EMPTY -> "_";
        };

        // is this is a black piece, make its character lowercase
        if (toConvert.player.whitePieces) {
            return conversion;
        } else {
            return conversion.toLowerCase();
        }
    }

    /**
     * Prints to the console a text representation of the board.
     */
    public void printBoard() {
        System.out.println(textBoard());
    }

    /**
     * Prints to the console a vertically and horizontally reversed text representation of the board.
     */
    public void printBoardInverted() {
        String[] split = textBoard().split("[\n]");
        String[] inverted = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            inverted[(split.length - 1) - i] = new StringBuilder(split[i]).reverse() + "\n";
        }
        System.out.println(String.join("", inverted));
    }

    public String textBoard() {
        String s = "";

        // top legend
        s += "   ";
        for (int c = 0; c < board[0].length; c++) {
            s += (char) ('a' + c) + " ";
        }
        s += " \n /-----------------\\\n";
        for (int r = 0; r < board.length; r++) {

            // left numbers
            s += (board.length - r) + "| ";

            // chess squares
            for (int c = 0; c < board[0].length; c++) {
                s += getSymbol(board[r][c]) + " ";
            }

            // right numbers
            s += ("|" + (board.length - r));

            // jump to next row
            s += "\n";
        }

        // bottom legend
        s += " \\-----------------/\n   ";
        for (int c = 0; c < board[0].length; c++) {
            s += (char) ('a' + c) + " ";
        }
        s += " \n";
        return s;
    }

    /**
     * Fills the game board with empty tiles.
     */
    private void clearBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = new Piece();
            }
        }
    }

    /**
     * Resets the game board to its initial state with all pieces in the correct positions
     */
    public void resetBoard() {

        // fill the board with empty tiles
        clearBoard();

        // add rows of pawns
        for (int c = 0; c < board[1].length; c++) {
            board[1][c] = new Piece(black, PieceType.PAWN);
            board[6][c] = new Piece(white, PieceType.PAWN);
        }

        // add white row
        board[0] = new Piece[]{
            new Piece(black, PieceType.ROOK),
            new Piece(black, PieceType.KNIGHT),
            new Piece(black, PieceType.BISHOP),
            new Piece(black, PieceType.KING),
            new Piece(black, PieceType.QUEEN),
            new Piece(black, PieceType.BISHOP),
            new Piece(black, PieceType.KNIGHT),
            new Piece(black, PieceType.ROOK)
        };

        // add black row
        board[7] = new Piece[]{
            new Piece(white, PieceType.ROOK),
            new Piece(white, PieceType.KNIGHT),
            new Piece(white, PieceType.BISHOP),
            new Piece(white, PieceType.KING),
            new Piece(white, PieceType.QUEEN),
            new Piece(white, PieceType.BISHOP),
            new Piece(white, PieceType.KNIGHT),
            new Piece(white, PieceType.ROOK)
        };
    }

    public boolean move(boolean whitePieces, String moveCode) {
        Player source = (whitePieces) ? white : black;
        return movePiece(source, moveCode, board);
    }

    /**
     * Handles a request to move a piece from a given Player.
     *
     * @param source      The Player attempting to make this move
     * @param moveCode    String representation of the attempted move
     * @param targetBoard The chess board for this move to be executed on
     * @return true if the move was legal and was executed, false otherwise.
     */
    private boolean movePiece(Player source, String moveCode, Piece[][] targetBoard) {

        // remove whitespace from moveCode
        moveCode = moveCode.replaceAll("[\\s]", "");

        // reject requests with invalid moveCode
        if (moveCode.length() != 4 || !moveCode.matches("(([a-hA-H][1-8]){2})")) {
            return false;
        }

        // specify and validate the requested move
        Move move = new Move(getBoardCoordinate(moveCode.substring(0, 2)), getBoardCoordinate(moveCode.substring(2)));

        // if the move is valid, do it
        if (move.valid(targetBoard)) {
            targetBoard[move.finish.x][move.finish.y] = targetBoard[move.start.x][move.start.y];
            targetBoard[move.start.x][move.start.y] = new Piece();
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
}