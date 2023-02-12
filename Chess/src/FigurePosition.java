import java.util.Objects;

public class FigurePosition {
    private final int row;
    private final int col;

    public FigurePosition(int row, int col) {
        this.row = row;
        this.col = col;
    }


    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FigurePosition that = (FigurePosition) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
