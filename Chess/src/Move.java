import java.util.Objects;

public class Move {
    private final FigurePosition posStart;
    private final FigurePosition posEnd;
    private final TypeFigure typeFigure;

    private final Color color;
    public Move(int rowStart, int colStart, int rowEnd, int colEnd, TypeFigure typeFigure, Color color) {
        this.color = color;
        this.posStart = new FigurePosition(rowStart, colStart);
        this.posEnd = new FigurePosition(rowEnd, colEnd);
        this.typeFigure = typeFigure;
    }

    public int getRowStart() {
        return posStart.getRow();
    }

    public int getColStart() {
        return posStart.getCol();
    }

    public int getColEnd() {
        return posEnd.getCol();
    }

    public int getRowEnd() {
        return posEnd.getRow();
    }

    public TypeFigure getTypeFigure() {
        return typeFigure;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return posStart.equals(move.posStart) && posEnd.equals(move.posEnd) && typeFigure == move.typeFigure && color == move.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posStart, posEnd, typeFigure, color);
    }

    @Override
    public String toString() {
        return getRowStart() + " " + getColStart() + " " + getRowEnd() + " " + getColEnd() + " " + getTypeFigure() + " " + getColor();
    }
}
