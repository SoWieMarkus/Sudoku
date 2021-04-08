package markus.wieland.sudoku;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.core.content.ContextCompat;

import java.util.Collections;
import java.util.List;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.game.GameBoardField;
import markus.wieland.games.game.grid.GridGameBoardFieldView;
import markus.wieland.games.game.view.GameStateField;

public class SudokuGameBoardFieldView extends androidx.appcompat.widget.AppCompatButton implements GridGameBoardFieldView {

    protected Coordinate coordinate;
    private boolean isGiven;
    private int value;
    private List<Integer> possibleNumbers;

    public SudokuGameBoardFieldView(Context context) {
        super(context);
    }

    public SudokuGameBoardFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuGameBoardFieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setValue(int number) {
        possibleNumbers.clear();
        this.value = value == number ? GameBoardField.FREE : number;
        update();
    }

    public void addPossibleNumber(int number) {
        this.value = GameBoardField.FREE;
        if (possibleNumbers.contains(number)) possibleNumbers.remove(Integer.valueOf(number));
        else possibleNumbers.add(number);
        Collections.sort(possibleNumbers);
        update();
    }

    public boolean isEmpty() {
        return value == GameBoardField.FREE;
    }

    public void markAsWrong() {
        setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.sudoku_field_background_wrong));
    }

    public void hint(int value) {
        setBackgroundDrawable(this.value == value
                ? ContextCompat.getDrawable(getContext(), R.drawable.sudoku_field_background_highlight)
                : ContextCompat.getDrawable(getContext(), R.drawable.sudoku_field_background_highlight_hint));
    }

    public void highlight(boolean completed) {
        setBackgroundDrawable(completed
                ? ContextCompat.getDrawable(getContext(), R.drawable.sudoku_field_background_hightlight_completed)
                : ContextCompat.getDrawable(getContext(), R.drawable.sudoku_field_background_highlight));
    }

    public void update() {
        setBackgroundDrawable(isGiven()
                ? ContextCompat.getDrawable(getContext(), R.drawable.sudoku_field_background_given)
                : ContextCompat.getDrawable(getContext(), R.drawable.sudoku_field_background));

        if (!possibleNumbers.isEmpty()) {
            setGravity(Gravity.START);
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            setText(buildStringFromPossibleNumbers());
            return;
        }

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        setGravity(Gravity.CENTER);
        setText(value == GameBoardField.FREE ? "" : String.valueOf(value));
    }

    private String buildStringFromPossibleNumbers() {
        return TextUtils.join(",", possibleNumbers);
    }

    @Override
    public void load(GameStateField stateField) {
        SudokuGameStateField sudokuGameStateField = (SudokuGameStateField) stateField;
        this.value = sudokuGameStateField.getValue();
        this.isGiven = sudokuGameStateField.isGiven();
        this.possibleNumbers = sudokuGameStateField.getPossibleNumbers();
        update();
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isGiven() {
        return isGiven;
    }

    public int getValue() {
        return value;
    }

    public List<Integer> getPossibleNumbers() {
        return possibleNumbers;
    }

    public SudokuGameStateField getSudokuGameStateField() {
        return new SudokuGameStateField(getCoordinate(), value, isGiven, possibleNumbers);
    }
}
