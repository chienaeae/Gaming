package renderer;

import camera.Camera;
import controllers.ImageResourceController;
import gameTile.Sprite;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Delay;
import util.Global;
import util.ImagePath;

public class ItemRenderer 
{
    protected BufferedImage img;
    protected boolean isEnd;
    protected Global.UNIT_SIZE unit;
    protected int currentFrameIndex;
    protected Delay delay;
    
    public ItemRenderer(String imgRoot, Global.UNIT_SIZE unit)
    {
        this.delay = new Delay(2);
        this.currentFrameIndex = 0;
        initItemRenderer(imgRoot, unit);
        this.isEnd = false;
    }
    public void initItemRenderer(String imgRoot, Global.UNIT_SIZE unit)
    {
        this.img = ImageResourceController.getInstance().tryGetImage(imgRoot);
        this.unit = unit;
    }
    public void pause() 
     {
        this.delay.stop();
    }
    public void start() 
    {
        this.delay.start();
    }
    public void end()
    {
        this.isEnd = true;
        this.currentFrameIndex = 0;
        this.img = ImageResourceController.getInstance().tryGetImage(ImagePath.ITEM_FEEDBACK);
    }
    public boolean isValid()
    {
        return !this.isEnd;
    }
    public boolean isEnd()
    {
        if(this.isEnd == true && this.currentFrameIndex == (this.img.getWidth()/Global.UNIT_SIZE.Item_Feedback.getImgW())-1)
        {
            return true;
        }
        return false;
    }
    public void update()
    {
        if(this.isEnd && this.currentFrameIndex == (this.img.getWidth()/Global.UNIT_SIZE.Item_Feedback.getImgW())-1)
        {
            return;
        }
        if(this.delay.isTrig())
        {
            if(this.isEnd()==false)
            {
                this.currentFrameIndex = (this.currentFrameIndex+1) % (this.img.getWidth()/this.unit.getImgW());
            }
            else
            {
                this.currentFrameIndex = (this.currentFrameIndex+1) % (this.img.getWidth()/Global.UNIT_SIZE.Item_Feedback.getImgW());
            }
        }
    }
    public void paint(Graphics g, int x, int y)
    {
        if(this.isEnd)
        {
            g.drawImage(img, x - Global.UNIT_SIZE.Item_Feedback.getWidth()/3,
                    y - Global.UNIT_SIZE.Item_Feedback.getHeight()/3,
                    x + this.unit.getWidth() + Global.UNIT_SIZE.Item_Feedback.getWidth()/3,
                    y + this.unit.getHeight() + Global.UNIT_SIZE.Item_Feedback.getHeight()/3,
                currentFrameIndex * Global.UNIT_SIZE.Item_Feedback.getImgW(),
                0,
                currentFrameIndex * Global.UNIT_SIZE.Item_Feedback.getImgW() + Global.UNIT_SIZE.Item_Feedback.getImgW(),
                Global.UNIT_SIZE.Item_Feedback.getImgH(), null);
        }
        else if(!this.isEnd)
        {
            g.drawImage(img, x, y, x + this.unit.getWidth(), y + this.unit.getHeight(),
                currentFrameIndex * this.unit.getImgW(),
                0,
                currentFrameIndex * this.unit.getImgW() + this.unit.getImgW(),
                this.unit.getImgH(), null);
        }
    }
}
