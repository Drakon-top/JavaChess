public interface Board {

    static final int text = 190;
    Result makeMove(Move move);

    Position getPosition();

    boolean moveIsValid(Move move);
}
