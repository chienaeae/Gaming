package controllers;

import camera.Camera;
import gameTile.Tile;
import gameobj.*;
import item.*;
import item.ChestItem.Color;
import itemFunc.*;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import renderer.*;
import util.*;

public class ItemController 
{
    private ArrayList<Item> items;
    private ArrayList<InteractItem> keyInputItems;
    private ArrayList<InteractItem> addItemItems;
    private int[][] itemMap;
    private Actor actor;
    
    public ItemController(String root, Actor actor)
    {
        constructItems();
        genItemMap(root);
        this.actor = actor;
        init();
    }
    public void constructItems()
    {
        this.items = new ArrayList<>();
        this.keyInputItems = new ArrayList<>();
        this.addItemItems = new ArrayList<>();
    }
    public Item genItem(int number, int x, int y)    //(0)生命 (1)金幣 (2)寶石 (3)黑寶箱 (4)紅寶箱 (5)火堆 (6)火把
    {
        switch(number)
        {
            case 0:
                if(this.actor.actorChoice() == Actor.ActorChoice.FOX) return new TouchItem(x, y, new LifeEffectItem(),ImagePath.CHERRY,Global.UNIT_SIZE.Cherry);
                else                                        return new TouchItem(x, y, new LifeEffectItem(),ImagePath.CARROT,Global.UNIT_SIZE.Carrot);
            case 1:
                if(this.actor.actorChoice() == Actor.ActorChoice.FOX) return new TouchItem(x, y, new CoinEffectItem(),ImagePath.COIN,Global.UNIT_SIZE.Coin);
                else                                        return new TouchItem(x, y, new CoinEffectItem(),ImagePath.STAR,Global.UNIT_SIZE.Star);
            case 2:
                return new TouchItem(x, y, new GemEffectItem(),ImagePath.GEM,Global.UNIT_SIZE.Gem);
            case 3:
                return new ChestItem(x,y, new ChestEffectItem(),Color.BLACK);
            case 4:
                return new ChestItem(x,y, new ChestEffectItem(),Color.RED);
            case 5:
                return new FireItem(x, y, new FireEffectItem());
            case 6:
                return new TorchItem(x, y, new TorchEffectItem());
            default:
                return null;
        }
    }
    public void init()
    {
        for(int i=0;i<this.itemMap.length;i++)
        {
            if(this.itemMap[i][0]>=10)   //提供兩位數道具編號使用
            {
                this.items.add(genItem(this.itemMap[i][0]/10,this.itemMap[i][1],this.itemMap[i][2]));
                chestInit(this.itemMap[i][0], this.items.get(i));
            }
            else
            {
                this.items.add(genItem(this.itemMap[i][0],this.itemMap[i][1],this.itemMap[i][2]));
                fireInit(this.itemMap[i][0], this.items.get(i));
            }
        }
    }
    private void genItemMap(String root)
    {
        int lineCount;
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/"+root));
            br.readLine();
            lineCount = Integer.parseInt(br.readLine());
            int itemMap[][] = new int[lineCount][3];
            for(int i=0;i<lineCount;i++)
            {
                String str = br.readLine();
                String[] tmp = str.split(",");
                itemMap[i][0] = Integer.parseInt(tmp[0]);
                itemMap[i][1] = Integer.parseInt(tmp[1]);
                itemMap[i][2] = Integer.parseInt(tmp[2]);
            }
            br.close();
            this.itemMap = itemMap;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void addItem()
    {
        for(int i=0;i<this.addItemItems.size();i++)
        {
            this.addItemItems.get(i).interactEffectItem(this.items, this.actor);
        }
    }
    public ArrayList<Item> items()
    {
        return this.items;
    }
    public void keyReleased(int keyCode)
    {
        for(int i=0;i<this.keyInputItems.size();i++)
        {
            this.keyInputItems.get(i).keyReleased(keyCode);
        }
    }
    public void keyPressed(int keyCode)
    {
        for(int i=0;i<this.keyInputItems.size();i++)
        {
            this.keyInputItems.get(i).keyPressed(keyCode);
        }
    }
    public void chestInit(int itemNum, Item item)
    {
        if(itemNum/10 ==3 || itemNum/10 == 4)
        {
            ChestItem interItem = (ChestItem)item;
            itemNum = itemNum % 10;
            switch (itemNum)
            {
                case 0:
                    interItem.initContent(ChestEffectItem.ChestContent.GEM);
                    break;
                case 1:
                    interItem.initContent(ChestEffectItem.ChestContent.LIFE);
                    break;
                case 2:
                    interItem.initContent(ChestEffectItem.ChestContent.COIN);
                    break;
            }
            this.addItemItems.add(interItem);
            this.keyInputItems.add(interItem);
        }
    }
    public void fireInit(int itemNum, Item item)
    {
        if(itemNum==5)
        {
            FireItem interItem = (FireItem)item;
            this.addItemItems.add(interItem);
            this.keyInputItems.add(interItem);
        }
        else if(itemNum==6)
        {
            TorchItem interItem = (TorchItem)item;
            this.addItemItems.add(interItem);
            this.keyInputItems.add(interItem);
        }
    }
    public void senser(Tile[][] tiles, Actor actor)
    {
        for(int i=0;i<this.items.size();i++)
        {
            this.items.get(i).senser(tiles, actor);
            if(items.get(i).isCollision(actor))
            {
                this.items.get(i).effect(actor);
            }
        }
    }
    public void update()
    {
        addItem();
        for(int i=0;i<this.items.size();i++)
        {
            this.items.get(i).update();
            if(this.items.get(i).isEnd())
            {
                this.items.remove(i);
                i--;
            }
        }
    }
    public void paint(Graphics g, Camera camera)
    {
        for(int i=0;i<this.items.size();i++)
        {
            this.items.get(i).paint(g, camera);
        }
    }
}
