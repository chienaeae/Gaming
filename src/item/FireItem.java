package item;

import camera.Camera;
import gameTile.Tile;
import gameobj.Actor;
import itemFunc.IHandleItemFunc;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.ItemRenderer;
import util.Global;
import util.ImagePath;

public class FireItem extends InteractItem{

    private class FireRenderer extends ItemRenderer
    {
        private int status;

        public FireRenderer() 
        {
            super(ImagePath.FIRE_SHEET, Global.UNIT_SIZE.Fire);
            this.status = 0;
        }
        @Override
        public void end()
        {
            this.isEnd = true;
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
            if(this.status==0)
            {
                this.currentFrameIndex = 0;
                return;
            }
            else if(this.status== 1 && this.delay.isTrig())
            {
                this.currentFrameIndex = (this.currentFrameIndex+1) % (this.img.getWidth()/this.unit.getImgW());
            }
        }
        @Override
        public void paint(Graphics g, int x, int y)
        {
            g.drawImage(img, x, y, x + this.unit.getWidth(), y + this.unit.getHeight(),
                    currentFrameIndex * this.unit.getImgW(),
                    this.status *  this.unit.getImgH(),
                    currentFrameIndex * this.unit.getImgW() + this.unit.getImgW(),
                    this.status *  this.unit.getImgH() + this.unit.getImgH(),
                    null);
        }
    }
    
    public FireItem(int x, int y, IHandleItemFunc func)
    {
        super(x, y, func, Global.UNIT_SIZE.Fire);
        initRenderer(new FireRenderer());
    }
    @Override
    public void paintComponent(Graphics g, Camera camera) 
    {
        this.renderer.paint(g,
                getX() - camera.getCameraX() - width()/2,
                getY() - camera.getCameraY() - height()/2);
    }
}
