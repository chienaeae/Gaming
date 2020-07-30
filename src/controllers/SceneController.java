package controllers;

import java.awt.Graphics;
import sence.Scene;
import util.CommandSolver.*;
import util.Delay;
import util.Mask;

public class SceneController 
{
    private Scene currentScene;
    private KeyListener kl;
    private MouseCommandListener ml;
    
    public void changeScene(Scene scene)
    {
        if(this.currentScene !=null)
        {
            this.currentScene.sceneEnd();
        }
        this.currentScene = scene;
        kl = currentScene.getKeyListener();
        ml = currentScene.getMouseListener();
        this.currentScene.sceneBegin();
    }
    public void sceneUpdate()
    {
        this.currentScene.sceneUpdate();
    }
    public void paint(Graphics g)
    {
        this.currentScene.paint(g);
    }
    
    public KeyListener getKL()
    {
        return this.kl;
    }
    public MouseCommandListener getML()
    {
        return this.ml;
    }
}

