public interface Position {
    boolean moveIsValid(Move move);

    Figure getCell(int row, int col);

    boolean inBoard(int row, int col);

    Move getLastMove();
}
