package sonetta.snakebox;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaCodecInfo;

/**
 * Created by ksook on 3/24/2016.
 */
public class Background {

    private Bitmap background;
    private int x;
    private int y;
    private int screenX = GameBoard.screenX;
    private int screenY = GameBoard.screenY;
    private int grid = GameBoard.grid;

    public Background(Bitmap res, int x, int y) {
        this.x = x;
        this.y = y;
        this.background = res;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(background,x,y,null);
    }

    public void gameGrid(Canvas canvas) {
        Paint paint = new Paint();

        if(MainActivity.gameDiff==1||MainActivity.gameDiff==2) {
            paint.setColor(Color.RED);
            //Vertical lines
            for (int s = 0; s <screenX; s+=grid) {
                canvas.drawLine(grid+s,grid,grid+s,screenY,paint);
            }
            //Horizontal lines
            for(int s = 0; s<screenY; s+=grid) {
                canvas.drawLine(0, grid + s, screenX, grid + s, paint);
            }
        } else if (MainActivity.gameDiff==3) {
            paint.setColor(Color.GREEN);
            //Vertical lines
            for (int s = 0; s <screenX; s+=grid) {
                canvas.drawLine(grid+s,grid,grid+s,screenY,paint);
            }
            //Horizontal lines
            for(int s = 0; s<screenY; s+=grid) {
                canvas.drawLine(0, grid + s, screenX, grid + s, paint);
            }
        }

    }
}
