package markus.wieland.sudoku;

import java.io.Serializable;
import java.util.HashMap;

import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.Highscore;

public class SudokuHighscore extends Highscore implements Serializable {

    private final HashMap<Difficulty, Long> records;

    private static final long NONE_VALUE = -1L;

    public SudokuHighscore() {
        records = new HashMap<>();
        records.put(Difficulty.EASY, NONE_VALUE);
        records.put(Difficulty.MEDIUM, NONE_VALUE);
        records.put(Difficulty.HARD, NONE_VALUE);
    }

    public boolean update(SudokuGameResult gameResult) {
        Long currentRecord = records.get(gameResult.getDifficulty());
        if (currentRecord == null)
            throw new IllegalArgumentException("Is not possible to store a record for difficulty " + gameResult.getDifficulty());
        if (currentRecord == NONE_VALUE || currentRecord > gameResult.getSeconds()) {
            records.put(gameResult.getDifficulty(), gameResult.getSeconds());
            return true;
        }
        return false;
    }

    public long getHighscore(Difficulty difficulty){
        return records.get(difficulty);
    }

}
