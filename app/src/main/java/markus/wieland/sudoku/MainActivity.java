package markus.wieland.sudoku;

import android.os.Bundle;
import android.view.WindowManager;

import markus.wieland.games.GameActivity;
import markus.wieland.games.game.GameConfiguration;
import markus.wieland.games.persistence.GameGenerator;
import markus.wieland.games.persistence.GameSaver;
import markus.wieland.games.screen.view.EndScreenView;
import markus.wieland.games.screen.view.StartScreenView;

public class MainActivity extends GameActivity<SudokuConfiguration, SudokuHighscore, markus.wieland.sudoku.SudokuGameState, SudokuGameResult, Sudoku> {

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected StartScreenView initializeStartScreen() {
        return findViewById(R.id.activity_sudoku_start_screen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected EndScreenView initializeEndScreen() {
        return findViewById(R.id.activity_sudoku_end_screen);
    }

    @Override
    protected void onStop() {
        if (game != null && gameSaver != null && game.isRunning())
            gameSaver.save(game.getGameState());
        super.onStop();
    }

    @Override
    public void onGameFinish(SudokuGameResult gameResult) {
        SudokuHighscore highscore = gameSaver.getHighScore();
        if (highscore == null) highscore = new SudokuHighscore();

        boolean newHighscore = highscore.update(gameResult);
        long highscoreValue = highscore.getHighscore(gameResult.getDifficulty());

        gameResult.setNewHighscore(newHighscore, highscoreValue);
        gameSaver.save((SudokuGameState) null);
        super.onGameFinish(gameResult);
        gameSaver.delete();
        gameSaver.save(highscore);
    }

    @Override
    protected GameGenerator<SudokuGameState> initializeGenerator(GameConfiguration configuration) {
        return new SudokuGenerator(((SudokuConfiguration) configuration));
    }

    @Override
    protected GameSaver<SudokuGameState, SudokuHighscore> initializeGameSaver() {
        return new GameSaver<>(SudokuGameState.class, SudokuHighscore.class, this);
    }

    @Override
    protected void initializeGame(SudokuGameState sudokuGameState) {
        game = new Sudoku(this, sudokuGameState, findViewById(R.id.activity_sudoku_game_board));
        game.start();
    }
}