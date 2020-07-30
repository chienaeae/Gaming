package controllers;

import camera.Camera;
import enemies.*;
import enemyMove.*;
import gameTile.Tile;
import gameobj.*;
import item.Item;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import renderer.*;
import util.Global;


public class EnemyController
{
    public enum MonsterNum
    {
        Opossum(0),
        Bee(1),
        Eagle(2),
        Frog(3),
        Piranha(4),
        Slug(5),
        Squirrel(6);
        
        private int Number;
        
        private MonsterNum(int num)
        {
            this.Number = num;
        }
    }
    
    private LevelOneMonsterRenderer mc;
    private int[][] monsterMap;
    private ArrayList<Enemy> enemies;
    
    public EnemyController(String fileName)
    {
        this.mc = new LevelOneMonsterRenderer();
        this.enemies = new ArrayList<>();
        init(fileName);
    }
    public Enemy genEnemy(int Number, int x, int y)
    {//Enemy(int x, int y,IEnemyAI ai, Global.UNIT_SIZE size)
        if(Number==MonsterNum.Opossum.Number)        return new Enemy(x, y, new GNormal(), Global.UNIT_SIZE.Opossum, this.mc, 0);
        else if (Number==MonsterNum.Bee.Number)      return new Enemy(x, y, new SHorizon(), Global.UNIT_SIZE.Bee, this.mc ,1);
        else if (Number==MonsterNum.Eagle.Number)     return new Enemy(x, y, new SVertical(), Global.UNIT_SIZE.Eagle, this.mc, 2);
        else if (Number==MonsterNum.Frog.Number)     return new Enemy(x, y, new GJumper(), Global.UNIT_SIZE.Frog, this.mc, 4);
        else if (Number==MonsterNum.Piranha.Number)   return new Enemy(x, y, new GStand(), Global.UNIT_SIZE.Piranha, this.mc,7);
        else if (Number==MonsterNum.Slug.Number)     return new Enemy(x, y, new GSlower(), Global.UNIT_SIZE.Slug, this.mc, 3);
        else if (Number==MonsterNum.Squirrel.Number)   return new Enemy(x, y, new GSquirrel(), Global.UNIT_SIZE.Squirrel, this.mc,10);
        else return null;
    }
    public void init(String root)
    {
        genMonsterMap("src/" + root);
        for(int i=0;i<this.monsterMap.length;i++)
        {
            this.enemies.add(genEnemy(this.monsterMap[i][0],this.monsterMap[i][1],this.monsterMap[i][2]));
        }
    }
    public void resetEnemy()
    {
        this.enemies = new ArrayList<>();
        for(int i=0;i<this.monsterMap.length;i++)
        {
            this.enemies.add(genEnemy(this.monsterMap[i][0],this.monsterMap[i][1],this.monsterMap[i][2]));
        }
    }
    public ArrayList<Enemy> enemies()
    {
        return this.enemies;
    }
    private void genMonsterMap(String root)
    {
        int lineCount;
        try{
            BufferedReader br = new BufferedReader(new FileReader(root));
            br.readLine();
            lineCount = Integer.parseInt(br.readLine());
            int monsters[][] = new int[lineCount][3];
            for(int i=0;i<lineCount;i++)
            {
                String str = br.readLine();
                String[] tmp = str.split(",");
                monsters[i][0] = Integer.parseInt(tmp[0]);
                monsters[i][1] = Integer.parseInt(tmp[1]);
                monsters[i][2] = Integer.parseInt(tmp[2]);
            }
            br.close();
            this.monsterMap = monsters;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void senser(Tile[][] tiles, ArrayList<Item> items, Actor actor)
    {
        for(int i=0;i<this.enemies.size();i++)
        {
            this.enemies.get(i).senser(tiles, actor);
            if(dieCheck(i))
            {
                this.enemies.get(i).treasureDrop(items, actor.actorChoice());
                this.enemies.remove(i);
                i--;
            }
        }
    }
    public boolean dieCheck(int index)
    {
        if(!this.enemies.get(index).isAlive() && this.enemies.get(index).isEnd())
        {
            return true;
        }
        return false;
    }
    public void update()
    {
        for(int i=0;i<this.enemies.size();i++)
        {
            this.enemies.get(i).update();
        }
    }
    public void paint(Graphics g, Camera camera)
    {
        for(int i=0;i<this.enemies.size();i++)
        {
            this.enemies.get(i).paint(g, camera);
        }
    }
}
