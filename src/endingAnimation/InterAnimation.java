package endingAnimation;

import background.Layer;
import background.LayerManger;
import camera.*;
import controllers.ImageResourceController;
import endingAnimation.Role.IActing;
import foxstate.Fall;
import foxstate.Idle;
import foxstate.Jump;
import foxstate.Run;
import gameTile.TileMap;
import gameobj.GameObject;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import renderer.FoxRenderer;
import renderer.RabbitRenderer;
import renderer.Renderer;
import util.Delay;
import util.ImagePath;

public abstract class InterAnimation 
{
    //動畫結束
    public Delay endingDelay;
    public boolean isEnd;
    
    public Delay slowMotionDelay;
    
    public AnimeDialo currentDialo;
    public ArrayList<Role> roles;
    public ArrayList<AnimeDialo> dialo;
    public TileMap map;
    public LayerManger layerManager;
    public AnimeCamera camera;
    
    public InterAnimation(TileMap map, int ending)
    {
        this.endingDelay = new Delay(ending*3);
        this.isEnd = false;
        this.slowMotionDelay = new Delay(1);
        this.slowMotionDelay.start();
        this.map = map;
        this.layerManager = new LayerManger();
        layerInit();
        this.roles = new ArrayList<>();
        roleInit();
        
        this.camera = new AnimeCamera(this.map.worldWidth(), this.map.worldHeight(), this.roles);
        cameraInit();
        
        this.dialo = new ArrayList<>();
        dialoInit();
    }
    public void addLayer(Layer layer)
    {
        this.layerManager.add(layer);
    }
    public void addRole(Role role)
    {
        this.roles.add(role);
    }
    public void addDialo(AnimeDialo dialo)
    {
        this.dialo.add(dialo);
    }
    
    //初始演出角色、鏡頭、過場台詞
    public abstract void roleInit();
    public abstract void cameraInit();
    public abstract void dialoInit();
    public abstract void layerInit();
    
    
    public boolean isEnd()
    {
        return this.isEnd;
    }
    public void checkDiaActive()
    {
        for(int i=0;i<this.dialo.size();i++)
        {
            if(this.dialo.get(i).isActive())
            {
                this.currentDialo = this.dialo.get(i);
            }
        }
    }
    public void checkCurrentDialoActive()
    {
        if(this.currentDialo == null)
        {
            return;
        }
        if(!this.currentDialo.isActive())
        {
            this.currentDialo = null;
        }
        else if(this.currentDialo.isActive())
        {
            this.currentDialo.update();
        }
    }
    public void keyReleased(int keyCode)
    {
        for(int i=0;i<this.dialo.size();i++)
        {
            this.dialo.get(i).keyReleased(keyCode);
        }
    }
    public void update()
    {
        if (this.slowMotionDelay.isTrig()) {
            checkDiaActive();
            checkCurrentDialoActive();
            if(this.currentDialo==null)
            {
                for(int i=0;i<this.dialo.size();i++)
                {
                    this.dialo.get(i).update();
                }
                for(int i=0;i<this.roles.size();i++)
                {
                    roles.get(i).senserTile(this.map.tiles());
                    roles.get(i).update();
                }
                this.camera.update();
                if(this.endingDelay.isTrig())
                {
                    this.isEnd = true;
                }
            }
        }
        this.layerManager.update();
    }
    public void paint(Graphics g)
    {
        this.layerManager.paint(g, this.camera);
        this.map.paint(g, this.camera);
        for(int i=0;i<roles.size();i++)
        {
            roles.get(i).paint(g, this.camera);
        }
        for(int i=0;i<this.dialo.size();i++)
        {
            this.dialo.get(i).paint(g);
        }
    }
}
