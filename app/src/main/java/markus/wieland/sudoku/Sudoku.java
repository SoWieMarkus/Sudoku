package markus.wieland.sudoku;

import android.os.CountDownTimer;

import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.Game;
import markus.wieland.games.game.GameEventListener;

public class Sudoku extends Game<SudokuGameState, SudokuGameResult> implements SudokuGameBoardFieldInteractListener {

    private long seconds;
    private final SudokuGameBoardView sudokuGameBoard;
    private int currentNumber;

    private final CountDownTimer timer;
    private final Difficulty difficulty;

    public Sudoku(GameEventListener<SudokuGameResult> gameEventListener, SudokuGameState sudokuGameState, SudokuGameBoardView sudokuGameBoard) {
        super(gameEventListener);
        this.seconds = sudokuGameState.getSeconds();
        this.difficulty = sudokuGameState.getDifficulty();
        this.currentNumber = 1;

        this.sudokuGameBoard = sudokuGameBoard;
        this.sudokuGameBoard.setSudokuGameBoardFieldInteractListener(this);
        this.sudokuGameBoard.loadGameState(sudokuGameState);
        this.sudokuGameBoard.updateDifficulty(difficulty);

        this.sudokuGameBoard.highlightNumber(currentNumber);
        this.sudokuGameBoard.checkGameForFinish();
        //this.sudokuGameBoard.showLines();

        timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                seconds++;
                sudokuGameBoard.updateTime(seconds);
            }

            @Override
            public void onFinish() {
                this.start();
            }
        };
    }

    @Override
    public void start() {
        super.start();
        timer.start();
    }

    @Override
    public void finish(SudokuGameResult gameResult) {
        super.finish(gameResult);
        timer.cancel();
    }


    @Override
    public SudokuGameState getGameState() {
        return new SudokuGameState(sudokuGameBoard.getGameState(), seconds, difficulty);
    }

    @Override
    public SudokuGameResult getResult() {
        return null;
    }

    @Override
    public void onClick(SudokuGameBoardFieldView sudokuGameBoardField) {
        sudokuGameBoard.clear();
        sudokuGameBoardField.setValue(currentNumber);
        sudokuGameBoard.highlightNumber(currentNumber);
        if (sudokuGameBoard.checkGameForFinish() != null) {
            finish(new SudokuGameResult(seconds, difficulty));
            sudokuGameBoard.clear();
            return;
        }
        sudokuGameBoard.clearPossibleNumbers();
    }

    @Override
    public void onLongClick(SudokuGameBoardFieldView sudokuGameBoardField) {
        sudokuGameBoardField.addPossibleNumber(currentNumber);
    }

    @Override
    public void selectNumber(int number) {
        this.currentNumber = number;
        sudokuGameBoard.clear();
        sudokuGameBoard.highlightNumber(currentNumber);
    }
}
