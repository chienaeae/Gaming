package background;

import renderer.Renderer;
import util.Global;

public class SelfMoveLayer extends Layer
{
    public SelfMoveLayer(String root, Background.CameraSpeed speed, Renderer.Dir dir)
    {
        super(Global.Frame.X.getScreenSize(),
                Global.Frame.Y.getScreenSize(),
                root,
                speed,
                dir);
    }
    public SelfMoveLayer(String root, Background.CameraSpeed speed, int addHeight, Renderer.Dir dir)
    {
        super(Global.Frame.X.getScreenSize(),
                Global.Frame.Y.getScreenSize(),
                root,
                speed,
                addHeight,
                dir);
    }
    @Override
    public void initArr()
    {
        super.backgroundArr = new Background[3];
        for(int i=0;i<3;i++)
        {
            if(i==0)
            {
                super.backgroundArr[i] = super.head;
            }
            else
            {
                super.backgroundArr[i] = new Background(super.backgroundArr[i-1], super.dir);
            }
        }
    }
    public void checkBound()
    {
        for(int i=0;i<super.backgroundArr.length;i++)
        {
            if(super.backgroundArr[i].right() <= 0 && this.dir==Renderer.Dir.LEFT)
            {
                if(i==0)
                {
                    super.backgroundArr[i].setX(super.backgroundArr[2].right());
                }
                else
                {
                    super.backgroundArr[i].setX(super.backgroundArr[i-1].right());
                }
            }
            else if(super.backgroundArr[i].left() >= Global.Frame.X.getScreenSize() && this.dir==Renderer.Dir.RIGHT)
            {
                if(i==0)
                {
                    super.backgroundArr[i].setX(super.backgroundArr[2].left() + super.backgroundArr[i].width());
                }
                else
                {
                    super.backgroundArr[i].setX(super.backgroundArr[i-1].left() + super.backgroundArr[i].width());
                }
            }
        }
    }
    @Override
    public void update()
    {
        checkBound();
        if(super.dir == Renderer.Dir.LEFT)
        {
            for(int i=0;i<super.backgroundArr.length;i++)
            {
                super.backgroundArr[i].setX(super.backgroundArr[i].left() - super.backgroundArr[i].speed().CameraSpeed()/2);
            }
        }
        else if (super.dir == Renderer.Dir.RIGHT)
        {
            for(int i=0;i<super.backgroundArr.length;i++)
            {
                super.backgroundArr[i].setX(super.backgroundArr[i].left() + super.backgroundArr[i].speed().CameraSpeed()/2);
            }
        }
    }
}
