import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class King extends ChessFigure {

    private boolean firstMove = true;

    public King(int row, int col, Color color) {
        super(row, col, color);
        type = TypeFigure.KING;
        COL_DELTA = new int[] {1, 1, -1, -1, 0, 0, 1, -1};
        ROW_DELTA = new int[] {1, -1, 1, -1, 1, -1, 0, 0};
    }

    @Override
    public void makeMove(Move move) {
        row = move.getRowEnd();
        col = move.getColEnd();
        firstMove = false;
    }

    @Override
    public boolean possibleMove(Position position, Move move) {
        if (move.getColor() != color || (move.getTypeFigure() != TypeFigure.KING
                && move.getTypeFigure() != TypeFigure.CASTLINGLONG && move.getTypeFigure() != TypeFigure.CASTLINGSHORT)) {
            return false;
        }
        Move[] possibleMoves = getPossibleMoves(position);
        for (Move nowMove : possibleMoves) {
            if (nowMove.equals(move)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Move[] getPossibleMoves(Position position) {
        List<Move> movesList = new ArrayList<>();
        for (int j = 0; j < ROW_DELTA.length; j++) {
            int rowNow = row + ROW_DELTA[j];
            int colNow = col + COL_DELTA[j];
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

        if (firstMove) {
            if (color == Color.WHITE && position.getCell(7, 7) != null
                    && position.getCell(7, 6) == null && position.getCell(7, 5) == null
                    && position.getCell(7, 7).getType() == TypeFigure.ROOK && position.getCell(7, 7).getFirstMove()) {
                movesList.add(new Move(row, col, row, col + 2, TypeFigure.CASTLINGSHORT, color));
            } else if (color == Color.WHITE && position.getCell(7, 0) != null
                    && position.getCell(7, 1) == null && position.getCell(7, 2) == null && position.getCell(7, 3) == null
                    && position.getCell(7, 0).getType() == TypeFigure.ROOK && position.getCell(7, 0).getFirstMove()) {
                movesList.add(new Move(row, col, row, col - 2, TypeFigure.CASTLINGLONG, color));
            } else if (color == Color.BLACK && position.getCell(0, 7) != null
                    && position.getCell(0, 6) == null && position.getCell(0, 5) == null
                    && position.getCell(0, 7).getType() == TypeFigure.ROOK && position.getCell(0, 7).getFirstMove()) {
                movesList.add(new Move(row, col, row, col + 2, TypeFigure.CASTLINGSHORT, color));
            } else if (color == Color.BLACK && position.getCell(0, 0) != null
                    && position.getCell(0, 1) == null && position.getCell(0, 2) == null && position.getCell(0, 3) == null
                    && position.getCell(0, 0).getType() == TypeFigure.ROOK && position.getCell(0, 0).getFirstMove()) {
                movesList.add(new Move(row, col, row, col - 2, TypeFigure.CASTLINGLONG, color));
            }
        }

        Move[] moves = new Move[movesList.size()];
        for (int i = 0; i < movesList.size(); i++) {
            moves[i] = movesList.get(i);
        }
        System.out.println(Arrays.toString(moves));
        return moves;
    }

    @Override
    public boolean getFirstMove() {
        return firstMove;
    }
}
