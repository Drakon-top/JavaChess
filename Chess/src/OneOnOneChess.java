import java.util.Map;

public class OneOnOneChess {
    private static final Map<String, TypeFigure> mapFigure = Map.of(
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

    public static void main(String[] args) {
        System.out.println("Chess");
        System.out.println("Players must enter codes in this format");
        System.out.println("{Name figure} {Start row} {Start col} {End row} {End col}");
        for (String item : mapFigure.keySet()) {
            System.out.println(item + " " + mapFigure.get(item));
        }
        System.out.println("Column is " + "ABCDEFGH");
        System.out.println("Castling is written like all moves");
        System.out.println();

        Player player1 = new HumanPlayer(Color.WHITE);
        Player player2 = new HumanPlayer(Color.BLACK);
        ChessGame game = new ChessGame(player1, player2);
        System.out.println(game.play(new ChessBoard()));
    }
}
