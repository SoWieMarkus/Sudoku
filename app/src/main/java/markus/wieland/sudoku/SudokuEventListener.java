package markus.wieland.sudoku;

import markus.wieland.games.game.GameEventListener;

public interface SudokuEventListener extends GameEventListener {
    void newSecond(long seconds);
}
