package dev.cgj.chess.engine;

import java.util.ArrayList;

public class Engine {
    private int bestMoveRecursive(PieceColor color, Board board, int depth) {
        int score = 0;

        // if desired analysis depth has been reached, go back
        if (depth <= 0) {
            return score;
        }

        // Check each of the player's pieces
        for (Coordinate playerPiece : board.getPiecesOfColor(color)) {

            // generate an list of possible moves
            ArrayList<Move> moves = getMoves(board, playerPiece);

            // iterate through list possible moves for the player
            for (Move move : moves) {

                // iterate through the other player's pieces
                PieceColor opponent = color.opposite();
                for (Coordinate opponentPiece : board.getPiecesOfColor(opponent) ) {

                    // generate a list of possible countermoves
                    ArrayList<Move> counterMoves = getMoves(board, opponentPiece);

                    // iterate through list of countermoves for the opponent
                    for (Move counterMove : counterMoves) {

                        // score the current move/countermove combination
                        // score += board.move().getScore(board);
                        // score -= counterMove.getScore(board);

                        // check score for the next moves in this scenario
                        score += bestMoveRecursive(color, board, depth - 1);
                    }
                }
            }
        }

        // return the aggregate score of this scenario
        return score;
    }

    private ArrayList<Move> getMoves(Board board, Coordinate coordinate) {
        // TODO: Implement
        return new ArrayList<>();
    }
}
