import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    private final Color color;

    private final Map<String, TypeFigure> mapFigure = Map.of(
            "K", TypeFigure.KING,
            "Q", TypeFigure.QUEEN,
            "N", TypeFigure.KNIGHT,
            "B", TypeFigure.BISHOP,
            "R", TypeFigure.ROOK,
            "P", TypeFigure.PAWN,
            "X", TypeFigure.CAPITULATE,
            "0-0", TypeFigure.CASTLINGSHORT,
            "0-0-0", TypeFigure.CASTLINGLONG
    );

    private final Map<String, Integer> mapNotation = Map.of(
            "A", 0,
            "B", 1,
            "C", 2,
            "D", 3,
            "E", 4,
            "F", 5,
            "G", 6,
            "H", 7
    );

    public HumanPlayer(final PrintStream out, final Scanner in, Color color) {
        this.out = out;
        this.in = in;
        this.color = color;
    }

    public HumanPlayer(Color color) {
        this(System.out, new Scanner(System.in), color);
    }

    @Override
    public Move move(Position position) {
        out.println("Position");
        out.println(position);
        out.println("Enter figure row and col start row and col end with space in one line");
        while (true) {
            try {
                return parseMove();
            } catch (Exception e) {
                out.println("Incorrect input");
                out.println("Enter figure row and col start row and col end with space in one line");
                for (String item : mapFigure.keySet()) {
                    out.println(item + " " + mapFigure.get(item));
                }
            }
        }
    }

    private Move parseMove() throws Exception {
        String typeIn = in.next().toUpperCase();
        if (!mapFigure.containsKey(typeIn)) {
            throw new IllegalArgumentException();
        }
        TypeFigure type = mapFigure.get(typeIn);
        if (type == TypeFigure.CAPITULATE) {
            return new Move(-1, -1, -1, -1, type, color);
        }
        String rowStringStart = in.next().toUpperCase();
        if (!mapNotation.containsKey(rowStringStart)) {
            throw new IllegalArgumentException();
        }
        int colStart = mapNotation.get(rowStringStart);
        int rowStart = 8 - in.nextInt();
        String rowStringEnd = in.next().toUpperCase();
        if (!mapNotation.containsKey(rowStringEnd)) {
            throw new IllegalArgumentException();
        }
        int colEnd = mapNotation.get(rowStringEnd);
        int rowEnd = 8 - in.nextInt();
        return new Move(rowStart, colStart, rowEnd, colEnd, type, color);
    }
}
