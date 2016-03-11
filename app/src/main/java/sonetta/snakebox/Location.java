package sonetta.snakebox;

/**
 * Created by ksook on 2/29/2016.
 */

public class Location{

    private int max;

    public Location() {

    }

    public int getLocationX() {
        max = GameBoard.screenX/GameBoard.grid-1;
        System.out.println("X: "+max);
        return GameBoard.grid*((int)(1+Math.random()*(max)));
    }

    public int getLocationY() {
        max = GameBoard.screenY/GameBoard.grid-1;
        System.out.println("Y: "+max);
        return GameBoard.grid*((int)(2+Math.random()*(max)));
    }
}
