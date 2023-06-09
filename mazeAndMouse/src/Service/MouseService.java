package Service;

import module.MazeMap;
import module.Mouse;

public class MouseService {
    private Mouse mouse;

    public MouseService(Mouse mouse) {
        this.mouse = mouse;
    }

    // help mouse to find a way to escape this maze map.
    // if currently the position of mouse is the bottom right grid,
    // the index of maze map is map[rowSize - 2][columnSize - 2],
    // which means mouse escapes successfully.
    public boolean findWay(MazeMap mazeMap, int currentRowIndex, int currentColumnIndex) {
        int[][] map = mazeMap.getMazeMapArr();

        if(map[mazeMap.getRowSize() - 2][mazeMap.getColumnSize() - 2] == mazeMap.getCheckedMove()) {
            return true;
        } else {
            if(map[currentRowIndex][currentColumnIndex] == mazeMap.getCanMove()) {  // if grid is 0, can move
                map[currentRowIndex][currentColumnIndex] = mazeMap.getCheckedMove(); // set status to 2 first
                // try to find a way which can move from bottom, right, top, left
                // use recursion
                if(findWay(mazeMap, currentRowIndex + 1, currentColumnIndex)) {  // go to bottom
                    return true;
                } else if(findWay(mazeMap, currentRowIndex, currentColumnIndex + 1)) { // go to right
                    return true;
                } else if(findWay(mazeMap, currentRowIndex - 1, currentColumnIndex)) { // go to top
                    return true;
                } else if(findWay(mazeMap, currentRowIndex, currentColumnIndex - 1)) { // go to left
                    return true;
                } else {
                    // if all directories return false, modify status to 3
                    map[currentRowIndex][currentColumnIndex] = mazeMap.getCheckedBarrier();
                    return false;
                }
            } else {
                // if grid is not 0( barrier 1), return false
                return false;
            }
        }
    }

    // display ways mouse tried
    public void showWays(MazeMap mazeMap) {
        int[][] map = mazeMap.getMazeMapArr();

        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

//    public void isEscape() {
//        if(findWay()) {
//            System.out.println("mouse " + mouse.getName() + " escapes successfully from our maze!");
//        }
//    }
}
