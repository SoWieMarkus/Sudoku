package markus.wieland.sudoku.game.gameboard;

import markus.wieland.games.game.GameBoardInteractionListener;
import markus.wieland.sudoku.game.gameboard.SudokuGameBoardField;

public interface SudokuGameBoardFieldInteractListener extends GameBoardInteractionListener<SudokuGameBoardField> {

    void onClick(SudokuGameBoardField sudokuGameBoardField);
    void onLongClick(SudokuGameBoardField sudokuGameBoardField);

}
