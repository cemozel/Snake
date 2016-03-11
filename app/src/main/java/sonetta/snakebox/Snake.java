package sonetta.snakebox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ksook on 3/5/2016.
 */
public class Snake extends GameObject {

    private Bitmap bitmap1;

    public Snake(Context context, int x, int y) {
        this.x = x;
        this.y = y;
        bitmap1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.invader1);
        bitmap1 = Bitmap.createScaledBitmap(bitmap1,60,60,false);
    }

    public void drawFace() {
        //Bitmap face = BitmapFactory.decodeResource()
    }
    public Bitmap getBitmap1() {
        return bitmap1;
    }
}
