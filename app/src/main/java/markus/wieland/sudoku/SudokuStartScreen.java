package markus.wieland.sudoku;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import markus.wieland.games.game.Difficulty;
import markus.wieland.games.screen.view.StartScreenView;

public class SudokuStartScreen extends StartScreenView {

    private Difficulty difficulty;

    public SudokuStartScreen(@NonNull Context context) {
        super(context);
    }

    public SudokuStartScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuStartScreen(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        close();
    }

    @Override
    protected void onShow() {
        super.onShow();
        setBackgroundColor(getContext().getColor(R.color.start));
    }

    @Override
    protected SudokuConfiguration getConfiguration() {
        return new SudokuConfiguration(difficulty);
    }

    @Override
    protected void onBuild() {
        this.difficulty = Difficulty.MEDIUM;
        findViewById(R.id.sudoku_start_screen_easy).setOnClickListener(v -> setDifficulty(Difficulty.EASY));
        findViewById(R.id.sudoku_start_screen_medium).setOnClickListener(v -> setDifficulty(Difficulty.MEDIUM));
        findViewById(R.id.sudoku_start_screen_hard).setOnClickListener(v -> setDifficulty(Difficulty.HARD));
    }
}
