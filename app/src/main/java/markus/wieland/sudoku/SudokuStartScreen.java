package markus.wieland.sudoku;

import android.app.Activity;

import markus.wieland.games.game.Difficulty;
import markus.wieland.games.screen.StartScreen;

public class SudokuStartScreen extends StartScreen {

    private Difficulty difficulty;

    public SudokuStartScreen(Activity activity) {
        super(activity.findViewById(R.id.startmenu));
        this.difficulty = Difficulty.MEDIUM;
        activity.findViewById(R.id.easy).setOnClickListener(v -> setDifficulty(Difficulty.EASY));
        activity.findViewById(R.id.medium).setOnClickListener(v -> setDifficulty(Difficulty.MEDIUM));
        activity.findViewById(R.id.hard).setOnClickListener(v -> setDifficulty(Difficulty.HARD));
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    protected SudokuConfiguration getConfiguration() {
        return new SudokuConfiguration(difficulty);
    }
}
