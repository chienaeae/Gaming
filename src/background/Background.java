package background;

import camera.Camera;
import controllers.ImageResourceController;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import renderer.Renderer;
import util.Global;

public class Background 
{
    public enum CameraSpeed
    {
        EXTREME_HIGH(10),
        HIGH(8),
        MEIDUM(6),
        SLOW(4),
        EXTREME_SLOW(2),
        FREEZE(0);
        
        private int speed;
        private CameraSpeed(int speed)
        {
            this.speed = speed;
        }
        public int CameraSpeed()
        {
            return this.speed;
        }
    }
    
    private BufferedImage[][] backgroundImg;
    private int left;
    private int top;
    private int sizeX;
    private int sizeY;
    private int scale;
    private CameraSpeed speed;
    
    public Background(String root, int left, int top, CameraSpeed speed)
    {
        BufferedImage origin = ImageResourceController.getInstance().tryGetImage(root);
        this.sizeX = origin.getWidth()/10; 
        this.sizeY = origin.getHeight()/10;
        this.left = left;
        this.top = top;
        this.scale = 1;
        this.backgroundImg = genImageArray(origin);
        this.speed = speed;
    }
    public Background(String root, int frameX, int frameY, int left, int top, CameraSpeed speed)
    {
        BufferedImage origin = ImageResourceController.getInstance().tryGetImage(root);
        this.sizeX = frameX/10;
        this.sizeY = frameY/10;
        this.left = left;
        this.top = top;
        this.scale = 1;
        this.backgroundImg = genImageArray(origin);
        this.speed = speed;
    }
    public Background(Background frontBackground, Renderer.Dir dir)
    {
        this.backgroundImg = frontBackground.backgroundImg;
        this.sizeX = frontBackground.sizeX;
        this.sizeY = frontBackground.sizeY;
        if(dir == Renderer.Dir.LEFT)
        {
            this.left = frontBackground.right();
        }
        else if(dir == Renderer.Dir.RIGHT)
        {
            this.left = frontBackground.left() - frontBackground.width();
        }
        this.top = frontBackground.top;
        this.scale = frontBackground.scale;
        this.speed = frontBackground.speed;
    }
    public BufferedImage[][] genImageArray(BufferedImage origin)
    {
        BufferedImage[][] imgArray = new BufferedImage[10][10];
        for(int i=0;i<imgArray.length;i++)
        {
            for(int j=0;j<imgArray[i].length;j++)
            {
                imgArray[i][j] = origin.getSubimage(j * this.sizeX,
                                                    i * this.sizeY,
                                                    this.sizeX,
                                                    this.sizeY);
            }
        }
        return imgArray;
    }
    public static BufferedImage[][][] genImageArray(BufferedImage origin, int frameX, int frameY)
    {
        int sizeX = frameX/10;
        int sizeY = frameY/10;
        int count = origin.getWidth() / frameX;
        BufferedImage[][][] imgArray = new BufferedImage[count][10][10];
        for(int i=0;i < count;i++)
        {
            for(int j=0;j < imgArray[i].length;j++)
            {
                for(int k=0;k < imgArray[i][j].length;k++)
                {
                    imgArray[i][j][k] = origin.getSubimage((k + (i * 10)) * sizeX ,
                                                    j * sizeY,
                                                    sizeX,
                                                    sizeY);
                }
            }
        }
        return imgArray;
    }
    public void setBackgroundImg(BufferedImage[][] backgroundImg)
    {
        this.backgroundImg = backgroundImg;
    }
    public void setX(int left)
    {
        this.left = left;
    }
    public void setY(int top)
    {
        this.top = top;
    }
    public void setScale(int scale)
    {
        this.scale = scale;
    }
    public CameraSpeed speed()
    {
        return this.speed;
    }
    public int left()
    {
        return this.left;
    }
    public int top()
    {
        return this.top;
    }
    public int right()
    {
        return this.left + this.sizeX * 10 * this.scale;
    }
    public int down()
    {
        return this.top + this.sizeY * 10 * this.scale;
    }
    public int width()
    {
        return this.sizeX * 10;
    }
    public int height()
    {
        return this.sizeY * 10;
    }
    public int indexLeft(int row, int col)
    {
        return left() + col * sizeX * this.scale;
    }
    public int indexRight(int row, int col)
    {
        return left() + (col + 1) * sizeX * this.scale;
    }
    public int indexTop(int row, int col)
    {
        return top() + row * sizeY * this.scale; 
    }
    public int indexDown(int row, int col)
    {
        return top() + (row + 1) * sizeY * this.scale;
    }
    public void update()
    {
        
    }
    public void paint(Graphics g, Camera camera)
    {
        for(int i=0;i<this.backgroundImg.length;i++)
        {
            for(int j=0;j<this.backgroundImg[i].length;j++)
            {
                if(camera.inRange(indexLeft(i, j) + camera.getCameraX() - camera.getCameraX() * this.speed.CameraSpeed()/20,
                                   indexTop(i, j) + camera.getCameraY() - camera.getCameraY() * this.speed.CameraSpeed()/50,
                                    this.sizeX  * this.scale,
                                    this.sizeY * this.scale))
                {
                    g.drawImage(this.backgroundImg[i][j],
                            indexLeft(i, j) - camera.getCameraX() * this.speed.CameraSpeed()/20 ,
                            indexTop(i, j) - camera.getCameraY() * this.speed.CameraSpeed()/50 ,
                            this.sizeX  * this.scale,
                            this.sizeY * this.scale,
                            null);
                }
            }
        }
    }
}
