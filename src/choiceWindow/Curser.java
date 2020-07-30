package choiceWindow;

import camera.Camera;
import controllers.ImageResourceController;
import gameobj.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Global;
import util.ImagePath;

public class Curser extends GameObject
{
    public enum Texture
    {
        NORMAL,WOOD, CIRCLE;
    }
    
    private BufferedImage img;
    private Global.UNIT_SIZE unit;
    
    public Curser(int x, int y, Texture texture)
    {
        super(x, y, Global.UNIT_SIZE.Curser.getWidth(), Global.UNIT_SIZE.Curser.getHeight(),true);
        init(texture);
    }
    public void init(Texture texture)
    {
        switch (texture)
        {
            case NORMAL:
                this.img = ImageResourceController.getInstance().tryGetImage(ImagePath.CURSER);
                this.unit = Global.UNIT_SIZE.Curser;
                break;
            case WOOD:
                this.img = ImageResourceController.getInstance().tryGetImage(ImagePath.WOOD_CURSER);
                this.unit = Global.UNIT_SIZE.WoodCurser;
            case CIRCLE:
                this.img = ImageResourceController.getInstance().tryGetImage(ImagePath.CIRCLE_CURSER);
                this.unit = Global.UNIT_SIZE.CircleCurser;
                break;
        }
    }
    @Override
    public void update() 
    {
        
    }

    @Override
    public void paintComponent(Graphics g, Camera camera) 
    {
        g.drawImage(this.img, getX() - this.unit.getWidth()/2,
                getY() - this.unit.getHeight()/2,
                this.unit.getWidth() ,
                this.unit.getHeight() ,
                null);
    }
}
