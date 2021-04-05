package markus.wieland.sudoku.game.gamestate;

import java.io.Serializable;

import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.persistence.GameState;
import markus.wieland.sudoku.game.gameboard.SudokuGameBoard;
import markus.wieland.sudoku.game.gameboard.SudokuGameBoardField;
import markus.wieland.sudoku.game.generator.SudokuGenerator;

public class SudokuGameState implements GameState, Serializable {

    private final long seconds;
    private final Difficulty difficulty;
    private final SerializableMatrix<SudokuGameStateField> matrix;

    public SudokuGameState(Difficulty difficulty, long seconds, SudokuGameBoard gameBoard) {
        this.seconds = seconds;
        this.difficulty = difficulty;
        this.matrix = new SerializableMatrix<>(9, 9);

        for (SudokuGameBoardField sudokuGameBoardField : gameBoard) {
            SudokuGameStateField stateField = new SudokuGameStateField(
                    sudokuGameBoardField.getValue(),
                    sudokuGameBoardField.isGiven(),
                    sudokuGameBoardField.getPossibleNumbers(),
                    sudokuGameBoardField.getX(),
                    sudokuGameBoardField.getY());
            matrix.set(sudokuGameBoardField.getX(), sudokuGameBoardField.getY(), stateField);
        }
    }

    public SudokuGameState(SudokuGenerator sudokuGenerator) {
        this.seconds = 0;
        this.matrix = sudokuGenerator.getMatrix();
        this.difficulty = sudokuGenerator.getDifficulty();
    }

    public long getSeconds() {
        return seconds;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public SerializableMatrix<SudokuGameStateField> getMatrix() {
        return matrix;
    }
}

