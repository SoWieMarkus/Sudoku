package markus.wieland.sudoku;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import markus.wieland.games.screen.EndScreen;
import markus.wieland.sudoku.game.SudokuGameResult;

public class SudokuEndScreen extends EndScreen {

    public SudokuEndScreen(@NonNull ViewGroup background) {
        super(background);
    }

    @Override
    protected void update() {
        SudokuGameResult sudokuGameResult = (SudokuGameResult) gameResult;

    }
}
