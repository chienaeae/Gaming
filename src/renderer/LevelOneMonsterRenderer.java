package renderer;

import gameTile.Sprite;
import java.awt.image.BufferedImage;
import util.Global;
import util.ImagePath;

public class LevelOneMonsterRenderer
{
    public enum Animation
    {
        OPOSSUM(0),
        BEE(1),
        EAGLE(2),
        SLUG(3),
        FROG_IDLE(4),
        FROG_JUMP(5),
        FROG_FALL(6),
        PIRANHA_IDLE(7),
        PIRANHA_ATTACK(8),
        ENEMY_DEATH(9),
        
        SQUIRREL_IDLE(10),
        SQUIRREL_HURT(11),
        SQUIRREL_JUMP(12),
        SQUIRREL_RUN(13);
        
        private int animationIndex;
        private Animation(int index)
        {
            this.animationIndex = index;
        }
        public int animationIndex()
        {
            return this.animationIndex;
        }
    }
    
    private BufferedImage[] opossum;
    private BufferedImage[] bee;
    private BufferedImage[] eagle;
    private BufferedImage[] slug;
    private BufferedImage[] frog_idle;
    private BufferedImage[] frog_jump;
    private BufferedImage[] frog_fall;
    private BufferedImage[] piranha_idle;
    private BufferedImage[] piranha_attack;
    private BufferedImage[] enemy_death;
    private BufferedImage[] squirrel_idle;
    private BufferedImage[] squirrel_hurt;
    private BufferedImage[] squirrel_jump;
    private BufferedImage[] squirrel_run;
    
    public LevelOneMonsterRenderer()
    {
        initImg();
    }
    public void initImg()
    {
        Sprite spr = new Sprite(Global.UNIT_SIZE.Opossum.getImgW(), Global.UNIT_SIZE.Opossum.getImgH(), ImagePath.OPOSSUM);
        this.opossum = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0),
            spr.getSprite(4, 0),
            spr.getSprite(5, 0)};
        
        spr.resetSize(Global.UNIT_SIZE.Bee.getImgW(), Global.UNIT_SIZE.Bee.getImgH());
        spr.loadSprite(ImagePath.BEE);
        this.bee = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0),
            spr.getSprite(4, 0),
            spr.getSprite(5, 0),
            spr.getSprite(6, 0),
            spr.getSprite(7, 0),};
        
        spr.resetSize(Global.UNIT_SIZE.Eagle.getImgW(), Global.UNIT_SIZE.Eagle.getImgH());
        spr.loadSprite(ImagePath.EAGLE);
        this.eagle = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        
        spr.resetSize(Global.UNIT_SIZE.Slug.getImgW(), Global.UNIT_SIZE.Slug.getImgH());
        spr.loadSprite(ImagePath.SLUG);
        this.slug = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        
        spr.resetSize(Global.UNIT_SIZE.Frog.getImgW(), Global.UNIT_SIZE.Frog.getImgH());
        spr.loadSprite(ImagePath.FROG_IDLE);
        this.frog_idle = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        
        spr.loadSprite(ImagePath.FROG_JUMP);
        this.frog_jump = new BufferedImage[]{spr.getSprite(1, 0)};
        this.frog_fall = new BufferedImage[]{spr.getSprite(2, 0)};
        
        spr.resetSize(Global.UNIT_SIZE.Piranha.getImgW(), Global.UNIT_SIZE.Piranha.getImgH());
        spr.loadSprite(ImagePath.PIRAHA_IDLE);
        this.piranha_idle = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0),
            spr.getSprite(4, 0)};
        spr.loadSprite(ImagePath.PIRAHA_ATTACK);
        this.piranha_attack = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0)};
        
        spr.resetSize(Global.UNIT_SIZE.death.getImgW(), Global.UNIT_SIZE.death.getImgH());
        spr.loadSprite(ImagePath.ENEMY_DEATH);
        this.enemy_death = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0),
            spr.getSprite(4, 0)};
        
        spr.resetSize(Global.UNIT_SIZE.Squirrel.getImgW(), Global.UNIT_SIZE.Squirrel.getImgH());
        spr.loadSprite(ImagePath.SQUIRREL_SHEET);
        this.squirrel_idle = new BufferedImage[]{spr.getSprite(0, 0),
            spr.getSprite(1, 0),
            spr.getSprite(2, 0),
            spr.getSprite(3, 0),
            spr.getSprite(4, 0),
            spr.getSprite(5, 0),
            spr.getSprite(6, 0),
            spr.getSprite(7, 0)};
        this.squirrel_hurt = new BufferedImage[]{spr.getSprite(0, 1),
            spr.getSprite(1, 1)};
        this.squirrel_jump = new BufferedImage[]{spr.getSprite(0, 2),
            spr.getSprite(1, 2),
            spr.getSprite(2, 2),
            spr.getSprite(3, 2)};
        this.squirrel_run = new BufferedImage[]{spr.getSprite(0, 3),
            spr.getSprite(1, 3),
            spr.getSprite(2, 3),
            spr.getSprite(3, 3),
            spr.getSprite(4, 3),
            spr.getSprite(5, 3)};
        
    }
    public BufferedImage[] getAnimationPack(int animationIndex) 
    {
        switch(animationIndex)
        {
            case 0:
                return this.opossum;
            case 1:
                return this.bee;
            case 2:
                return this.eagle;
            case 3:
                return this.slug;
            case 4:
                return this.frog_idle;
            case 5:
                return this.frog_jump;
            case 6:
                return this.frog_fall;
            case 7:
                return this.piranha_idle;
            case 8:
                return this.piranha_attack;
            case 9:
                return this.enemy_death;
            case 10:
                return this.squirrel_idle;
            case 11:
                return this.squirrel_hurt;
            case 12:
                return this.squirrel_jump;
            case 13:
                return this.squirrel_run;
            default:
                return null;
        }
    }
}