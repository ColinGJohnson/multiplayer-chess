package dev.cgj.chess.engine;

import java.util.ArrayList;

public class Player {
    boolean whitePieces = false;
    String name = "player";
    boolean check = false;
    boolean checkMate = false;
    ArrayList<Piece> pieces = new ArrayList<>();

    public Player(boolean whitePieces, String name) {
        this.whitePieces = whitePieces;
        this.name = name;
    }
}
