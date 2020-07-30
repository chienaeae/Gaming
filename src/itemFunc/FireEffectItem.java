package itemFunc;

import controllers.AudioRC;
import gameTile.Tile;
import gameobj.Actor;
import item.ChestItem;
import item.InteractItem;
import item.Item;
import java.util.ArrayList;
import util.AudioPath;
import util.Global;

public class FireEffectItem implements IHandleItemFunc
{
    private boolean actorCollision;
    private boolean isSpark;
    private boolean opened;
    
    public FireEffectItem()
    {
        this.actorCollision = false;
        this.isSpark = false;
        this.opened = false;
    }
    public void rangeTile(Tile[][] tiles, Item self)
    {
        for(int i=0;i<tiles.length;i++)
        {
            for(int j=0;j<tiles[i].length;j++)
            {
                if(tiles[i][j].getY() >= self.colliderTop() - Global.UNIT_SIZE.Tile.getHeight()            //a
                        && tiles[i][j].getY() <=  self.colliderDown() + Global.UNIT_SIZE.Tile.getHeight()  //b
                        && tiles[i][j].getX() >=  self.colliderLeft() - Global.UNIT_SIZE.Tile.getWidth()   //c
                        && tiles[i][j].getX() <=  self.colliderRight() + Global.UNIT_SIZE.Tile.getWidth()) //d
                {
                    senseTile(tiles[i][j], self);
                }
            }
        }
    }
    public void senseTile(Tile tile, Item self)
    {
        switch(tile.getType())
        {
            case NORMAL:
                tileHit(tile, self);
                break;
            case CANJUMP:
                tileHit(tile, self);
                break;
            case CANHURT:
                tileHit(tile, self);
                break;
            case CLIMB_HEAD:
                tileHit(tile, self);
                break;
        }
    }
    public void tileHit(Tile tile, Item self)
    {
        if(self.downCollsion(tile, 8)) //腳
        {
            self.setY(tile.colliderTop() - self.colliderHeight()/2);
            self.zeroAccY();
            self.zeroVelY();
        }
        if(self.topCollsion(tile, 8))//頭
        {
            self.setY(tile.colliderDown() + self.colliderHeight()/2);
            self.zeroVelY();
        }
        if(self.leftCollsion(tile, 8))//左
        {
            self.zeroVelX();
            self.setX(tile.colliderRight() + self.colliderWidth()/2);
        }
        if(self.rightCollsion(tile, 8))//右
        {
            self.zeroVelX();
            self.setX(tile.colliderLeft() - self.colliderWidth()/2);
        }
    }
    public void isActorCollsion(Item self, Actor actor)
    {
        if(self.isCollision(actor))
        {
            this.actorCollision = true;
        }
        else
        {
            this.actorCollision = false;
        }
    }
    public void diamondCheck(Item self, Actor actor)
    {
        if(actor.diamondNum()!=3 || this.opened)
        {
            return;
        }
        AudioRC.getInstance().play(AudioPath.FIRE);
        self.end();
        this.opened = true;
    }
    @Override
    public void initDiversity(int divideNum) 
    {
    }
     @Override
    public void keyPressed(InteractItem item, int keyCode) 
    {
        if(keyCode == Global.ITEM_INTERACT_BUTTOM)
        {
            this.isSpark = true;
        }
    }
    @Override
    public void keyReleased(InteractItem  item, int keyCode) 
    {
        if(keyCode == Global.ITEM_INTERACT_BUTTOM)
        {
            this.isSpark = false;
        }
    }
    @Override
    public void interactEffectItem(Item self, ArrayList<Item> items, Actor actor) 
    {
        if(this.actorCollision && this.isSpark && this.opened)
        {
            actor.missionComplete();
        }
    }
    @Override
    public void senser(Tile[][] tiles, Item self, Actor actor) 
    {
        diamondCheck(self, actor);
        rangeTile(tiles, self);
        isActorCollsion(self, actor);
        self.speedControl();
        self.gravityAcc();
    }
    @Override
    public void itemEffect(Item self, Actor actor) {
        
    }
    
}
