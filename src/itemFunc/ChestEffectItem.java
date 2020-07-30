package itemFunc;

import controllers.AudioRC;
import gameTile.Tile;
import gameobj.Actor;
import item.*;
import java.util.ArrayList;
import util.AudioPath;
import util.Global;
import util.ImagePath;

public class ChestEffectItem implements IHandleItemFunc{

    public enum ChestContent{
        GEM,LIFE,COIN;
    }
    
    private boolean actorCollision;
    private boolean isSpark;
    private boolean opened;
    private ChestContent content;
    
    public ChestEffectItem()
    {
        this.actorCollision = false;
        this.isSpark = false;
        this.opened = false;
        this.content = ChestContent.COIN;
    }
    @Override
    public void initDiversity(int contentNum)
    {
        switch(contentNum)
        {
            case 0:
                this.content = ChestContent.GEM;
                break;
            case 1:
                this.content = ChestContent.LIFE;
                break;
            case 2:
                this.content = ChestContent.COIN;
                break;
        }
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
    public void gemContent(Item self, ArrayList<Item> items, Actor.ActorChoice characterChoice)
    {
        if(this.actorCollision && this.isSpark && !this.opened)
        {
            items.add(new TouchItem(self.getX(), self.getY(), new SpurtGemEffectIem(),ImagePath.GEM,Global.UNIT_SIZE.Gem));
            AudioRC.getInstance().play(AudioPath.CHEST);
            this.opened = true;
            self.end();
        }
    }
    public void lifeContent(Item self, ArrayList<Item> items, Actor.ActorChoice characterChoice)
    {
        if(this.actorCollision && this.isSpark && !this.opened && characterChoice == Actor.ActorChoice.FOX)
        {
            for(int i=0;i<3;i++)
            {
                items.add(new TouchItem(self.getX(), self.getY(), new SpurtLiftEffectItem(),ImagePath.CHERRY,Global.UNIT_SIZE.Cherry));
            }
            AudioRC.getInstance().play(AudioPath.CHEST);
            this.opened = true;
            self.end();
        }
        else if(this.actorCollision && this.isSpark && !this.opened && characterChoice == Actor.ActorChoice.RABBIT)
        {
            for(int i=0;i<3;i++)
            {
                items.add(new TouchItem(self.getX(), self.getY(), new SpurtLiftEffectItem(),ImagePath.CARROT,Global.UNIT_SIZE.Carrot));
            }
            AudioRC.getInstance().play(AudioPath.CHEST);
            this.opened = true;
            self.end();
        }
    }
    public void coinContent(Item self, ArrayList<Item> items, Actor.ActorChoice characterChoice)
    {
        if(this.actorCollision && this.isSpark && !this.opened && characterChoice == Actor.ActorChoice.FOX)
        {
            for(int i=0;i<12;i++)
            {
                items.add(new TouchItem(self.getX(), self.getY(), new SpurtCoinEffectItem(),ImagePath.COIN,Global.UNIT_SIZE.Coin));
            }
            AudioRC.getInstance().play(AudioPath.CHEST);
            this.opened = true;
            self.end();
        }
        else if(this.actorCollision && this.isSpark && !this.opened && characterChoice == Actor.ActorChoice.RABBIT)
        {
            for(int i=0;i<12;i++)
            {
                items.add(new TouchItem(self.getX(), self.getY(), new SpurtCoinEffectItem(),ImagePath.STAR,Global.UNIT_SIZE.Star));
            }
            AudioRC.getInstance().play(AudioPath.CHEST);
            this.opened = true;
            self.end();
        }
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
    public void keyReleased(InteractItem item, int keyCode) 
    {
        if(keyCode == Global.ITEM_INTERACT_BUTTOM)
        {
            this.isSpark = false;
        }
    }
    @Override
    public void senser(Tile[][] tiles, Item self, Actor actor) 
    {
        rangeTile(tiles, self);
        isActorCollsion(self, actor);
        self.speedControl();
        self.gravityAcc();
    }
    @Override
    public void itemEffect(Item self, Actor actor) 
    {
        
    }
    @Override
    public void interactEffectItem(Item self, ArrayList<Item> items, Actor actor) 
    {
        switch(this.content)
        {
            case GEM:
                gemContent(self, items, actor.actorChoice());
                break;
            case LIFE:
                lifeContent(self, items, actor.actorChoice());
                break;
            case COIN:
                coinContent(self, items, actor.actorChoice());
                break;
        }
    }
}
