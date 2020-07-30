package renderer;

import choiceWindow.Word;
import controllers.ImageResourceController;
import gameobj.Actor;
import gameobj.Actor.ActorChoice;
import gameobj.Status;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Delay;
import util.Global;
import util.ImagePath;

public class StatusRenderer 
{
    private ActorChoice actorChoice;
    private Delay delay;
    private int coinCurrentIndex;
    private int diamondCurrentIndex;
    private int lifeIconCurrentIndex;
    private BufferedImage lifeIcon;
    private BufferedImage health;
    private BufferedImage coin;
    private Global.UNIT_SIZE coinUnit;
    private BufferedImage diamond;
    private BufferedImage diaNull;
    private Word title;
    
    public StatusRenderer(ActorChoice actorChoice)
    {
        this.actorChoice = actorChoice;
        this.title = new Word(0, 0, " ", " ", 0);
        this.delay = new Delay(2);
        this.delay.start();
        init(this.actorChoice);
    }
    public void init(ActorChoice actorChoice)
    {
        this.health = ImageResourceController.getInstance().tryGetImage(ImagePath.HEART);
        if(actorChoice == ActorChoice.FOX)
        {
            this.lifeIcon = ImageResourceController.getInstance().tryGetImage(ImagePath.FOX_IDLE);
            this.coin = ImageResourceController.getInstance().tryGetImage(ImagePath.COIN);
            this.coinUnit = Global.UNIT_SIZE.Coin;
        }
        else
        {
            this.lifeIcon = ImageResourceController.getInstance().tryGetImage(ImagePath.RABBIT_IDLE);
            this.coin = ImageResourceController.getInstance().tryGetImage(ImagePath.STAR);
            this.coinUnit = Global.UNIT_SIZE.Star;
        }
        this.diamond = ImageResourceController.getInstance().tryGetImage(ImagePath.GEM);
        this.diaNull = ImageResourceController.getInstance().tryGetImage(ImagePath.GEM_NULL);
    }
    public void setCoinNum(int coin)
    {
        this.title.setTitle("  " + coin);
        this.title.setFont("Leelawadee UI");
        this.title.setPosition(150, 182);
        this.title.setSize(37);
        this.title.setColor(255, 64, 0);
    }
    public void setLifeNum(int life)
    {
        this.title.setTitle("  " + life);
        this.title.setFont("Leelawadee UI");
        this.title.setPosition(1120, 125);
        this.title.setSize(37);
        this.title.setColor(255, 64, 0);
    }
    public void update()
    {
        animationUpdate();
    }
    public void statusProcess(Graphics g, int life, int health, int coin, int diamond)
    {
        lifeRenderer(g, 1020, 50, life);
        healthRenderer(g, 100, 75, 60, health);
        coinRenderer(g, 100, 150, coin);
        diamondRenderer(g , 1040, 150, 50, diamond);
    }
    public void animationUpdate()
    {
        if(this.delay.isTrig())
        {
            this.coinCurrentIndex = (this.coinCurrentIndex + 1) % (this.coin.getWidth()/this.coinUnit.getImgW());
            this.diamondCurrentIndex = (this.diamondCurrentIndex + 1) % (this.diamond.getWidth()/Global.UNIT_SIZE.Gem.getImgW());
            this.lifeIconCurrentIndex = (this.lifeIconCurrentIndex + 1) % (this.lifeIcon.getWidth()/this.actorChoice.getImgW());
        }
    }
    public void healthRenderer(Graphics g, int x, int y, int dis, int health)
    {
        int k = Status.HEALTH_MAX / 4;
        int theHeart = (health-1) / 4 ;
        for(int i=0;i<k;i++)
        {
            if(i > theHeart)
            {
                g.drawImage(this.health, x + i*dis, y, x + Global.UNIT_SIZE.Heart.getWidth() + i*dis, y + Global.UNIT_SIZE.Heart.getHeight(),
                    4 * Global.UNIT_SIZE.Heart.getImgW(),
                    0,
                    4 * Global.UNIT_SIZE.Heart.getImgW() + Global.UNIT_SIZE.Heart.getImgW(),
                    Global.UNIT_SIZE.Heart.getImgH(),
                    null);
            }
            else if(i == theHeart)
            {
                g.drawImage(this.health, x + i*dis, y, x + Global.UNIT_SIZE.Heart.getWidth() + i*dis, y + Global.UNIT_SIZE.Heart.getHeight(),
                    Math.abs((health-1) % 4 - 3) * Global.UNIT_SIZE.Heart.getImgW(),
                    0,
                    Math.abs((health-1) % 4 - 3) * Global.UNIT_SIZE.Heart.getImgW() + Global.UNIT_SIZE.Heart.getImgW(),
                    Global.UNIT_SIZE.Heart.getImgH(),
                    null);
            }
            else if(i < theHeart)
            {
                g.drawImage(this.health, x + i*dis, y, x + Global.UNIT_SIZE.Heart.getWidth() + i*dis, y + Global.UNIT_SIZE.Heart.getHeight(),
                    0,
                    0,
                    Global.UNIT_SIZE.Heart.getImgW(),
                    Global.UNIT_SIZE.Heart.getImgH(),
                    null);
            }
        }
    }
    public void coinRenderer(Graphics g, int x, int y, int coin)
    {
        g.drawImage(this.coin, x, y, x + this.coinUnit.getWidth(), y + this.coinUnit.getHeight(),
                this.coinCurrentIndex * this.coinUnit.getImgW(),
                0,
                this.coinCurrentIndex * this.coinUnit.getImgW() + this.coinUnit.getImgW(),
                this.coinUnit.getImgH(),
                null);
        setCoinNum(coin);
        this.title.paint(g);
    }
    public void diamondRenderer(Graphics g , int x, int y, int dis, int diamond)
    {
        int theD = diamond;  //1顆
        for(int i=0; i<Status.DIAMOND_MAX; i++)
        {
            if(i < theD)
            {
                g.drawImage(this.diamond, x + dis * i, y, x + Global.UNIT_SIZE.Gem.getWidth() + dis * i, y + Global.UNIT_SIZE.Gem.getHeight(),
                    this.diamondCurrentIndex * Global.UNIT_SIZE.Gem.getImgW(),
                    0,
                    this.diamondCurrentIndex * Global.UNIT_SIZE.Gem.getImgW() + Global.UNIT_SIZE.Gem.getImgW(),
                    Global.UNIT_SIZE.Gem.getImgH(),
                    null);
            }
            else
            {
                g.drawImage(this.diaNull, x + dis * i, y, x + Global.UNIT_SIZE.Gem.getWidth() + dis * i, y + Global.UNIT_SIZE.Gem.getHeight(),
                    this.diamondCurrentIndex * Global.UNIT_SIZE.Gem.getImgW(),
                    0,
                    this.diamondCurrentIndex * Global.UNIT_SIZE.Gem.getImgW() + Global.UNIT_SIZE.Gem.getImgW(),
                    Global.UNIT_SIZE.Gem.getImgH(),
                    null);
            }
        }
    }
    public void lifeRenderer(Graphics g, int x, int y, int life)
    {
        g.drawImage(this.lifeIcon, x, y, this.actorChoice.getWidth()+ x, this.actorChoice.getHeight() + y,
                this.lifeIconCurrentIndex * this.actorChoice.getImgW(),
                0,
                this.lifeIconCurrentIndex * this.actorChoice.getImgW() + this.actorChoice.getImgW(),
                this.actorChoice.getImgH(),
                null);
        setLifeNum(life);
        this.title.paint(g);
    }
}
