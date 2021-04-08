package markus.wieland.sudoku;

import androidx.core.content.ContextCompat;

import markus.wieland.games.game.view.GameBoardInteractionListener;

public interface SudokuGameBoardFieldInteractListener extends GameBoardInteractionListener {

    void onClick(SudokuGameBoardFieldView sudokuGameBoardField);

    void onLongClick(SudokuGameBoardFieldView sudokuGameBoardField);

    void selectNumber(int number);

    void selectHint();

    void abortGame();

}
