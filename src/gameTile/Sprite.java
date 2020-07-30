package gameTile;

import controllers.ImageResourceController;
import java.awt.image.BufferedImage;

public class Sprite 
{
    private BufferedImage spriteSheet;
    private int tileSizeX;
    private int tileSizeY;
    
    public Sprite(int tileSizeX, int tileSizeY, String path)
    {
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
        loadSprite(path);
    }
    public int imageWidth()
    {
        return this.spriteSheet.getWidth();
    }
    public int imageHeight()
    {
        return this.spriteSheet.getHeight();
    }
    public void loadSprite(String path) {

        this.spriteSheet = null;
        this.spriteSheet = ImageResourceController.getInstance().tryGetImage(path);
    }
    public void resetSize(int tileSizeX, int tileSizeY)
    {
        this.tileSizeX = tileSizeX;
        this.tileSizeY = tileSizeY;
    }
    public void reloadSprite(String path)
    {
        this.spriteSheet = ImageResourceController.getInstance().tryGetImage(path);
    }
    public BufferedImage getSprite(int xGrid, int yGrid) {
        
        return this.spriteSheet.getSubimage(xGrid * this.tileSizeX, yGrid * this.tileSizeY, this.tileSizeX, this.tileSizeY);
    }
}
