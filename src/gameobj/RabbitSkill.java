package gameobj;

import controllers.AudioRC;
import foxstate.Climb;
import gameTile.Tile;
import util.AudioPath;
import util.Delay;

public class RabbitSkill implements ActorSkill
{
    public static final double CLIMB_SPEED = 7;
    public static final double MOVE_SPEED = 1.5;
    public static final double JUMP_HEIGHT = -45;
    public static final double SECOND_JUMP_HEIGHT =-40 ;
    
    private boolean canSecondJump;
    private Delay secJumpDelay;
    
    ////頭部碰撞時，連續跳躍除錯
    private Delay headHitDealy;
    private boolean headHitLock;
    
    public RabbitSkill()
    {
        this.secJumpDelay = new Delay(4);
        this.headHitDealy = new Delay(20);
    }
    public void climb(int keyCode, Actor actor)
    {
        if(actor.canClimb() && keyCode==6)
        {
            actor.zeroAccY();
            actor.zeroVelY();
            actor.addVelY(-CLIMB_SPEED);
        }
        else if(actor.canClimb() && keyCode==7)
        {
            actor.zeroAccY();
            actor.zeroVelY();
            actor.addVelY(CLIMB_SPEED);
        }
        if(actor.canClimb())
        {
            actor.changeState(new Climb());
            actor.stateLock();
        }
        else
        {
            actor.stateUnlock();
        }
    }
    public void jump(Actor actor)
    {
        if(actor.canJump() && actor.StateNum()!=3)
        {
            AudioRC.getInstance().play(AudioPath.RABBIT_JUMP);
            this.secJumpDelay.reStart();
            actor.setCanJump(false);
            actor.zeroAccY();
            actor.zeroVelY();
            actor.addVelY(JUMP_HEIGHT);
        }
    }
    public void jumpSec(Actor actor)
    {
        if(this.canSecondJump)
        {
            AudioRC.getInstance().play(AudioPath.RABBIT_JUMP);
            actor.zeroAccY();
            actor.zeroVelY();
            actor.addVelY(SECOND_JUMP_HEIGHT);
            this.canSecondJump = false;
            this.secJumpDelay.stop();
        }
    }
    public void secJumpHandle(Actor actor)
    {
        if(this.secJumpDelay.isTrig() && !actor.canJump())
        {
            this.canSecondJump = true;
        }
    }
    public void fall(Actor actor)
    {
        if(actor.velY()>0)
        {
            actor.addAccY(-0.12);
        }
    }
    public void headHitLock()
    {
        if(!this.headHitLock)
        {
            return;
        }
        if(this.headHitLock && this.headHitDealy.isTrig())
        {
            this.headHitLock = false;
            this.headHitDealy.stop();
        }
    }
    @Override
    public void initStatus(Actor actor)
    {
        actor.setCanClimb(false);
    }
    @Override
    public void senseTile(Tile tile, Actor actor)
    {
        headHitLock();
        switch(tile.getType())
        {
            case NORMAL:
                if(actor.downCollsion(tile, 8)  && !this.headHitLock)
                {
                    actor.setCanJump(true);
                }
                if(actor.topCollsion(tile, 8))
                {
                    this.headHitLock = true;
                    this.headHitDealy.start();
                }
                break;
            case CANJUMP:
                if(actor.downCollsion(tile, 0) && actor.velY()==0)
                {
                    actor.setCanJump(true);
                }
                break;
            case CANHURT:
                if(actor.downCollsion(tile, 0))
                {
                    actor.setCanJump(true);
                }
                break;
            case CLIMB_HEAD:
                if(actor.downCollsion(tile, 0))
                {
                    actor.setCanJump(true);
                }
                break;
            case CLIMB_NODE:
                break;
        }
    }
    @Override
    public void keyInput(int keyCode, Actor actor)
    {
        switch(keyCode) //左(4),右(5),上(6),下(7)
        {
            case -1:
                jump(actor);
                jumpSec(actor);
                break;
            case 6:
                climb(keyCode,actor);
                break;
            case 7:
                climb(keyCode,actor);
                break;
            case 4:
                actor.addVelX(-MOVE_SPEED);
                break;
            case 5:
                actor.addVelX(MOVE_SPEED);
                break;
        }
    }
    @Override
    public void keyReleased(int keyCode, Actor actor)
    {
        switch(keyCode)
        {
            case 6:
                actor.stateUnlock();
                break;
            case 7:
                actor.stateUnlock();
                break;
        }
    }
    @Override
    public void update(Actor actor)
    {     
        fall(actor);
        secJumpHandle(actor);
    }
}
