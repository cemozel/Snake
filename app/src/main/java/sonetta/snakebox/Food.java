package sonetta.snakebox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by ksook on 3/4/2016.
 */
public class Food extends GameObject {

    private Random random = new Random();
    private int grid = GameBoard.grid;
    private Paint paint = new Paint();

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        Location location = new Location();
        x = location.getLocationX();
        y = location.getLocationY();
    }

    public void draw(Canvas canvas) {

        paint.setColor(Color.RED);
        canvas.drawRect(x,y,x+grid,y+grid,paint);

        if(MainActivity.gameDiff==3) {
            paint.setColor(Color.argb(random.nextInt(255),random.nextInt(255),random.nextInt(255),random.nextInt(255)));
        }
    }
}
