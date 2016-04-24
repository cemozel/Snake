package sonetta.snakebox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.camera2.params.MeteringRectangle;

/**
 * Created by ksook on 3/4/2016.
 */
public class Walls {

    public int grid = GameBoard.grid;
    public int sX = GameBoard.screenX;
    public int sY = GameBoard.screenY;
    private Paint paint = new Paint();


    public Walls() {

    }

    public Rect northWall() {
        return new Rect(grid,grid,sX-grid,2*grid);
    }

    public Rect southWall() {
        return new Rect(0,sY-grid,sX,sY);
    }

    // Left top right bottom
    public Rect eastWall() {
        return new Rect(sX-grid,grid,sX,sY-grid);
    }

    public Rect westWall() {
        return new Rect(0,grid,grid,sY-grid);
    }

    public void draw(Canvas canvas) {
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        if (MainActivity.gameDiff==2||MainActivity.gameDiff==3) {
            canvas.drawRect(northWall(),paint);
            canvas.drawRect(southWall(),paint);
            canvas.drawRect(eastWall(),paint);
            canvas.drawRect(westWall(),paint);
        }
    }

    public Rect getNorthWall() {
        return northWall();
    }

    public Rect getSouthWall() {
        return southWall();
    }

    public Rect getWestWall() {
        return westWall();
    }

    public Rect getEastWall() {
        return eastWall();
    }
}
