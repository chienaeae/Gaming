package choiceWindow;

import camera.Camera;
import controllers.AudioRC;
import controllers.ImageResourceController;
import gameobj.Actor;
import gameobj.Actor.ActorChoice;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.AudioPath;
import util.Delay;
import util.Global;
import util.ImagePath;

public class ScoreWindow 
{
    public static final int LIFE_UP_COIN = 100;
    private BufferedImage panel;
    
    private Word life;
    private Word coin;
    private Delay countDelay;
    private int lifeNum;
    private int coinNum;
    
    private Delay showDelay;
    private boolean lifeShow;
    private boolean coinShow;
    
    private Delay animationDelay;
    private BufferedImage lifeIcon;
    private BufferedImage coinIcon;
    
    private int lifeIconCurrentIndex;
    private int coinCurrentIndex;
    
    private Global.UNIT_SIZE coinUnit;
    private ActorChoice actorChoice;
    private int exact_coinNum;
    private int exact_lifeNum;
    
    private int state; //(0)未確定(1)返回主選單(2)進入下一關
    private boolean isFinish;
    private Word mainMenu;
    private Word nextLevel;
    private Curser curser;
    
    public ScoreWindow(ActorChoice actorChoice, int lifeNum, int coinNum)
    {
        this.actorChoice = actorChoice;
        this.exact_lifeNum = lifeNum;
        this.exact_coinNum = coinNum;
        this.showDelay = new Delay(60);
        this.countDelay = new Delay(2);
        this.animationDelay = new Delay(2);
        this.lifeNum = 0;
        this.coinNum = 0;
        this.lifeShow = false;
        this.coinShow = false;
        this.isFinish = false;
        this.state = 0;
        initImage();
        initWords();
    }
    public void initImage()
    {
        this.panel = ImageResourceController.getInstance().tryGetImage(ImagePath.SCORE_WINDOW_PANEL);
        if(this.actorChoice == ActorChoice.FOX)
        {
            this.lifeIcon = ImageResourceController.getInstance().tryGetImage(ImagePath.FOX_IDLE);
            this.coinIcon = ImageResourceController.getInstance().tryGetImage(ImagePath.COIN);
            this.coinUnit = Global.UNIT_SIZE.Coin;
        }
        else if(this.actorChoice == ActorChoice.RABBIT)
        {
            this.lifeIcon = ImageResourceController.getInstance().tryGetImage(ImagePath.RABBIT_IDLE);
            this.coinIcon = ImageResourceController.getInstance().tryGetImage(ImagePath.STAR);
            this.coinUnit = Global.UNIT_SIZE.Star;
        }
    }
    public void initWords()
    {
        this.life = new Word(650, 310, "X  "+this.lifeNum,"Sitka Text",47);
        this.life.setColor(255, 255, 255);
        this.coin = new Word(653, 410, "X  "+this.coinNum,"Sitka Text",47);
        this.coin.setColor(255, 255, 255);
    }
    public int state()
    {
        return this.state;
    }
    public int changedLife()
    {
        return this.exact_lifeNum;
    }
    public int changedCoin()
    {
        return this.exact_coinNum;
    }
    public void keyReleased(int keyCode)  // 6上 7下
    {
        if(this.isFinish && (keyCode==6 || keyCode==7)) AudioRC.getInstance().play(AudioPath.SELECTION);
        if(this.state==0 && this.isFinish)
        {
            switch(keyCode)
            {
                case 6:
                    this.state = 1;
                    this.curser = new Curser(480, 500, Curser.Texture.CIRCLE);
                    break;
                case 7:
                    this.state = 2;
                    this.curser = new Curser(480, 555, Curser.Texture.CIRCLE);
                    break;
            }
        }
        else if(this.state==1)
        {
            switch(keyCode)
            {
                case 6:
                    this.state = 2;
                    break;
                case 7:
                    this.state = 2;
                    break;
            }
        }
        else if(this.state==2)
        {
            switch(keyCode)
            {
                case 6:
                    this.state = 1;
                    break;
                case 7:
                    this.state = 1;
                    break;
            }
        }
    }
    public void wordControl()
    {
        if(this.state==0)
        {
            return;
        }
        if(this.state ==1)
        {
            this.mainMenu.setColor(255, 255, 255);
            this.mainMenu.setSize(47);
            this.nextLevel.setColor(195, 195, 195);
            this.nextLevel.setSize(42);
        }
        else if(this.state ==2)
        {
            this.nextLevel.setColor(255, 255, 255);
            this.nextLevel.setSize(47);
            this.mainMenu.setColor(195, 195, 195);
            this.mainMenu.setSize(42);
        }
    }
    public void curseControl()
    {
        if(this.state ==1)
        {
            this.curser.setVelY(-10);
            if(this.curser.getY()<=500)
            {
                this.curser.zeroVelY();
            }
            this.curser.speedControl();
        }
        else if(this.state ==2)
        {
            this.curser.setVelY(10);
            if(this.curser.getY()>=555)
            {
                this.curser.zeroVelY();
            }
            this.curser.speedControl();
        }
    }
    public void countControl()
    {
        if(this.lifeShow && !this.coinShow && this.countDelay.isTrig() && this.lifeNum < this.exact_lifeNum)
        {
            this.lifeNum++;
            AudioRC.getInstance().play(AudioPath.HEALTH);
        }
        if(this.lifeShow && this.coinShow && this.countDelay.isTrig() && this.coinNum < this.exact_coinNum)
        {
            this.coinNum++;
            AudioRC.getInstance().play(AudioPath.COIN);
            if(this.coinNum == LIFE_UP_COIN)
            {
                this.countDelay.resetDelayFrame(12);
            }
            if(this.coinNum > LIFE_UP_COIN)
            {
                AudioRC.getInstance().play(AudioPath.LIFE_ADD);
                this.coinNum -= LIFE_UP_COIN;
                this.exact_coinNum -= LIFE_UP_COIN;
                this.exact_lifeNum++;
                this.lifeNum++;
                this.countDelay.resetDelayFrame(2);
            }
        }
        if(this.isFinish && this.mainMenu==null && this.nextLevel == null)
        {
            this.mainMenu = new Word(530, 510, "Main Menu", "Sitka Text",42);
            this.mainMenu.setColor(195, 195, 195);
            this.nextLevel = new Word(580, 570, "Next", "Sitka Text",42);
            this.nextLevel.setColor(195, 195, 195);
            this.countDelay.stop();
        }
    }
    public void showControl()
    {
        if(this.isFinish)
        {
            return;
        }
        if(this.showDelay.isTrig())
        {
            if(!this.lifeShow)
            {
                this.lifeShow = true;
            }
            else if(this.lifeShow && this.lifeNum == this.exact_lifeNum)
            {
                this.coinShow = true;
            }
            if(this.lifeShow && this.lifeNum == this.exact_lifeNum && this.coinNum == this.exact_coinNum)
            {
                this.isFinish = true;
            }
        }
    }
    public void update()
    {
        wordControl();
        countControl();
        showControl();
        animationUpdate();
        curseControl();
    }
    private void animationUpdate()
    {
        if(this.animationDelay.isTrig())
        {
            this.coinCurrentIndex = (this.coinCurrentIndex + 1) % (this.coinIcon.getWidth()/this.coinUnit.getImgW());
            this.lifeIconCurrentIndex = (this.lifeIconCurrentIndex + 1) % (this.lifeIcon.getWidth()/this.actorChoice.getImgW());
        }
    }
    private void countRenderer(Graphics g)
    {
        if(this.lifeShow)
        {
            this.life.setTitle("X  "+this.lifeNum);
            this.life.paint(g);
        }
        if(this.coinShow)
        {
            if(this.coinNum==100)
            {
                this.coin.setColor(255, 255, 255);
                this.coin.setSize(47);
                this.coin.setTitle("X  ");
                this.coin.paint(g);
                this.coin.setColor(255, 242, 0);
                this.coin.setSize(59);
                this.coin.setTitle("   " + this.coinNum);
                this.coin.paint(g);
            }
            else
            {
                this.coin.setTitle("X  "+this.coinNum);
                this.coin.setColor(255, 255, 255);
                this.coin.setSize(47);
                this.coin.paint(g);
            }
            
        }
    }
    private void coinRenderer(Graphics g, int x, int y)
    {
        g.drawImage(this.coinIcon, x, y, x + this.coinUnit.getWidth()+8, y + this.coinUnit.getHeight()+8,
                this.coinCurrentIndex * this.coinUnit.getImgW(),
                0,
                this.coinCurrentIndex * this.coinUnit.getImgW() + this.coinUnit.getImgW(),
                this.coinUnit.getImgH(),
                null);
    }
    private void lifeRenderer(Graphics g, int x, int y)
    {
        g.drawImage(this.lifeIcon, x, y, this.actorChoice.getWidth()+ x, this.actorChoice.getHeight() + y,
                this.lifeIconCurrentIndex * this.actorChoice.getImgW(),
                0,
                this.lifeIconCurrentIndex * this.actorChoice.getImgW() + this.actorChoice.getImgW(),
                this.actorChoice.getImgH(),
                null);
    }
    public void paint(Graphics g, Camera camera)
    {
        g.drawImage(this.panel, 0, 0, Global.Frame.X.getFrameSize(), Global.Frame.Y.getFrameSize(), null);
        coinRenderer(g, 505, 370);
        lifeRenderer(g, 480, 234);
        countRenderer(g);
        if(this.isFinish && this.nextLevel!=null)
        {
            this.nextLevel.paint(g);
            this.mainMenu.paint(g);
            if(this.curser!=null)
            {
                this.curser.paint(g);
            }
        }
    }
}
