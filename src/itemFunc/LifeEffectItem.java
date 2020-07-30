package itemFunc;

import controllers.AudioRC;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import item.Item;
import item.TouchItem;
import util.AudioPath;

public class LifeEffectItem implements ITouchItemFunc{
    
    @Override
    public void senser(Tile[][] tiles, Item self, Actor actor) 
    {
        self.speedControl();
    }
    @Override
    public void itemEffect(Item self ,Actor actor)
    {
        if(isValid(self))
        {
            AudioRC.getInstance().play(AudioPath.HEALTH);
            actor.setHealth(actor.healthNum()+1);
            self.end();
        }
    }
}   
