package enemyMove;

import enemies.Enemy;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import renderer.Renderer;

public class SNormal implements IEnemyAI
{
    
    private static final int ACTIVE_WIDTH = 300;
    private static final int ACTIVE_HEIGHT = 300;
    private static final double SPEED = 2;
    private int habitX;
    private int habitY;
    private Renderer.Forward forward;
    
    public SNormal()
    {
        this.forward = Renderer.Forward.UP;
    }
    private void changeForward(Enemy self)
    {
        self.zeroVelY();
        self.zeroAccY();
        if(this.forward == Renderer.Forward.DOWN)
        {
            this.forward = Renderer.Forward.UP;
        }
        else if (this.forward == Renderer.Forward.UP)
        {
            this.forward = Renderer.Forward.DOWN;
        }
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
    public boolean isActiveX(Enemy self)
    {
        if(self.nextFrameX() <= this.habitX + ACTIVE_WIDTH
                && self.nextFrameX() >= this.habitX - ACTIVE_WIDTH)
        {
            return true;
        }
        return false;
    }
    public boolean isActiveY(Enemy self)
    {
        if(self.nextFrameY() <= this.habitY + ACTIVE_HEIGHT
                && self.nextFrameY() >= this.habitX - ACTIVE_HEIGHT)
        {
            return true;
        }
        return false;
    }
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
                normalTileHit(self, tile);
                break;
            case CANJUMP:
                normalTileHit(self, tile);
                break;
            case CANHURT:
                normalTileHit(self, tile);
                break;
            case CLIMB_HEAD:
                normalTileHit(self, tile);
                break;
            case CLIMB_NODE:
                normalTileHit(self, tile);
                break;
        }
    }
    public void normalTileHit(Enemy self, Tile tile)
    {
        if(self.downCollsion(tile, 5)) //腳
        {
            self.setY(tile.colliderTop() - self.colliderHeight()/2 -10);
            changeForward(self);
        }
        if(self.topCollsion(tile, 5))//頭
        {
            self.setY(tile.colliderDown() + self.colliderHeight()/2 +10);
            changeForward(self);
        }
        if(self.leftCollsion(tile, 5))//左
        {
            self.setX(tile.colliderRight() + self.colliderWidth()/2 +10);
            self.changeDir();
        }
        if(self.rightCollsion(tile, 5))//右
        {
            self.setX(tile.colliderLeft() - self.colliderWidth()/2 -10);
            self.changeDir();
        }
    }
    private void skyTargetDirAt(Enemy self, GameObject obj)
    {
        skyTargetDirAt(self, obj.getX(), obj.getY());
    }
    private void skyTargetDirAt(Enemy self, int targetX, int targetY)
    {
        if(targetX > self.getX() && self.dir() == Renderer.Dir.LEFT)
        {
            self.changeDir();
        }
        else if(targetX < self.getX() && self.dir() == Renderer.Dir.RIGHT)
        {
            self.changeDir();
        }
        if(targetY > self.getY() && this.forward == Renderer.Forward.UP)
        {
            changeForward(self);
        }
        else if(targetY < self.getY() && this.forward == Renderer.Forward.DOWN)
        {
            changeForward(self);
        }
    }
    private boolean skyDistanceSenser(Enemy self, GameObject obj, int disIn)
    {
        if(Math.sqrt((Math.pow(self.getX()-obj.getX(),2) + Math.pow(self.getY()-obj.getY(),2)))  //sqrt((x2 - x1) + (y2 - y1)) 
                <= disIn)
        {
            return true;
        }
        return false;
    }
    
    @Override
    public void initStatus(Enemy self) 
    {
        this.habitX = self.getX();
        this.habitY = self.getY();
    }
    
    @Override
    public void senser(Tile[][] tiles, Enemy self, Actor actor) 
    {
        rangeTile(self, tiles);
        self.checkDie(actor);
        if(skyDistanceSenser(self, actor, 200) && self.MoveSig() != Enemy.MoveSig.BACK)
        {
            self.setMoveSig(Enemy.MoveSig.ATTACK);
        }
        else if(!skyDistanceSenser(self, actor, 200))
        {
            self.setMoveSig(Enemy.MoveSig.BACK);
        }
        else if(self.MoveSig() == Enemy.MoveSig.ATTACK)
        {
            skyTargetDirAt(self, actor);
        }
        else if(self.MoveSig() == Enemy.MoveSig.BACK)
        {
            skyTargetDirAt(self, this.habitX, this.habitY);
        }
    }

    @Override
    public void ahead(Enemy self) 
    {
        if(!isActiveX(self))
        {
            self.changeDir();
            if(self.MoveSig() == Enemy.MoveSig.ATTACK)
            {
                self.setMoveSig(Enemy.MoveSig.BACK);
            }
        }
        if(!isActiveY(self))
        {
            changeForward(self);
            if(self.MoveSig() == Enemy.MoveSig.ATTACK)
            {
                self.setMoveSig(Enemy.MoveSig.BACK);
            }
        }
        if(self.MoveSig() == Enemy.MoveSig.BACK && backHabit(self))
        {
            self.setMoveSig(Enemy.MoveSig.GO);
        }
        if((self.MoveSig() == Enemy.MoveSig.GO
                || self.MoveSig() == Enemy.MoveSig.ATTACK
                || self.MoveSig() == Enemy.MoveSig.BACK) && self.dir() == Renderer.Dir.LEFT)
        {
            self.addVelX(SPEED);
        }
        else if((self.MoveSig() == Enemy.MoveSig.GO
                || self.MoveSig() == Enemy.MoveSig.ATTACK
                || self.MoveSig() == Enemy.MoveSig.BACK) && self.dir() == Renderer.Dir.RIGHT)
        {
            self.addVelX(SPEED);
        }
        if((self.MoveSig() == Enemy.MoveSig.ATTACK
                || self.MoveSig() == Enemy.MoveSig.BACK) && this.forward== Renderer.Forward.DOWN)
        {
            self.addVelY(SPEED);
        }
        else if((self.MoveSig() == Enemy.MoveSig.ATTACK
                || self.MoveSig() == Enemy.MoveSig.BACK) && this.forward == Renderer.Forward.UP)
        {
            self.addVelY(-SPEED);
        }
    }
}
