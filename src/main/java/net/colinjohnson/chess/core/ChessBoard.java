package net.colinjohnson.chess.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.colinjohnson.chess.core.pieces.ChessPiece;
import net.colinjohnson.chess.core.pieces.PieceType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class modelling a 8x8 chess board. No chess rules are verified.
 */
public class ChessBoard implements Serializable {

    // Width and height of a standard chess board
    public static final int BOARD_SIZE = 8;

    // 2-D array of chess pieces representing board arrangement
    private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    // List of pieces currently on this chess board
    private ArrayList<ChessPiece> pieces;

    private final ArrayList<ChessBoardObserver> observers = new ArrayList<>();

    /**
     * Construct a new empty chess board.
     */
    public ChessBoard() {
        pieces = new ArrayList<>();
        clearBoard();
    }

    /**
     * Copy constructor.
     *
     * @param chessBoard The chess board to copy.
     */
    public ChessBoard(ChessBoard chessBoard) {
        this();
        for (ChessPiece chessPiece : chessBoard.getPieces()) {
            addPiece(chessPiece.clone());
        }
    }

    public void registerObserver(ChessBoardObserver observer) {
        observers.add(observer);
    }

    public void detachObserver(ChessBoardObserver observer) {
        observers.remove(observer);
    }

    public static ChessBoard fromJSON(String json) throws JsonProcessingException {

        // decode json
        ObjectMapper objectMapper = new ObjectMapper();
        List<List<String>> parsedJson = objectMapper.readValue(json, new TypeReference<>(){});

        // construct board
        ChessBoard decodedBoard = new ChessBoard();

        for (int i = 0; i < parsedJson.size(); i++) {
            List<String> row = parsedJson.get(i);

            for (int j = 0; j < row.size(); j++) {
                ChessPosition position = new ChessPosition(i, j);
                char symbol = row.get(j).charAt(0);

                // skip empty squares (leave as null on board)
                if (symbol == ' ') {
                    continue;
                }

                ChessColor color = (int)symbol < 97 ? ChessColor.BLACK : ChessColor.WHITE;
                symbol = Character.toLowerCase(symbol);
                PieceType pieceType = switch(symbol) {
                    case 'p' -> PieceType.PAWN;
                    case 'r' -> PieceType.ROOK;
                    case 'n' -> PieceType.KNIGHT;
                    case 'b' -> PieceType.BISHOP;
                    case 'q' -> PieceType.QUEEN;
                    case 'k' -> PieceType.KING;
                    default -> throw new IllegalStateException("Unexpected value: " + symbol);
                };
                decodedBoard.addPiece(ChessPiece.getPieceOfType(pieceType, color, position));
            }
        }

        return decodedBoard;
    }

    /**
     * Creates a JSON representation of this board readable by {@link #fromJSON(String)}.
     *
     * @return A JSON string representing this board.
     */
    public String toJSON() {

        List<List<String>> encodedBoard = new ArrayList<>();

        for (int r = 0; r < board.length; r++) {
            ArrayList<String> row = new ArrayList<>();

            for (int c = 0; c < board[r].length; c++) {
                ChessPiece piece = board[r][c];

                String symbol = "";

                if (piece != null) {
                    symbol = switch (piece.getPieceType()) {
                        case BISHOP -> "b";
                        case KING -> "k";
                        case KNIGHT -> "n";
                        case PAWN -> "p";
                        case QUEEN -> "q";
                        case ROOK -> "r";
                    };

                    if (piece.getColor() == ChessColor.BLACK) {
                        symbol = symbol.toUpperCase();
                    }
                }

                row.add(symbol);
            }
            encodedBoard.add(row);
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            return objectMapper.writeValueAsString(encodedBoard);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public ChessPiece getPieceAt(int rank, int file) {
        return board[rank][file];
    }

    public ChessPiece getPieceAt(ChessPosition position) {
        return board[position.getRank()][position.getFile()];
    }

    public ArrayList<ChessPiece> getPieces() {
        return pieces;
    }

    /**
     * Move a piece on this board from one square to another. This method makes no attempt to
     * validate the legality of the move.
     *
     * @param move The move to execute on this chess board.
     */
    public void movePiece(ChessMove move) {
        ChessPiece movedPiece = getPieceAt(move.from);
        ChessPiece capturedPiece = getPieceAt(move.to);

        // update the board
        board[move.from.getRank()][move.from.getFile()] = null;
        board[move.from.getRank()][move.from.getFile()] = movedPiece;

        // update the pieces
        if (capturedPiece != null) {
            pieces.remove(capturedPiece);
        }
        movedPiece.setPosition(move.to);
    }

    /**
     * Adds a piece to this chess board. This action will overwrite any piece currently on the
     * board at the same location.
     *
     * @param piece The chess piece to add.
     */
    public void addPiece(ChessPiece piece) {
        if (getPieceAt(piece.getPosition()) != null) {
            throw new IllegalArgumentException("There is already a piece at the given location");
        }

        pieces.add(piece);
        ChessPosition position = piece.getPosition();
        board[position.getRank()][position.getFile()] = piece;
    }

    /**
     * Removes a piece from this chess board.
     *
     * @param piece The chess piece to remove.
     */
    public void removePiece(ChessPiece piece) {
        pieces.remove(piece);
        board[piece.getPosition().getRank()][piece.getPosition().getFile()] = null;
    }

    /**
     * Removes all pieces from the board;
     */
    public void clearBoard() {
        pieces = new ArrayList<>();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[0].length; c++) {
                board[r][c] = null;
            }
        }
    }

    /**
     * Checks if a given piece exists on the board.
     *
     * @param piece The piece to check for.
     * @return True if the given piece is on the board.
     */
    public boolean containsPiece(ChessPiece piece) {
        return pieces.contains(piece);
    }

    /**
     * Rotate this 90 degrees clockwise. Used to cleanly implement move checking.
     *
     * @param n The number of 90 degree rotations to perform.
     */
    public ChessBoard rotateCW(int n) {
        ChessPiece[][] rotatedBoard = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

        for (ChessPiece piece: pieces) {
            ChessPosition position = piece.getPosition().rotateCW(n);
            rotatedBoard[position.getRank()][position.getFile()] = piece;
        }

        board = rotatedBoard;
        return this;
    }
}
