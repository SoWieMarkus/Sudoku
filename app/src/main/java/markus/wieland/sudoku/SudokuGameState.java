package markus.wieland.sudoku;

import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.grid.GridGameState;

public class SudokuGameState extends GridGameState<SudokuGameStateField> {

    private final long seconds;
    private final Difficulty difficulty;

    public SudokuGameState(SerializableMatrix<SudokuGameStateField> matrix, long seconds, Difficulty difficulty) {
        super(matrix);
        this.seconds = seconds;
        this.difficulty = difficulty;
    }

    public long getSeconds() {
        return seconds;
    }

    public SudokuGameState(SudokuGenerator sudokuGenerator) {
        super(sudokuGenerator.getMatrix());
        this.seconds = 0;
        this.difficulty = sudokuGenerator.getDifficulty();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}
