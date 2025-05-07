package dev.cgj.chess.engine;

public class ChessGame {
    public boolean over = false;

    public Board board;
    public Player white;
    public Player black;

    public ChessGame(String whiteName, String blackName) {
        board = Board.EMPTY;
        white = new Player(true, whiteName);
        black = new Player(false, blackName);
    }

    public boolean move(boolean whitePieces, String moveCode) {
        PieceColor pieceColor = (whitePieces) ? PieceColor.WHITE : PieceColor.BLACK;

        Move move = Move.fromString(moveCode);
        if (board.get(move.start()).color() == pieceColor) {
            return false;
        }

        board = board.move(move);
        return true;
    }
}
