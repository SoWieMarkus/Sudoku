package markus.wieland.sudoku.game.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import markus.wieland.games.elements.SerializableMatrix;
import markus.wieland.games.game.Difficulty;
import markus.wieland.games.persistence.GameGenerator;
import markus.wieland.sudoku.game.gamestate.SudokuGameState;
import markus.wieland.sudoku.game.gamestate.SudokuGameStateField;

public class SudokuGenerator implements GameGenerator<SudokuGameState> {

    private final Difficulty difficulty;
    private final int[][] matrix = new int[9][9];
    private final Random random;
    private final int[][] blocks = new int[][]{
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}
    };

    public SudokuGenerator(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.random = new Random();
        build();
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void build() {
        initializeBoard();
        for (int i = 0; i < 2; i++) {
            switchBlockColumns();
        }
        for (int i = 0; i < 2; i++) {
            switchBlockRows();
        }

        for (int i = 0; i < 5; i++) {
            switchColumnsInsideBlockColumn();
            switchRowsInsideBlockRow();
        }

        removeValues(52);
    }

    private void switchBlockRows() {
        int rowBlock1 = random.nextInt(3);
        int rowBlock2 = random.nextInt(3);
        for (int i = 0; i < 3; i++) {
            switchRows(blocks[rowBlock1][i], blocks[rowBlock2][i]);
        }
    }

    private void switchBlockColumns() {
        int columnBlock1 = random.nextInt(3);
        int columnBlock2 = random.nextInt(3);
        for (int i = 0; i < 3; i++) {
            switchColumns(blocks[columnBlock1][i], blocks[columnBlock2][i]);
        }
    }

    private void switchRowsInsideBlockRow() {
        int blockRow = random.nextInt(3);
        int row1 = random.nextInt(3);
        int row2 = random.nextInt(3);
        System.out.println("Switching block row: " + blockRow + " " + row1 + " " + row2);
        switchRows(blocks[blockRow][row1], blocks[blockRow][row2]);

    }

    private void switchColumnsInsideBlockColumn() {
        int blockColumn = random.nextInt(3);
        int column1 = random.nextInt(3);
        int column2 = random.nextInt(3);
        System.out.println("Switching block column: " + blockColumn + " " + column1 + " " + column2);
        switchColumns(blocks[blockColumn][column1], blocks[blockColumn][column2]);

    }


    private void switchColumns(int column1, int column2) {
        for (int i = 0; i < 9; i++) {
            int valueTemp = matrix[i][column1];
            matrix[i][column1] = matrix[i][column2];
            matrix[i][column2] = valueTemp;
        }
    }

    private void switchRows(int row1, int row2) {
        for (int i = 0; i < 9; i++) {
            int valueTemp = matrix[row1][i];
            matrix[row1][i] = matrix[row2][i];
            matrix[row2][i] = valueTemp;
        }
    }

    private void initializeBoard() {
        List<Integer> firstRow = generateFirstRow();
        applyListToRow(firstRow, 0);

        int[] shifts = new int[]{3, 3, 1, 3, 3, 1, 3, 3};

        for (int i = 0; i < shifts.length; i++) {
            shift(firstRow, shifts[i]);
            applyListToRow(firstRow, i + 1);
        }
    }

    private void removeValues(int amount) {
        for (int i = 0; i < amount; i++) {
            boolean found = false;
            while (!found) {
                int x = random.nextInt(9);
                int y = random.nextInt(9);
                found = matrix[x][y] != -1;
                if (found) matrix[x][y] = -1;
            }
        }
    }

    private void applyListToRow(List<Integer> list, int row) {
        for (int i = 0; i < 9; i++) {
            matrix[row][i] = list.get(i);
        }
    }

    private void shift(List<Integer> row, int amount) {
        Collections.rotate(row, amount);
    }

    private List<Integer> generateFirstRow() {
        List<Integer> firstRow = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            firstRow.add(i);
        }
        Collections.shuffle(firstRow);
        return firstRow;
    }

    public SerializableMatrix<SudokuGameStateField> getMatrix() {
        SerializableMatrix<SudokuGameStateField> serializableMatrix = new SerializableMatrix<>(9, 9);
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                serializableMatrix.set(x, y, new SudokuGameStateField(matrix[x][y],
                        matrix[x][y] != -1, new ArrayList<>(), x, y));
            }
        }
        return serializableMatrix;
    }

    @Override
    public SudokuGameState generate() {
        return new SudokuGameState(this);
    }
}
