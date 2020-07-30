package renderer;

import endingAnimation.Role;
import gameTile.Sprite;
import gameobj.Actor;
import java.awt.image.BufferedImage;
import util.Global;
import util.ImagePath;

public class RabbitRenderer extends Renderer
{
    
    private BufferedImage[] idle;
    private BufferedImage[] climb;
    private BufferedImage[] duck;
    private BufferedImage[] hurt;
    private BufferedImage[] jump;
    private BufferedImage[] fall;
    private BufferedImage[] run;
    
    public RabbitRenderer()
    {
        super(0, Actor.ActorChoice.RABBIT.getImgW(), Actor.ActorChoice.RABBIT.getImgH());
    }
    public RabbitRenderer(int defaultMove)
    {
        super(defaultMove, Role.RoleList.FOX);
    }
    @Override
    public void initImg() {
        
        Sprite spr = new Sprite(Actor.ActorChoice.RABBIT.getImgW(), Actor.ActorChoice.RABBIT.getImgH(), ImagePath.RABBIT_IDLE);

        this.idle = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0),
            spr.getSprite(4, 0),
            spr.getSprite(5, 0),
            spr.getSprite(6, 0),
            spr.getSprite(7, 0),
            spr.getSprite(8, 0)};
        spr.reloadSprite(ImagePath.RABBIT_CLIMB);
        this.climb = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        spr.reloadSprite(ImagePath.RABBIT_DUCK);
        this.duck = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        spr.reloadSprite(ImagePath.RABBIT_HURT);
        this.hurt = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0)};
        spr.reloadSprite(ImagePath.RABBIT_JUMP);
        this.jump = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        spr.reloadSprite(ImagePath.RABBIT_FALL);
        this.fall = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        spr.reloadSprite(ImagePath.RABBIT_RUN);
        this.run = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0),
            spr.getSprite(4, 0),
            spr.getSprite(5, 0),
            spr.getSprite(6, 0),
            spr.getSprite(7, 0)};
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
            this.currentMovePack = this.duck;
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
