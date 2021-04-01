package markus.wieland.sudoku;

import markus.wieland.games.game.GameBoardInteractionListener;

public interface SudokuGameBoardFieldInteractListener extends GameBoardInteractionListener<SudokuGameBoardField> {

    void onClick(SudokuGameBoardField sudokuGameBoardField);
    void onLongClick(SudokuGameBoardField sudokuGameBoardField);

}
