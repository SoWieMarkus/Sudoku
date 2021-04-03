package markus.wieland.sudoku;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import markus.wieland.games.game.Difficulty;
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

        Difficulty difficulty = Difficulty.EASY;

        gameSaver = new GameSaver<>(SudokuGameState.class, SudokuHighscore.class, this);
        SudokuGameState sudokuGameState = gameSaver.getGameState();
        if (sudokuGameState == null) sudokuGameState = new SudokuGameState(new SudokuGenerator(difficulty));

        sudoku = new Sudoku(this, findViewById(R.id.container), sudokuGameState);
        sudoku.start();

        for (int i = 1; i < 10; i++) {
            int finalI = i;
            findViewById(getResources().getIdentifier("number" + i, "id", getPackageName()))
                    .setOnClickListener(v -> sudoku.select(finalI));
        }
    }


    @Override
    protected void onStop() {
        gameSaver.save(sudoku.getGameState(), new SudokuHighscore());
        super.onStop();
    }

    @Override
    public void newSecond(long seconds) {
        ((TextView) findViewById(R.id.time)).setText(seconds + "s");
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