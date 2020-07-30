package itemFunc;

import gameTile.Tile;
import gameobj.Actor;
import item.Item;
import item.TouchItem;

public interface ITouchItemFunc {
    
    public default boolean isValid(Item self)
    {
        if(self.isValid())
        {
            return true;
        }
        return false;
    }
    public void senser(Tile[][] tiles, Item self, Actor actor);
    public void itemEffect(Item self ,Actor actor);
}
