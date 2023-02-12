import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Knight extends ChessFigure {

    public Knight(int row, int col, Color color) {
        super(row, col, color);
        type = TypeFigure.KNIGHT;
        ROW_DELTA = new int[] {1, 2, 2, 1};
        COL_DELTA = new int[] {-2, -1, 1, 2};
    }

    @Override
    public Move[] getPossibleMoves(Position position) {
        List<Move> movesList = new ArrayList<>();
        for (int j = 0; j < ROW_DELTA.length * 2; j++) {
            int k = j < 4 ? 1 : -1;
            int rowNow = row + k * ROW_DELTA[j % ROW_DELTA.length];
            int colNow = col + COL_DELTA[j % COL_DELTA.length];
            if (!position.inBoard(rowNow, colNow)) {
                continue;
            }
            Figure figure = position.getCell(rowNow, colNow);
            if (figure == null) {
                movesList.add(new Move(row, col, rowNow, colNow, type, color));
            } else if (figure.getColor() != color) {
                movesList.add(new Move(row, col, rowNow, colNow, type, color));
            }
        }

        Move[] moves = new Move[movesList.size()];
        for (int i = 0; i < movesList.size(); i++) {
            moves[i] = movesList.get(i);
        }
        return moves;
    }
}
