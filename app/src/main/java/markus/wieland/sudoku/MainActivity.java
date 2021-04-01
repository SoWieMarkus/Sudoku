package markus.wieland.sudoku;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import markus.wieland.games.persistence.GameSaver;
import markus.wieland.sudoku.game.Sudoku;
import markus.wieland.sudoku.game.SudokuEventListener;
import markus.wieland.sudoku.game.SudokuHighscore;
import markus.wieland.sudoku.game.gamestate.SudokuGameState;
import markus.wieland.sudoku.game.generator.SudokuGenerator;

public class MainActivity extends AppCompatActivity implements SudokuEventListener {

    private GameSaver<SudokuGameState, SudokuHighscore> gameSaver;
    private Sudoku sudoku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameSaver = new GameSaver<>(SudokuGameState.class, SudokuHighscore.class, this);
        SudokuGameState sudokuGameState = gameSaver.getGameState();
        if (sudokuGameState == null) sudokuGameState = new SudokuGameState(new SudokuGenerator());

        sudoku = new Sudoku(this, findViewById(R.id.container), sudokuGameState);
        sudoku.start();
    }

    @Override
    protected void onStop() {
        gameSaver.save(sudoku.getGameState(), new SudokuHighscore());
        super.onStop();
    }

    @Override
    public void newSecond(long seconds) {

    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGamePause() {

    }

    @Override
    public void onGameFinish() {
        gameSaver.delete();
    }
}