package markus.wieland.sudoku.game;

import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameResult;

public class SudokuGameResult implements GameResult {

    private final long seconds;
    private final Difficulty difficulty;
    private boolean newHighscore;

    public SudokuGameResult(long seconds, Difficulty difficulty) {
        this.seconds = seconds;
        this.difficulty = difficulty;
    }

    public boolean isNewHighscore() {
        return newHighscore;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setNewHighscore(boolean newHighscore) {
        this.newHighscore = newHighscore;
    }

    public long getSeconds() {
        return seconds;
    }
}
