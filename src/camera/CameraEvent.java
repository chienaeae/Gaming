package camera;

import endingAnimation.Event;
import gameobj.GameObject;

public class CameraEvent 
{
    private int duration;
    private int targetIndex;
    
    public CameraEvent(int duration, int targetIndex)
    {
        this.duration = duration;
        this.targetIndex = targetIndex;
    }
    public int duration()
    {
        return this.duration;
    }
    public int target()
    {
        return this.targetIndex;
    }
}
