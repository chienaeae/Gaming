package renderer;

import endingAnimation.Role;
import gameTile.Sprite;
import gameobj.Actor;
import java.awt.image.BufferedImage;
import util.Global;
import util.ImagePath;

public class FoxRenderer extends Renderer
{
    private BufferedImage[] idle;
    private BufferedImage[] climb;
    private BufferedImage[] crouch;
    private BufferedImage[] hurt;
    private BufferedImage[] jump;
    private BufferedImage[] fall;
    private BufferedImage[] run;
    
    public FoxRenderer()
    {
        super(0, Actor.ActorChoice.FOX.getImgW(), Actor.ActorChoice.FOX.getImgH());
    }
    public FoxRenderer(int defaultMove)
    {
        super(defaultMove, Role.RoleList.FOX);
    }
    @Override
    public void initImg()
    {
        Sprite spr = new Sprite(Actor.ActorChoice.FOX.getImgW(), Actor.ActorChoice.FOX.getImgH(), ImagePath.FOX_IDLE);
        this.idle = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        spr.reloadSprite(ImagePath.FOX_CLIMB);
        this.climb = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0)};
        spr.reloadSprite(ImagePath.FOX_CROUCH);
        this.crouch = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0)};
        spr.reloadSprite(ImagePath.FOX_HURT);
        this.hurt = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0)};
        spr.reloadSprite(ImagePath.FOX_JUMP);
        this.jump = new BufferedImage[]{spr.getSprite(0, 0)};
        this.fall = new BufferedImage[]{spr.getSprite(1, 0)};
        spr.reloadSprite(ImagePath.FOX_RUN);
        this.run = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0),
            spr.getSprite(4, 0),
            spr.getSprite(5, 0)};
    }
    @Override
    public void setFrame() {
        if (this.currentMove == 0) {
            this.currentMovePack = this.idle;
        }
        if (this.currentMove == 1) {
            this.currentMovePack = this.climb;
        }
        if (this.currentMove == 2) {
            this.currentMovePack = this.crouch;
        }
        if (this.currentMove == 3) {
            this.currentMovePack = this.hurt;
        }
        if (this.currentMove == 4) {
            this.currentMovePack = this.jump;
        }
        if (this.currentMove == 5) {
            this.currentMovePack = this.fall;
        }
        if (this.currentMove == 6) {
            this.currentMovePack = this.run;
        }
    }
}
