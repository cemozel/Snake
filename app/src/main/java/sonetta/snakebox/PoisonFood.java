package sonetta.snakebox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by ksook on 4/24/2016.
 */
public class PoisonFood extends GameObject {


    private Location location;
    private Random random = new Random();
    private int grid = GameBoard.grid;
    private int argb = random.nextInt(255);
    private Paint paint = new Paint();

    public PoisonFood(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        location = new Location();
        argb = random.nextInt(255);
        x = location.getLocationX();
        y = location.getLocationY();
        //       System.out.println("X: " + x + " Y:" + y);
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(x,y,x+grid,y+grid,paint);
        paint.setColor(Color.argb(100,0,argb,argb));
    }
}
