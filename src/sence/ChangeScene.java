package sence;

import choiceWindow.ChangeSceneWindow;
import controllers.ImageResourceController;
import controllers.SceneController;
import interlude.Timer;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.CommandSolver;
import util.Global;
import util.ImagePath;

public class ChangeScene extends  Scene {
    
    private ChangeSceneWindow csW;
    
    public ChangeScene(SceneController sceneController) {
        
        super(sceneController);
        this.csW = new ChangeSceneWindow();
    }

    @Override
    public void sceneBegin() {
        this.csW.initLayer();
    }

    @Override
    public void sceneUpdate() {
       this.csW.update();
    }

    @Override
    public void sceneEnd() {
    }

    @Override
    public void paint(Graphics g) {
        this.csW.paint(g);
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new MyKeyListener();
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return null;
    }

    public class MyKeyListener implements CommandSolver.KeyListener {

        @Override
        public void keyPressed(int commandCode, long trigTime) 
        {
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) 
        {
        }

        @Override
        public void keyTyped(char c, long trigTime) 
        {

        }
    }
    
}
