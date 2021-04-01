package markus.wieland.games.game;

public interface GameBoardInteractionListener<E extends GameBoardField> {
    void onMove(int x, int y, E e);
}
