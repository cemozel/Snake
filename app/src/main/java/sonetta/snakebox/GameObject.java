package sonetta.snakebox;

import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by ksook on 3/5/2016.
 */
public abstract class GameObject {

    protected int x;
    protected int y;

    protected int width=60;
    protected int height=60;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rect getRect() {
        return new Rect(x,y,x+width,y+height);
    }
}
