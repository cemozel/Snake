package sonetta.snakebox;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ksook on 3/5/2016.
 */
public class Snake extends GameObject {

    private int grid = GameBoard.grid;

    public Snake(int x, int y) {//Bitmap res, int x, int y, int numFrames) {
        this.x = x;
        this.y = y;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawRect(getRect(),paint);
    }
}
