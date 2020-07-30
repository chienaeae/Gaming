package choiceWindow;

import background.Background;
import background.LayerManger;
import background.SelfMoveLayer;
import camera.Camera;
import controllers.ImageResourceController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import renderer.Renderer;
import util.Delay;
import util.ImagePath;

public class ChangeSceneWindow 
{
    public static final int TITLE_PX = 300;
    public static final int TITLE_PY = 300;
    private final int[] flash = new int[]{25,9,19,20,9,15,19,8};
    private static final int DURATION = 100;
    
    private int currentFlashIndex;
    private boolean isTitleShow;
    private Delay flashDelay;
    
    private LayerManger layers;
    private BufferedImage title;
    private int toLevel;
    private Camera camera;
    
    private Delay durDelay;
    private boolean isFinish;
    
    public ChangeSceneWindow()
    {
        this.camera = new Camera();
        this.toLevel = 3;
        this.title = ImageResourceController.getInstance().tryGetImage(ImagePath.WORD);
        this.layers = new LayerManger();
        this.currentFlashIndex = 0;
        this.durDelay = new Delay(DURATION);
        this.isFinish = false;
        this.flashDelay = new Delay(this.flash[this.currentFlashIndex]);
    }
    public void initLayer()
    {
        this.layers.add(new SelfMoveLayer(ImagePath.SC_LAYER_STAR, Background.CameraSpeed.FREEZE, 0 ,Renderer.Dir.LEFT));
        this.layers.add(new SelfMoveLayer(ImagePath.SC_LAYER_FOUR, Background.CameraSpeed.EXTREME_SLOW, 0 ,Renderer.Dir.LEFT));
        this.layers.add(new SelfMoveLayer(ImagePath.SC_LAYER_THREE, Background.CameraSpeed.SLOW, 0 ,Renderer.Dir.LEFT));
        this.layers.add(new SelfMoveLayer(ImagePath.SC_LAYER_TWO, Background.CameraSpeed.MEIDUM, 0 ,Renderer.Dir.LEFT));
        this.layers.add(new SelfMoveLayer(ImagePath.SC_LAYER_ONE, Background.CameraSpeed.HIGH, 0 ,Renderer.Dir.LEFT));
    }
    public void setToLevel(int toLevel)
    {
        this.toLevel = toLevel;
    }
    
    public void update()
    {
        if(this.durDelay.isTrig())
        {
            this.isFinish = true;
            this.durDelay.stop();
        }
        titleFlash();
        this.layers.update();
    }
    public boolean isFinish()
    {
        return this.isFinish;
    }
    public void titleFlash()
    {
        if(this.flashDelay.isTrig())
        {
            this.isTitleShow = !this.isTitleShow;
            if(this.isTitleShow)
            {
                this.currentFlashIndex = (this.currentFlashIndex + 1) % this.flash.length;
                this.flashDelay.resetDelayFrame(this.flash[this.currentFlashIndex]);
            }
            else
            {
                this.flashDelay.resetDelayFrame(4);
            }
        }
    }
    public void paint(Graphics g)
    {
        this.layers.paint(g, this.camera);
        if(this.isTitleShow)
        {
            g.drawImage(this.title,
                    TITLE_PX,
                    TITLE_PY,
                    650 + TITLE_PX,
                    97 + TITLE_PY,
                    0,
                    694*(this.toLevel-1),
                    4234 , 694*this.toLevel,
                    null) ;
        }
    }
}
