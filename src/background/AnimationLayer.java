package background;

import renderer.Renderer;
import util.Global;

public class AnimationLayer extends Layer
{
    public AnimationLayer(int mapWidth, int mapHeight, String root, int frameX, int frameY,  Background.CameraSpeed speed, int addHeight, Renderer.Dir dir, int scale)
    {
        super(mapWidth,
                mapHeight,
                root,
                frameX,
                frameY,
                speed,
                addHeight,
                dir,
                scale);
    }
    public AnimationLayer(String root, int frameX, int frameY,  Background.CameraSpeed speed, int addHeight, Renderer.Dir dir, int scale)
    {
        super(Global.Frame.X.getScreenSize(),
                Global.Frame.Y.getScreenSize(),
                root,
                frameX,
                frameY,
                speed,
                addHeight,
                dir,
                scale);
    }
    @Override
    public void initArr()
    {
        int dis = (super.head.right()+ (super.mapWidth - Global.Frame.X.getScreenSize())) * super.head.speed().CameraSpeed()/20;
        int times = dis/super.head.width()+2;
        super.backgroundArr = new Background[times];
        for(int i=0;i<times;i++)
        {
            if(i==0)
            {
                super.backgroundArr[i] = super.head;
            }
            else
            {
                super.backgroundArr[i] = new AnimationBackground((AnimationBackground)super.backgroundArr[i-1], super.dir);
            }
        }
    }
    public void animationUpdate()
    {
        for(int i=0; i<super.backgroundArr.length; i++)
        {
            super.backgroundArr[i].update();
        }
    }
    @Override
    public void update()
    {
        animationUpdate();
    }
}
