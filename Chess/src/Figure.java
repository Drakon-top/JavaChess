public interface Figure {

    boolean possibleMove(Position position, Move move);

    Color getColor();

    void makeMove(Move move);

    TypeFigure getType();

    int getRow();

    int getCol();

    Move[] getPossibleMoves(Position position);

    void makeMoveFuture(Move move);

    boolean getFirstMove();

}
