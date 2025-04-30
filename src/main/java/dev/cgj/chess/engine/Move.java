package dev.cgj.chess.engine;

public record Move(Coordinate start, Coordinate finish) {

    public static Move fromString(String moveCode) {
        moveCode = moveCode.replaceAll("[\\s]", "");

        if (moveCode.length() != 4 || !moveCode.matches("(([a-hA-H][1-8]){2})")) {
            throw new IllegalArgumentException("Invalid move code");
        }

        // specify and validate the requested move
        return new Move(
            getBoardCoordinate(moveCode.substring(0, 2)),
            getBoardCoordinate(moveCode.substring(2))
        );
    }

    /**
     * Gets a coordinate on the two-dimensional board from algebraic chess notation.
     *
     * @param moveCode The moveCode to translate. Eg. "A2A3"
     * @return The point (r,c), accessed (x,y), on the chess board represented by the given moveCode.
     */
    private static Coordinate getBoardCoordinate(String moveCode) {
        int r = 7 - (moveCode.charAt(1) - 49);
        int c = (moveCode.substring(0, 1).toLowerCase().toCharArray()[0] - 97);
        return new Coordinate(r, c);
    }
}
