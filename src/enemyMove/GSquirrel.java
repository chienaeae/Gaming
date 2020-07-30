package enemyMove;

import controllers.AudioRC;
import enemies.Enemy;
import foxstate.Hurt;
import gameTile.Tile;
import gameobj.Actor;
import static gameobj.Actor.HURT_BACK_X;
import static gameobj.Actor.HURT_BACK_Y;
import gameobj.GameObject;
import item.Item;
import item.TouchItem;
import itemFunc.SpurtCoinEffectItem;
import itemFunc.SpurtGemEffectIem;
import itemFunc.SpurtLiftEffectItem;
import java.util.ArrayList;
import renderer.Renderer;
import util.AudioPath;
import util.Delay;
import util.Global;
import util.ImagePath;

public class GSquirrel implements IEnemyAI{

    public enum mode
    {
        pos(new int[]{1,0,0,1}),
        neg(new int[]{1,0,0});
        
        private int[] modeRound;
        private mode(int []modeRound)
        {
            this.modeRound = modeRound;
        }
    }
    public static final int VISION_X = 700;
    public static final int VISION_Y = 650;
    public static final int LIFE_MAX = 25; 
    private static final double SPEED = 2.5;
    private int habitX;
    private int habitY;
    
    private int life;
    private mode mode;
    private int currentModeIndex;
    private int fightMode;  //(0)躍擊 (1)衝刺
    
    private boolean canJump;
    
    private boolean reFuncState;
    private Delay reFuncDelay;
    private Delay flickDelay;
    private Delay jumpDelay;
    
    private GameObject target;
    
    public GSquirrel()
    {
        genMode(Global.random(0, 1));
        this.life = LIFE_MAX;
        this.currentModeIndex = 0;
        this.reFuncState = false;
        this.flickDelay = new Delay(3);
        this.reFuncDelay = new Delay(45);
        this.jumpDelay = new Delay(35);
    }
    public void genMode(int num)
    {
        switch (num)
        {
            case 0:
                this.mode = mode.neg;
                break;
            case 1:
                this.mode = mode.pos;
                break;
        }
    }
    public void clickMode()
    {
        this.currentModeIndex = (this.currentModeIndex + 1)%this.mode.modeRound.length;
        this.fightMode = this.mode.modeRound[this.currentModeIndex];
        if(this.fightMode==0) //jumpAttack
        {
            this.jumpDelay.resetDelayFrame(30);
            this.jumpDelay.reStart();
        }
        else if(this.fightMode==1) // walkAttack
        {
            this.jumpDelay.resetDelayFrame(40);
            this.jumpDelay.reStart();
        }
    }
    public void actorStep(Enemy self, Actor actor)
    {
        if(actor.step(self) && self.isAlive() && actor.alive() && !this.reFuncState)
        {
            actor.zeroVelY();
            actor.zeroAccY();
            actor.addVelY(-40);
            this.life--;
            hurt(self);
            if(this.life==0)
            {
                this.reFuncState = false;
                self.flick(true);
                this.reFuncDelay.stop();
                self.die();
            }
        }
    }
    public boolean isHurting(Enemy self)
    {
        if(self.animationNum()==11 && self.velY()!=0)
        {
            return true;
        }
        else if(self.animationNum()==11 && self.velY()==0)
        {
            self.setAnimation(10);
            return true;
        }
        return false;
    }
    public void animationLogic(Enemy self)
    { 
        if(isHurting(self))
        {
            return;
        }
        else if(self.velX()==0 && self.velY()==0)
        {
            self.setAnimation(10);
        }
        else if(self.velY()!=0)
        {
            self.setAnimation(12);
        }
        else if(self.velY()==0 && self.velX()!=0)
        {
            self.setAnimation(13);
        }
    }
    public void refunc(Enemy self)
    {
        this.reFuncState = true;
        self.canHurt(false);
        this.reFuncDelay.start();
    }
    public void refuncAnimation(Enemy self)
    {
        if(this.reFuncDelay.isTrig())
        {
            this.reFuncState = false;
            self.canHurt(true);
            self.flick(true);
            this.reFuncDelay.stop();
            return;
        }
        if(this.reFuncState && this.flickDelay.isTrig())
        {
            self.flick(!self.flick());
        }
    }
    public void hurt(Enemy self)
    {
        clickMode();
        self.setAnimation(11);
        if(self.dir()==Renderer.Dir.LEFT)
        {
            self.setVelX(HURT_BACK_X);
        }
        else if(self.dir()==Renderer.Dir.RIGHT)
        {
            self.setVelX(-HURT_BACK_X);
        }
        self.setVelY(-HURT_BACK_Y);
        AudioRC.getInstance().play(AudioPath.ENEMY_DEATH);
        refunc(self);
    }
    public boolean backHabit(Enemy self)  
    {
        if(self.getX() <= this.habitX + self.colliderWidth()
                && self.getX() >= this.habitX -self.colliderWidth()
                && self.getY() >= this.habitY - self.colliderHeight()
                && self.getY() <= this.habitY + self.colliderHeight())
        {
            return true;
        }
        return false;
    }
    private void rushSpeed(Enemy self, int rushSpeed)
    {
        if(self.dir() == Renderer.Dir.RIGHT)
        {
            self.addVelX(rushSpeed);
        }
        else if (self.dir() == Renderer.Dir.LEFT)
        {
            self.addVelX(-rushSpeed);
        }
    }
    
    //tile碰撞判斷
    public void rangeTile(Enemy self, Tile[][] tiles)
    {
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                if(tiles[i][j].getY() >= self.colliderTop() - Actor.ActorChoice.FOX.getHeight()            //a
                        && tiles[i][j].getY() <=  self.colliderDown() + Actor.ActorChoice.FOX.getHeight()  //b
                        && tiles[i][j].getX() >=  self.colliderLeft() - Actor.ActorChoice.FOX.getWidth()   //c
                        && tiles[i][j].getX() <=  self.colliderRight() + Actor.ActorChoice.FOX.getWidth()) //d
                {
                    senseTile(self, tiles[i][j]);
                }
            }
        }
    }
    public void senseTile(Enemy self, Tile tile)
    {
        switch(tile.getType())
        {
            case NORMAL:
                solidTileHit(self, tile);
                break;
            case CANJUMP:
                solidTileHit(self, tile);
                break;
            case CANHURT:
                solidTileHit(self, tile);
                break;
            case CLIMB_HEAD:
                solidTileHit(self, tile);
                break;
            case CLIMB_NODE:
                break;
        }
    }
    public void solidTileHit(Enemy self, Tile tile)
    {
        if(self.downCollsion(tile, 0)) //腳
        {
            self.setY(tile.colliderTop() - self.colliderHeight()/2);
            self.zeroVelY();
            self.zeroAccY();
        }
        if(self.topCollsion(tile, 0))//頭
        {
            self.setY(tile.colliderDown() + self.colliderHeight()/2);
            self.zeroVelY();
        }
        if(self.leftCollsion(tile, 0))//左
        {
            self.setX(tile.colliderRight() + self.colliderWidth()/2 +1);
            self.zeroVelX();
        }
        if(self.rightCollsion(tile, 0))//右
        {
            self.setX(tile.colliderLeft() - self.colliderWidth()/2 -1);
            self.zeroVelX();
        }
    }
    
    //人物偵測
    private void groundTargetDirAt(Enemy self, GameObject obj)
    {
        groundTargetDirAt(self, obj.getX());
    }
    private void groundTargetDirAt(Enemy self, int targetX)
    {
        if(targetX > self.getX() && self.dir() == Renderer.Dir.LEFT)
        {
            self.changeDir();
        }
        else if(targetX < self.getX() && self.dir() == Renderer.Dir.RIGHT)
        {
            self.changeDir();
        }
    }
    private boolean groundDistanceSenser(Enemy self, GameObject obj, int disIn)
    {
        if(obj.getX() <= self.colliderRight() + disIn
                && obj.getX() >=  self.colliderLeft() -  disIn
                && obj.getY() >=  self.colliderTop() - self.colliderHeight()
                && obj.getY() <= self.colliderDown() + self.colliderHeight() *2)
        {
            return true;
        }
        return false;
    }
    private boolean groundDistanceSenser(Enemy self, GameObject obj, int disFrom, int disTo)
    {
        if(disFrom > disTo)
        {
            int tmp = disFrom;
            disFrom = disTo;
            disTo = disFrom;
        }
        if(obj.getX() <= self.colliderRight() + disTo
                && obj.getX() >=  self.colliderRight() + disFrom
                && obj.getY() >=  self.colliderTop() - self.colliderHeight()
                && obj.getY() <= self.colliderDown())
        {
            return true;
        }
        if(obj.getX() >= self.colliderLeft() - disTo
                && obj.getX() <=  self.colliderLeft() -  disFrom
                && obj.getY() >=  self.colliderTop() - self.colliderHeight()
                && obj.getY() <= self.colliderDown())
        {
            return true;
        }
        return false;
    }
    private boolean aboveDisSenser(Enemy self, GameObject obj)
    {
        if(obj.getX() <= self.colliderRight() + VISION_X
                && obj.getX() >=  self.colliderLeft() - VISION_X
                && obj.getY() >=  self.colliderTop() - VISION_Y
                && obj.getY() <= self.colliderDown() + VISION_Y)
        {
            return true;
        }
        return false;
    }
    private boolean belowDisSenser(Enemy self, GameObject obj)
    {
        if(obj.getX() <= self.colliderRight() + VISION_X
                && obj.getX() >=  self.colliderLeft() - VISION_X
                && obj.getY() >=  self.colliderTop() - VISION_Y
                && obj.getY() <= self.colliderDown() + VISION_Y)
        {
            return true;
        }
        return false;
    }
    private void walkAttack(Enemy self, Actor actor)
    {
        if(self.velY()!=0)
        {
            this.canJump = false;
        }
        else if(self.velY()==0 && this.jumpDelay.isTrig())
        {
            this.canJump = true;
        }
        if(!groundDistanceSenser(self, actor ,VISION_X) &&
                !aboveDisSenser(self, actor))
        {
            self.setMoveSig(Enemy.MoveSig.IDLE);
        }
        if(aboveDisSenser(self, actor) && this.canJump && !this.reFuncState)
        {
            groundTargetDirAt(self, actor);
            self.addVelY(-45);
            this.canJump = false;
            self.setMoveSig(Enemy.MoveSig.GO);
        }
        if(groundDistanceSenser(self, actor ,VISION_X))
        {
             groundTargetDirAt(self, actor);
            self.setMoveSig(Enemy.MoveSig.GO);
        }
    }
    private void jumpAttack(Enemy self, Actor actor)
    {
        if(self.velY()!=0)
        {
            this.canJump = false;
        }
        else if(self.velY()==0 && this.jumpDelay.isTrig())
        {
            this.canJump = true;
        }
        if(!groundDistanceSenser(self, actor ,VISION_X) &&
                !aboveDisSenser(self, actor) &&
                !belowDisSenser(self, actor))
        {
            self.setMoveSig(Enemy.MoveSig.IDLE);
        }
        if(aboveDisSenser(self, actor)&& !this.reFuncState)
        {
//            this.target = actor;
            groundTargetDirAt(self, actor);
            if(this.canJump)
            {
                self.addVelY(-65);
                this.canJump = false;
            }
            self.setMoveSig(Enemy.MoveSig.ATTACK);
        }
        if(groundDistanceSenser(self, actor ,VISION_X) && !this.reFuncState)
        {
//            this.target = actor;
            groundTargetDirAt(self, actor);
            if(this.canJump)
            {
                self.addVelY(-65);
                this.canJump = false;
            }
            self.setMoveSig(Enemy.MoveSig.ATTACK);
        }
        if(belowDisSenser(self, actor) && !this.reFuncState)
        {
//            this.target = actor;
            groundTargetDirAt(self, actor);
            if(this.canJump)
            {
                self.addVelY(-65);
                this.canJump = false;
            }
            self.setMoveSig(Enemy.MoveSig.ATTACK);
        }
    }
    @Override
    public void treasureDrop(Enemy self, ArrayList<Item> items, Actor.ActorChoice characterChoice)
    {
        items.add(new TouchItem(self.getX(), self.getY(), new SpurtGemEffectIem(),ImagePath.GEM,Global.UNIT_SIZE.Gem));
    }
    
    @Override
    public void initStatus(Enemy self) 
    {
        this.habitX = self.getX();
        this.habitY = self.getY();
        self.setMoveSig(Enemy.MoveSig.IDLE);
    }
    @Override
    public void senser(Tile[][] tiles, Enemy self, Actor actor) 
    {
        self.gravityAcc();
        refuncAnimation(self);
        animationLogic(self);
        actorStep(self, actor);
        rangeTile(self, tiles);
        
        switch(this.fightMode)
        {
            case 0:
                jumpAttack(self,actor);
                break;
            case 1:
                walkAttack(self, actor);
                break;
        }
    }
    @Override
    public void ahead(Enemy self) 
    {
        if(self.MoveSig() != Enemy.MoveSig.IDLE && self.dir() == Renderer.Dir.RIGHT)
        {
            self.addVelX(SPEED);
        }
        else if(self.MoveSig() != Enemy.MoveSig.IDLE && self.dir() == Renderer.Dir.LEFT)
        {
            self.addVelX(-SPEED);
        }
        else
        {   
            self.zeroVelX();
        }
//        if(self.dir() == Renderer.Dir.RIGHT && self.MoveSig() == Enemy.MoveSig.GO)
//        {
//            self.addVelX(SPEED*2);
//        }
//        else if (self.dir() == Renderer.Dir.LEFT && self.MoveSig() == Enemy.MoveSig.GO)
//        {
//            self.addVelX(-SPEED*2);
//        }
//        else if(self.MoveSig() == Enemy.MoveSig.IDLE)
//        {
//            self.zeroVelX();
//        }
//        if(self.MoveSig() == Enemy.MoveSig.ATTACK && self.dir() == Renderer.Dir.LEFT && self.velY()!=0 )
//        {
//            self.addVelX(-SPEED*2);
//        }
//        else if (self.MoveSig() == Enemy.MoveSig.ATTACK  && self.dir() == Renderer.Dir.RIGHT && self.velY()!=0 )
//        {
//            self.addVelX(SPEED*2);
//        }
    }
}
