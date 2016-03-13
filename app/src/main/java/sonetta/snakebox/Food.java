package sonetta.snakebox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.Random;

/**
 * Created by ksook on 3/4/2016.
 */
public class Food extends GameObject {

    private Location location;
    private Context context;
    private Random random = new Random();
    private int choice = random.nextInt(7);
    private Bitmap image;

    public Food(Context context, int x, int y) {
        this.context = context;
        this.x = x;
        this.y = y;
    }

    public void update() {
        location = new Location();
        choice = random.nextInt(3);
        x = location.getLocationX();
        y = location.getLocationY();
        System.out.println("X: " + x + " Y:" + y);
    }

    public Bitmap getFood() {
        switch (this.getChoice()) {
            case 0:
                image = BitmapFactory.decodeResource(context.getResources(), R.drawable.apple);
                break;
            case 1:
                image = BitmapFactory.decodeResource(context.getResources(),R.drawable.banana);
                break;
            case 2:
                image = BitmapFactory.decodeResource(context.getResources(),R.drawable.grape);
                break;
            case 3:
                image = BitmapFactory.decodeResource(context.getResources(),R.drawable.peach);
                break;
            case 4:
                image = BitmapFactory.decodeResource(context.getResources(),R.drawable.weird1);
                break;
            case 5:
                image = BitmapFactory.decodeResource(context.getResources(),R.drawable.weird2);
                break;
            case 6:
                image = BitmapFactory.decodeResource(context.getResources(),R.drawable.weird3);
                break;
        }
        image = Bitmap.createScaledBitmap(image,width,height,false);
        return image;
    }

    public int getChoice() {
        return choice;
    }
}
