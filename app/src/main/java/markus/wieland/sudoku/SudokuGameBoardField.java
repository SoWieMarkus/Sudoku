package markus.wieland.sudoku;

import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import markus.wieland.games.game.GameBoardField;

public class SudokuGameBoardField extends GameBoardField implements View.OnClickListener, View.OnLongClickListener {

    private List<Integer> possibleNumbers;
    private boolean isGiven;

    private final SudokuGameBoardFieldInteractListener sudokuGameBoardFieldInteractListener;
    private final int x;
    private final int y;

    public SudokuGameBoardField(int x, int y, View view, SudokuGameBoardFieldInteractListener sudokuGameBoardFieldInteractListener) {
        super(view);
        this.x = x;
        this.y = y;
        this.isGiven = false;
        this.possibleNumbers = new ArrayList<>();
        this.sudokuGameBoardFieldInteractListener = sudokuGameBoardFieldInteractListener;

        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
    }

    public void setPossibleNumbers(List<Integer> possibleNumbers) {
        this.possibleNumbers = possibleNumbers;
        update();
    }

    public void setGiven(boolean given) {
        isGiven = given;
        update();
    }

    public List<Integer> getPossibleNumbers() {
        return possibleNumbers;
    }

    public boolean isGiven() {
        return isGiven;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void update() {
        Button gameFieldView = (Button) view;

        if (!possibleNumbers.isEmpty()) {
            gameFieldView.setGravity(Gravity.START);
            gameFieldView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            gameFieldView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            gameFieldView.setText(buildStringFromPossibleNumbers());
            return;
        }

        gameFieldView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        gameFieldView.setGravity(Gravity.CENTER);
        gameFieldView.setText(value == FREE ? "" : String.valueOf(value));
        gameFieldView.setBackgroundColor(
                isGiven ? Color.parseColor("#F2F2F2")
                        : Color.parseColor("#FFFFFF"));
    }

    @Override
    public void setValue(int value){
        this.value = this.value == value ? FREE : value;
    }

    public void markAsWrong() {
        view.setBackgroundColor(Color.parseColor("#FF0000"));
    }

    public void highlight() {
        view.setBackgroundColor(Color.parseColor("#8BBADC"));
    }

    private String buildStringFromPossibleNumbers() {
        Collections.sort(possibleNumbers);
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < possibleNumbers.size(); i++) {
            Integer integer = possibleNumbers.get(i);
            if (i == possibleNumbers.size() - 1) text.append(integer);
            else text.append(integer).append(",");
        }
        return text.toString();
    }


    @Override
    public void onClick(View v) {
        sudokuGameBoardFieldInteractListener.onClick(SudokuGameBoardField.this);
    }

    @Override
    public boolean onLongClick(View v) {
        sudokuGameBoardFieldInteractListener.onLongClick(SudokuGameBoardField.this);
        return true;
    }
}
