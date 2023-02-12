import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessFigure {

    private boolean firstMove = true;

    public Rook(int row, int col, Color color) {
        super(row, col, color);
        type = TypeFigure.ROOK;
        COL_DELTA = new int[] {0, 0, 1, -1};
        ROW_DELTA = new int[] {1, -1, 0, 0};
    }

    @Override
    public void makeMove(Move move) {
        row = move.getRowEnd();
        col = move.getColEnd();
        firstMove = false;
    }

    @Override
    public boolean getFirstMove() {
        return firstMove;
    }
}
