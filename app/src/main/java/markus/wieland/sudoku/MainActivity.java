package markus.wieland.sudoku;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import markus.wieland.games.GameActivity;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameConfiguration;
import markus.wieland.games.persistence.GameGenerator;
import markus.wieland.games.persistence.GameSaver;
import markus.wieland.games.screen.EndScreen;
import markus.wieland.games.screen.StartScreen;
import markus.wieland.sudoku.game.Sudoku;
import markus.wieland.sudoku.game.SudokuEventListener;
import markus.wieland.sudoku.game.SudokuGameResult;
import markus.wieland.sudoku.game.SudokuHighscore;
import markus.wieland.sudoku.game.gamestate.SudokuGameState;
import markus.wieland.sudoku.game.generator.SudokuGenerator;

public class MainActivity extends GameActivity<SudokuHighscore,SudokuGameState, SudokuGameResult, Sudoku> implements SudokuEventListener{

    private GameSaver<SudokuGameState, SudokuHighscore> gameSaver;
    private Sudoku sudoku;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Difficulty difficulty = Difficulty.EASY;


        for (int i = 1; i < 10; i++) {
            int finalI = i;
            findViewById(getResources().getIdentifier("number" + i, "id", getPackageName()))
                    .setOnClickListener(v -> sudoku.select(finalI));
        }
    }

    @Override
    protected StartScreen initializeStartScreen() {
        return null;
    }

    @Override
    protected EndScreen initializeEndScreen() {
        return null;
    }


    @Override
    protected void onStop() {
        //TODO
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
    public void onGameFinish(SudokuGameResult gameResult) {
        SudokuHighscore highscore = gameSaver.getHighScore();
        gameResult.setNewHighscore(highscore.update(gameResult));
        gameSaver.save(null, highscore);
        super.onGameFinish(gameResult);
        gameSaver.delete();
    }

    @Override
    protected GameGenerator<SudokuGameState> initializeGenerator(GameConfiguration configuration) {
        return new SudokuGenerator(((SudokuConfiguration)configuration).getDifficulty());
    }

    @Override
    protected GameSaver<SudokuGameState, SudokuHighscore> initializeGameSaver() {
        return new GameSaver<>(SudokuGameState.class, SudokuHighscore.class, this);
    }

    @Override
    protected void initializeGame(SudokuGameState sudokuGameState) {
        sudoku = new Sudoku(this, findViewById(R.id.container), sudokuGameState);
        sudoku.start();
    }
}