package sonetta.snakebox;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

    GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the display object to access screen details
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        //Initialize gameBoard and set it as a view
        gameBoard = new GameBoard(this, point.x, point.y);
        setContentView(gameBoard);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameBoard.paused();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameBoard.resume();
    }
}
