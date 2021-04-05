package markus.wieland.sudoku.game;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameResult;
import markus.wieland.games.game.Highscore;

public class SudokuHighscore extends Highscore implements Serializable {

    private final EnumMap<Difficulty, Long> records;

    private static final long NONE_VALUE = -1L;

    public SudokuHighscore() {
        records = new EnumMap<>(Difficulty.class);
        records.put(Difficulty.EASY, NONE_VALUE);
        records.put(Difficulty.MEDIUM, NONE_VALUE);
        records.put(Difficulty.HARD, NONE_VALUE);
    }

    public SudokuHighscore(Map<Difficulty, Long> records) {
        this.records = (EnumMap<Difficulty, Long>) records;
    }

    public boolean update(SudokuGameResult gameResult) {
        Long currentRecord = records.get(gameResult.getDifficulty());
        if (currentRecord == null) throw new IllegalArgumentException("Isnt not possible to store a record for difficulty " + gameResult.getDifficulty());
        if (currentRecord == NONE_VALUE || currentRecord > gameResult.getSeconds()) {
            records.put(gameResult.getDifficulty(), gameResult.getSeconds());
            return true;
        }
        return false;
    }

}
