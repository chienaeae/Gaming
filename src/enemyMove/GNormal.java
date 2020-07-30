package enemyMove;

import enemies.Enemy;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import renderer.Renderer;
import renderer.Renderer.Dir;
import util.Delay;

public class GNormal implements IEnemyAI
{
    private static final double SPEED = 3;
    
    private Delay delay;
    
    public GNormal()
    {
        this.delay = new Delay(2);
        this.delay.start();
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
        if(self.downCollsion(tile, 8)) //腳
        {
            self.setY(tile.colliderTop() - self.colliderHeight()/2);
            self.zeroVelY();
            self.zeroAccY();
        }
        if(self.topCollsion(tile, 8))//頭
        {
            self.setY(tile.colliderDown() + self.colliderHeight()/2);
            self.zeroVelY();
        }
        if(self.leftCollsion(tile, 8))//左
        {
            self.setX(tile.colliderRight() + self.colliderWidth()/2);
            self.zeroVelX();
        }
        if(self.rightCollsion(tile, 8))//右
        {
            self.setX(tile.colliderLeft() - self.colliderWidth()/2);
            self.zeroVelX();
        }
    }
    
    private void changeDir(Enemy self)
    {
        if(self.dir() == Renderer.Dir.LEFT)
        {
            self.setDir(Renderer.Dir.RIGHT);
        }
        else if (self.dir() == Renderer.Dir.RIGHT)
        {
            self.setDir(Renderer.Dir.LEFT);
        }
    }
    private boolean isfrontTile(Enemy self, Tile tile)
    {
        if(self.dir() == Renderer.Dir.LEFT
                && tile.getY() <= self.getY() + tile.colliderHeight()/2
                && tile.getY() >= self.getY() - tile.colliderHeight()/2
                && tile.getX() >= self.colliderLeft() - tile.colliderWidth()
                && tile.getX() <= self.colliderLeft())
        {
            return true;
        }
        else if(self.dir() == Renderer.Dir.RIGHT
                && tile.getY() <= self.getY() + tile.colliderHeight()/2
                && tile.getY() >= self.getY() - tile.colliderHeight()/2
                && tile.getX() <= self.colliderRight() + tile.colliderWidth()
                && tile.getX() >= self.colliderRight())
        {
            return true;
        }
        return false;
    }
    private boolean isfrontDownTile(Enemy self, Tile tile)
    {
        if(self.dir() == Renderer.Dir.LEFT
                && tile.getY() >= self.colliderDown()
                && tile.getY() <= self.colliderDown() + tile.colliderHeight()
                && tile.getX() >= self.colliderLeft() - tile.colliderWidth()
                && tile.getX() <= self.colliderLeft())
        {
            return true;
        }
        else if(self.dir() == Renderer.Dir.RIGHT
                && tile.getY() >= self.colliderDown()
                && tile.getY() <= self.colliderDown() + tile.colliderHeight()
                && tile.getX() <= self.colliderRight() + tile.colliderWidth()
                && tile.getX() >= self.colliderRight())
        {
            return true;
        }
        return false;
    }
    private boolean groundTileSenser(Enemy self, Tile[][] tiles)
    {
        Tile frontTile = null;
        Tile frontDownTile = null;
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                if(isfrontTile(self,tiles[i][j])) frontTile = tiles[i][j];
                if(isfrontDownTile(self, tiles[i][j])) frontDownTile = tiles[i][j];
            }
        }
        if(frontTile.getType() == Tile.TileType.NORMAL
                || frontTile.getType() == Tile.TileType.CANHURT
                || frontTile.getType() == Tile.TileType.CLIMB_HEAD
                || frontTile.getType() == Tile.TileType.CANJUMP) 
        {
            return false;
        }
        else if(frontTile.getType() == Tile.TileType.APPARENT 
                && frontDownTile.getType() == Tile.TileType.APPARENT) 
        {
            
            return false;
        }
        else
        {
            return true;
        }
    }
    private void groundTargetDirAt(Enemy self, GameObject obj)
    {
        groundTargetDirAt(self, obj.getX());
    }
    private void groundTargetDirAt(Enemy self, int targetX)
    {
        if(targetX > self.getX() && self.dir() == Renderer.Dir.LEFT)
        {
            changeDir(self);
        }
        else if(targetX < self.getX() && self.dir() == Renderer.Dir.RIGHT)
        {
            changeDir(self);
        }
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
    private boolean groundDistanceSenser(Enemy self, GameObject obj, int disIn)
    {
        if(obj.getX() <= self.colliderRight() + disIn
                && obj.getX() >=  self.colliderLeft() -  disIn
                && obj.getY() >=  self.colliderTop() - self.colliderHeight()
                && obj.getY() <= self.colliderDown())
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
    @Override
    public void initStatus(Enemy self) 
    {
        
    }
    @Override
    public void senser(Tile[][] tiles, Enemy self, Actor actor)
    {
        self.gravityAcc();
        self.speedControl();
        self.checkDie(actor);
        rangeTile(self, tiles);
        if(groundDistanceSenser(self, actor ,200))
        {
            groundTargetDirAt(self, actor);
            self.setMoveSig(Enemy.MoveSig.ATTACK);
        }
        else
        {
            self.setMoveSig(Enemy.MoveSig.GO);
        }
        if(!groundTileSenser(self, tiles))
        {
            self.setMoveSig(Enemy.MoveSig.BACK);
        }
    }
    @Override
    public void ahead(Enemy self)
    {
        if(this.delay.isTrig())
        {
            if(self.MoveSig() == Enemy.MoveSig.BACK)
            {
                changeDir(self);
            }
            if(self.dir() == Renderer.Dir.RIGHT && self.MoveSig() == Enemy.MoveSig.GO)
            {
                self.addVelX(SPEED);
            }
            else if (self.dir() == Renderer.Dir.LEFT && self.MoveSig() == Enemy.MoveSig.GO)
            {
                self.addVelX(-SPEED);
            }
            if(self.dir() == Renderer.Dir.RIGHT && self.MoveSig() == Enemy.MoveSig.ATTACK)
            {
                self.addVelX(SPEED);
                rushSpeed(self,3);
            }
            else if (self.dir() == Renderer.Dir.LEFT && self.MoveSig() == Enemy.MoveSig.ATTACK)
            {
                self.addVelX(-SPEED);
                rushSpeed(self,3);
            }
        }
    }
}
