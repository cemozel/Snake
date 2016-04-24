package sonetta.snakebox;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by ksook on 2/17/2016.
 */
public class GameBoard extends SurfaceView implements SurfaceHolder.Callback {

    protected MainThread thread;
    private Location location = new Location();
    public boolean paused = true, gameOver = false, first = true;
    public boolean poisonTouch = false;
    private Walls walls;
    public static int screenX=MainActivity.screenWidth, screenY=MainActivity.screenHeight;
    public static int grid = 60;
    private int score = 0, speed = 60, bodySize=1;
    private float x1,x2,y1,y2;
    private int dx,dy;
    private Snake snake;
    private Food food;
    private PoisonFood poisonFood;
    private long snakeTimer;
    private long elapsedSnakeTimer;
    private ArrayList<Snake> snakeParts;
    private Background background;

    public GameBoard(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    public void begin() {
        gameOver = false;
        first = true;
        dx = dy = 0;
        bodySize=1;
        score = 0;
        //walls = new Walls();
        snake = new Snake(location.getLocationX(),location.getLocationY());
        //snake.setX(600); snake.setY(600);
        food.setX(300); food.setY(300);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        background = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.background),0,0);
        snake = new Snake(600,600);
        snakeParts = new ArrayList<>();
        snakeTimer = System.nanoTime();
        food = new Food(300,300);
        walls = new Walls();
        poisonFood = new PoisonFood(location.getLocationX(),location.getLocationY());

        thread = new MainThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
                // Set thread to null so garbage collector can pick up
                thread = null;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        // Control game speed
        elapsedSnakeTimer = (System.nanoTime()-snakeTimer)/1000000;
        System.out.println("Timer:"+elapsedSnakeTimer);
        if(elapsedSnakeTimer>this.getDifficulty()) {

            // Move the snake
            snakeParts.add(new Snake(snake.getX(), snake.getY()));
            if (dx > 0) {
                snake = new Snake(snake.getX() + speed, snake.getY());
            } else if (dx < 0) {
                snake = new Snake(snake.getX() - speed, snake.getY());
            } else if (dy > 0) {
                snake = new Snake(snake.getX(), snake.getY() - speed);
            } else if (dy < 0) {
                snake = new Snake(snake.getX(), snake.getY() + speed);
            }
            snakeTimer = System.nanoTime();
        }
        if(snakeParts.size()>bodySize) {
            snakeParts.remove(0);
        }

        // Eating
        if(collision(snakeParts.get(snakeParts.size()-1),food)) {
            food.update();
            poisonFood.update();
            bodySize++;
            score++;
        }

        // Wall collision
        if(collision(snakeParts.get(snakeParts.size()-1).getRect(),walls.getEastWall()) ||
                collision(snakeParts.get(snakeParts.size()-1).getRect(),walls.getNorthWall()) ||
                collision(snakeParts.get(snakeParts.size()-1).getRect(),walls.getSouthWall()) ||
                collision(snakeParts.get(snakeParts.size()-1).getRect(),walls.getWestWall())) {
            // Splat flash
            gameOver = true;

        }

        // Touch poison food
        if(collision(snakeParts.get(snakeParts.size()-1).getRect(),poisonFood.getRect())) {
            gameOver = true;
        }

        if(gameOver) {
            this.begin();
        }
    }

    public void draw(Canvas canvas) {

        if(canvas!=null) {
            // Lock the canvas so we can paint on it
            final int savedState = canvas.save();

            Paint paint = new Paint();
            paint.setColor(Color.RED);

            // Draw the background
            canvas.drawColor(Color.LTGRAY);
            for(int i = 0; i<snakeParts.size(); i++) {
                snakeParts.get(i).draw(canvas);
            }

            // Draw gameGrid
            background.gameGrid(canvas);

            // Draw food
            food.draw(canvas);

            if(MainActivity.gameDiff==3) {
                if(!collision(poisonFood.getRect(),food.getRect())) {
                    poisonFood.draw(canvas);
                }
            }

            // Poison touch
            if(collision(poisonFood.getRect(),snakeParts.get(snakeParts.size()-1).getRect())) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.poisioneat),screenX/5,screenY/2,null);
            }

            // Draw walls
            walls.draw(canvas);

            canvas.restoreToCount(savedState);
        }
    }

    public boolean collision(GameObject a, GameObject b) {
        return a.getRect().intersect(b.getRect());
    }

    public boolean collision(Rect a, Rect b) {
        return a.intersect(b);
    }

    public int getDifficulty() {
        return MainActivity.gameDiff==1?80:MainActivity.gameDiff==2?60:40;
    }

    @Override
    public boolean onTouchEvent( MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.paused=false;
                x1 = motionEvent.getX();
                y1 = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = motionEvent.getX();
                y2 = motionEvent.getY();
                    if(x2>x1&&(Math.abs(y2-y1)<Math.abs(x2-x1))) {
                        dx = speed; dy = 0;
                    } else if (x2<x1&&(Math.abs(y2-y1)<Math.abs(x2-x1))) {
                        dx = -speed; dy = 0;
                    } else if (y2>y1&&(Math.abs(x2-x1)<Math.abs(y2-y1))) {
                        dy = -speed; dx = 0;
                    } else if (y2<y1&&(Math.abs(x2-x1)<Math.abs(y2-y1))) {
                        dy = speed; dx = 0;
                    }
        }

        if(motionEvent.getY() < grid) {
            begin();
        }
        return true;

    }
}
