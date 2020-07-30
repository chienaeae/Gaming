package itemFunc;

import controllers.AudioRC;
import gameTile.Tile;
import gameobj.Actor;
import item.Item;
import util.AudioPath;
import util.Delay;
import util.Global;

public class SpurtCoinEffectItem implements ITouchItemFunc
{
    private boolean spurt;
    private boolean canEat;
    private Delay delay;
    
    public SpurtCoinEffectItem()
    {
        this.delay = new Delay(10);
        this.spurt = false;
        this.canEat = false;
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
    public void initSpurt(Item self)
    {
        if(this.spurt==false)
        {
            self.addVelY(Global.random(-35, -25));
            self.addVelX(Global.random(-35, 35));
            this.spurt = true;
        }
    }
    public void canEat()
    {
        if(this.delay.isTrig())
        {
            this.canEat = true;
            this.delay.stop();
        }
    }
    
    @Override
    public void senser(Tile[][] tiles, Item self, Actor actor) 
    {
        canEat();
        initSpurt(self);
        rangeTile(tiles, self);
        self.speedControl();
        self.gravityAcc();
    }

    @Override
    public void itemEffect(Item self, Actor actor) 
    {
        if(isValid(self) && this.canEat)
        {
            AudioRC.getInstance().play(AudioPath.COIN);
            actor.setCoin(actor.coinNum()+1);
            self.end();
        }
    }
}
