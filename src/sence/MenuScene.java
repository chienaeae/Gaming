package sence;

import background.*;
import camera.Camera;
import choiceWindow.MenuChoiceWindow;
import choiceWindow.Word;
import controllers.*;
import gameTile.TileMap;
import gameobj.Actor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import renderer.Renderer;
import util.*;

public class MenuScene extends Scene{

    public static final int LAYER_CHANGING_TIME = 120; //1:60禎
    
    private Delay bgDelay;
    private Camera camera;
    private Mask mask;
    private LayerManger night;
    private LayerManger forest;
    private LayerManger city;
    private MenuChoiceWindow choiceW;
    private int state;
    private boolean changing;
    
    public MenuScene(SceneController sceneController)
    {
        super(sceneController);
        camera = new Camera();
        this.mask = new Mask(false);
        this.choiceW = new MenuChoiceWindow();
        this.state = this.choiceW.state();
        this.bgDelay = new Delay(LAYER_CHANGING_TIME);
        this.night = new LayerManger();
        this.forest = new LayerManger();
        this.city = new LayerManger();
    }
    public void layerInit()
    {
        this.night.add(new SelfMoveLayer(ImagePath.N_LAYER_ONE, Background.CameraSpeed.FREEZE, 720 ,Renderer.Dir.LEFT));
        this.night.add(new SelfMoveLayer(ImagePath.N_LAYER_TWO, Background.CameraSpeed.EXTREME_SLOW, 720 ,Renderer.Dir.LEFT));
        this.night.add(new SelfMoveLayer(ImagePath.N_LAYER_THREE, Background.CameraSpeed.SLOW, 720 ,Renderer.Dir.LEFT));
        this.night.add(new SelfMoveLayer(ImagePath.N_LAYER_FOUR, Background.CameraSpeed.MEIDUM, 720 ,Renderer.Dir.LEFT));
        this.night.add(new SelfMoveLayer(ImagePath.N_LAYER_FIVE, Background.CameraSpeed.MEIDUM, 720 ,Renderer.Dir.LEFT));
        this.night.add(new SelfMoveLayer(ImagePath.N_LAYER_SIX, Background.CameraSpeed.MEIDUM, 720 ,Renderer.Dir.LEFT));
        this.city.add(new SelfMoveAnimationLayer(ImagePath.C_LAYER_THREE, 450, 180,  Background.CameraSpeed.EXTREME_SLOW, -720, Renderer.Dir.LEFT, 4));
        this.city.add(new SelfMoveAnimationLayer(ImagePath.C_LAYER_ONE, 450, 180,  Background.CameraSpeed.EXTREME_SLOW, -720, Renderer.Dir.LEFT, 4));
        this.city.add(new SelfMoveAnimationLayer(ImagePath.C_LAYER_TWO, 450, 180,  Background.CameraSpeed.MEIDUM, -720, Renderer.Dir.LEFT, 4));
        this.forest.add(new SelfMoveLayer(ImagePath.F_LAYER_ONE, Background.CameraSpeed.FREEZE, 0,Renderer.Dir.LEFT));
        this.forest.add(new SelfMoveLayer(ImagePath.F_LAYER_TWO, Background.CameraSpeed.EXTREME_SLOW, 0,Renderer.Dir.LEFT));
        this.forest.add(new SelfMoveLayer(ImagePath.F_LAYER_THREE, Background.CameraSpeed.SLOW, 0,Renderer.Dir.LEFT));
        this.forest.add(new SelfMoveLayer(ImagePath.F_LAYER_FOUR, Background.CameraSpeed.SLOW, 0,Renderer.Dir.LEFT));
        this.forest.add(new SelfMoveLayer(ImagePath.F_LAYER_FIVE, Background.CameraSpeed.MEIDUM, 0,Renderer.Dir.LEFT));
    }
    public void backgroundChange()
    {
        if(this.bgDelay.isTrig() && !this.changing)
        {
            this.mask.trigger();
        }
        if(this.mask.canChange() && !this.changing)
        {
            this.night.setY(this.night.getY() + 720);
            this.city.setY(this.city.getY() + 720);
            this.forest.setY(this.forest.getY() + 720);
        }
        if(this.night.getY() > 720)
        {
            this.night.setY(-720);
        }
        if(this.city.getY() > 720)
        {
            this.city.setY(-720);
        }
        if(this.forest.getY() > 720)
        {
            this.forest.setY(-720);
        }
    }
    public void sceneChangeSet()
    {
        this.changing = true;
        this.mask.autoMode(false);
        this.mask.whiteEffect(false);
        this.bgDelay.resetDelayFrame(70);
        this.bgDelay.zero();
        this.mask.trigger();
    }
    public void sceneChange()
    {
        if(this.bgDelay.isTrig() && this.changing)
        {
            switch(this.state)
            {
                case 1:
                    this.sceneController.changeScene(new ChoiceScene(this.sceneController));
                    break;
                case 2:
                    System.exit(state);
            }
        }
    }
    @Override
    public void sceneBegin() 
    {
        AudioRC.getInstance().play(AudioPath.NO_SOUND);
        AudioRC.getInstance().tryGetAudio(AudioPath.MENU).loop();
        this.changing = false;
        layerInit();
    }
    @Override
    public void sceneUpdate() 
    {
        sceneChange();
        backgroundChange();
        this.mask.update();
        this.night.update();
        this.city.update();
        this.forest.update();
        this.choiceW.update();
    }
    @Override
    public void sceneEnd() 
    {
    }

    @Override
    public void paint(Graphics g) 
    {
        this.night.paint(g, this.camera);
        this.city.paint(g, this.camera);
        this.forest.paint(g, this.camera);
        if(!this.changing)
        {
            this.mask.paint(g);
            this.choiceW.paint(g, this.camera);
        }
        else
        {
            this.choiceW.paint(g, this.camera);
            this.mask.paint(g);
        }
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() 
    {
        return new CommandSolver.KeyListener(){
            
             @Override
            public void keyPressed(int commandCode, long trigTime) 
            {
                
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) 
            {
                choiceW.keyReleased(commandCode);
                state = choiceW.state();
                if(commandCode == -2 && state!=0)
                {
                    AudioRC.getInstance().play(AudioPath.CONFIRM);
                    AudioRC.getInstance().tryGetAudio(AudioPath.MENU).stop();
                    sceneChangeSet();
                }
            }

            @Override
            public void keyTyped(char c, long trigTime) 
            {
                
            }
        };
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() 
    {
        return new CommandSolver.MouseCommandListener(){
            
            @Override
            public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime)
            {
                if(state == state.PRESSED)
                {
                    Global.log("X:" + (camera.getCameraX()+e.getX()) + ", Y:" + (camera.getCameraY() + e.getY()));
                }
            }  
        };
    }
}
