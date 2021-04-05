package markus.wieland.sudoku;

import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameConfiguration;

public class SudokuConfiguration implements GameConfiguration {

    private final Difficulty difficulty;

    public SudokuConfiguration(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
