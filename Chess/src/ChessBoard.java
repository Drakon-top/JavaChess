import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessBoard implements Board {

    private Figure[][] listFigures;
    public static final int[] COL_DELTA_CHECK = new int[] {1, 1, -1, -1, 0, 0, 1, -1};
    public static final int[] ROW_DELTA_CHECK = new int[] {1, -1, 1, -1, 1, -1, 0, 0};
    private int size;
    private Color colorMotion;

    private FigurePosition posBlackKing;
    private FigurePosition posWhiteKing;

    private Move lastMove = null;

    public ChessBoard() {
        size = 8;
        listFigures = startPosition();
        colorMotion = Color.WHITE;
    }

    @Override
    public Result makeMove(Move move) {
        if (move.getTypeFigure() == TypeFigure.CAPITULATE) {
            return Result.LOSE;
        }
        if (!moveIsValid(move)) {
            return Result.INCORRECT;
        }

        if (move.getTypeFigure() == TypeFigure.CASTLINGSHORT) {
            if (!moveIsValid(new Move(move.getRowStart(), move.getColStart(), move.getRowEnd(),
                    move.getColEnd() - 1, TypeFigure.KING, move.getColor()))) {
                return Result.INCORRECT;
            }
        } else if (move.getTypeFigure() == TypeFigure.CASTLINGLONG) {
            if (!moveIsValid(new Move(move.getRowStart(), move.getColStart(), move.getRowEnd(),
                    move.getColEnd() - 1, move.getTypeFigure(), move.getColor()))) {
                return Result.INCORRECT;
            }
        }

        Figure figureNow = listFigures[move.getRowStart()][move.getColStart()];
        figureNow.makeMove(move);
        posBlackKing = positionKing(Color.BLACK);
        posWhiteKing = positionKing(Color.WHITE);
        if (figureNow.getType() == TypeFigure.PAWN && Math.abs(move.getColStart() - move.getColEnd()) == 1
                && listFigures[move.getRowEnd()][move.getColEnd()] == null) {
            listFigures[move.getRowStart()][move.getColEnd()] = null;
        }
        listFigures[move.getRowEnd()][move.getColEnd()] = figureNow;
        listFigures[move.getRowStart()][move.getColStart()] = null;
        if (move.getTypeFigure() == TypeFigure.CASTLINGSHORT) {
            listFigures[move.getRowStart()][move.getColStart() + 1] = listFigures[move.getRowEnd()][move.getColEnd() + 1];
            listFigures[move.getRowEnd()][move.getColEnd() + 1] = null;
        } else if (move.getTypeFigure() == TypeFigure.CASTLINGLONG) {
            listFigures[move.getRowStart()][move.getColStart() - 1] = listFigures[move.getRowEnd()][0];
            listFigures[move.getRowEnd()][0] = null;
        }
        lastMove = move;
        colorMotion = colorMotion == Color.WHITE ? Color.BLACK : Color.WHITE;
        boolean check = verifyCheck(colorMotion);
        boolean motion = impossibleMotion(colorMotion);
        if (check && motion) {
            return Result.WIN;
        } else if (motion) {
            return Result.DRAW;
        }

        return Result.UNKNOWN;
    }

    @Override
    public Position getPosition() {
        return new ChessPosition(size, colorMotion, listFigures, lastMove);
    }

    @Override
    public boolean moveIsValid(Move move) {
        return inBoard(move.getRowStart(), move.getColStart()) &&
                inBoard(move.getRowEnd(), move.getColEnd()) &&
                listFigures[move.getRowStart()][move.getColStart()] != null &&
                (listFigures[move.getRowStart()][move.getColStart()].getType() == move.getTypeFigure() ||
                        (listFigures[move.getRowStart()][move.getColStart()].getType() == TypeFigure.KING &&
                                (move.getTypeFigure() == TypeFigure.CASTLINGLONG || move.getTypeFigure() == TypeFigure.CASTLINGSHORT))) &&
                listFigures[move.getRowStart()][move.getColStart()].getColor() == colorMotion &&
                (listFigures[move.getRowEnd()][move.getColEnd()] == null ||
                        listFigures[move.getRowEnd()][move.getColEnd()].getType() != TypeFigure.KING) &&
                listFigures[move.getRowStart()][move.getColStart()].possibleMove(getPosition(), move) &&
                                !testMotionOnCheck(move, colorMotion);
    }

    private Figure[][] startPosition() {
        Figure[][] field = new Figure[size][size];
        // Black
        field[0][0] = new Rook(0,0, Color.BLACK);
        field[0][7] = new Rook(0,7, Color.BLACK);
        field[0][1] = new Knight(0,1, Color.BLACK);
        field[0][6] = new Knight(0,6, Color.BLACK);
        field[0][2] = new Bishop(0,2, Color.BLACK);
        field[0][5] = new Bishop(0,5, Color.BLACK);
        field[0][3] = new Queen(0,3, Color.BLACK);
        field[0][4] = new King(0,4, Color.BLACK);
        posBlackKing = new FigurePosition(0, 4);
        for (int i = 0; i < size; i++) {
            field[1][i] = new Pawn(1, i, Color.BLACK);
        }
        // White
        field[7][0] = new Rook(7,0, Color.WHITE);
        field[7][7] = new Rook(7,7, Color.WHITE);
        field[7][1] = new Knight(7,1, Color.WHITE);
        field[7][6] = new Knight(7,6, Color.WHITE);
        field[7][2] = new Bishop(7,2, Color.WHITE);
        field[7][5] = new Bishop(7,5, Color.WHITE);
        field[7][3] = new Queen(7,3, Color.WHITE);
        field[7][4] = new King(7,4, Color.WHITE);
        posWhiteKing = new FigurePosition(7, 4);
        for (int i = 0; i < size; i++) {
            field[6][i] = new Pawn(6, i, Color.WHITE);
        }
        return field;
    }


    private boolean testMotionOnCheck(Move move, Color color) {
        Figure figureNow = listFigures[move.getRowStart()][move.getColStart()];
        figureNow.makeMoveFuture(move);
        Figure figureEnd = listFigures[move.getRowEnd()][move.getColEnd()];
        listFigures[move.getRowEnd()][move.getColEnd()] = figureNow;
        listFigures[move.getRowStart()][move.getColStart()] = null;
        posBlackKing = positionKing(Color.BLACK);
        posWhiteKing = positionKing(Color.WHITE);
        boolean result = verifyCheck(color);
        posBlackKing = positionKing(Color.BLACK);
        posWhiteKing = positionKing(Color.WHITE);
        figureNow.makeMoveFuture(new Move(move.getRowEnd(), move.getColEnd(), move.getRowStart(),
                move.getColStart(), move.getTypeFigure(), move.getColor()));
        listFigures[move.getRowStart()][move.getColStart()] = figureNow;
        listFigures[move.getRowEnd()][move.getColEnd()] = figureEnd;
        return result;
    }

    private boolean verifyCheck(Color color) {
        FigurePosition posNow = color == Color.WHITE ? posWhiteKing : posBlackKing;
        for (int j = 0; j < COL_DELTA_CHECK.length; j++) {
            int nowRow = posNow.getRow() + ROW_DELTA_CHECK[j];
            int nowCol = posNow.getCol() + COL_DELTA_CHECK[j];
            while (inBoard(nowRow, nowCol)) {
                Figure nowFigure = listFigures[nowRow][nowCol];
                if (nowFigure == null) {
                    nowRow += ROW_DELTA_CHECK[j];
                    nowCol += COL_DELTA_CHECK[j];
                    continue;
                }
                if (nowFigure.getColor() != color && nowFigure.possibleMove(getPosition(), new Move(
                        nowRow, nowCol, posNow.getRow(), posNow.getCol(), nowFigure.getType(), nowFigure.getColor()))) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean inBoard(int row, int col) {
        return 0 <= row && row < size && 0 <= col && col < size;
    }

    private boolean impossibleMotion(Color color) {
        List<Figure> figuresOnBoard = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (listFigures[i][j] != null && listFigures[i][j].getColor() == color) {
                    figuresOnBoard.add(listFigures[i][j]);
                }
            }
        }

        Position nowPosition = getPosition();
        for (Figure figure : figuresOnBoard) {
            Move[] moves = figure.getPossibleMoves(nowPosition);
            for (Move move : moves) {
                if (moveIsValid(move)) {
                    System.out.println(move);
                    return false;
                }
            }
        }
        return true;
    }

    private FigurePosition positionKing(Color color) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (listFigures[i][j] != null && listFigures[i][j].getType() == TypeFigure.KING && listFigures[i][j].getColor() == color) {
                    return new FigurePosition(i , j);
                }
            }
        }
        return new FigurePosition(0, 0);
    }

    private void test() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (listFigures[i][j] == null) {
                    System.out.print(i + " " + j + " null ");
                } else {
                    System.out.print(listFigures[i][j].getRow() + " " + listFigures[i][j].getCol() + " " + listFigures[i][j].getType() + " ");
                }
            }
            System.out.println();
        }
    }
}
