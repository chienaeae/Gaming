package renderer;

import controllers.AudioRC;
import controllers.ImageResourceController;
import endingAnimation.Role;
import gameTile.Sprite;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.AudioPath;
import util.Delay;
import util.Global;
import util.ImagePath;

public abstract class Renderer 
{
    public enum Dir {
        LEFT, RIGHT;
    }
    public enum Forward{
        UP, DOWN;
    }

    //死掉狀態處理///
    private BufferedImage dieGlass;
    private boolean isGlassShow;
    private int dieCount;
    ////////////////
    
    protected BufferedImage[] currentMovePack;
    protected int currentFrameIndex;
    protected int currentMove;
    private int sizeX;
    private int sizeY;
    private Dir dir;
    private Delay delay;
    
    public Renderer(int currentMovement, int sizeX, int sizeY)
    {
        this.delay = new Delay(3);
        this.dir = Dir.RIGHT;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.dieGlass = ImageResourceController.getInstance().tryGetImage(ImagePath.GLASS_BROKEN);
        this.isGlassShow = false;
        initImg();
        setMovement(currentMovement);
    }
    public Renderer(int defaultMove, Role.RoleList role)////提供給過場動畫專用的renderer
    {
        this.delay = new Delay(3);
        this.dir = Dir.RIGHT;
        this.sizeX = role.getImgW();
        this.sizeY = role.getImgH();
        this.isGlassShow = false;
        this.dieGlass = null;
        initImg();
        setMovement(defaultMove);
    }
    
    public abstract void initImg();
    public abstract void setFrame();
    
    public void setMovement(int currentMove) {
        //死掉狀態
//        if(currentMove==9)
//        {
//            this.currentMove = 3;
//            setFrame();
//            this.currentMove = 9;
//            return;
//        }
        this.currentMove = currentMove;
        if(this.currentMove != 9)
        {
            this.isGlassShow = false;
            this.dieCount = 0;
        }
        setFrame();
    }
    public void pause() {
        this.delay.stop();
    }
    public void start() {
        this.delay.start();
    }
    public void setDir(Dir dir) {
        this.dir = dir;
    }
    public void reSetMovement() {
        this.currentFrameIndex = 0;
    }
    public boolean isMoveEnd() {
        if (this.currentFrameIndex == this.currentMovePack.length) {
            return true;
        }
        return false;
    }
    public void update() 
    {
        if(this.currentMove==9)
        {
            this.dieCount++;
        }
        if(this.currentMove==9 && this.dieCount >= 30)
        {
            this.dieCount = 30;
            this.currentFrameIndex = 0;
            return;
        }
        if (this.delay.isTrig()) {
            this.currentFrameIndex = (this.currentFrameIndex + 1) % this.currentMovePack.length;
        }
    }
    public void dieAnimation(Graphics g, int x, int y, int w, int h)
    {
        g.drawImage(this.currentMovePack[currentFrameIndex],
                x-8 * this.dieCount, y-8*this.dieCount, x + w + 8*this.dieCount, y + w + 8*this.dieCount,
                0,
                0,
                this.sizeX,
                this.sizeY,
                null);
        if(this.dieCount==30)
        {
            if(!this.isGlassShow)
            {
                this.isGlassShow = true;
                AudioRC.getInstance().play(AudioPath.ACTOR_HIT_GLASS);
            }
            g.drawImage(this.dieGlass, 0, 0, Global.Frame.X.getScreenSize(), Global.Frame.Y.getScreenSize(), null);
        }
    }
    public void paint(Graphics g, int x, int y, int w, int h) 
    {
        if(currentFrameIndex >= this.currentMovePack.length){
            currentFrameIndex = 0;
        }
        if (this.dir == Dir.RIGHT) {
            g.drawImage(this.currentMovePack[currentFrameIndex], x, y, x + w, y + h,
                    0,
                    0,
                    this.sizeX,
                    this.sizeY,
                    null);
        } else if (this.dir == Dir.LEFT) {
            g.drawImage(this.currentMovePack[currentFrameIndex], x, y, x + w, y + h,
                    this.sizeX,
                    0,
                    0,
                    this.sizeY,
                    null);
        }
    }
}
