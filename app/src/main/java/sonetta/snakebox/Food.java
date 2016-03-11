package sonetta.snakebox;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.params.MeteringRectangle;

import java.util.Calendar;

/**
 * Created by ksook on 3/4/2016.
 */
public class Food extends GameObject {

    private Location location;

    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        location = new Location();
        x = location.getLocationX();
        y = location.getLocationY();
    }
}
