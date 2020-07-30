package item;

import camera.Camera;
import gameTile.Tile;
import gameobj.Actor;
import itemFunc.*;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.ItemRenderer;
import util.Global;
import util.ImagePath;

public class ChestItem extends InteractItem{
    
    public enum Color{
      BLACK(0), RED(1);

      private int colorNum;
      private Color(int colorNum)
      {
          this.colorNum = colorNum;
      }
      public int colorNum()
      {
          return this.colorNum;
      }
  }
    private class ChestRenderer extends ItemRenderer
    {
    
    private Color color;
    private int status;
    public ChestRenderer(Color color)
    {
        super(ImagePath.CHEST_SHEET, Global.UNIT_SIZE.Chest);
        this.color = color;
        this.status = 0;
    }
    @Override
    public void end()
    {
        this.isEnd = true;
        this.currentFrameIndex = 0;
        this.status = 1;
    }
    @Override
    public boolean isEnd()
    {
        return false;
    }
    @Override
    public void update()
    {
        if(this.isEnd && this.currentFrameIndex == (this.img.getWidth()/Global.UNIT_SIZE.Chest.getImgW())-1)
        {
            this.currentFrameIndex = (this.img.getWidth()/Global.UNIT_SIZE.Chest.getImgW())-1;
            return;
        }
        if(this.delay.isTrig())
        {
            this.currentFrameIndex = (this.currentFrameIndex+1) % (this.img.getWidth()/this.unit.getImgW());
        }
    }
    @Override
    public void paint(Graphics g, int x, int y)
    {
        g.drawImage(img, x, y, x + this.unit.getWidth(), y + this.unit.getHeight(),
                currentFrameIndex * this.unit.getImgW(),
                this.status *  this.unit.getImgH() + this.color.colorNum() * 2 * this.unit.getImgH(),
                currentFrameIndex * this.unit.getImgW() + this.unit.getImgW(),
                this.status *  this.unit.getImgH() + this.color.colorNum() * 2 * this.unit.getImgH() + this.unit.getImgH(), null);
    }
}
    
    public ChestItem(int x, int y, IHandleItemFunc func, Color color)
    {
        super(x, y, func, Global.UNIT_SIZE.Chest);
        initRenderer(new ChestRenderer(color));
    }
    public void initContent(ChestEffectItem.ChestContent content)
    {
        switch (content)
        {
            case GEM:
                super.func.initDiversity(0);
                break;
            case LIFE:
                super.func.initDiversity(1);
                break;
            case COIN:
                super.func.initDiversity(2);
                break;
        }
    }
    @Override
    public void paintComponent(Graphics g, Camera camera) 
    {
        this.renderer.paint(g,
                getX() - camera.getCameraX() - width()/2,
                getY() - camera.getCameraY() - height()/2);
    }
}
