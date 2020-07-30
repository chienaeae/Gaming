package gameobj;

import camera.Camera;
import gameobj.Actor.ActorChoice;
import java.awt.Graphics;
import renderer.StatusRenderer;
import util.Delay;
import util.Global;

public class Status 
{
    public static final int HEALTH_MAX = 12;
    public static final int DIAMOND_MAX = 3;
    public static final int LIFE_MAX = 3;
    
    private boolean isAlive;
    private int health;
    private int coin;
    private int diamond;
    private int life;
    private ActorChoice actorChoice;
    private StatusRenderer renderer;
    
    private int dieX;
    private int dieY;
    
    private int rebornX;
    private int rebornY;
    
    public Status(ActorChoice actorChoice, int bornX, int bornY, int life, int health, int coin, int diamond)
    {
        this.actorChoice = actorChoice;
        this.renderer = new StatusRenderer(actorChoice);
        initStatus(bornX, bornY, life, health, coin, diamond);
    }
    public void initStatus_reborn()
    {
        this.isAlive = true;
        setLife(this.life -1);
        setHealth(Actor.DEFAULT_HEALTH);
        setCoin(this.coin);
        setDiamond(this.diamond);
    }
    public void initStatus(int bornX, int bornY, int life, int health, int coin, int diamond)
    {
        this.isAlive = true;
        setBornPosition(bornX, bornY);
        setLife(life);
        setHealth(health);
        setCoin(coin);
        setDiamond(diamond);
    }
    public void setLevelChangeStatus(int life, int coin)
    {
        setLife(life);
        setCoin(coin);
        setDiamond(0);
    }
    public void setLife(int life)
    {
        this.life = life;
    }
    public void setHealth(int health)
    {
        this.health = health;
        if(this.health <= 0)
        {
            this.health = 0;
        }
        else if(this.health >= HEALTH_MAX)
        {
            this.health = HEALTH_MAX;
        }
        if(this.health==0)
        {
            this.isAlive = false;
        }
    }
    public void setCoin(int coin)
    {
        this.coin = coin;
        if(this.coin <= 0)
        {
            this.coin = 0;
        }
    }
    public void setDiamond(int diamond)
    {
        this.diamond = diamond;
        if(this.diamond <= 0)
        {
            this.diamond = 0;
        }
        if(this.diamond >= DIAMOND_MAX)
        {
            this.diamond = DIAMOND_MAX;
        }
    }
    public int lifeNum()
    {
        return this.life;
    }
    public int healthNum()
    {
        return this.health;
    }
    public int coinNum()
    {
        return this.coin;
    }
    public int diamondNum()
    {
        return this.diamond;
    }
    public void setBornPosition(int bornX, int bornY)
    {
        this.rebornX = bornX;
        this.rebornY = bornY;
    }
    public int bornX()
    {
        return this.rebornX;
    }
    public int bornY()
    {
        return this.rebornY;
    }
    public boolean isAlive()
    {
        return this.isAlive;
    }
    public void update()
    {
        this.renderer.animationUpdate();
    }
    public void paint(Graphics g, Camera camera)
    {
        this.renderer.statusProcess(g, this.life, this.health, this.coin, this.diamond);
    }
}
