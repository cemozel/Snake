package sonetta.snakebox;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/*
Game difficulties
Easy - No walls
Medium - Four walls
Hard - Four walls with green lines
Extreme - Green screen every now and then
Insane - Poision fruits
Elite - Moving walls with yellow fruit moving them back
 */
public class MainActivity extends Activity {

    public static int screenWidth;
    public static int screenHeight;
    public static int gameDiff;

    //stats
    int hi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the display object to access screen details
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Find out the width and height of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        screenWidth = point.x;
        screenHeight = point.y;

        setContentView(R.layout.content_main);
    }

    public void startEasy(View view) {

        gameDiff = 1;
        Intent easy = new Intent(this,GameActivity.class);
        startActivity(easy);
    }

    public void startMedium(View view) {
        gameDiff = 2;
        Intent medium = new Intent(this,GameActivity.class);
        startActivity(medium);
    }

    public void startHard(View view) {
        gameDiff = 3;
        Intent hard = new Intent(this,GameActivity.class);
        startActivity(hard);
    }
}
