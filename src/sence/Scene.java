package sence;

import controllers.*;
import java.awt.Graphics;
import util.CommandSolver.*;

public abstract class Scene 
{
    protected SceneController sceneController;
    
    public Scene(SceneController sceneController)
    {
        this.sceneController = sceneController;
    }
    public abstract void sceneBegin();
    public abstract void sceneUpdate();
    public abstract void sceneEnd();
    public abstract void paint(Graphics g);
    
    public abstract KeyListener getKeyListener();
    public abstract MouseCommandListener getMouseListener();
}
