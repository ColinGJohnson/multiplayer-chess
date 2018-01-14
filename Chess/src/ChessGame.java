public class ChessGame {
    private PlayerPiece[][] board = new PlayerPiece[8][8];
    public Player white;
    public Player black;

    /**
     * ChessGame constructor
     *
     * @param whiteName Name for the player with white pieces.
     * @param blackName Name for the player with black pieces.
     */
    public ChessGame(String whiteName, String blackName) {
        resetBoard();
        white = new Player(true, whiteName);
        black = new Player(false, blackName);
    } // ChessGame constructor

    /**
     * enum representing unowned chess pieces
     */
    public enum Piece {
        pawn,
        rook,
        knight,
        bishop,
        king,
        queen,
        empty
    } // Piece

    /**
     * Class representing a Player who has a name and is white or black
     */
    private class Player {
        public Player(boolean white, String name) {
            this.white = white;
            this.name = name;
        } // Player constructor

        boolean white = false;
        String name = "player";
        boolean check = false;
        boolean checkMate = false;
    } // Player

    /**
     * Class representing a Piece owned by a specific player
     */
    private class PlayerPiece {
        public PlayerPiece() {
        }

        public PlayerPiece(Player player, Piece piece) {
            this.player = player;
            this.piece = piece;
        }

        Player player = null;
        Piece piece = Piece.empty;

    } // PlayerPiece

    public String getSymbol(PlayerPiece toConvert) {

        // return an underscore to represent empty squares
        if (toConvert.piece == Piece.empty) {
            return "_";
        }

        // select an appropriate character for each type of game piece
        String conversion;
        switch (toConvert.piece) {
            case pawn:
                conversion = "P";
            case rook:
                conversion = "R";
            case knight:
                conversion = "N";
            case bishop:
                conversion = "B";
            case king:
                conversion = "K";
            case queen:
                conversion = "Q";
            default:
                conversion = "_";
        }

        // is this is a black piece, make it's character lowercase
        if (toConvert.player.white) {
            return conversion;
        } else {
            return conversion.toLowerCase();
        }
    } // getSymbol

    public void printBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(getSymbol(board[r][c]) + " ");
            }
            System.out.print("\n");
        }
    } // printBoard

    public void printBoardInverted() {
        for (int r = board.length; r > 0; r--) {
            for (int c = 0; c < board[0].length; c++) {
                System.out.print(getSymbol(board[r][c]) + " ");
            }
            System.out.print("\n");
        }
    } // printBoard

    /**
     * Fills the game board with empty tiles.
     */
    public void clearBoard() {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = new PlayerPiece();
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
            board[1][c] = new PlayerPiece(black, Piece.pawn);
            board[6][c] = new PlayerPiece(white, Piece.pawn);
        }

        // add white row
        board[0] = new PlayerPiece[]{
                new PlayerPiece(black, Piece.rook),
                new PlayerPiece(black, Piece.knight),
                new PlayerPiece(black, Piece.bishop),
                new PlayerPiece(black, Piece.king),
                new PlayerPiece(black, Piece.queen),
                new PlayerPiece(black, Piece.bishop),
                new PlayerPiece(black, Piece.knight),
                new PlayerPiece(black, Piece.rook)
        };

        // add black row
        board[7] = new PlayerPiece[]{
                new PlayerPiece(white, Piece.rook),
                new PlayerPiece(white, Piece.knight),
                new PlayerPiece(white, Piece.bishop),
                new PlayerPiece(white, Piece.king),
                new PlayerPiece(white, Piece.queen),
                new PlayerPiece(white, Piece.bishop),
                new PlayerPiece(white, Piece.knight),
                new PlayerPiece(white, Piece.rook)
        };
    } // resetBoard

    /**
     * Handles a request to move a piece from a given Player.
     *
     * @param source The Player attempting to make this move
     * @return true if the move was legal and was executed, false otherwise.
     */
    public boolean movePiece(Player source) {
        return false;
    } // movePiece
} // ChessGame
