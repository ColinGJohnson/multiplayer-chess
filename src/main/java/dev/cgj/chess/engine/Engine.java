package dev.cgj.chess.engine;

import java.util.Collections;
import java.util.List;

public class Engine {

    /**
     * @param board The board on which to find moves.
     * @param coordinate Coordinate of the piece to find moves for.
     *
     * @return A list of legal moves.
     */
    public List<Move> getMoves(Board board, Coordinate coordinate) {
        switch (board.get(coordinate).piece()) {
            case PAWN -> {
                return getPawnMoves(board, coordinate);
            }
            case ROOK -> {
                return getRookMoves(board, coordinate);
            }
            case KNIGHT -> {
                return getKnightMoves(board, coordinate);
            }
            case BISHOP -> {
                return getBishopMoves(board, coordinate);
            }
            case KING -> {
                return getKingMoves(board, coordinate);
            }
            case QUEEN -> {
                return getQueenMoves(board, coordinate);
            }
            case EMPTY -> {
                return Collections.emptyList();
            }
        }
        throw new IllegalArgumentException("Unknown piece type");
    }

    private List<Move> getPawnMoves(Board board, Coordinate coordinate) {
        // TODO: Not implemented
        return Collections.emptyList();
    }

    private List<Move> getRookMoves(Board board, Coordinate coordinate) {
        // TODO: Not implemented
        return Collections.emptyList();
    }

    private List<Move> getKnightMoves(Board board, Coordinate coordinate) {
        // TODO: Not implemented
        return Collections.emptyList();
    }

    private List<Move> getBishopMoves(Board board, Coordinate coordinate) {
        // TODO: Not implemented
        return Collections.emptyList();
    }

    private List<Move> getKingMoves(Board board, Coordinate coordinate) {
        // TODO: Not implemented
        return Collections.emptyList();
    }

    private List<Move> getQueenMoves(Board board, Coordinate coordinate) {
        // TODO: Not implemented
        return Collections.emptyList();
    }
}
