package markus.wieland.sudoku;

import android.widget.LinearLayout;

import java.util.Collections;

import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.games.game.Game;
import markus.wieland.sudoku.gamestate.SudokuGameState;
import markus.wieland.sudoku.gamestate.SudokuGameStateField;

public class Sudoku extends Game<SudokuGameState> implements SudokuGameBoardFieldInteractListener {

    private long seconds;
    private final SudokuGameBoard sudokuGameBoard;
    private int currentNumber;

    public Sudoku(SudokuEventListener gameEventListener, LinearLayout linearLayout, SudokuGameState sudokuGameState) {
        super(gameEventListener);
        this.seconds = sudokuGameState.getSeconds();
        this.currentNumber = 1;
        sudokuGameBoard = new SudokuGameBoard(linearLayout, this);
        sudokuGameBoard.loadGameState(sudokuGameState);
        gameEventListener.newSecond(1);
    }

    @Override
    public SudokuGameState getGameState() {
        SerializableMatrix<SudokuGameStateField> sudokuGameStates = new SerializableMatrix<>(9, 9);
        for (SudokuGameBoardField sudokuGameBoardField : sudokuGameBoard.getMatrix()) {
            sudokuGameStates.set(sudokuGameBoardField.getX(), sudokuGameBoardField.getY(),
                    new SudokuGameStateField(sudokuGameBoardField.getValue(), sudokuGameBoardField.isGiven(),
                            sudokuGameBoardField.getPossibleNumbers(), sudokuGameBoardField.getX(), sudokuGameBoardField.getY()));
        }
        return new SudokuGameState(seconds, sudokuGameBoard);
    }

    public void select(int number) {
        sudokuGameBoard.highlight(number);
        currentNumber = number;
    }

    @Override
    public void onClick(SudokuGameBoardField sudokuGameBoardField) {
        if (sudokuGameBoardField.isGiven()) return;
        sudokuGameBoardField.setValue(currentNumber);
        if (sudokuGameBoard.checkForWin(0)) {
            finish();
            sudokuGameBoard.clear();
            return;
        }
        sudokuGameBoard.clear();
        sudokuGameBoard.highlight(currentNumber);
    }

    @Override
    public void onLongClick(SudokuGameBoardField sudokuGameBoardField) {
        if (sudokuGameBoardField.isGiven()) return;
        if (sudokuGameBoardField.getPossibleNumbers().contains(currentNumber)) {
            sudokuGameBoardField.getPossibleNumbers().remove(Integer.valueOf(currentNumber));
        } else {
            sudokuGameBoardField.getPossibleNumbers().add(currentNumber);
        }
        Collections.sort(sudokuGameBoardField.getPossibleNumbers());
        sudokuGameBoard.clear();
        sudokuGameBoard.highlight(currentNumber);
    }
}
