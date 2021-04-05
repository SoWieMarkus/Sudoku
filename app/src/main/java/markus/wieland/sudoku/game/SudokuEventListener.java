package markus.wieland.sudoku.game;

import markus.wieland.games.game.GameEventListener;

public interface SudokuEventListener extends GameEventListener<SudokuGameResult> {
    void newSecond(long seconds);
}
