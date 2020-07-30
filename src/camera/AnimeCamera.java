package camera;

import endingAnimation.Role;
import gameobj.GameObject;
import java.util.ArrayList;
import util.Delay;

public class AnimeCamera extends Camera
{
    private Delay delay;
    private Role currentTargetRole;
    private ArrayList<Role> roles;
    private CameraEvent[] events;
    private int currentEventIndex;
    
    public AnimeCamera(int mapWidth, int mapHeight, ArrayList<Role> roles)
    {
        super(mapWidth, mapHeight);
        this.roles = roles;
    }
    public void initCameraEvent(CameraEvent[] ce)
    {
        this.events = ce;
        this.currentEventIndex = 0;
        this.delay = new Delay(1);
        this.currentTargetRole = roles.get(this.events[this.currentEventIndex].target());
    }
    public void lock()
    {
        this.delay.stop();
    }
    public void unlock()
    {
        this.delay.reStart();
    }
    public void trig()
    {
        if(this.delay.isTrig())
        {
            this.delay.resetDelayFrame(this.events[this.currentEventIndex].duration() * 3);        //設定演出時間，以0.1秒為單位，0.1秒為六禎
            this.currentTargetRole = roles.get(this.events[this.currentEventIndex].target());      //設定當前演出動作
            if(this.currentEventIndex !=this.events.length-1)                                      //設定下一幕的演出編號，若為陣列中最後一幕則不設定。
            {
                this.currentEventIndex++;
            }
        }
    }
    @Override
    public void update()
    {
        super.update();
        trig();
        setTarget(this.currentTargetRole);
    }
}
