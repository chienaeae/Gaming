package endingAnimation;

import camera.Camera;
import endingAnimation.Event.keyCode;
import static endingAnimation.Role.RoleList.FOX;
import static endingAnimation.Role.RoleList.RABBIT;
import enemies.Enemy;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import java.awt.Graphics;
import renderer.Renderer;
import renderer.Renderer.Dir;
import util.Delay;
import util.Global;

public class Role extends GameObject
{
    public enum RoleList
    {
        FOX(100,96,40,48,33,32),
        RABBIT(111,96,40,60,37,32);
        
        private int w;
        private int h;
        private int colliderW;
        private int colliderH;
        private int imgW;
        private int imgH;
        
        private RoleList(int w, int h, int colliderW, int colliderH,  int imgW, int imgH)
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
    
    public interface IActing{
        
        public Event[] initEvents();
        public void animationLogic(GameObject self, Renderer renderer);
        public void move(Role role);
        public void setFall(Role role);
        
    }
    
    private static final double BASIC_MOVE_SPEED = 1.5;
    private static final double BASIC_JUMP_HEIGHT = -30;
    private IActing act;
    private RoleList role;
    private Renderer renderer;
    private Renderer.Dir dir;
    
    private double speed;
    private double jumpHeight;
    
    private boolean isFall;
    private boolean perfromLock;
    
    private Delay delay;
    private int currentActIndex;
    private keyCode currentEvent;
    private Event[] acting;
    
    public Role(int x, int y, RoleList role, Renderer renderer)
    {
        super(x, y, role.getWidth(),
                    role.getHeight(),
                    role.getColliderW(),
                    role.getColliderH());
        this.role = role;
        this.renderer = renderer;
        this.speed = BASIC_MOVE_SPEED;
        this.jumpHeight = BASIC_JUMP_HEIGHT;
        this.dir = Renderer.Dir.RIGHT;
        this.isFall = true;
    }
    public Role(int x, int y, RoleList role, Renderer renderer, double speed, double jumpHeight, Renderer.Dir dir)
    {
        super(x, y, role.getWidth(),
                    role.getHeight(),
                    role.getColliderW(),
                    role.getColliderH());
        this.role = role;
        this.renderer = renderer;
        this.speed = speed;
        this.jumpHeight = jumpHeight;
        this.dir = dir;
        this.isFall = true;
    }
    public void initAct(IActing act)
    {
        this.act = act;
        initEvent();
        initPerfor();
        this.act.setFall(this);
    }
    /////動畫元素/////
    public void animationLogic()
    {
        this.act.animationLogic(this, this.renderer);
    }
    public void initEvent()
    {
        this.acting = this.act.initEvents();
    }
    public void initPerfor()
    {
       this.currentActIndex = 0;
       this.delay = new Delay(1);
       this.currentEvent = this.acting[this.currentActIndex].keyCode();
    }
    //////////////////
    public void perform()
    {
        if(delay.isTrig())
        {
            this.delay.resetDelayFrame(this.acting[this.currentActIndex].duration()*3); //設定演出時間，以0.1秒為單位，0.1秒為六禎
            this.currentEvent = this.acting[this.currentActIndex].keyCode();               //設定當前演出動作
            if(this.currentActIndex!=this.acting.length-1)                                 //設定下一幕的演出編號，若為陣列中最後一幕則不設定。
            {
                this.currentActIndex++;
            }
        }
    }
    public void setFall(boolean isFall)
    {
        this.isFall = isFall;
    }
    public double speed()
    {
        return this.speed;
    }
    public double jumpHeight()
    {
        return this.jumpHeight;
    }
    public void setDir(Renderer.Dir dir)
    {
        this.dir = dir;
    }
    public Renderer.Dir dir()
    {
        return this.dir;
    }
    public void move()
    {
        this.act.move(this);
    }
    public void lock()
    {
        this.perfromLock = true;
        this.delay.stop();
    }
    public void unlock()
    {
        this.perfromLock = false;
        this.delay.reStart();
    }
    public void rendererUpdate()
    {
        this.renderer.setDir(this.dir);
        this.renderer.update();
    }
    public keyCode currentEvent()
    {
        return this.currentEvent;
    }
    public void rangeTile(Tile[][] tiles)
    {
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                if(tiles[i][j].getY() >= colliderTop() - Actor.ActorChoice.RABBIT.getHeight()            //a
                        && tiles[i][j].getY() <=  colliderDown() + Actor.ActorChoice.RABBIT.getHeight()  //b
                        && tiles[i][j].getX() >=  colliderLeft() - Actor.ActorChoice.RABBIT.getWidth()   //c
                        && tiles[i][j].getX() <=  colliderRight() + Actor.ActorChoice.RABBIT.getWidth()) //d
                {
                    switch(tiles[i][j].getType())
                    {
                        case NORMAL:
                            solidTileHit(tiles[i][j]);
                            break;
                        case CANJUMP:
                            solidTileHit(tiles[i][j]);
                            break;
                        case CANHURT:
                            solidTileHit(tiles[i][j]);
                            break;
                        case CLIMB_HEAD:
                            solidTileHit(tiles[i][j]);
                            break;
                        case CLIMB_NODE:
                            solidTileHit(tiles[i][j]);
                            break;
                    }
                }
            }
        }
    }
    public void solidTileHit(Tile tile)
    {
        if(downCollsion(tile, 8)) //腳
        {
            setY(tile.colliderTop() - this.colliderHeight()/2);
            this.velY = 0;
            this.accY = 0;
        }
        if(topCollsion(tile, 8))//頭
        {
            setY(tile.colliderDown() + this.colliderHeight()/2);
            this.velY = 0;
        }
        if(leftCollsion(tile, 0))//左
        {            
            this.velX = 0;
            setX(tile.colliderRight() + this.colliderWidth()/2 +1);
        }
        if(rightCollsion(tile, 0))//右
        {
            setX(tile.colliderLeft() - this.colliderWidth()/2 -1 );
            this.velX = 0;
        }
    }
    public void senserTile(Tile[][] tiles)
    {
        move();
        rangeTile(tiles);
        speedControl();
    }
    public int delayFrame()
    {
        return this.delay.delayFrame();
    }
    @Override
    public void update() 
    {
        perform();
        animationLogic();
        rendererUpdate();
        if(this.isFall)
        {
            gravityAcc();
        }
    }
    @Override
    public void paintComponent(Graphics g, Camera camera) 
    {
        if(this.role == FOX)
        {
            this.renderer.paint(
                g,
                getX() - camera.getCameraX() - this.width()/2,
                getY() - camera.getCameraY() - this.height()/2 -25,
                this.width(),
                this.height());
        }
        else if(this.role == RABBIT)
        {
            this.renderer.paint(
                g,
                getX() - camera.getCameraX() - this.width()/2,
                getY() - camera.getCameraY() - this.height()/2 -15,
                this.width(),
                this.height());
        }
    }
}
