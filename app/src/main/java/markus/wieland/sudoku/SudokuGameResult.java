package markus.wieland.sudoku;

import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameResult;

public class SudokuGameResult implements GameResult {

    private final long seconds;
    private final Difficulty difficulty;
    private boolean newHighscore;
    private long highscore;

    public SudokuGameResult(long seconds, Difficulty difficulty) {
        this.seconds = seconds;
        this.difficulty = difficulty;
        this.newHighscore = false;
        this.highscore = 0;
    }

    public boolean isNewHighscore() {
        return newHighscore;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setNewHighscore(boolean newHighscore, long highscore) {
        this.newHighscore = newHighscore;
        this.highscore = highscore;
    }

    public long getSeconds() {
        return seconds;
    }

    public long getHighscore() {
        return highscore;
    }
}
