package dev.cgj.chess.ui;

import dev.cgj.chess.engine.Board;
import dev.cgj.chess.engine.Coordinate;

import static dev.cgj.chess.engine.Board.SIZE;

public class BoardUtils {

    /**
     * Prints to the console a vertically and horizontally reversed text representation of the board.
     */
    public static void textBoardInverted(Board board) {
        String[] split = textBoard(board).split("[\n]");
        String[] inverted = new String[split.length];
        for (int i = 0; i < split.length; i++) {
            inverted[(split.length - 1) - i] = new StringBuilder(split[i]).reverse() + "\n";
        }
        System.out.println(String.join("", inverted));
    }

    public static String textBoard(Board board) {
        StringBuilder s = new StringBuilder();

        // top legend
        s.append("   ");
        for (int c = 0; c < SIZE; c++) {
            s.append((char) ('a' + c)).append(" ");
        }
        s.append(" \n /-----------------\\\n");
        for (int r = 0; r < SIZE; r++) {

            // left numbers
            s.append((SIZE - r)).append("| ");

            // chess squares
            for (int c = 0; c < SIZE; c++) {
                s.append(board.get(new Coordinate(r, c)).symbol()).append(" ");
            }

            // right numbers
            s.append("|").append(SIZE - r);

            // jump to next row
            s.append("\n");
        }

        // bottom legend
        s.append(" \\-----------------/\n   ");
        for (int c = 0; c < SIZE; c++) {
            s.append((char) ('a' + c)).append(" ");
        }
        s.append(" \n");
        return s.toString();
    }
}
