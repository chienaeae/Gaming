package itemFunc;

import gameobj.Actor;
import item.ChestItem;
import item.InteractItem;
import item.Item;
import java.util.ArrayList;
import util.Global;

public interface IHandleItemFunc extends ITouchItemFunc
{
    public void initDiversity(int divideNum);
    public void keyPressed(InteractItem item, int keyCode);
    public void keyReleased(InteractItem item, int keyCode);
    public void interactEffectItem(Item self, ArrayList<Item> items, Actor actor);
}

