package tiletest;

import controllers.GameKernel;
import controllers.SceneController;
import gameobj.Actor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import sence.ChangeScene;
import sence.EndingScene;
import sence.EndingScene;
import sence.MainScene;
import sence.MenuScene;
import util.CommandSolver;

public class GI implements CommandSolver.KeyListener, CommandSolver.MouseCommandListener, GameKernel.GameInterface
{
    private SceneController sceneController;
    
    public GI()
    {
        this.sceneController = new SceneController();

//        this.sceneController.changeScene(new ChangeScene(this.sceneController));
//        this.sceneController.changeScene(new MainScene(this.sceneController, Actor.ActorChoice.FOX));
//        this.sceneController.changeScene(new MainScene(this.sceneController, Actor.ActorChoice.RABBIT));
//        this.sceneController.changeScene(new MainScene(this.sceneController, Actor.ActorChoice.FOX));
        this.sceneController.changeScene(new MenuScene(this.sceneController));
//        this.sceneController.changeScene(new EndingScene(this.sceneController, Actor.ActorChoice.FOX));
    }
    @Override
    public void paint(Graphics g) 
    {
        this.sceneController.paint(g);
    }

    @Override
    public void update() 
    {
        this.sceneController.sceneUpdate();
    }
    
    @Override
    public void keyPressed(int commandCode, long trigTime) {
        if (sceneController.getKL() != null) {
            sceneController.getKL().keyPressed(commandCode, trigTime);
        }
        
    }

    @Override
    public void keyReleased(int commandCode, long trigTime) {
        if (sceneController.getKL() != null) {
            sceneController.getKL().keyReleased(commandCode, trigTime);
        }
        
    }

    @Override
    public void keyTyped(char c, long trigTime) {
        if (sceneController.getKL() != null) {
            sceneController.getKL().keyTyped(c, trigTime);
        }
        
    }

    @Override
    public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
        if (state != null && sceneController.getML() != null) {
            sceneController.getML().mouseTrig(e, state, trigTime);
        }
        
    }
}
