package gameobj;

import camera.Camera;
import controllers.AudioRC;
import enemies.Enemy;
import foxstate.*;
import gameTile.Tile;
import static gameobj.Status.HEALTH_MAX;
import item.Item;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.*;
import util.*;

public class Actor extends GameObject{
    
    public enum ActorChoice
    {
        FOX(100,96,40,48,33,32),
        RABBIT(111,96,40,60,37,32);
        
        private int w;
        private int h;
        private int colliderW;
        private int colliderH;
        private int imgW;
        private int imgH;
        
        private ActorChoice(int w, int h, int colliderW, int colliderH,  int imgW, int imgH)
        {
            this.w = w;
            this.h = h;
            this.colliderW = colliderW;
            this.colliderH = colliderH;
            this.imgW = imgW;
            this.imgH = imgH;
        }
        public int getWidth()
        {
            return this.w;
        }
        public int getHeight()
        {
            return this.h;
        }
        public int getColliderW()
        {
            return this.colliderW;
        }
        public int getColliderH()
        {
            return this.colliderH;
        }
        public int getImgW()
        {
            return this.imgW;
        }
        public int getImgH()
        {
            return this.imgH;
        }
    }
    public static final double GRAVITY = 0.3;
    public static final double HURT_BACK_X = 17;
    public static final double HURT_BACK_Y = 20;
    public static final double HURT_BACK_HEAD_Y = 4;
    public static final int DEFAULT_LIFE = 2;
    public static final int DEFAULT_HEALTH = 12;
    public static final int DEFAULT_COIN = 0;
    public static final int DEFAULT_DIAMOND = 0;
    
    private float accer;
    private StateContext stateContext;
    private ActorChoice actorChoice;
    private ActorSkill skill;
    private Status status;
    
    private boolean canJump;
    private boolean canClimb;
    
    private boolean reFuncState;
    private Delay reFuncDelay;
    private boolean flick;
    private Delay flickDelay;
    
    private boolean isDead;
    private Delay rebornDelay;
    
    private boolean isFinish;
    private boolean missionComplete;
    
    public Actor(ActorChoice actorChoice)
    {
        super(288,288,actorChoice.getWidth(),
                  actorChoice.getHeight(),
                  actorChoice.getColliderW(),
                  actorChoice.getColliderH());
        this.actorChoice = actorChoice;
        this.status = new Status(actorChoice, 288, 288, DEFAULT_LIFE, DEFAULT_HEALTH, DEFAULT_COIN, DEFAULT_DIAMOND);
        this.isFinish = false;
        this.reFuncState = true;
        this.reFuncDelay = new Delay(45);
        this.flickDelay = new Delay(3);
        this.rebornDelay =new Delay(90);
        initActorRenderer(actorChoice);
    }
    public void initStatus(int x, int y)
    {
        setX(x);
        setY(y);
        this.isDead = false;
        this.setBornPosition(x, y);
        this.missionComplete = false;
    }
    public void initSkill()
    {
        this.skill.initStatus(this);
    }
    public void initActorRenderer(ActorChoice actorChoice)
    {
        if(actorChoice == ActorChoice.FOX)
        {
            this.skill = new FoxSkill();
            this.stateContext = new StateContext(this, new FoxRenderer());
        }
        else if(actorChoice == ActorChoice.RABBIT)
        {
            this.skill = new RabbitSkill();
            this.stateContext = new StateContext(this, new RabbitRenderer());
        }
    }
    public void rangeItems(ArrayList<Item> items)
    {
        if(!this.status.isAlive())
        {
            return;
        }
        for(int i=0;i<items.size();i++)
        {
            if(isCollision(items.get(i)))
            {
                items.get(i).effect(this);
            }
        }
//        int i = getY() / Global.UNIT_SIZE.Tile.getColliderH();
//        int j = getX() / Global.UNIT_SIZE.Tile.getColliderW();
//        for(int m=0;m<9;m++){
//            Tile tile = tiles[i - 1 + m/3][j - 1 + m%3];
//            senseTile(tile);
//        }
    }
    public void rangeEnemies(ArrayList<Enemy> enemies)
    {
        if(!this.status.isAlive())
        {
            return;
        }
        for(int i=0;i<enemies.size();i++)
        {
            if(downCollsion(enemies.get(i), 0) && enemies.get(i).isAlive() && enemies.get(i).canHurt()) //腳
            {
                if(!this.reFuncState)
                {
                    this.velY = 0;
                    this.accY = 0;
                    hurt();
                }
                return;
            }
            if(topCollsion(enemies.get(i), 0) && enemies.get(i).isAlive() && enemies.get(i).canHurt())//頭
            {
                if(!this.reFuncState)
                {
                    this.velY = 0;
                    this.accY = 0;
                    hurtHead();
                }
                return;
            }
            if(leftCollsion(enemies.get(i), 0) && enemies.get(i).isAlive() && enemies.get(i).canHurt())//左
            {
                
                if(!this.reFuncState)
                {
                    this.velX = 0;
                    hurt();
                }
                return;
            }
            if(rightCollsion(enemies.get(i), 0) && enemies.get(i).isAlive() && enemies.get(i).canHurt())//右
            {
                if(!this.reFuncState)
                {
                    this.velX = 0;
                    hurt();
                }
                return;
            }
            if(isCollision(enemies.get(i)) && enemies.get(i).isAlive() && enemies.get(i).canHurt())
            {
                if(!this.reFuncState)
                {
                    this.velX = 0;
                    hurt();
                }
                return;
            }
        }
    }
    public void rangeTile(Tile[][] tiles)
    {
        initSkill();
        if(!this.status.isAlive())
        {
            return;
        }
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                if(tiles[i][j].collider.centerY() >= this.collider.top() - this.colliderHeight()        //a
                        && tiles[i][j].collider.centerY() <=  this.collider.down() + this.colliderHeight()    //b
                        && tiles[i][j].collider.centerX() >=  this.collider.left() - this.colliderWidth()    //c
                        && tiles[i][j].collider.centerX() <=  this.collider.right() + this.colliderWidth()) //d
                {
                    this.skill.senseTile(tiles[i][j], this);
                    senseTile(tiles[i][j]);
                    senseClimbTile(tiles[i][j]);
                }
            }
        }
    }
    public void senseTile(Tile tile)
    {
        if(!this.status.isAlive())
        {
            return;
        }
        switch(tile.getType())
        {
            case NORMAL:
                normalTileHit(tile);
                break;
            case CANJUMP:
                canJumpTileHit(tile);
                break;
            case CANHURT:
                canHurtTileHit(tile);
                break;
            case CLIMB_HEAD:
                canClimbTileHit(tile);
                break;
            case CLIMB_NODE:
                canClimbTileHit(tile);
                break;
        }
    }
    public void normalTileHit(Tile tile)
    {
        if(downCollsion(tile, 0)) //腳
        {
            setY(tile.colliderTop() - this.colliderHeight()/2);
            this.velY = 0;
            this.accY = 0;
        }
        if(topCollsion(tile, 0))//頭
        {
            setY(tile.colliderDown() + this.colliderHeight()/2);
            this.velY = 0;
        }
        if(leftCollsion(tile, 0))//左
        {            
            setX(tile.colliderRight() + this.colliderWidth()/2 +1);
            this.velX = 0;
        }
        if(rightCollsion(tile, 0))//右
        {
            this.velX = 0;
            setX(tile.colliderLeft() - this.colliderWidth()/2 -1 );
        }
    }
    public void canJumpTileHit(Tile tile)
    {
        if(downCollsion(tile, 0) 
                && this.colliderDown() -8 < tile.colliderTop()) //腳
        {
            setY(tile.colliderTop() - this.colliderHeight()/2);
            this.velY = 0;
            this.accY = 0;
        }
    }
    public void canHurtTileHit(Tile tile)
    {
        if(downCollsion(tile, 0)) //腳
        {
            setY(tile.colliderTop() - this.colliderHeight()/2);
            this.velY = 0;
            this.accY = 0;
            if(!this.reFuncState)
            {
                hurt();
            }
        }
        if(topCollsion(tile,0))//頭
        {
            setY(tile.colliderDown() + this.colliderHeight()/2);
            this.velY = 0;
            if(!this.reFuncState)
            {
                hurtHead();
            }
        }
        if(leftCollsion(tile,0))//左
        {
            setX(tile.colliderRight() + this.colliderWidth()/2);
            this.velX = 0;
            if(!this.reFuncState)
            {
                hurt();
            }
        }
        if(rightCollsion(tile,0))//右
        {
            setX(tile.colliderLeft() - this.colliderWidth()/2);
            this.velX = 0;
            if(!this.reFuncState)
            {
                hurt();
            }
        }
    }
    public void canClimbTileHit(Tile tile)
    {
        if(downCollsion(tile,0) 
                && this.colliderDown() -8 < tile.colliderTop()
                && this.stateContext.animationNum() !=1
                && tile.getType().typeNum() == 3)  //CLIMB_HEAD
        {
            setY(tile.colliderTop() - this.colliderHeight()/2);
            this.velY = 0;
            this.accY = 0;
        }
    }
    public void hurt()
    {
        if(!this.alive())
        {
            return;
        }
        AudioRC.getInstance().play(AudioPath.HURT);
        this.stateContext.changeState(new Hurt());
        stateLock();
        if(this.stateContext.getDir()==Renderer.Dir.LEFT)
        {
            this.velX += HURT_BACK_X;
        }
        else if(this.stateContext.getDir()==Renderer.Dir.RIGHT)
        {
            this.velX += -HURT_BACK_X;
        }
        this.velY += -HURT_BACK_Y;
        setHealth(healthNum()-1);
        setRefuncState(true);
        if(!this.alive())
        {
            zeroVelX();
            zeroVelY();
        }
    }
    public void hurtHead()
    {
        if(!this.alive())
        {
            return;
        }
        AudioRC.getInstance().play(AudioPath.HURT);
        this.stateContext.changeState(new Hurt());
        stateLock();
        if(this.stateContext.getDir()==Renderer.Dir.LEFT)
        {
            this.velX += HURT_BACK_X;
        }
        else if(this.stateContext.getDir()==Renderer.Dir.RIGHT)
        {
            this.velX += -HURT_BACK_X;
        }
        this.velY += HURT_BACK_HEAD_Y;
        setHealth(healthNum()-1);
        setRefuncState(true); 
        if(!this.alive())
        {
            zeroVelX();
            zeroVelY();
        }
    }
    public void senseClimbTile(Tile tile)
    {
        if(collider.centerX()+5 < tile.colliderRight()
                && collider.centerX()-5 > tile.colliderLeft()
                && collider.centerY() - collider.height()/2 -5 < tile.colliderDown()
                && collider.centerY() + collider.height()/2 +5 > tile.colliderTop()
                && (tile.getType()==Tile.TileType.CLIMB_HEAD || tile.getType()==Tile.TileType.CLIMB_NODE))
        {
            this.canClimb = true;
        }
    }
    public void keyInput(int keyCode)
    {
        if(!this.status.isAlive())
        {
            return;
        }
        this.skill.keyInput(keyCode, this);
    }
    public void keyReleased(int keyCode)
    {
        if(!this.status.isAlive())
        {
            return;
        }
        this.skill.keyReleased(keyCode, this);
    }
    //狀態處理
    public void changeState(State newState)
    {
        this.stateContext.changeState(newState);
    }
    public void stateUnlock()
    {
        this.stateContext.stateUnlock();
    }
    public void stateLock()
    {
        this.stateContext.stateLock();
    }
    public int StateNum()
    {
        return this.stateContext.animationNum();
    }
    public boolean canJump()
    {
        return this.canJump;
    }
    public void setCanJump(boolean canJump)
    {
        this.canJump = canJump;
    }
    public boolean canClimb()
    {
        return this.canClimb;
    }
    public void setCanClimb(boolean canClimb)
    {
        this.canClimb = canClimb;
    }
    //數值控制
    public void setHealth(int health)
    {
        this.status.setHealth(health);
    }
    public void setCoin(int coin)
    {
        this.status.setCoin(coin);
    }
    public void setDiamond(int diamond)
    {
        this.status.setDiamond(diamond);
    }
    public int lifeNum()
    {
        return this.status.lifeNum();
    }
    public int healthNum()
    {
        return this.status.healthNum();
    }
    public int coinNum()
    {
        return this.status.coinNum();
    }
    public int diamondNum()
    {
        return this.status.diamondNum();
    }
    public void setLevelChangeStatus(int life, int coin)
    {
        this.status.setLevelChangeStatus(life, coin);
    }
    public boolean alive()
    {
        return this.status.isAlive();
    }
    
    //關卡目標
    public boolean missionState()
    {
        return this.missionComplete;
    }
    public void missionComplete()
    {
        this.missionComplete = true;
    }
    
    
    //受傷狀態，暫時無敵
    public void setRefuncState(boolean state)
    {
        this.reFuncState = state;
    }
    public void checkRefunc()
    {
        if(this.reFuncState && this.reFuncDelay.isTrig())
        {
            this.reFuncState = false;
        }
        else if(!this.alive())
        {
            this.reFuncState = false;
        }
    }
    public boolean reFuncState()
    {
        return this.reFuncState;
    }
    
    public ActorChoice actorChoice()
    {
       return this.actorChoice;
    }
    public void actorAnimation(Graphics g, Camera camera)
    {
        if(this.reFuncState && this.flickDelay.isTrig())
        {
            this.flick = !this.flick;
        }
        if(this.reFuncState && this.flick)
        {
            this.stateContext.paint(g, camera, this.actorChoice, getX(), getY(), this.width(), this.height());
            return;
        }
        else if(!this.reFuncState)
        {
            this.stateContext.paint(g, camera, this.actorChoice, getX(), getY(), this.width(), this.height());
        }
    }
    
    //重生
    public void setBornPosition(int bornX, int bornY)
    {
        this.status.setBornPosition(bornX, bornY);
    }
    public void reborn()
    {
        if(this.alive())
        {
            this.rebornDelay.reStart();
            return;
        }
        else if(!this.alive() && this.rebornDelay.isTrig())
        {
            if(this.status.lifeNum()>0)
            {
                this.setX(this.status.bornX());
                this.setY(this.status.bornY());
                this.status.initStatus_reborn();
                this.stateContext.changeState(new Idle());
                this.stateContext.stateUnlock();
                this.stateContext.setDir(Renderer.Dir.RIGHT);
                this.setRefuncState(true);
                this.zeroAccY();
                this.zeroVelX();
                this.zeroVelY();
                this.isDead = false;
            }
            else if(this.status.lifeNum()<=0)
            {
                this.isFinish = true;
            }
        }
    }
    public boolean finish()
    {
        return this.isFinish;
    }
    @Override
    public void gravityAcc()
    {
        if(!this.alive())
        {
            return;
        }
        this.accY += Actor.GRAVITY;
    }
    public void senser(Tile[][] tiles, ArrayList<Enemy> enemies, ArrayList<Item> items)
    {
        double cX = this.velX;
        rangeTile(tiles);
        rangeEnemies(enemies);
        if(this.velX != cX){                                                    //當受傷時，重新判斷周遭的碰撞判斷
            int i = getY() / Global.UNIT_SIZE.Tile.getColliderH();
            int j = getX() / Global.UNIT_SIZE.Tile.getColliderW();
            for(int m=0;m<9;m++){
                Tile tile = tiles[i - 1 + m/3][j - 1 + m%3];
                senseTile(tile);
            }
        }
//        rangeItems(items);
    }
    @Override
    public void update()
    {
        checkRefunc();
        speedControl();
        gravityAcc();
        this.skill.update(this);
        this.stateContext.stateControl(velX, velY);
        this.stateContext.update();
        this.status.update();
        reborn();
    }
    public void statusPaint(Graphics g, Camera camera)
    {
        this.status.paint(g, camera);
    }
    @Override
    public void paintComponent(Graphics g, Camera camera)
    {
        actorAnimation(g, camera);
    }
    public void checkAlive(Camera camera)
    {
        if(!isDead && !this.alive())
        {
            AudioRC.getInstance().play(AudioPath.ACTOR_DEATH);
            this.isDead = true;
        }
        if(!this.alive())
        {
            this.stateContext.changeState(new Die());
            int toPositionX = camera.getCameraX() + Global.Frame.X.getFrameSize()/2;
            int toPositionY = getY() - Global.Frame.X.getFrameSize()/2;
            if(Math.abs(toPositionX - getX()) > 5){
                this.setX(getX() + ((toPositionX - getX() > 0)?2:-2));
            }
            if(Math.abs(toPositionY - getY()) > 5){
                this.setY(getY() + ((toPositionY - getY() > 0)?(int)accer:-(int)accer));
            }
            accer -= 0.3f;
        }
        else if(this.alive())
        {
            accer = 10f;
        }
    }
}
