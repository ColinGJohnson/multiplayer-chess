package net.colinjohnson.chess.gui;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.colinjohnson.chess.core.ChessColor;
import net.colinjohnson.chess.core.pieces.ChessPiece;
import net.colinjohnson.chess.core.pieces.PieceType;

import java.util.HashMap;

public class ChessSquare extends Label {
    ChessColor color;

    // Avoid loading icon images multiple times
    public static HashMap<PieceType, Image> whiteIcons;
    public static HashMap<PieceType, Image> blackIcons;

    static {
        whiteIcons = new HashMap<>();
        whiteIcons.put(PieceType.PAWN, new Image("/icons/png/pawn_white.png"));
        whiteIcons.put(PieceType.ROOK, new Image("/icons/png/rook_white.png"));
        whiteIcons.put(PieceType.KNIGHT, new Image("/icons/png/knight_white.png"));
        whiteIcons.put(PieceType.BISHOP, new Image("/icons/png/bishop_white.png"));
        whiteIcons.put(PieceType.QUEEN, new Image("/icons/png/queen_white.png"));
        whiteIcons.put(PieceType.KING, new Image("/icons/png/king_white.png"));

        blackIcons = new HashMap<>();
        blackIcons.put(PieceType.PAWN, new Image("/icons/png/pawn_black.png"));
        blackIcons.put(PieceType.ROOK, new Image("/icons/png/rook_black.png"));
        blackIcons.put(PieceType.KNIGHT, new Image("/icons/png/knight_black.png"));
        blackIcons.put(PieceType.BISHOP, new Image("/icons/png/bishop_black.png"));
        blackIcons.put(PieceType.QUEEN, new Image("/icons/png/queen_black.png"));
        blackIcons.put(PieceType.KING, new Image("/icons/png/king_black.png"));
    }

    public ChessSquare(ChessColor color) {
        this.color = color;

        setSkin(createDefaultSkin());
        if (color == ChessColor.WHITE) {
            setStyle("-fx-background-color: white;");
        } else {
            setStyle("-fx-background-color: gray;");
        }

        /*
        setText("test");
        setContentDisplay(ContentDisplay.TOP);
        setTextAlignment(TextAlignment.CENTER);
        setGraphicTextGap(10);
        */
        setPrefHeight(50);
        setPrefWidth(50);

    }

    public void setDisplayedPiece(ChessPiece piece) {
        ImageView img;

        if (piece.getColor() == ChessColor.WHITE) {
            img = new ImageView(whiteIcons.get(piece.getPieceType()));
        } else {
            img = new ImageView(blackIcons.get(piece.getPieceType()));
        }

        img.setPreserveRatio(true);
        img.setFitWidth(50);
        img.setFitHeight(50);
        setGraphic(img);
    }
}
