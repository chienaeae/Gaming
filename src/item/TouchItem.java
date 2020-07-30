package item;

import camera.Camera;
import gameTile.Tile;
import gameobj.Actor;
import itemFunc.ITouchItemFunc;
import java.awt.Graphics;
import renderer.ItemRenderer;
import util.Global;

public class TouchItem extends Item
{
    private ITouchItemFunc func;
    private ItemRenderer itemRenderer;
    
    public TouchItem(int x, int y, ITouchItemFunc func, String imgRoot, Global.UNIT_SIZE unit)
    {
        super(x, y, unit.getWidth(), unit.getHeight(), unit.getColliderW(), unit.getColliderH());
        this.func = func;
        this.itemRenderer = new ItemRenderer(imgRoot, unit);
    }
    @Override
    public void senser(Tile[][] tiles, Actor actor)
    {
        this.func.senser(tiles, this, actor);
    }
    @Override
    public void end()
    {
        this.itemRenderer.end();
    }
    @Override
    public boolean isValid()
    {
        return this.itemRenderer.isValid();
    }
    @Override
    public boolean isEnd()
    {
        return this.itemRenderer.isEnd();
    }
    @Override
    public void effect(Actor actor)
    {
        this.func.itemEffect(this, actor);
    }
    @Override
    public void update()
    {
        this.itemRenderer.update();
    }
    @Override
    public void paintComponent(Graphics g, Camera camera)
    {
        this.itemRenderer.paint(g,
                getX() - camera.getCameraX() - width()/2,
                getY() - camera.getCameraY() - height()/2);
    }
}
