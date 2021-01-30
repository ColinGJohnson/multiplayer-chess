package net.colinjohnson.chess.core;

import net.colinjohnson.chess.core.pieces.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a game of chess.
 *
 * @author Colin Johnson
 */
public class ChessGame {

    // true when the game is finished, either by a checkmate or a stalemate
    private boolean finished = false;

    // the current state this chess game's board
    private final ChessBoard board;

    // the player with white pieces
    private final ChessPlayer whitePlayer;

    // the player with black pieces
    private final ChessPlayer blackPlayer;

    // reference to the current player
    private ChessPlayer currentPlayer;

    /**
     * ChessGame constructor
     *
     * @param whiteName Name for the player with white pieces.
     * @param blackName Name for the player with black pieces.
     */
    public ChessGame(String whiteName, String blackName) {
        whitePlayer = new ChessPlayer(ChessColor.WHITE, whiteName);
        blackPlayer = new ChessPlayer(ChessColor.BLACK, blackName);
        board = new ChessBoard();
    }

    public ChessPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean movePiece(ChessMove move) {

        // check move validity
        if (!moveIsValid(move)) {
            return false;
        }

        // Most is valid, so do it and switch the current player
        board.movePiece(move);
        switchCurrentPlayer();
        return true;
    }

    /**
     * Promote a pawn at a given rank/file on the board, if it is legal to do so.
     *
     * @param promotion The piece to promote the pawn to.
     * @param file      The file of the pawn to promote.
     * @param rank      The rank of the pawn to promote.
     * @return True if the promotion was successful, false otherwise.
     */
    public boolean promotePiece(PieceType promotion, int file, int rank) {

        // no player can have two kings
        if (promotion == PieceType.KING) {
            return false;
        }

        // there must be a pawn at the given location
        ChessPiece targetPawn = board.getPieceAt(new ChessPosition(rank, file));
        if (targetPawn.getPieceType() != PieceType.PAWN) {
            return false;
        }

        // the pawn must be at the correct end of the board
        if ((targetPawn.getColor() == ChessColor.WHITE && rank != 7) ||
                (targetPawn.getColor() == ChessColor.BLACK && rank != 0)) {
            return false;
        }

        // promote the pawn
        ChessPosition position = targetPawn.getPosition();
        ChessColor color = targetPawn.getColor();
        board.removePiece(targetPawn);
        board.addPiece(ChessPiece.getPieceOfType(promotion, color, position));
        return true;
    }

    /**
     * Promote a pawn at a given position on the board, if it is legal to do so.
     *
     * @param promotion The piece to promote the pawn to.
     * @param position  The position of the pawn to promote.
     * @return True if the promotion was successful, false otherwise.
     */
    public boolean promotePiece(PieceType promotion, ChessPosition position) {
        return promotePiece(promotion, position.getFile(), position.getRank());
    }

    /**
     * Switches the current player from white to black, or vice versa, depending on the current
     * player.
     */
    public void switchCurrentPlayer() {
        if (currentPlayer.getColor() == ChessColor.WHITE) {
            currentPlayer = blackPlayer;
        } else {
            currentPlayer = whitePlayer;
        }
    }

    /**
     * Get an ArrayList of all the valid moves that a piece can make on a given board. There will
     * be no valid moves if the piece or the board is the piece is not on the board, or if either
     * the board or the piece
     *
     * @param board The chess board to get valid moves on.
     * @return A list of valid moves.
     */
    public static List<ChessMove> getValidMoves(ChessBoard board) {

        if (board == null) {
            throw new NullPointerException("Board must not be null.");
        }

        ArrayList<ChessMove> validMoves = new ArrayList<>();

        for (ChessPiece piece : board.getPieces()) {
            validMoves.addAll(piece.getMoves(board));
        }

        return validMoves;
    }

    public static boolean moveIsValid(ChessBoard board, ChessPlayer currentPlayer, ChessMove move) {

        ChessPiece movedPiece = board.getPieceAt(move.from);

        // there must be a piece to move at the specified location
        if (movedPiece == null) {
            return false;

        // the moved piece must be owned by the current player
        } else if (movedPiece.getColor() != currentPlayer.getColor()) {
            return false;
        }

        for (ChessMove validMove : movedPiece.getMoves(board)) {
            if (validMove.to == move.to && validMove.from == move.from) {
                return true;
            }
        }
        return false;
    }

    public boolean moveIsValid(ChessMove move) {
        return moveIsValid(board, currentPlayer, move);
    }

    /**
     * Evaluates a given chess board.
     *
     * @param board The board to evaluate.
     * @return An integer indicating which player has the advantage. Positive numbers indicate a
     * white advantage, while negative numbers indicate a black advantage.
     */
    public static int evaluateBoard(ChessBoard board) {
        int evaluation = 0;

        // material evaluation
        for (ChessPiece piece : board.getPieces()) {
            if (piece.getColor() == ChessColor.WHITE) {
                evaluation += piece.getValue();
            } else {
                evaluation -= piece.getValue();
            }
        }

        return evaluation;
    }

    public int evaluateBoard() {
        return evaluateBoard(board);
    }

    public static int evaluateMove(ChessBoard board, ChessMove move) {
        int boardEvalBefore = evaluateBoard(board);
        int boardEvalAfter = evaluateBoard((new ChessBoard(board)).movePiece(move));
        return boardEvalAfter - boardEvalBefore;
    }

    public int evaluateMove(ChessMove move) {
        return evaluateMove(board, move);
    }
}
