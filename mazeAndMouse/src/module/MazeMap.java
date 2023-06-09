package module;

public class MazeMap {
    private int[][] mazeMapArr;
    private int rowSize;
    private int columnSize;

    // 0 means can move
    // 1 means barriers, can not move
    // 2 means already checked a specific grid which is 0 before
    // 3 means already checked a specific grid which is 1 before
    int canMove = 0;
    int barrier = 1;
    int checkedMove = 2;
    int checkedBarrier = 3;

    public MazeMap(int rowSize, int columnSize){
        this.rowSize = rowSize;  // row number of mazeMap
        this.columnSize = columnSize;  // column number of mazeMap
        mazeMapArr = new int[rowSize][columnSize];
    }

    public int[][] getMazeMapArr() {
        return mazeMapArr;
    }

    public void setMazeMapArr(int[][] mazeMap) {
        this.mazeMapArr = mazeMap;
    }

    public int getRowSize() {
        return rowSize;
    }

    public void setRowSize(int rowSize) {
        this.rowSize = rowSize;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getCanMove() {
        return canMove;
    }

    public void setCanMove(int canMove) {
        this.canMove = canMove;
    }

    public int getBarrier() {
        return barrier;
    }

    public void setBarrier(int barrier) {
        this.barrier = barrier;
    }

    public int getCheckedMove() {
        return checkedMove;
    }

    public void setCheckedMove(int checkedMove) {
        this.checkedMove = checkedMove;
    }

    public int getCheckedBarrier() {
        return checkedBarrier;
    }

    public void setCheckedBarrier(int checkedBarrier) {
        this.checkedBarrier = checkedBarrier;
    }
}
