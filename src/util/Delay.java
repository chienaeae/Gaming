package util;

public class Delay {
    
    private int delayFrame;
    private int counter;
    private boolean isPause;
    
    public Delay(int delayFrame)
    {
        this.delayFrame = delayFrame;
    }
    public int counter()
    {
        return this.counter;
    }
    public int delayFrame()
    {
        return this.delayFrame;
    }
    public void resetDelayFrame(int delayFrame)
    {
        this.delayFrame = delayFrame;
    }
    public void zero()
    {
        this.counter = 0;
    }
    public void pause()
    {
        this.isPause = true;
    }
    public void stop()
    {
        this.isPause = true;
        this.counter = 0;
    }
    public void start()
    {
        this.isPause = false;
        this.counter = 0;
    }
    public void reStart()
    {
        stop();
        start();
    }
    public boolean isTrig()
    {
        if(!this.isPause && this.counter ++ ==this.delayFrame)
        {
            this.counter = 0;
            return true;
        }
        return false;
    }
    
    public boolean getTrig(){
        return this.counter == this.delayFrame;
    }
}
