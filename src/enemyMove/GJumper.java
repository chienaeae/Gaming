package enemyMove;

import enemies.Enemy;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import renderer.Renderer;
import util.Delay;

public class GJumper implements IEnemyAI 
{
    private static final double SPEED = 3;
    private Delay delay;
    
    public GJumper()
    {
        this.delay = new Delay(35);
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
            self.setX(tile.colliderRight() + self.colliderWidth()/2);
            self.zeroVelX();
        }
        if(self.rightCollsion(tile, 0))//右
        {
            self.setX(tile.colliderLeft() - self.colliderWidth()/2);
            self.zeroVelX();
        }
    }
    private void changeDir(Enemy self)
    {
        self.zeroVelX();
        if(self.dir() == Renderer.Dir.LEFT)
        {
            self.setDir(Renderer.Dir.RIGHT);
        }
        else if (self.dir() == Renderer.Dir.RIGHT)
        {
            self.setDir(Renderer.Dir.LEFT);
        }
    }
    private boolean isfrontTile(Enemy self, Tile tile, double dis)
    {
        if(self.dir() == Renderer.Dir.LEFT
                && tile.getY() <= self.getY() + tile.colliderHeight()/2                //上
                && tile.getY() >= self.getY() - tile.colliderHeight()/2                //下
                && tile.getX() >= self.colliderLeft() - tile.colliderWidth() - dis     //左
                && tile.getX() <= self.colliderLeft() - dis)                           //右
        {
            return true;
        }
        else if(self.dir() == Renderer.Dir.RIGHT
                && tile.getY() <= self.getY() + tile.colliderHeight()/2
                && tile.getY() >= self.getY() - tile.colliderHeight()/2
                && tile.getX() <= self.colliderRight() + tile.colliderWidth() + dis
                && tile.getX() >= self.colliderRight() + dis) 
        {
            return true;
        }
        return false;
    }
    private boolean isfrontDownTile(Enemy self, Tile tile, double dis)
    {
        if(self.dir() == Renderer.Dir.LEFT
                && tile.getY() >= self.colliderDown()                                    //上
                && tile.getY() <= self.colliderDown() + tile.colliderHeight()            //下
                && tile.getX() >= self.colliderLeft() - tile.colliderWidth() - dis       //左
                && tile.getX() <= self.colliderLeft() - dis)                             //右
        {
            return true;
        }
        else if(self.dir() == Renderer.Dir.RIGHT
                && tile.getY() >= self.colliderDown()
                && tile.getY() <= self.colliderDown() + tile.colliderHeight()
                && tile.getX() <= self.colliderRight() + tile.colliderWidth() + dis
                && tile.getX() >= self.colliderRight() + dis)
        {
            return true;
        }
        return false;
    }
    private boolean dropDownTileSenser(Enemy self, Tile[][] tiles, double dis)
    {
        Tile frontTile = null;
        Tile frontDownTile = null;
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                if(isfrontTile(self, tiles[i][j], dis)) frontTile = tiles[i][j];
                if(isfrontDownTile(self, tiles[i][j], dis)) frontDownTile = tiles[i][j];
            }
        }
        if(frontTile.getType() == Tile.TileType.NORMAL
                || frontTile.getType() == Tile.TileType.CANHURT
                || frontTile.getType() == Tile.TileType.CLIMB_HEAD
                || frontTile.getType() == Tile.TileType.CANJUMP
                || frontTile.getType() == Tile.TileType.CLIMB_NODE) 
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
    
    public void animationLogic(Enemy self) 
    {
        if(self.velY() == 0)
        {
            self.setAnimation(4);
        }
        else if(self.velY() < 0)
        {
            self.setAnimation(5);
        }
        if(self.velY()>0)
        {
            self.setAnimation(6);
        }
    }
    
    @Override
    public void initStatus(Enemy self) 
    {
        
    }
    
    @Override
    public void senser(Tile[][] tiles, Enemy self, Actor actor) 
    {
        self.gravityAcc();
        rangeTile(self, tiles);
        self.speedControl();
        self.checkDie(actor);
        animationLogic(self);
        if(self.velY()==0 && dropDownTileSenser(self, tiles, SPEED * 5))
        {
            self.setMoveSig(Enemy.MoveSig.GO);
        }
        else if(self.velY()==0 && !dropDownTileSenser(self, tiles,SPEED * 5))
        {
            self.setMoveSig(Enemy.MoveSig.BACK);
        }
    }

    @Override
    public void ahead(Enemy self) 
    {
        if(self.MoveSig() == Enemy.MoveSig.BACK)
        {
            changeDir(self);
        }
        if(self.dir() == Renderer.Dir.RIGHT && self.MoveSig() == Enemy.MoveSig.GO)
        {
            if(self.velY()==0 && this.delay.isTrig())
            {
                self.addVelY(-22);
            }
            else if(self.velY()!=0)
            {
                self.addVelX(SPEED);
            }
        }
        else if (self.dir() == Renderer.Dir.LEFT && self.MoveSig() == Enemy.MoveSig.GO)
        {
            if(self.velY()==0 && this.delay.isTrig())
            {
                self.addVelY(-22);
            }
            else if(self.velY()!=0)
            {
                self.addVelX(-SPEED);
            }
        }
    }
}
