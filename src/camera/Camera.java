

package camera;

import background.Background;
import gameobj.Actor;
import gameobj.GameObject;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import util.Global;

public class Camera 
{
    public static final int VIEWPORT_SIZE_X = Global.Frame.X.getFrameSize();
    public static final int VIEWPORT_SIZE_Y = Global.Frame.Y.getFrameSize();
    
    public static final double MOVE_SPEED_LIMIE = 20.0;
    
    public static final double TWEEN = 0.9;
    
    private int worldWidth;
    private int worldHeight;
    private int offsetLeft;
    private int offsetRight;
    private int offsetTop;
    private int offsetDown;
    private double camX;
    private double camY;
    private double moveSpeedX;
    private double moveSpeedY;
    
    private boolean toTarget;
    private boolean paintAll;
    private double targetPointX;
    private double targetPointY;
    
    public Camera(int mapWidth, int mapHeight)
    {
        this.worldWidth = mapWidth;
        this.worldHeight = mapHeight;
        this.camX = mapWidth/2 - VIEWPORT_SIZE_X/2;
        this.camY = mapHeight/2 - VIEWPORT_SIZE_Y/2;
        this.targetPointX = this.camX;
        this.targetPointY = this.camY;
        this.toTarget = true;
        this.paintAll = false;
        this.offsetLeft = 0;
        this.offsetRight = worldWidth - VIEWPORT_SIZE_X;
        this.offsetTop = 0;
        this.offsetDown = worldHeight-VIEWPORT_SIZE_Y;
    }
    public Camera()
    {
        this.worldWidth = VIEWPORT_SIZE_X;
        this.worldHeight = VIEWPORT_SIZE_Y;
        this.camX = VIEWPORT_SIZE_X/2 - VIEWPORT_SIZE_X/2;
        this.camY = VIEWPORT_SIZE_Y/2 - VIEWPORT_SIZE_Y/2;
        this.targetPointX = this.camX;
        this.targetPointY = this.camY;
        this.toTarget = true;
        this.paintAll = false;
        this.offsetLeft = 0;
        this.offsetRight = worldWidth - VIEWPORT_SIZE_X;
        this.offsetTop = 0;
        this.offsetDown = worldHeight-VIEWPORT_SIZE_Y;
    }
    public void checkBound()
    {
        if (camX >= offsetRight)
        {
            camX = offsetRight;
        }
        else if (camX <= offsetLeft)
        {
            camX = offsetLeft;
        }
        if (camY >= offsetDown)
        {
            camY = offsetDown;
        }
        else if(camY <= offsetTop)
        {
            camY = offsetTop;
        }
    }
    //鏡頭視窗尺寸
    public double getTop()
    {
        return this.camY;
    }
    public double getDown()
    {
        return this.camY + VIEWPORT_SIZE_Y;
    }
    public double getLeft()
    {
        return this.camX;
    }
    public double getRight()
    {
        return this.camX + VIEWPORT_SIZE_X;
    }
    
    //-------------------鏡頭移動-------------------
    public void accurate(int keyCode)
    {
        if(Math.abs(this.moveSpeedX) < MOVE_SPEED_LIMIE 
                && Math.abs(this.moveSpeedY) < MOVE_SPEED_LIMIE)
        {
            switch(keyCode)
            {
                case 3:
                    this.moveSpeedY += 2;
                    break;
                case 0:
                    this.moveSpeedX -= 2;
                    break;
                case 1:
                    this.moveSpeedX += 2;
                    break;
                case 2:
                    this.moveSpeedY -= 2;
                    break;
            }
        }
    }
    public int getCameraX()  //鏡頭X，位在視窗左上角
    {
        return (int)this.camX;
    }
    public int getCameraY()
    {
        return (int)this.camY;
    }
    public void decelerate()
    {
        this.moveSpeedX = this.moveSpeedX * TWEEN;
        this.moveSpeedY = this.moveSpeedY * TWEEN;
    }
    //手動移動模式
    public void moveX()
    {
        this.camX += this.moveSpeedX;
    }
    public void moveY()
    {
        this.camY += this.moveSpeedY;
    }
    //鎖定玩家移動處理
    public void setTarget(Actor actor)
    {
        if(!actor.alive())
        {
            return;
        }
        this.targetPointX = actor.getX() - Global.Frame.X.getScreenSize()/2;
        this.targetPointY = actor.getY() - Global.Frame.Y.getScreenSize()/2 -100;
    }
    public void setTarget(GameObject obj)
    {
        this.targetPointX = obj.getX() - Global.Frame.X.getScreenSize()/2;
        this.targetPointY = obj.getY() - Global.Frame.Y.getScreenSize()/2 -100;
    }
    public void targetMove()
    {
        this.camX += (this.targetPointX - this.camX) /7 ; //X的距離/7
        this.camY += (this.targetPointY - this.camY) /7 ;
    }
    public void move()
    {
        if(this.toTarget)
        {
            targetMove();
            
        }
        else
        {
            moveX();
            moveY();
            decelerate();
        }
    }
    public void update()
    {   
        move();
        checkBound();
    }
    
    public boolean inRange(GameObject obj)
    {
        if(obj.rectTop() + obj.rectHeight() >= this.getTop() 
                && obj.rectDown() - obj.rectHeight() <= this.getDown() 
                && obj.rectLeft() + obj.rectWidth() >= this.getLeft()
                && obj.rectRight() - obj.rectWidth() <= this.getRight()
                || this.paintAll==true)
        {
            return true;
        }
        
        return false;
    }
    public boolean inRange(int x, int y, int w, int h)
    {
        if(y + h >= this.getTop()
                && y <= this.getDown() 
                && x + w >= this.getLeft()
                && x <= this.getRight()
                || this.paintAll==true)
        {
            return true;
        }
        return false;
    }
    //鎖定玩家判斷
    public void toPlayer()
    {
        this.toTarget = true;
    }
    public void offPlayer()
    {
        this.toTarget = false;
    }
    public void paintAll()
    {
        this.paintAll = true;
    }
    public void paintPiece()
    {
        this.paintAll = false;
    }
    public void paint(Graphics g)
    {
        g.drawRect((int)this.camX - VIEWPORT_SIZE_X/2
                , (int)this.camY - VIEWPORT_SIZE_Y/2
                , (int)this.camX + VIEWPORT_SIZE_X/2
                , (int)this.camY + VIEWPORT_SIZE_Y/2);
    }

}
