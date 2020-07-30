package enemyMove;

import enemies.Enemy;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import renderer.Renderer;

public class GStand implements IEnemyAI{
    
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
    public void animationLogic(Enemy self)
    {
        if(self.MoveSig()==Enemy.MoveSig.ATTACK)
        {
            self.setAnimation(8);
        }
        else
        {
            self.setAnimation(7);
        }
    }
    @Override
    public void senser(Tile[][] tiles, Enemy self, Actor actor) 
    {
        self.gravityAcc();
        self.speedControl();
        self.checkDie(actor);
        rangeTile(self, tiles);
        if(groundDistanceSenser(self, actor, 200) && actor.getX() > self.getX())
        {
            self.setDir(Renderer.Dir.RIGHT);
            self.setMoveSig(Enemy.MoveSig.ATTACK);
        }
        else if(groundDistanceSenser(self, actor, 200) && actor.getX() < self.getX())
        {
            self.setDir(Renderer.Dir.LEFT);
            self.setMoveSig(Enemy.MoveSig.ATTACK);
        }
        else
        {
            self.setMoveSig(Enemy.MoveSig.IDLE);
        }
    }

    @Override
    public void ahead(Enemy self) 
    {
        animationLogic(self);
    }

    @Override
    public void initStatus(Enemy self) 
    {
        
    }
    
}
