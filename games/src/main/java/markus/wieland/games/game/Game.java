package markus.wieland.games.game;

import markus.wieland.games.persistence.GameState;

public abstract class Game<S extends GameState> {

    protected boolean isRunning;
    protected GameEventListener gameEventListener;

    public Game(GameEventListener gameEventListener) {
        isRunning = false;
        this.gameEventListener = gameEventListener;
    }

    public void start() {
        isRunning = true;
        this.gameEventListener.onStart();
    }

    public void pause() {
        isRunning = false;
        this.gameEventListener.onPause();
    }

    public void finish() {
        isRunning = false;
        this.gameEventListener.onFinish();
    }

    public abstract S getGameState();

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
