package markus.wieland.games.game;

public interface GameEventListener {

    void onStart();
    void onPause();
    void onFinish();
}
