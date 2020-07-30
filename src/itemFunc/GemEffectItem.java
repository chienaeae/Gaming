package itemFunc;

import controllers.AudioRC;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import item.Item;
import item.TouchItem;
import util.AudioPath;
import util.Delay;

public class GemEffectItem implements ITouchItemFunc{

    public static final int UP_DOWN_FRAME = 13;
    
    private Delay delay;
    private boolean up;
    
    public GemEffectItem()
    {
        this.delay = new Delay(UP_DOWN_FRAME);
    }
    public void cloud(Item self)
    {
        if(this.delay.isTrig())
        {
            this.up = !this.up;
        }
        if(this.up==true)
        {
            self.setY(self.getY()+1);
        }
        else
        {
            self.setY(self.getY()-1);
        }
        self.setY(self.nextFrameY());
    }
    @Override
    public void senser(Tile[][] tiles, Item self, Actor actor) 
    {
         cloud(self);
    }

    @Override
    public void itemEffect(Item self ,Actor actor)
    {
        if(isValid(self))
        {
            AudioRC.getInstance().play(AudioPath.GEM);
            actor.setDiamond(actor.diamondNum()+1);
            self.end();
        }
    }

}
