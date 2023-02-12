public class Bishop extends ChessFigure {

    public Bishop(int row, int col, Color color) {
        super(row, col, color);
        type = TypeFigure.BISHOP;
        COL_DELTA = new int[] {1, 1, -1, -1};
        ROW_DELTA = new int[] {1, -1, 1, -1};
    }
}
