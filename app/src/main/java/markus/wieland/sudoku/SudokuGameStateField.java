package markus.wieland.sudoku;

import java.util.List;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.game.grid.GridGameStateField;

public class SudokuGameStateField extends GridGameStateField {

    private final int value;
    private final boolean isGiven;
    private final List<Integer> possibleNumbers;

    public SudokuGameStateField(Coordinate coordinate, int value, boolean isGiven, List<Integer> possibleNumbers) {
        super(coordinate);
        this.value = value;
        this.isGiven = isGiven;
        this.possibleNumbers = possibleNumbers;
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
