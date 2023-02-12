import java.util.ArrayList;
import java.util.List;

public abstract class ChessFigure implements Figure {
    protected Color color;
    protected int row;
    protected int col;
    protected TypeFigure type;
    protected int[] ROW_DELTA = new int[0];
    protected int[] COL_DELTA = new int[0];

    public ChessFigure(int row, int col, Color color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }

    @Override
    public Move[] getPossibleMoves(Position position) {
        List<Move> movesList = new ArrayList<>();
        for (int j = 0; j < ROW_DELTA.length; j++) {
            int i = 1;
            int rowNow = row + i * ROW_DELTA[j];
            int colNow = col + i * COL_DELTA[j];
            while (position.inBoard(rowNow, colNow)) {
                Figure figure = position.getCell(rowNow, colNow);
                if (figure == null) {
                    movesList.add(new Move(row, col, rowNow, colNow, type, color));
                    rowNow += ROW_DELTA[j];
                    colNow += COL_DELTA[j];
                    continue;
                }
                if (figure.getColor() != color) {
                    movesList.add(new Move(row, col, rowNow, colNow, type, color));
                }
                break;
            }
        }

        Move[] moves = new Move[movesList.size()];
        for (int i = 0; i < movesList.size(); i++) {
            moves[i] = movesList.get(i);
        }
        return moves;
    }

    @Override
    public boolean possibleMove(Position position, Move move) {
        if (move.getColor() != color || move.getTypeFigure() != type) {
            return false;
        }
        Move[] possibleMoves = getPossibleMoves(position);
        System.out.println(possibleMoves.length);
        for (Move nowMove : possibleMoves) {
            if (nowMove.equals(move)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void makeMove(Move move) {
        row = move.getRowEnd();
        col = move.getColEnd();
    }

    @Override
    public TypeFigure getType() {
        return type;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getCol() {
        return col;
    }

    @Override
    public void makeMoveFuture(Move move) {
        row = move.getRowEnd();
        col = move.getColEnd();
    }

    @Override
    public boolean getFirstMove() {
        return true;
    }
}
