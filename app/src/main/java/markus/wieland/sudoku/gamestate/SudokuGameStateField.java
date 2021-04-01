package markus.wieland.sudoku.gamestate;

import java.io.Serializable;
import java.util.List;

public class SudokuGameStateField implements Serializable {

    private final int value;
    private final boolean isGiven;
    private final List<Integer> possibleNumbers;
    private final int x;
    private final int y;

    public SudokuGameStateField(int value, boolean isGiven, List<Integer> possibleNumbers, int x, int y) {
        this.value = value;
        this.isGiven = isGiven;
        this.possibleNumbers = possibleNumbers;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public boolean isGiven() {
        return isGiven;
    }

    public List<Integer> getPossibleNumbers() {
        return possibleNumbers;
    }
}
