package gameobj;

import gameTile.Tile;

public interface ActorSkill 
{
    public void initStatus(Actor actor);
    public void senseTile(Tile tile, Actor actor);
    public void keyInput(int keyCode, Actor actor);
    public void keyReleased(int keyCode, Actor actor);
    public void update(Actor actor);
}
