public class Queen extends ChessFigure {

    public Queen(int row, int col, Color color) {
        super(row, col, color);
        type = TypeFigure.QUEEN;
        COL_DELTA = new int[] {1, 1, -1, -1, 0, 0, 1, -1};
        ROW_DELTA = new int[] {1, -1, 1, -1, 1, -1, 0, 0};
    }
}
