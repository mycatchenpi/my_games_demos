package Service;

import module.MazeMap;

public class MazeMapService {
    private MazeMap mazeMap;

    public MazeMapService(MazeMap mazeMap) {
        this.mazeMap = mazeMap;
    }

    //darw the mazeMap
    public MazeMap drawMap() {
        //MazeMap mazeMap = new MazeMap(rowSize, columnSize);
        int[][] map = mazeMap.getMazeMapArr();
        int rowSize = mazeMap.getRowSize();
        int columnSize = mazeMap.getColumnSize();
        int barrier = mazeMap.getBarrier();

        // (1) set all grids of the first row and the last row to 1
        for(int i = 0; i < columnSize; i++) {
            map[0][i] = barrier;
            map[rowSize - 1][i] = barrier;
        }

        //(2) set all grids of the first column and the last column to 1
        //    excepts the 4 grids in 4 corners
        for(int i = 1; i < rowSize - 1; i++) {
            map[i][0] = barrier;
            map[i][columnSize - 1] = barrier;
        }

        map[3][1] = barrier;
        map[3][2] = barrier;
        map[3][3] = barrier;
        map[4][3] = barrier;

        return mazeMap;
    }

    // display mazeMap
    public void showMazeMap() {
        int[][] mapArr = mazeMap.getMazeMapArr();

        for(int i = 0; i < mapArr.length; i++) {
            for(int j = 0; j < mapArr[i].length; j++) {
                System.out.print(mapArr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
