package markus.wieland.sudoku.gamestate;

import java.io.Serializable;

import markus.wieland.games.persistence.GameState;
import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.sudoku.SudokuGameBoard;
import markus.wieland.sudoku.SudokuGameBoardField;
import markus.wieland.sudoku.generator.SudokuGenerator;

public class SudokuGameState extends GameState implements Serializable {

    private final long seconds;
    private final SerializableMatrix<SudokuGameStateField> matrix;

    public SudokuGameState(long seconds, SudokuGameBoard gameBoard) {
        this.seconds = seconds;
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
    }

    public long getSeconds() {
        return seconds;
    }

    public SerializableMatrix<SudokuGameStateField> getMatrix() {
        return matrix;
    }
}

