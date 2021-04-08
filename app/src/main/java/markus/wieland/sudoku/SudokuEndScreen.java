package markus.wieland.sudoku;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import markus.wieland.games.screen.view.EndScreenView;

public class SudokuEndScreen extends EndScreenView {

    private TextView textViewNewHighscore;
    private TextView textViewScore;
    private TextView textViewHighscore;


    public SudokuEndScreen(@NonNull Context context) {
        super(context);
    }

    public SudokuEndScreen(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuEndScreen(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onNewGameResult() {
        SudokuGameResult sudokuGameResult = (SudokuGameResult) gameResult;
        setBackgroundColor(getContext().getColor(R.color.win));

        textViewNewHighscore.setVisibility(sudokuGameResult.isNewHighscore() ? VISIBLE : GONE);
        textViewScore.setText(DateUtils.formatElapsedTime(sudokuGameResult.getSeconds()));
        textViewHighscore.setText(DateUtils.formatElapsedTime(sudokuGameResult.getHighscore()));
    }

    @Override
    protected void onBuild() {
        findViewById(R.id.sudoku_end_screen_play_again).setOnClickListener(view -> close(true));
        findViewById(R.id.sudoku_end_screen_back).setOnClickListener(view -> close(false));

        textViewHighscore = findViewById(R.id.sudoku_end_screen_highscore);
        textViewNewHighscore = findViewById(R.id.sudoku_end_screen_new_highscore);
        textViewScore = findViewById(R.id.sudoku_end_screen_score);
    }
}
