package background;

import camera.Camera;
import controllers.ImageResourceController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import renderer.Renderer;
import util.Delay;

public class AnimationBackground extends Background
{
    private BufferedImage[][][] backgroundImgArr;
    private Delay delay;
    private int currentIndex;
    
    public AnimationBackground(String root, int frameX, int frameY, int left, int top, CameraSpeed speed)
    {
        super(root, frameX, frameY, left, top, speed);
        this.backgroundImgArr = genImageArray(ImageResourceController.getInstance().tryGetImage(root), frameX, frameY);
        this.delay = new Delay(15);
        this.currentIndex = 0;
    }
    public AnimationBackground(AnimationBackground ab, Renderer.Dir dir)
    {
        super(ab, dir);
        this.backgroundImgArr = ab.backgroundImgArr;
        this.delay = new Delay(15);
        this.currentIndex = 0;
    }
    @Override
    public void update()
    {
        if(this.delay.isTrig())
        {
            this.currentIndex = (this.currentIndex + 1) % this.backgroundImgArr.length;
        }
        setBackgroundImg(backgroundImgArr[this.currentIndex]);
    }
}
