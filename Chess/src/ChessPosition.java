import java.util.Map;

public class ChessPosition implements Position {

    private Figure[][] listFigures;

    private int size;
    private Color colorMotion;
    private Move lastMove;

    private final Map<TypeFigure, String> mapFigure = Map.of(
            TypeFigure.KING, "K",
            TypeFigure.QUEEN, "Q",
            TypeFigure.KNIGHT, "N",
            TypeFigure.BISHOP, "B",
            TypeFigure.ROOK, "R",
            TypeFigure.PAWN, "P"
    );

    private final Map<Color, String> mapColorFigure = Map.of(
            Color.BLACK, "b",
            Color.WHITE, "w"
    );

    private final String notation = "ABCDEFGH";

    public ChessPosition(int size, Color colorMotion, Figure[][] listFigures, Move lastMove) {
        this.size = size;
        this.colorMotion = colorMotion;
        this.listFigures = listFigures;
        this.lastMove = lastMove;
    }

    @Override
    public boolean moveIsValid(Move move) {
        return inBoard(move.getRowStart(), move.getColStart()) &&
                inBoard(move.getRowEnd(), move.getColEnd()) &&
                listFigures[move.getRowStart()][move.getColStart()] != null &&
                listFigures[move.getRowStart()][move.getColStart()].getType() == move.getTypeFigure() &&
                listFigures[move.getRowStart()][move.getColStart()].getColor() == colorMotion &&
                (listFigures[move.getRowEnd()][move.getColEnd()] == null ||
                        listFigures[move.getRowEnd()][move.getColEnd()].getType() != TypeFigure.KING) &&
                listFigures[move.getRowStart()][move.getColStart()].possibleMove(this, move);
    }

    @Override
    public Figure getCell(int row, int col) {
        if (inBoard(row, col)) {
            return listFigures[row][col];
        }
        return null;
    }

    @Override
    public boolean inBoard(int row, int col) {
        return 0 <= row && row < size && 0 <= col && col < size;
    }

    @Override
    public Move getLastMove() {
        return lastMove;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(" ");
        sb.append("_".repeat(size * 9 + 3)).append("\n");
        for (int i = 0; i < size + 1; i++) {
            if (i != size) {
                sb.append("|");
                for (int j = 0; j < size + 1; j++) {
                    if (j == 0) {
                        sb.append("   |");
                    } else {
                        sb.append("        |");
                    }
                }
                sb.append("\n");
            }

            sb.append("|");
            for (int j = 0; j < size + 1; j++) {
                if (j == 0 && i != size) {
                    sb.append(" ").append(8 - i).append(" |");

                    continue;
                }

                if (i == size) {
                    if (j == 0) {
                        sb.append("___|");
                    } else {
                        sb.append("___").append(notation.charAt(j - 1)).append(notation.toLowerCase().charAt(j - 1)).append("___|");
                    }
                    continue;
                }

                if (listFigures[i][j - 1] != null) {
                    sb.append("   ").append(mapColorFigure.get(listFigures[i][j - 1].getColor()))
                            .append(mapFigure.get(listFigures[i][j - 1].getType())).append("   |");
                } else {
                    sb.append("        |");
                }
            }
            sb.append("\n");

            if (i != size) {
                sb.append("|");
                for (int j = 0; j < size + 1; j++) {
                    if (j == 0) {
                        sb.append("___|");
                    } else {
                        sb.append("________|");
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
