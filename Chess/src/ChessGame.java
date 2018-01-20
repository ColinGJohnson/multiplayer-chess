import java.awt.*;
import java.util.ArrayList;

public class ChessGame {
    private Piece[][] board = new Piece[8][8]; // row-major array representation of chess board
    public Player white; // the Player with white pieces
    public Player black; // the Player with black pieces

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
    } // ChessGame constructor

    /**
     * enum representing unowned chess pieces
     */
    public enum PieceType {
        pawn,
        rook,
        knight,
        bishop,
        king,
        queen,
        empty
    } // PieceType

    /**
     * Inner class representing a Player who has a name and is white or black
     */
    private class Player {
        boolean whitePieces = false;
        String name = "player";
        boolean check = false;
        boolean checkMate = false;
        ArrayList<Piece> pieces = new ArrayList<>();

        public Player(boolean whitePieces, String name) {
            this.whitePieces = whitePieces;
            this.name = name;
        } // Player constructor
    } // Player

    /**
     * Inner class representing a Player who has a name and is white or black
     */
    private class botPlayer extends Player {
        public botPlayer(boolean white) {
            super(white, "bot");
        } // Player constructor
    } // Player

    /**
     * Inner class representing a Piece owned by a specific player
     */
    private class Piece {
        Player player;
        PieceType piece;
        Point location; // TODO: does not update

        public Piece() {
            this.player = new Player(false, "placeHolder");
            this.piece = PieceType.empty;
        }

        public Piece(Player player, PieceType piece) {
            this.player = player;
            this.piece = piece;
        }
    } // Piece

    /**
     * Inner class representing a chess move
     */
    //TODO: Unfinished
    private class Move {
        Point start;
        Point finish;

        /**
         * Move constructor.
         *
         * @param start
         * @param finish
         */
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
    } // Move

    /**
     * Generates a string representation of a given Piece object. White pieces are lowercase while black pieces are uppercase.
     *
     * @param toConvert The Piece object to convert.
     * @return A symbol for the given piece.
     */
    public static String getSymbol(Piece toConvert) {

        // select an appropriate character for each type of game piece
        String conversion;
        switch (toConvert.piece) {
            case pawn:
                conversion = "P";
                break;
            case rook:
                conversion = "R";
                break;
            case knight:
                conversion = "N";
                break;
            case bishop:
                conversion = "B";
                break;
            case king:
                conversion = "K";
                break;
            case queen:
                conversion = "Q";
                break;
            case empty:
                conversion = "_";
                break;
            default:
                conversion = "X";
                break;
        }

        // is this is a black piece, make it's character lowercase
        if (toConvert.player.whitePieces) {
            return conversion;
        } else {
            return conversion.toLowerCase();
        }
    } // getSymbol

    /**
     * Prints to the console a text representation of the board.
     */
    public void printBoard() {
        System.out.println(textBoard());
    } // printBoard

    /**
     * Prints to the console an vertically and horizontally reversed text representation of the board.
     */
    public void printBoardInverted() {
        String[] split = textBoard().split("[\n]");
        String[] inverted = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            inverted[(split.length - 1) - i] = new StringBuilder(split[i]).reverse().toString() + "\n";
        }
        System.out.println(String.join("", inverted));
    } // printBoardInverted

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
    } // textBoard

    /**
     * Fills the game board with empty tiles.
     */
    private void clearBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = new Piece();
            }
        }
    } // printBoard

    /**
     * Resets the game board to its initial state with all pieces in the correct positions
     */
    public void resetBoard() {

        // fill the board with empty tiles
        clearBoard();

        // add rows of pawns
        for (int c = 0; c < board[1].length; c++) {
            board[1][c] = new Piece(black, PieceType.pawn);
            board[6][c] = new Piece(white, PieceType.pawn);
        }

        // add white row
        board[0] = new Piece[]{
                new Piece(black, PieceType.rook),
                new Piece(black, PieceType.knight),
                new Piece(black, PieceType.bishop),
                new Piece(black, PieceType.king),
                new Piece(black, PieceType.queen),
                new Piece(black, PieceType.bishop),
                new Piece(black, PieceType.knight),
                new Piece(black, PieceType.rook)
        };

        // add black row
        board[7] = new Piece[]{
                new Piece(white, PieceType.rook),
                new Piece(white, PieceType.knight),
                new Piece(white, PieceType.bishop),
                new Piece(white, PieceType.king),
                new Piece(white, PieceType.queen),
                new Piece(white, PieceType.bishop),
                new Piece(white, PieceType.knight),
                new Piece(white, PieceType.rook)
        };
    } // resetBoard

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
        Move move = new Move(getBoardCoordiate(moveCode.substring(0, 2)), getBoardCoordiate(moveCode.substring(2)));

        // if the move is valid, do it
        if (move.valid(targetBoard)) {
            targetBoard[move.finish.x][move.finish.y] = targetBoard[move.start.x][move.start.y];
            targetBoard[move.start.x][move.start.y] = new Piece();
        }

        // if method has proceeded to this point, the move must have been successful
        return true;
    } // movePiece

    /**
     * @param player
     * @param opponent
     * @param depth
     * @param simBoard
     * @return
     */
    //TODO: Unfinished, needs to be restructured
    private int bestMoveRecursive(Player player, Player opponent, int depth, Piece[][] simBoard) {
        int score = 0;

        // if desired analysis depth has been reached, go back
        if (depth <= 0) {
            return score;
        }

        // iterate through all of target player's pieces
        for (Piece playerPiece : player.pieces) {

            // generate an list of possible moves
            ArrayList<Move> moves = getMoves(playerPiece.location, simBoard);

            // iterate through list possible moves for the player
            for (Move move : moves) {

                // iterate through the other players's pieces
                for (Piece opponentPiece : opponent.pieces) {

                    // generate an list of possible countermoves
                    ArrayList<Move> counterMoves = getMoves(opponentPiece.location, simBoard);

                    // iterate through list of countermoves for the opponent
                    for (Move counterMove : counterMoves) {

                        // score the current move/countermove combination
                        score += move.getScore(board);
                        score -= counterMove.getScore(board);

                        // check score for the next moves in this scenario
                        score += bestMoveRecursive(player, opponent, depth - 1, simBoard.clone());
                    }
                }
            }
        }

        // return the aggregate score of this scenario
        return score;
    } // bestMoveRecursive

    /**
     * @param pieceLocation
     * @param board
     * @return
     */
    //TODO: unfinished
    private ArrayList getMoves(Point pieceLocation, Piece[][] board) {
        ArrayList<Move> moves = new ArrayList();

        return moves;
    } // getMove

    /**
     * Gets a coordinate on the two-dimensional board from algebraic chess notation.
     *
     * @param moveCode The moveCode to translate. Eg. "A2A3"
     * @return The point (r,c), accessed (x,y), on the chess board represented by the given moveCode, null if the moveCode is invalid.
     */
    private Point getBoardCoordiate(String moveCode) {

        // parse the moveCode and return it
        int r = (board.length - 1) - (moveCode.charAt(1) - 49);
        int c = (moveCode.substring(0, 1).toLowerCase().toCharArray()[0] - 97);
        return new Point(r, c);
    } // getBoardCoordinate

    public static void main(String args[]) {
        ChessGame test = new ChessGame("", "");
        test.printBoard();
        test.movePiece(test.white, "a1a2", test.board);
        test.printBoard();
    } // main method
} // ChessGame