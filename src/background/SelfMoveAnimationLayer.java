package background;

import renderer.Renderer;
import util.Global;

public class SelfMoveAnimationLayer extends Layer
{
    public SelfMoveAnimationLayer(String root, int frameX, int frameY,  Background.CameraSpeed speed, int addHeight, Renderer.Dir dir, int scale)
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
        super.backgroundArr = new Background[3];
        for(int i=0;i<3;i++)
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
            else if(super.backgroundArr[i].left() >= Global.Frame.X.getScreenSize() && super.dir==Renderer.Dir.RIGHT)
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
        checkBound();
        animationUpdate();
        if(super.dir == Renderer.Dir.LEFT)
        {
            for(int i=0;i<super.backgroundArr.length;i++)
            {
                super.backgroundArr[i].setX(super.backgroundArr[i].left() - super.backgroundArr[i].speed().CameraSpeed()/2);
            }
        }
        else if (this.dir == Renderer.Dir.RIGHT)
        {
            for(int i=0;i<super.backgroundArr.length;i++)
            {
                super.backgroundArr[i].setX(super.backgroundArr[i].left() + super.backgroundArr[i].speed().CameraSpeed()/2);
            }
        }
    }
}
