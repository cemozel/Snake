package sonetta.snakebox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ksook on 3/5/2016.
 */
public class Snake extends GameObject {

    private Bitmap bitmap1;
    private Context context;

    public Snake(Context context, int x, int y) {
        this.x = x;
        this.y = y;
        this.context = context;
        bitmap1 = BitmapFactory.decodeResource(context.getResources(),R.drawable.eastface);
        bitmap1 = Bitmap.createScaledBitmap(bitmap1,60,60,false);
    }

    public Bitmap drawTail() {
        Bitmap tail = BitmapFactory.decodeResource(context.getResources(),R.drawable.easttail);
        tail = Bitmap.createScaledBitmap(tail,width,height,false);
        return tail;
    }
    public Bitmap getBitmap1() {
        return bitmap1;
    }
}
