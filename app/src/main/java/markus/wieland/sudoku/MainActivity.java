package markus.wieland.sudoku;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import markus.wieland.games.persistence.GameSaver;
import markus.wieland.sudoku.gamestate.SudokuGameState;
import markus.wieland.sudoku.generator.SudokuGenerator;

public class MainActivity extends AppCompatActivity implements SudokuEventListener {

    private GameSaver<SudokuGameState> gameSaver;
    private Sudoku sudoku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameSaver = new GameSaver<>(SudokuGameState.class, this);
        SudokuGameState sudokuGameState = gameSaver.getGameState();
        if (sudokuGameState == null) sudokuGameState = new SudokuGameState(new SudokuGenerator());

        sudoku = new Sudoku(this, null, sudokuGameState);
        sudoku.start();
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

    }
}