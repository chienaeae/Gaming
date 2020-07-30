package item;

import camera.Camera;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import renderer.ItemRenderer;
import util.Global;

public abstract class Item extends GameObject
{
    public Item(int x, int y, int width, int height, int colliderW, int colliderH)
    {
        super(x, y, width, height, colliderW, colliderH);
    }
    public abstract void senser(Tile[][] tiles, Actor actor);
    public abstract void end();
    public abstract boolean isEnd();
    public abstract boolean isValid();
    public abstract void effect(Actor actor);
    
}
