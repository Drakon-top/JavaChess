public class ChessGame {

    private final Player player1;
    private final Player player2;

    public ChessGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
        while (true) {
            int result;
            for (int i = 0; i < 2; i++) {
                Player player = i == 0 ? player1 : player2;
                try {
                    result = move(board, player, i + 1);
                } catch (final NullPointerException e) {
                    result = i + 1;
                }
                if (result != -1) {
                    System.out.println(board.getPosition());
                    return result;
                }

            }
        }
    }

    private int move(Board board, Player player, int num) {
        Move move = player.move(board.getPosition());
        if (move.getTypeFigure() == TypeFigure.CAPITULATE) {
            System.out.println("Player " + num + " capitulate");
            System.out.println("Player " + (3 - num) + " won");
        }
        Result result = board.makeMove(move);
        while (result == Result.INCORRECT) {
            System.out.println("Incorrect Move");
            move = player.move(board.getPosition());
            result = board.makeMove(move);
        }

        System.out.println("Player " + num + " make move " + move);
//        System.out.println("Position:\n" + board.getPosition());
        System.out.println();
        if (result == Result.WIN) {
            System.out.println("Player " + num + " won");
            return num;
        } else if (result == Result.LOSE) {
            System.out.println("Player " + num + " lose");
            return 3 - num;
        } else if (result == Result.DRAW) {
            System.out.println("Draw");
            return 0;
        } else {
            return -1;
        }
    }
}
