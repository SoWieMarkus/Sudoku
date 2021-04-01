package markus.wieland.sudoku;

import android.graphics.Color;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.elements.Line;
import markus.wieland.games.game.GameBoard;
import markus.wieland.games.game.GameBoardInteractionListener;
import markus.wieland.sudoku.gamestate.SudokuGameState;
import markus.wieland.sudoku.gamestate.SudokuGameStateField;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SudokuGameBoard extends GameBoard<LinearLayout, SudokuGameBoardField, SudokuGameState> implements SudokuGameBoardFieldInteractListener {

    public SudokuGameBoard(LinearLayout layout, GameBoardInteractionListener<SudokuGameBoardField> listener) {
        super(9, 9, layout, listener);
    }

    @Override
    protected void initializeLines() {
        // horizontal
        for (int x = 0; x < 9; x++) {
            Line line = new Line();
            for (int y = 0; y < 9; y++) {
                line.add(new Coordinate(x, y));
            }
            lines.add(line);
        }

        // vertical
        for (int y = 0; y < 9; y++) {
            Line line = new Line();
            for (int x = 0; x < 9; x++) {
                line.add(new Coordinate(x, y));
            }
            lines.add(line);
        }

        addLine(0, 3, 0, 3);
        addLine(0, 3, 3, 6);
        addLine(0, 3, 6, 9);

        addLine(3, 6, 0, 3);
        addLine(3, 6, 3, 6);
        addLine(3, 6, 6, 9);

        addLine(6, 9, 0, 3);
        addLine(6, 9, 3, 6);
        addLine(6, 9, 6, 9);

    }

    private void addLine(int minX, int maxX, int minY, int maxY) {
        for (int x = minX; x < maxX; x++) {
            Line line = new Line();
            for (int y = minY; y < maxY; y++) {
                line.add(new Coordinate(x, y));
            }
            lines.add(line);
        }
    }

    @Override
    protected void initializeFields() {
        for (int i = 0; i < 3; i++) {
            layout.addView(buildRow(i * 3, (i + 1) * 3));
        }


        matrix.set(0, 0, new SudokuGameBoardField(0, 0, layout, this));
    }

    private ConstraintLayout buildRow(int minX, int maxX) {
        ConstraintLayout row = new ConstraintLayout(layout.getContext());
        row.setBackgroundColor(Color.parseColor("#000"));
        row.setLayoutParams(new ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        for (int i = 0; i < 3; i++) {
            row.addView(buildBox(minX, maxX, i * 3, (i + 1) * 3));
        }


        return row;
    }

    private ConstraintLayout buildBox(int minX, int maxX, int minY, int maxY) {
        return null;
    }

    public boolean checkCorrect() {
        clear();
        boolean error = false;
        for (Line line : lines) {
            List<Integer> multipleValues = getMultipleValues(line);
            if (!multipleValues.isEmpty()){
                markAsWrong(multipleValues, line);
                error = true;
            }
        }
        return !error;
    }

    private void markAsWrong(List<Integer> multipleValues, Line line) {
        for (Coordinate coordinate : line) {
            SudokuGameBoardField field = matrix.get(coordinate);
            if (multipleValues.contains(field.getValue()))
                field.markAsWrong();
        }
    }

    private List<Integer> getMultipleValues(Line line){
        List<Integer> multipleValues = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (Coordinate coordinate : line) {
            int value = matrix.get(coordinate).getValue();
            if (values.contains(value)) multipleValues.add(value);
            values.add(matrix.get(coordinate).getValue());
        }
        return multipleValues;
    }

    private boolean checkComplete() {
        for (SudokuGameBoardField field : matrix) {
            if (field.isEmpty()) return false;
        }
        return true;
    }


    @Override
    public boolean checkForWin(int player) {
        return checkCorrect() && checkComplete();
    }

    @Override
    public boolean checkForTie() {
        return false;
    }

    public void clear() {
        for (SudokuGameBoardField gameBoardField : matrix) {
            gameBoardField.update();
        }
    }

    public void highlight(int value) {
        clear();
        for (SudokuGameBoardField gameBoardField : matrix) {
            if (gameBoardField.getValue() == value) gameBoardField.highlight();
        }
    }

    @Override
    protected void loadGameState(SudokuGameState gameState) {
        for (SudokuGameStateField field : gameState.getMatrix()) {
            SudokuGameBoardField gameBoardField = matrix.get(field.getX(), field.getY());
            gameBoardField.setGiven(field.isGiven());
            gameBoardField.setPossibleNumbers(field.getPossibleNumbers());
            gameBoardField.update();
        }
    }

    @Override
    public void onClick(int x, int y, SudokuGameBoardField sudokuGameBoardField) {
        gameBoardInteractionListener.onMove(x,y,sudokuGameBoardField);
    }
}
