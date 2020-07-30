package renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import renderer.*;
import util.Delay;
import util.Global;

public class MonsterRender 
{
    private LevelOneMonsterRenderer controller;
    private BufferedImage[] currentImagePack;
    private int currentAnimation;
    private int currentFrameIndex;
    private Renderer.Dir dir;
    private Delay delay;
    private Global.UNIT_SIZE size;
    private boolean isAlive;  //怪物死亡
    
    public MonsterRender(LevelOneMonsterRenderer controller, int animationIndex, Global.UNIT_SIZE size)
    {
        this.controller = controller;
        this.currentImagePack = this.controller.getAnimationPack(animationIndex);
        this.size = size;
        this.dir = Renderer.Dir.LEFT;
        this.delay = new Delay(2);
        this.isAlive = true;
        start();
    }
    public void setImagePack(int animationIndex)
    {
        if(this.isAlive == true)
        {
            this.currentAnimation = currentAnimation;
            this.currentImagePack = this.controller.getAnimationPack(animationIndex);
        }
    }
    public int currentAnimation()
    {
        return this.currentAnimation;
    }
    public void pause() 
     {
        this.delay.stop();
    }
    public void start() 
    {
        this.delay.start();
    }
    public void reSetMovement() 
    {
        this.currentFrameIndex = 0;
    }
    public void setDir(Renderer.Dir dir) 
    {
        this.dir = dir;
    }
    public void die()
    {
        if(this.isAlive == false && this.currentFrameIndex==this.currentImagePack.length-1)
        {
            return;
        }
        setImagePack(9);
        reSetMovement();
        this.isAlive = false;
    }
    public boolean isEnd()   //怪物死亡，並且死亡動畫結束
    {
        if(!this.isAlive
                && this.currentFrameIndex==this.currentImagePack.length-1)
        {
            return true;
        }
        return false;
    }
    public void update() 
    {
        if(this.isAlive == false && this.currentFrameIndex==this.currentImagePack.length-1)
        {
            return;
        }
        if(this.delay.isTrig()) 
        {
            currentFrameIndex = (currentFrameIndex + 1) % this.currentImagePack.length;
        }
    }
    public void paint(Graphics g, int x, int y, int w, int h) {
        
        if(this.isAlive == false && this.currentFrameIndex==this.currentImagePack.length-1)
        {
            return;
        }
        if(currentFrameIndex >= this.currentImagePack.length){
            currentFrameIndex = 0;
        }
        if(this.isAlive==false && this.size!=Global.UNIT_SIZE.Squirrel)
        {
            g.drawImage(this.currentImagePack[currentFrameIndex], 
                    x,
                    y,
                    x + Global.UNIT_SIZE.death.getWidth(),
                    y + Global.UNIT_SIZE.death.getHeight(),
                    0,
                    0,
                    Global.UNIT_SIZE.death.getImgW(),
                    Global.UNIT_SIZE.death.getImgH(),
                    null);
            return;
        }
        else if(this.isAlive==false && this.size==Global.UNIT_SIZE.Squirrel)
        {
            g.drawImage(this.currentImagePack[currentFrameIndex], 
                    x,
                    y,
                    x + Global.UNIT_SIZE.death.getWidth() + 150,
                    y + Global.UNIT_SIZE.death.getHeight() + 90,
                    0,
                    0,
                    Global.UNIT_SIZE.death.getImgW(),
                    Global.UNIT_SIZE.death.getImgH(),
                    null);
            return;
        }
        if (this.dir == Renderer.Dir.LEFT) {
            g.drawImage(this.currentImagePack[currentFrameIndex], x, y, x + w, y + h,
                    0,
                    0,
                    this.size.getImgW(),
                    this.size.getImgH(),
                    null);
        } else if (this.dir == Renderer.Dir.RIGHT) {
            g.drawImage(this.currentImagePack[currentFrameIndex], x, y, x + w, y + h,
                    this.size.getImgW(),
                    0,
                    0,
                    this.size.getImgH(),
                    null);
        }
    }
}
