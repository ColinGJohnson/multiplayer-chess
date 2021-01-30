package net.colinjohnson.chess.core;

public interface ChessBoardObserver {

    /**
     * Notifies observers that the state of the chess board has changed.
     *
     * @param board The updated chess board.
     */
    void onBoardUpdate(ChessBoard board);
}
