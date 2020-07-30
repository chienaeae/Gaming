package foxstate;

import camera.Camera;
import gameobj.Actor;
import gameobj.GameObject;
import java.awt.Graphics;
import renderer.FoxRenderer;
import renderer.Renderer;

public class StateContext 
{
    private State currentState;
    private Renderer renderer;
    private Renderer.Dir dir;
    private boolean stateLock;
    
    private int paintX;
    private int paintY;
    
    public StateContext(GameObject obj, Renderer renderer)
    {
        this.currentState = new Idle();
        this.renderer = renderer;
        this.dir = Renderer.Dir.RIGHT;
        this.stateLock = false;
    }
    //狀態調整
    public void stateControl(double velX, double velY)
    {
        if(this.currentState.animationPerform()==9)
        {
            stateLock();
        }
        if(!this.stateLock)
        {
            if(velX>0 && this.dir == Renderer.Dir.LEFT)
            {
                this.setDir(Renderer.Dir.RIGHT);
            }
            else if(velX<0 && this.dir == Renderer.Dir.RIGHT)
            {
                this.setDir(Renderer.Dir.LEFT);
            }
            if(velY<0 )
            {
                changeState(new Jump());
            }
            if(velY>0 )
            {
               changeState(new Fall());
            }
            if(Math.abs(velX) > 0 && velY==0 )
            {
                changeState(new Run());
            }
            if(velX==0 && velY==0 )
            {
                changeState(new Idle());
            }
        }
        else if(this.currentState.animationPerform()==3 && velY==0)
        {
            stateUnlock();
        }
        
    }
    public void changeState(State newState)
    {
        this.currentState = newState;
    }
    public void stateLock()
    {
        this.stateLock = true;
    }
    public void stateUnlock()
    {
        this.stateLock = false;
    }
    public int animationNum()
    {
        return this.currentState.animationPerform();
    }
    //動畫調整
    public void animationStop()
    {
        this.renderer.pause();
    }
    public void animationStart()
    {
        this.renderer.start();
    }
    
    //移動處理
    public void setDir(Renderer.Dir dir)
    {
        this.dir = dir;
        this.renderer.setDir(dir);
    }
    public Renderer.Dir getDir()
    {
        return this.dir;
    }
    
    //邏輯更新
    public void update()
    {
        this.renderer.setMovement(this.currentState.animationPerform());
        this.renderer.update();
    }
    //畫面更新
    public void paint(Graphics g, Camera camera, Actor.ActorChoice actorChoice,  int x, int y, int width, int height)
    {
        if(this.currentState.animationPerform()==9 && actorChoice == Actor.ActorChoice.FOX)
        {
            this.renderer.dieAnimation(g,
                    x - camera.getCameraX() - width/2,
                    y - camera.getCameraY() - height/2 - 25,
                    width,
                    height);
            return;
        }
        else if(this.currentState.animationPerform()==9 && actorChoice == Actor.ActorChoice.RABBIT)
        {
            
            this.renderer.dieAnimation(g,
                    x - camera.getCameraX() - width/2,
                    y - camera.getCameraY() - height/2 - 15,
                    width,
                    height);
            return;
        }
        if(actorChoice == Actor.ActorChoice.FOX)
        {
            this.renderer.paint(
                g,
                x - camera.getCameraX() - width/2,
                y - camera.getCameraY() - height/2 - 25,
                width,
                height);
        }
        else if(actorChoice == Actor.ActorChoice.RABBIT)
        {
            this.renderer.paint(
                g,
                x - camera.getCameraX() - width/2,
                y - camera.getCameraY() - height/2 -15,
                width,
                height);
        }
    }
}
