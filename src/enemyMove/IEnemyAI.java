package enemyMove;

import controllers.AudioRC;
import enemies.Enemy;
import gameTile.Tile;
import gameobj.Actor;
import gameobj.Actor.ActorChoice;
import gameobj.GameObject;
import item.Item;
import item.TouchItem;
import itemFunc.SpurtCoinEffectItem;
import itemFunc.SpurtLiftEffectItem;
import java.util.ArrayList;
import util.AudioPath;
import util.Global;
import util.ImagePath;

public interface IEnemyAI 
{
    public default void treasureDrop(Enemy self, ArrayList<Item> items, Actor.ActorChoice characterChoice)
    {
        int prob = Global.random(0, 100);
        if(prob<=35) //生命
        {
            if(characterChoice == ActorChoice.FOX)
            {
                items.add(new TouchItem(self.getX(), self.getY(), new SpurtLiftEffectItem(),ImagePath.CHERRY,Global.UNIT_SIZE.Cherry));
            }
            else if(characterChoice == ActorChoice.RABBIT)
            {
                items.add(new TouchItem(self.getX(), self.getY(), new SpurtLiftEffectItem(),ImagePath.CARROT,Global.UNIT_SIZE.Carrot));
            }
        }
        else if(prob > 35 && prob <= 85)//金幣
        {
            if(characterChoice == ActorChoice.FOX)
            {
                for(int i=0;i<6;i++)
                {
                    items.add(new TouchItem(self.getX(), self.getY(), new SpurtCoinEffectItem(),ImagePath.COIN,Global.UNIT_SIZE.Coin));
                }
            }
            else if(characterChoice == ActorChoice.RABBIT)
            {
                for(int i=0;i<6;i++)
                {
                    items.add(new TouchItem(self.getX(), self.getY(), new SpurtCoinEffectItem(),ImagePath.STAR,Global.UNIT_SIZE.Star));
                }
            }
        }
    }
    public void initStatus(Enemy self);
    public void senser(Tile[][] tiles, Enemy self, Actor actor);
    public void ahead(Enemy self);
}
