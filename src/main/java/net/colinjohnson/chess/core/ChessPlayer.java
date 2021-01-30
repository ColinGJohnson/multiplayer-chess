package net.colinjohnson.chess.core;

public class ChessPlayer {

    private ChessColor color;
    private String name;
    private boolean check = false;
    private boolean checkmate = false;

    public ChessPlayer(ChessColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public ChessColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // TODO: add validation name characters and length
        this.name = name;
    }
}
