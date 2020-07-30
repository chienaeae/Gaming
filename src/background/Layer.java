package background;

import camera.Camera;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.Renderer;
import util.Global;

public class Layer 
{
    protected Background head;
    protected Background[] backgroundArr;
    protected Renderer.Dir dir;
    protected int mapWidth;
    protected int mapHeight;
    
    public Layer(int mapWidth, int mapHeight, String root, Background.CameraSpeed speed)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.head = new Background(root,0,0, speed);
        initArr();
    }
    public Layer(int mapWidth, int mapHeight, String root, Background.CameraSpeed speed, int addHeight)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.head = new Background(root,0,0, speed);
        this.head.setY(addHeight);
        initArr();
    }
    public Layer(int mapWidth, int mapHeight, String root, Background.CameraSpeed speed, Renderer.Dir dir)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.head = new Background(root,0,0, speed);
        this.dir = dir;
        initArr();
    }
    public Layer(int mapWidth, int mapHeight, String root, Background.CameraSpeed speed,int addHeight, Renderer.Dir dir)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.head = new Background(root,0,0, speed);
        this.dir = dir;
        this.head.setY(addHeight);
        initArr();
    }
      //提供給Animation使用的建構子，最後的scale為乘以幾倍
    public Layer(int mapWidth, int mapHeight, String root, int frameX, int frameY,  Background.CameraSpeed speed, Renderer.Dir dir, int scale)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.head = new AnimationBackground(root,frameX, frameY ,0 ,0 , speed);
        this.dir = dir;
        this.head.setScale(scale);
        initArr();
    }
    public Layer(int mapWidth, int mapHeight, String root, int frameX, int frameY,  Background.CameraSpeed speed,int addHeight, Renderer.Dir dir, int scale)
    {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.head = new AnimationBackground(root,frameX, frameY ,0 ,0 , speed);
        this.dir = dir;
        this.head.setY(addHeight);
        this.head.setScale(scale);
        initArr();
    }
    public void initArr()
    {
        //head的右方，距離大地圖之右方之距離
        int dis = (this.head.right()+ (this.mapWidth - Global.Frame.X.getFrameSize())) * this.head.speed().CameraSpeed()/20;
        //一共需要多少個bg
        int times = dis/this.head.width()+3;
        this.backgroundArr = new Background[times];
        for(int i=0;i<times;i++)
        {
            if(i==0)
            {
                this.backgroundArr[i] = this.head;
            }
            else
            {
                this.backgroundArr[i] = new Background(this.backgroundArr[i-1], Renderer.Dir.LEFT);
            }
        }
    }
    public void setY(int newY)
    {
        this.head.setY(newY);
        for(int i=0;i<this.backgroundArr.length;i++)
        {
            this.backgroundArr[i].setY(newY);
        }
    }
    public int getY()
    {
        return this.head.top();
    }
    public void update()
    {
        
    }
    public void paint(Graphics g, Camera camera)
    {
        for(int i=0;i<this.backgroundArr.length;i++)
        {
            this.backgroundArr[i].paint(g, camera);
        }
    }
}
