package item;

import gameTile.Tile;
import gameobj.Actor;
import itemFunc.IHandleItemFunc;
import java.util.ArrayList;
import renderer.ItemRenderer;
import util.Global;

public abstract class InteractItem extends Item
{
    protected ItemRenderer renderer;
    protected IHandleItemFunc func;
    
    public InteractItem(int x, int y, IHandleItemFunc func, Global.UNIT_SIZE size)
    {
        super(x, y, size.getWidth(), size.getHeight(), size.getColliderW(), size.getColliderH());
        this.func = func;
    }
    public void initRenderer(ItemRenderer renderer)
    {
        this.renderer = renderer;
    }
    public void keyPressed(int keyCode)
    {
        this.func.keyPressed(this, keyCode);
    }
    public void keyReleased(int keyCode)
    {
        this.func.keyReleased(this, keyCode);
    }
    public void interactEffectItem(ArrayList<Item> items, Actor actor)
    {
        this.func.interactEffectItem(this, items, actor);
    }
    public void senser(Tile[][] tiles, Actor actor) 
    {
        this.func.senser(tiles, this, actor);
    }
    public boolean isEnd() 
    {
        return this.renderer.isEnd();
    }
    public void effect(Actor actor) 
    {
        this.func.itemEffect(this, actor);
    }
    public boolean isValid() 
    {
        return this.renderer.isValid();
    }
    public void end() 
    {
        this.renderer.end();
    }
    public void update() 
    {
        this.renderer.update();
    }
}
