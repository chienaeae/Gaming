package endingAnimation;

public class Event 
{
    public enum keyCode
    {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        IDLE;
    }
    
    private int duration;
    private keyCode keyCode;
    
    public Event(int dur, keyCode keyCode)
    {
        this.duration = dur;
        this.keyCode = keyCode;
    }
    public int duration()
    {
        return this.duration;
    }
    public keyCode keyCode()
    {
        return this.keyCode;
    }
}
