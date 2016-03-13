package sonetta.snakebox;

/**
 * Created by ksook on 2/29/2016.
 */

public class Location{

    private int max,min;
    private int s = GameBoard.grid;

    public Location() {
        //screenX = 2392 screenY = 1440
    }

    public int getLocationX() {
        max = GameBoard.screenX/s-1;
        return s*((int)(1+Math.random()*(max)));
    }

    public int getLocationY() {
        max = GameBoard.screenY/s-2;
        min = 2;
        return s*((int) (Math.random()*(max - min)+min));
    }
}
