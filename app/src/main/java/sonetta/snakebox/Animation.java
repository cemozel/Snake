package sonetta.snakebox;

import android.graphics.Bitmap;

/**
 * Created by ksook on 3/10/2016.
 */
public class Animation {

    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;

    public void setFrames(Bitmap[] frames) {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }

    public void update() {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>delay) {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length) {
            currentFrame = 0;
        }
    }

    public Bitmap getImage() {
        return frames[currentFrame];
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public long getDelay() {
        return delay;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
