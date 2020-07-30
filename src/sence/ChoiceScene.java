package sence;

import background.*;
import camera.Camera;
import controllers.AudioRC;
import controllers.SceneController;
import gameobj.Actor;
import interlude.*;
import java.awt.Graphics;
import renderer.Renderer;
import util.*;

public class ChoiceScene extends Scene{
    
    //**轉換場景**//
    private Camera camera;
    private boolean isChanging;
    private boolean isSet;
    private LayerManger bg;
    private Delay sceneChangeDelay;
    private Mask sceneChangeMask;
    //************//
    
    private FoxStory fox ;
    private RabbitStory rabbit ;
    private SquirrelStory squirrel ;
    private Dialog dialog ;
    public static boolean isFox ;
    
    public ChoiceScene(SceneController sceneController) {
        
        super(sceneController);
        this.isSet = false;
        this.isChanging = false;
        this.camera = new Camera();
        this.bg = new LayerManger();
    }

    @Override
    public void sceneBegin() {
        AudioRC.getInstance().tryGetAudio(AudioPath.CHOICE).loop();
        this.fox = new FoxStory (550, 430) ;
        this.rabbit = new RabbitStory(600,430) ;
        this.squirrel = new SquirrelStory(1100 ,400) ;
        this.dialog = new Dialog();
        this.bg.add(new Layer(Global.Frame.X.getFrameSize(), Global.Frame.Y.getFrameSize(), ImagePath.M_LAYER_ZERO, Background.CameraSpeed.FREEZE, 0));
        this.bg.add(new AnimationLayer(ImagePath.M_LAYER_THREE, 660, 180,  Background.CameraSpeed.FREEZE, -120, Renderer.Dir.LEFT, 4));
        this.bg.add(new Layer(Global.Frame.X.getFrameSize(), Global.Frame.Y.getFrameSize(), ImagePath.CHOICE_BACKGROUD, Background.CameraSpeed.FREEZE, -33));
        this.bg.add(new SelfMoveAnimationLayer(ImagePath.M_LAYER_FIVE, 660,180,Background.CameraSpeed.SLOW, 25,Renderer.Dir.LEFT,4));
    }

    @Override
    public void sceneUpdate() {
        Timer.getInstance().start();
        this.squirrel.update();
        this.fox.update();
        this.rabbit.update();
        this.bg.update();
        this.dialog.update();
        sceneChangeReady();
        sceneChange();
        if(this.sceneChangeMask!=null)
        {
            this.sceneChangeMask.update();
        }
    }

    public void sceneChangeSet()
    {
        this.isSet = true;
        this.isChanging = false;
        this.sceneChangeDelay = new Delay(720);
    }
    public void sceneChangeReady()
    {
        if(this.isChanging)
        {
            return;
        }
        if(this.isSet && this.sceneChangeDelay.isTrig())
        {
            this.isChanging = true;
            this.sceneChangeMask = new Mask(false);
            this.sceneChangeMask.autoMode(false);
            this.sceneChangeMask.whiteEffect(false);
            this.sceneChangeDelay.resetDelayFrame(50);
            this.sceneChangeDelay.zero();
            this.sceneChangeMask.trigger();
        }
    }
    public void sceneChange()
    {
        if(this.sceneChangeDelay==null || !this.isSet)
        {
            return;
        }
        if(this.sceneChangeDelay.isTrig() && this.isChanging)
        {
            int choice;
            if(isFox) choice = 1;
            else choice = 2;
            switch(choice)
            {
                case 1:
                    this.sceneController.changeScene(new MainScene(this.sceneController, Actor.ActorChoice.RABBIT));
                    break;
                case 2:
                    this.sceneController.changeScene(new MainScene(this.sceneController,  Actor.ActorChoice.FOX));
                    break;
            }
        }
    }
    @Override
    public void sceneEnd() {
        AudioRC.getInstance().tryGetAudio(AudioPath.CHOICE).stop();
        Timer.getInstance().timerDef();
    }

    @Override
    public void paint(Graphics g ) {
        
        this.bg.paint(g, this.camera);
        this.squirrel.paint(g);
        this.fox.paint(g);
        this.rabbit.paint(g);      
        this.dialog.paint(g) ;
        if(this.sceneChangeMask!=null)
        {
            this.sceneChangeMask.paint(g);
        }
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
        public void keyPressed(int commandCode, long trigTime) {
        }

        @Override
        public void keyReleased(int commandCode, long trigTime) {
            
            switch (commandCode) 
            {
                case Global.VK_RIGHT:
                    if(dialog.getState() == 3)
                    {
                        AudioRC.getInstance().play(AudioPath.SELECTION);
                        ChoiceScene.isFox = !ChoiceScene.isFox ;
                    }
                    break;
                case Global.VK_LEFT:
                    if(dialog.getState() == 3)
                    {
                        AudioRC.getInstance().play(AudioPath.SELECTION);
                        ChoiceScene.isFox = !ChoiceScene.isFox ;
                    }
                    break;
                case Global.VK_ENTER:
                    if(Timer.getInstance().Squirrelstop()){
                        if(dialog.getState() == 0){
                            AudioRC.getInstance().play(AudioPath.CLICK);
                            dialog.setState(1) ;
                        }
                        else if(dialog.getState() == 1){
                            AudioRC.getInstance().play(AudioPath.CLICK);
                            dialog.setState(2) ;
                        }else if(dialog.getState() == 2){
                            AudioRC.getInstance().play(AudioPath.CLICK);
                            dialog.setState(3) ;
                            Timer.buttonShow = true ;
                        }else if(dialog.getState() == 3){
                            dialog.setState(4);
                            Timer.catchStory = true ;
                            AudioRC.getInstance().play(AudioPath.CONFIRM);
                            sceneChangeSet();
                        }
                    }
                    break;
                }
            }

        @Override
        public void keyTyped(char c, long trigTime) {

        }
    }
    
}
