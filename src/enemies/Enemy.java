package enemies;

import camera.Camera;
import controllers.AudioRC;
import controllers.EnemyController;
import enemyMove.IEnemyAI;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import item.Item;
import java.awt.Graphics;
import java.util.ArrayList;
import renderer.LevelOneMonsterRenderer;
import renderer.MonsterRender;
import renderer.Renderer;
import util.AudioPath;
import util.Global;

public class Enemy extends GameObject
{
    public enum MoveSig
    {
        GO("go"), BACK("back"), ATTACK("attack"), IDLE("idle");
        
        private String sig;
        private MoveSig(String sig)
        {
            this.sig = sig;
        }
        public String toStirng()
        {
            return this.sig;
        }
    }
    protected MoveSig moveSig;
    private MonsterRender renderer;
    private Renderer.Dir dir;
    private boolean isAlive;
    private IEnemyAI ai;
    private Global.UNIT_SIZE size;
    private EnemyController.MonsterNum monsterNum;
    
    private boolean flick;
    private boolean canHurt;
    
    public Enemy(int x, int y,IEnemyAI ai, Global.UNIT_SIZE size, LevelOneMonsterRenderer controller, int animationIndex)
    {
        super(x, y, size.getWidth(), size.getHeight(), size.getColliderW(), size.getColliderH());
        this.size = size;
        this.ai = ai;
        initAI();
        initRenderer(controller, animationIndex);
        this.dir = Renderer.Dir.LEFT;
        this.moveSig = MoveSig.GO;
        this.isAlive = true;
        this.flick = true;
        this.canHurt = true;
    }
    public void initAI()
    {
        this.ai.initStatus(this);
    }
    
    //信號
    public void setMoveSig(MoveSig moveSig)
    {
        this.moveSig = moveSig;
    }
    public MoveSig MoveSig()
    {
        return this.moveSig;
    }
    
    //動畫
    public void initRenderer(LevelOneMonsterRenderer controller, int animationIndex)
    {
        this.renderer = new MonsterRender(controller, animationIndex,this.size);
    }
    public void rendererUpdate()
    {
        this.renderer.setDir(this.dir);
        this.renderer.update();
    }
    public void setAnimation(int aniIndex)
    {
        this.renderer.setImagePack(aniIndex);
    }
    public int animationNum()
    {
        return this.renderer.currentAnimation();
    }
    
    //方向
    public void changeDir()
    {
        this.velX = 0;
        this.accX = 0;
        if(this.dir == Renderer.Dir.LEFT)
        {
            this.dir = Renderer.Dir.RIGHT;
        }
        else if (this.dir == Renderer.Dir.RIGHT)
        {
            this.dir = Renderer.Dir.LEFT;
        }
    }
    public void setDir(Renderer.Dir dir)
    {
        this.velX = 0;
        this.accX = 0;
        this.dir = dir;
    }
    public Renderer.Dir dir()
    {
        return this.dir;
    }
    public void initMonsterNum(EnemyController.MonsterNum monsterNum)
    {
        this.monsterNum = monsterNum;
    }
    public EnemyController.MonsterNum monsterNum()
    {
        return this.monsterNum;
    }
    
    //生命狀態處理
    public boolean isAlive()
    {
        return this.isAlive;
    }
    public void die()
    {
        AudioRC.getInstance().play(AudioPath.ENEMY_DEATH);
        this.renderer.die();
        this.isAlive = false;
    }
    public void live()
    {
        this.isAlive = true;
    }
    public boolean isEnd()
    {
        return this.renderer.isEnd();
    }
    public void checkDie(Actor actor)
    {
        if(actor.step(this) && this.isAlive() && actor.alive())
        {
            actor.zeroVelY();
            actor.zeroAccY();
            actor.addVelY(-40);
            die();
        }
    }
    
    //松鼠硬直
    public boolean flick()
    {
        return this.flick;
    }
    public void flick(boolean isFlick)
    {
        this.flick = isFlick;
    }
    public void move()
    {
        this.ai.ahead(this);
    }
    public void senser(Tile[][] tiles, Actor actor)
    {
        this.ai.senser(tiles, this, actor);
    }
    public boolean canHurt()
    {
        return this.canHurt;
    }
    public void canHurt(boolean canHurt)
    {
        this.canHurt = canHurt;
    }
    public void treasureDrop(ArrayList<Item> items, Actor.ActorChoice characterChoice)
    {
        this.ai.treasureDrop(this, items, characterChoice);
    }
    @Override
    public void update()
    {
        rendererUpdate();
        speedControl();
        if(this.isAlive)
        {
            move();
        }
    }
    @Override
    public void paintComponent(Graphics g, Camera camera)
    {
        if(this.flick)
        {
            this.renderer.paint(g,
                    getX() - camera.getCameraX() - width()/2,
                    getY() - camera.getCameraY() - height()/2 -15,
                    this.size.getWidth(),
                    this.size.getHeight());
        }
    }
}
