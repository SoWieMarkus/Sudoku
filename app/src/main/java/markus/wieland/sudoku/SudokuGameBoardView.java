package markus.wieland.sudoku;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import markus.wieland.games.elements.Coordinate;
import markus.wieland.games.elements.Line;
import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.game.GameBoardField;
import markus.wieland.games.game.grid.GridGameBoardView;
import markus.wieland.games.persistence.GameState;

public class SudokuGameBoardView extends GridGameBoardView<SudokuGameBoardFieldView> implements View.OnClickListener, View.OnLongClickListener {

    private TextView textViewDifficulty;
    private TextView textViewSeconds;
    private SudokuGameBoardFieldInteractListener sudokuGameBoardFieldInteractListener;

    public SudokuGameBoardView(@NonNull Context context) {
        super(context);
    }

    public SudokuGameBoardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuGameBoardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSudokuGameBoardFieldInteractListener(SudokuGameBoardFieldInteractListener sudokuGameBoardFieldInteractListener) {
        this.sudokuGameBoardFieldInteractListener = sudokuGameBoardFieldInteractListener;
    }

    @Override
    protected int getSizeX() {
        return 9;
    }

    @Override
    protected int getSizeY() {
        return 9;
    }

    public SerializableMatrix<SudokuGameStateField> getGameState() {
        SerializableMatrix<SudokuGameStateField> sudokuGameStateFields = new SerializableMatrix<>(9, 9);
        for (SudokuGameBoardFieldView view : matrix) {
            sudokuGameStateFields.set(view.getCoordinate(), view.getSudokuGameStateField());
        }
        return sudokuGameStateFields;
    }

    public void updateTime(long seconds) {
        textViewSeconds.setText(DateUtils.formatElapsedTime(seconds));
    }

    public void updateDifficulty(Difficulty difficulty) {
        switch (difficulty) {
            case EASY:
                textViewDifficulty.setText(getContext().getString(R.string.sudoku_difficulty_easy));
                break;
            case MEDIUM:
                textViewDifficulty.setText(getContext().getString(R.string.sudoku_difficulty_medium));
                break;
            default:
                textViewDifficulty.setText(getContext().getString(R.string.sudoku_difficulty_hard));
                break;
        }
    }

    @Override
    protected void initializeFields() {

        for (int i = 1; i < 10; i++) {
            int finalI = i;
            findViewById(getResources().getIdentifier("number" + i, "id", getContext().getPackageName()))
                    .setOnClickListener(v -> sudokuGameBoardFieldInteractListener.selectNumber(finalI));
        }

        textViewDifficulty = findViewById(R.id.sudoku_game_board_difficulty);
        textViewSeconds = findViewById(R.id.sudoku_game_board_time);
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
        Line line = new Line();
        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                line.add(new Coordinate(x, y));
            }
        }
        lines.add(line);
    }

    public boolean checkCorrect() {
        boolean error = false;
        for (Line line : lines) {
            List<Integer> multipleValues = getMultipleValues(line);
            if (!multipleValues.isEmpty()) {
                markAsWrong(multipleValues, line);
                error = true;
            }
        }
        return !error;
    }

    private void markAsWrong(List<Integer> multipleValues, Line line) {
        for (Coordinate coordinate : line) {
            SudokuGameBoardFieldView field = matrix.get(coordinate);
            if (multipleValues.contains(field.getValue()))
                field.markAsWrong();
        }
    }

    private List<Integer> getMultipleValues(Line line) {
        List<Integer> multipleValues = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (Coordinate coordinate : line) {
            int value = matrix.get(coordinate).getValue();
            if (value == GameBoardField.FREE) continue;
            if (values.contains(value)) multipleValues.add(value);
            values.add(matrix.get(coordinate).getValue());
        }
        return multipleValues;
    }

    public void showLines(){
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                for (Line line : lines) {
                    clear();
                    for (Coordinate coordinate : line) {
                        matrix.get(coordinate).highlight();
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        thread.start();
    }

    private List<Integer> getValuesOfLine(Line line) {
        List<Integer> values = new ArrayList<>();
        for (Coordinate coordinate : line){
            values.add(matrix.get(coordinate).getValue());
        }
        return values;
    }

    public void clearPossibleNumbers(){
        for (Line line : lines) {
            List<Integer> values = getValuesOfLine(line);
            for (Coordinate coordinate : line) {
                SudokuGameBoardFieldView field = matrix.get(coordinate);
                for (Integer value : values) {
                    if(field.getPossibleNumbers().contains(value)) {
                        field.getPossibleNumbers().remove(value);
                        field.update();
                    }
                }
            }
        }
    }

    private boolean checkComplete() {
        for (SudokuGameBoardFieldView field : matrix) {
            if (field.isEmpty()) return false;
        }
        return true;
    }


    public boolean checkForWin() {
        return checkCorrect() && checkComplete();
    }


    @Override
    protected SudokuGameResult checkGameForFinish() {
        if (checkForWin()) return new SudokuGameResult(0, Difficulty.EASY);
        return null;
    }

    @Override
    protected void loadGameState(GameState gameState) {
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                int id = getContext().getResources().getIdentifier("field" + x + "" + y, "id", getContext().getPackageName());
                SudokuGameBoardFieldView view = findViewById(id);
                view.setCoordinate(new Coordinate(x, y));
                view.setOnClickListener(this);
                view.setOnLongClickListener(this);
                matrix.set(x, y, view);
            }
        }
        SudokuGameState sudokuGameState = (SudokuGameState) gameState;
        for (SudokuGameStateField field : sudokuGameState) {
            matrix.get(field.getCoordinate()).load(field);
        }
    }

    @Override
    public void onClick(View view) {
        SudokuGameBoardFieldView sudokuGameBoardFieldView = (SudokuGameBoardFieldView) view;
        if (!sudokuGameBoardFieldView.isGiven())
            sudokuGameBoardFieldInteractListener.onClick(sudokuGameBoardFieldView);
    }

    @Override
    public boolean onLongClick(View view) {
        SudokuGameBoardFieldView sudokuGameBoardFieldView = (SudokuGameBoardFieldView) view;
        if (!sudokuGameBoardFieldView.isGiven())
            sudokuGameBoardFieldInteractListener.onLongClick(sudokuGameBoardFieldView);
        return true;
    }

    public void clear() {
        for (SudokuGameBoardFieldView view : matrix) {
            view.update();
        }
    }

    public void highlightNumber(int numberToHighlight) {
        for (SudokuGameBoardFieldView view : matrix) {
            if (view.getValue() == numberToHighlight) {
                view.highlight();
            }
        }
    }
}
