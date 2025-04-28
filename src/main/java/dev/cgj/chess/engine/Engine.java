package dev.cgj.chess.engine;

import java.awt.Point;
import java.util.ArrayList;

public class Engine {
    private int bestMoveRecursive(Player player, Player opponent, int depth, Piece[][] simBoard) {
        int score = 0;

        // if desired analysis depth has been reached, go back
        if (depth <= 0) {
            return score;
        }

        // Check each of the player's pieces
        for (Piece playerPiece : player.pieces) {

            // generate an list of possible moves
            ArrayList<Move> moves = getMoves(playerPiece.location, simBoard);

            // iterate through list possible moves for the player
            for (Move move : moves) {

                // iterate through the other player's pieces
                for (Piece opponentPiece : opponent.pieces) {

                    // generate a list of possible countermoves
                    ArrayList<Move> counterMoves = getMoves(opponentPiece.location, simBoard);

                    // iterate through list of countermoves for the opponent
                    for (Move counterMove : counterMoves) {

                        // score the current move/countermove combination
                        //score += move.getScore(board);
                        //score -= counterMove.getScore(board);

                        // check score for the next moves in this scenario
                        score += bestMoveRecursive(player, opponent, depth - 1, simBoard.clone());
                    }
                }
            }
        }

        // return the aggregate score of this scenario
        return score;
    }

    private ArrayList<Move> getMoves(Point pieceLocation, Piece[][] board) {
        // TODO: Implement
        return new ArrayList<>();
    }
}
