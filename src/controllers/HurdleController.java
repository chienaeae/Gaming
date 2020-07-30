package controllers;

import background.*;
import camera.Camera;
import gameTile.TileMap;
import gameobj.Actor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import util.*;

public class HurdleController 
{
    public enum Level
    {
        LEVEL_TEST,
        LEVEL_ONE,
        LEVEL_TWO,
        LEVEL_THREE;
    }
    private Camera camera;
    private Delay slowMotionDelay;
    private long lastInput;
    
    private Actor actor;
    
    private TileMap map;
    private EnemyController enemies;
    private ItemController items;
    private LayerManger layers;
    
    public HurdleController(Level level, Actor actor)
    {
        this.slowMotionDelay = new Delay(1);
        this.actor = actor;
        hurdleBegin();
        initHurdle(level);
    }
    public void hurdleBegin()
    {
    }
    public void initHurdle(Level level)
    {
        this.setSlowMotion(1);
        this.layers = new LayerManger();
        this.slowMotionDelay.start();
        switch (level)
        {
            case LEVEL_TEST:
                this.actor.initStatus(288,288);
                this.map = new TileMap(ImagePath.TEST_LEVEL_MAP_PNG,
                        ImagePath.TEST_LEVEL_TILE_SHEET,
                        ImagePath.TEST_LEVEL_MAP_TXT);
                this.items = new ItemController(ImagePath.TEST_LEVEL_ITEM, this.actor);
                this.enemies = new EnemyController(ImagePath.TEST_LEVEL_MONSTER);
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), "/resources/background1.png", Background.CameraSpeed.FREEZE));
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), "/resources/background2.png", Background.CameraSpeed.EXTREME_SLOW, -350));
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), "/resources/background3.png", Background.CameraSpeed.MEIDUM, -200));
                this.camera = new Camera(this.map.worldWidth(), this.map.worldHeight());
                break;
            case LEVEL_ONE:
                this.actor.initStatus(288,288);
                this.map = new TileMap(ImagePath.LEVEL_ONE_MAP_PNG,
                        ImagePath.LEVEL_ONE_TILE_SHEET,
                        ImagePath.LEVEL_ONE_MAP_TXT);
                this.items = new ItemController(ImagePath.LEVEL_ONE_ITEM, this.actor);
                this.enemies = new EnemyController(ImagePath.LEVEL_ONE_MONSTER);
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_ONE_LAYER_TWO, Background.CameraSpeed.SLOW, 0));
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_ONE_LAYER_ONE, Background.CameraSpeed.HIGH, 400));
                this.camera = new Camera(this.map.worldWidth(), this.map.worldHeight());
                break;
            case LEVEL_TWO:
                this.actor.initStatus(288,288);
                this.map = new TileMap(ImagePath.LEVEL_TWO_MAP_PNG,
                        ImagePath.LEVEL_TWO_TILE_SHEET,
                        ImagePath.LEVEL_TWO_MAP_TXT);
                this.items = new ItemController(ImagePath.LEVEL_TWO_ITEM, this.actor);
                this.enemies = new EnemyController(ImagePath.LEVEL_TWO_MONSTER);
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_TWO_LAYER_TWO, Background.CameraSpeed.SLOW,-200));
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_TWO_LAYER_ONE, Background.CameraSpeed.MEIDUM,-200));
                this.camera = new Camera(this.map.worldWidth(), this.map.worldHeight());
                break;
            case LEVEL_THREE:
                this.actor.initStatus(700,288);
                this.map = new TileMap(ImagePath.LEVEL_THREE_MAP_PNG,
                        ImagePath.LEVEL_THREE_TILE_SHEET,
                        ImagePath.LEVEL_THREE_MAP_TXT);
                this.items = new ItemController(ImagePath.LEVEL_THREE_ITEM, this.actor);
                this.enemies = new EnemyController(ImagePath.LEVEL_THREE_MONSTER);
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_THREE_LAYER_FOUR, Background.CameraSpeed.FREEZE));
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_THREE_LAYER_THREE, Background.CameraSpeed.SLOW));
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_THREE_LAYER_TWO, Background.CameraSpeed.HIGH));
                this.layers.add(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_THREE_LAYER_ONE, Background.CameraSpeed.EXTREME_HIGH));
                this.camera = new Camera(this.map.worldWidth(), this.map.worldHeight());
                break;
        }
    }
    public void hurdleUpdate()
    {
         if (this.slowMotionDelay.isTrig()) {
            
            this.camera.update();
            this.camera.setTarget(this.actor);

            this.actor.checkAlive(this.camera);
            this.actor.senser(this.map.tiles(), this.enemies.enemies(), this.items.items());
            this.actor.update();
            
            this.enemies.senser(this.map.tiles(), this.items.items(), actor);
            this.enemies.update();

            this.items.senser(this.map.tiles(), actor);
            this.items.update();
        }
         this.layers.update();
    }
    public void hurdlePaint(Graphics g)
    {
        this.layers.paint(g, this.camera);
        this.map.paint(g, this.camera);
        this.items.paint(g, this.camera);
        this.enemies.paint(g, this.camera);
        this.actor.paint(g, this.camera);
        this.actor.statusPaint(g, this.camera);
    }
    public void keyPressed(int keyCode, long trigTime)
    {
        if(slowMotionDelay.getTrig())
        {
            lastInput = trigTime;
        }
        if(trigTime == lastInput)
        {
            items.keyPressed(keyCode);
            actor.keyInput(keyCode);
            camera.accurate(keyCode);
        }

        if (keyCode == 100) {
            camera.toPlayer();
        }
        if (keyCode == 101) {
            camera.offPlayer();
        }
        if (keyCode == 102 && Global.IS_DEBUG) {
            camera.paintAll();
        }
        if (keyCode == 103 && Global.IS_DEBUG) {
            camera.paintPiece();
        }
    }
    public void keyReleased(int keyCode, long trigTime)
    {
        actor.keyReleased(keyCode);
        items.keyReleased(keyCode);
    }
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) 
    {
        if (state == state.PRESSED) 
        {
            Global.log("X:" + (this.camera.getCameraX() + e.getX()) + ", Y:" + (this.camera.getCameraY() + e.getY()));
        }
    }
    public Actor.ActorChoice actorChoice()
    {
        return this.actor.actorChoice();
    }
    public int actorCoin()
    {
        return this.actor.coinNum();
    }
    public int actorLife()
    {
        return this.actor.lifeNum();
    }
    public void setLevelChangeStatus(int life, int coin)
    {
        this.actor.setLevelChangeStatus(life, coin);
    }
    public boolean checkOver()
    {
        if(this.actor.finish())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkMissionComplete()
    {
        if(this.actor.missionState())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void setSlowMotion(int frameDelay)
    {
        this.slowMotionDelay.resetDelayFrame(frameDelay);
    }
    public int slowMotion()
    {
        return this.slowMotionDelay.delayFrame();
    }
}
