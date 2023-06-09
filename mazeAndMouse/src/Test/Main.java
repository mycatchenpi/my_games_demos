package Test;

import Service.MazeMapService;
import Service.MouseService;
import module.MazeMap;
import module.Mouse;

public class Main {
    public static void main(String[] args) {
        // create a maze map
        MazeMap mazeMap = new MazeMap(8, 7);
        MazeMapService ms = new MazeMapService(mazeMap);
        // design maze map
        ms.drawMap();
        // display maze map
        ms.showMazeMap();
        System.out.println("--------------------------");

        // create a mouse to find a way leaving maze map
        Mouse mouse = new Mouse("Mickey");
        MouseService mouseService = new MouseService(mouse);
        boolean isEscape = mouseService.findWay(mazeMap,1, 1);
        mouseService.showWays(mazeMap);

        if(isEscape) {
            System.out.println("mouse " + mouse.getName() + " escapes successfully from our maze!");
        } else {
            System.out.println("mouse " + mouse.getName() + " lost in our maze!");
        }
    }
}