import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessFigure {

    private boolean firstMove;

    public Pawn(int row, int col, Color color) {
        super(row, col, color);
        type = TypeFigure.PAWN;
        firstMove = true;
    }


    @Override
    public void makeMove(Move move) {
        firstMove = false;
        row = move.getRowEnd();
        col = move.getColEnd();
    }

    @Override
    public Move[] getPossibleMoves(Position position) {
        List<Move> movesList = new ArrayList<>();
        int k = color == Color.WHITE ? -1 : 1;

        if (firstMove && position.getCell(row + 2 * k, col) == null) {
            movesList.add(new Move(row, col, row + 2 * k, col, TypeFigure.PAWN, color));
        }

        if (position.inBoard(row + k, col) && position.getCell(row + k, col) == null) {
            movesList.add(new Move(row, col, row + k, col, TypeFigure.PAWN, color));
        }

        if (position.inBoard(row + k, col - 1) &&
                position.getCell(row + k, col - 1) != null &&
                position.getCell(row + k, col - 1).getColor() != color) {
            movesList.add(new Move(row, col, row + k, col - 1, TypeFigure.PAWN, color));
        }

        if (position.inBoard(row + k, col + 1) &&
                position.getCell(row + k, col + 1) != null &&
                position.getCell(row + k, col + 1).getColor() != color) {
            movesList.add(new Move(row, col, row + k, col + 1, TypeFigure.PAWN, color));
        }

        Move move = position.getLastMove();
        if (move != null && move.getTypeFigure() == TypeFigure.PAWN && move.getColor() != color &&
                Math.abs(move.getRowEnd() - move.getRowStart()) == 2 && Math.abs(move.getColEnd() - col) == 1 &&
                (move.getRowStart() == 6 || move.getRowStart() == 1) && move.getRowEnd() == row) {
            int r = move.getColEnd() > col ? 1 : -1;
            movesList.add(new Move(row, col, row + k, col + r, TypeFigure.PAWN, color));
        }

        Move[] moves = new Move[movesList.size()];
        for (int i = 0; i < movesList.size(); i++) {
            moves[i] = movesList.get(i);
        }
        return moves;
    }
}
