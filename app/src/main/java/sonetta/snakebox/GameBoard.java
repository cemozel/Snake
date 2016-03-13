package sonetta.snakebox;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.camera2.params.MeteringRectangle;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ksook on 2/17/2016.
 */
public class GameBoard extends SurfaceView implements Runnable {

    private Context context;
    private Thread thread=null;
    private SurfaceHolder surfaceHolder;
    private Location location = new Location();
    private volatile boolean playing=true;
    public boolean paused = true, gameOver = false, first = true;
    private Canvas canvas;
    private Walls walls;
    private Paint paint;
    private long fps, timeThisFrame;
    public static int screenX, screenY; //screenX = 2392 screenY = 1440
    public static int grid = 60;
    private int score = 0, speed = 60, bodySize=0, difficulty=75;
    private float x1,x2,y1,y2;
    private int dx,dy;
    private Snake snake;
    private Food sfood;
    private ArrayList<Snake> snakeBody;

    public GameBoard(Context context, int x, int y) {
        super(context);
        this.context = context;
        surfaceHolder = getHolder();
        paint = new Paint();
        screenX = x; screenY = y;
        this.begin();
    }

    public void begin() {
        gameOver = false;
        first = true;
        dx = dy = 0;
        difficulty = 75;
        bodySize=1;
        snakeBody = new ArrayList<>();
        snakeBody.clear();
        walls = new Walls();
        snake = new Snake(context,600,600);
        snakeBody.add(snake);
        sfood = new Food(context,360,360);
    }

    @Override
    public void run() {
        while (playing) {
            long startFrameTime = System.currentTimeMillis();
            if(!paused) {
                this.update();
            }
            this.draw();
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if(timeThisFrame>=1) {
                fps = 1000/timeThisFrame;
            }
        }
    }

    public void update() {

        // Control game speed
        try {
            thread.sleep(difficulty);
        } catch (Exception e) {}

        // Move the snake
        snakeBody.add(new Snake(context,snake.getX(), snake.getY()));
        if(dx>0) {
            snake = new Snake(context,snake.getX()+speed, snake.getY());
        } else if(dx<0) {
            snake = new Snake(context,snake.getX()-speed, snake.getY());
        } else if (dy>0) {
            snake = new Snake(context,snake.getX(), snake.getY()-speed);
        } else if (dy<0) {
            snake = new Snake(context,snake.getX(), snake.getY()+speed);
        }

        if(snakeBody.size()>bodySize) {
            snakeBody.remove(0);
        }

        if(this.collision(snake.getRect(), sfood.getRect())) {
            difficulty--;
            if(difficulty==10) {
                difficulty=10;
            }
        }
        this.checkGameOver();
    }

    public boolean collision(Rect a, Rect b) {
        if(a.intersect(b)) {
            bodySize++;
            score++;
            sfood.update();
            return true;
        } return  false;
    }

    public void checkGameOver() {
        if(bodySize>2) {
            for(int i = 0; i<bodySize-1; i++) {
                if(snake.getRect().intersect(snakeBody.get(i).getRect())) {
                    gameOver = true;
                }
            }
        }

        /*if(player.getX() <= 0 || player.getX() >= screenX || player.getY() <= 0 || player.getY() >= screenY) {
            gameOver = true;
        }*/
    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            // Background color
            canvas.drawColor(Color.argb(200, 200, 200, 200));


            // Draw snake
            for(int i =0; i<snakeBody.size(); i++) {
                // Draw face and tail
                if(i==0) {
                    if(dx>0) {
                        canvas.drawBitmap(snake.drawTail(),snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else if (dx<0) {
                        Bitmap tail = BitmapFactory.decodeResource(context.getResources(),R.drawable.westtail);
                        tail = Bitmap.createScaledBitmap(tail,grid,grid,false);
                        canvas.drawBitmap(tail,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else if (dy>0) {
                        Bitmap tail = BitmapFactory.decodeResource(context.getResources(),R.drawable.northtail);
                        tail = Bitmap.createScaledBitmap(tail,grid,grid,false);
                        canvas.drawBitmap(tail,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else {
                        Bitmap tail = BitmapFactory.decodeResource(context.getResources(),R.drawable.southtail);
                        tail = Bitmap.createScaledBitmap(tail,grid,grid,false);
                        canvas.drawBitmap(tail,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    }
                } else if (i==snakeBody.size()-1) {
                    if(dx>0) {
                        Bitmap face = BitmapFactory.decodeResource(context.getResources(),R.drawable.eastface);
                        face = Bitmap.createScaledBitmap(face,grid,grid,false);
                        canvas.drawBitmap(face,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else if (dx<0) {
                        Bitmap face = BitmapFactory.decodeResource(context.getResources(),R.drawable.westface);
                        face = Bitmap.createScaledBitmap(face,grid,grid,false);
                        canvas.drawBitmap(face,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else if (dy>0) {
                        Bitmap face = BitmapFactory.decodeResource(context.getResources(),R.drawable.northface);
                        face = Bitmap.createScaledBitmap(face,grid,grid,false);
                        canvas.drawBitmap(face,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else {
                        Bitmap face = BitmapFactory.decodeResource(context.getResources(),R.drawable.southface);
                        face = Bitmap.createScaledBitmap(face,grid,grid,false);
                        canvas.drawBitmap(face,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    }
                } else {
                    if(dx>0) {
                        Bitmap body = BitmapFactory.decodeResource(context.getResources(),R.drawable.eastbody);
                        body = Bitmap.createScaledBitmap(body,grid,grid,false);
                        canvas.drawBitmap(body,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else if (dx<0) {
                        Bitmap body = BitmapFactory.decodeResource(context.getResources(),R.drawable.westbody);
                        body = Bitmap.createScaledBitmap(body,grid,grid,false);
                        canvas.drawBitmap(body,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else if (dy>0) {
                        Bitmap body = BitmapFactory.decodeResource(context.getResources(),R.drawable.northbody);
                        body = Bitmap.createScaledBitmap(body,grid,grid,false);
                        canvas.drawBitmap(body,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    } else {
                        Bitmap body = BitmapFactory.decodeResource(context.getResources(),R.drawable.southbody);
                        body = Bitmap.createScaledBitmap(body,grid,grid,false);
                        canvas.drawBitmap(body,snakeBody.get(i).getX(),snakeBody.get(i).getY(),paint);
                    }
                }
            }

            // Draw Food
//            paint.setColor(Color.MAGENTA);
//            paint.setStyle(Paint.Style.FILL);
//            canvas.drawRect(sfood.getRect(),paint);
            // Draw first food
            if(first) {
                canvas.drawBitmap(sfood.getFood(),sfood.getX(),sfood.getY(),paint);
            } else if(this.collision(snake.getRect(), sfood.getRect())) {
                canvas.drawBitmap(sfood.getFood(),sfood.getX(),sfood.getY(),paint);
                first = false;
            }

            // Vertical lines
            paint.setColor(Color.RED);
            for (int s = 0; s < screenX; s+=grid) {
                canvas.drawLine(grid+s,grid,grid+s,screenY,paint);
            }
            //Horizontal lines
            for(int s = 0; s<screenY; s+=grid) {
                canvas.drawLine(0,grid+s,screenX,grid+s,paint);
            }

            // Brick wall
            paint.setColor(Color.CYAN);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(walls.northWall(), paint);
            canvas.drawRect(walls.southWall(),paint);
            canvas.drawRect(walls.westWall(),paint);
            canvas.drawRect(walls.eastWall(),paint);

            // Scoring
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(60);
            canvas.drawText("Score: " + score, 10, 50, paint);

            // The end
            if(gameOver) {
                paint.setColor(Color.BLACK);
                canvas.drawRect(0, grid, screenX, screenY, paint);
                paint.setColor(Color.WHITE);
                paint.setTextSize(200);
                canvas.drawText("GAMEOVER",screenX/4,screenY/2,paint);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void paused() {
        playing = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e("Error:" , "joined thread" );
        }
    }

    public void resume() {
        playing = true;
        thread = new Thread(this);
        thread.start();
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
