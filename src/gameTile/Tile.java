package gameTile;

import camera.Camera;
import gameobj.GameObject;
import graph.Rect;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Global;

public class Tile extends GameObject
{
    public enum TileType
    {
        NORMAL(0),
        CANJUMP(1),
        CANHURT(2),
        CLIMB_HEAD(3),
        CLIMB_NODE(4),
        APPARENT(5);
        
        private int typeNum;
        
        private TileType(int typeNum)
        {
            this.typeNum = typeNum;
        }
        public int typeNum()
        {
            return this.typeNum;
        }
    }
    private BufferedImage img;
    private TileType type;

    
    public Tile(int positionX, int positionY, BufferedImage img, TileType type)    //positionX 和positionY 代表在map上的位置
    {
        super(positionX * Global.UNIT_SIZE.Tile.getWidth() + Global.UNIT_SIZE.Tile.getWidth()/2,
              positionY * Global.UNIT_SIZE.Tile.getHeight() + Global.UNIT_SIZE.Tile.getHeight()/2,
                          Global.UNIT_SIZE.Tile.getWidth() ,
                          Global.UNIT_SIZE.Tile.getHeight(),true);
        this.img = img;
        this.type = type;
    }
    public BufferedImage getImage()
    {
        return this.img;
    }
    public TileType getType()
    {
        return this.type;
    }
    @Override
    public void update()
    {
        
    }
    @Override
    public void paintComponent(Graphics g, Camera camera)
    {
        g.drawImage(img,
                rect.left() - camera.getCameraX(),
                rect.top()  - camera.getCameraY(),
                Global.UNIT_SIZE.Tile.getWidth(),
                Global.UNIT_SIZE.Tile.getHeight(), null);
    }
}
