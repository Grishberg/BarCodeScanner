package com.github.grishberg.barcodescanner.sheets;

/**
 * Created by grishberg on 04.02.18.
 */
public class FoundCellResult {
    private final int row;
    private final int col;

    public FoundCellResult(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "FoundCellResult{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
