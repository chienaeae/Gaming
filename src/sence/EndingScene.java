package sence;

import controllers.AudioRC;
import controllers.SceneController;
import endingAnimation.FoxEnding;
import endingAnimation.InterAnimation;
import endingAnimation.RabbitEnding;
import gameTile.TileMap;
import gameobj.Actor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import util.AudioPath;
import util.CommandSolver;
import util.Delay;
import util.ImagePath;
import util.Mask;

public class EndingScene extends Scene{

    private InterAnimation endingAnimation;
    
    private boolean isAnimationEnd;
    ///轉換場景
    private Mask mask;
    private boolean changing;
    private Delay sceneDelay;
    
    public EndingScene(SceneController sceneController, Actor.ActorChoice actorChoice)
    {
        super(sceneController);
        endingSwitch(actorChoice);
    }
    
    public void endingSwitch(Actor.ActorChoice actorChoice)
    {
        switch(actorChoice)
        {
            case FOX:
                this.endingAnimation = new FoxEnding();
                break;
            case RABBIT:
                this.endingAnimation = new RabbitEnding();
                break;
        }
    }
    public void checkAnimeEnd()
    {
        if(this.changing)
        {
            AudioRC.getInstance().tryGetAudio(AudioPath.END).stop();
            return;
        }
        if(this.endingAnimation.isEnd())
        {
            this.isAnimationEnd = true;
            this.changing = true;
            this.mask.autoMode(false);
            this.mask.whiteEffect(false);
            this.sceneDelay = new Delay(80);
            this.mask.trigger();
        }
    }
    public void sceneChange()
    {
        if(this.sceneDelay==null)
        {
            return;
        }
        if(this.sceneDelay.isTrig() && this.changing)
        {
            this.sceneController.changeScene(new MenuScene(this.sceneController));
        }
    }
    @Override
    public void sceneBegin() 
    {
        this.isAnimationEnd = false;
        this.mask = new Mask(true);
        this.mask.whiteEffect(false);
        this.mask.trigger();
        AudioRC.getInstance().tryGetAudio(AudioPath.END).loop();
    }

    @Override
    public void sceneUpdate() 
    {
        sceneChange();
        checkAnimeEnd();
        this.endingAnimation.update();
        this.mask.update();
    }

    @Override
    public void sceneEnd() 
    {
    }

    @Override
    public void paint(Graphics g) 
    {
        this.endingAnimation.paint(g);
        this.mask.paint(g);
    }

    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new CommandSolver.KeyListener() {

            @Override
            public void keyPressed(int commandCode, long trigTime) {
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                endingAnimation.keyReleased(commandCode);
            }

            @Override
            public void keyTyped(char c, long trigTime) {
            }
        };
    }

    @Override
    public CommandSolver.MouseCommandListener getMouseListener() {
        return new CommandSolver.MouseCommandListener() {

            @Override
            public void mouseTrig(MouseEvent e, CommandSolver.MouseState state, long trigTime) {
            }
        };
    }
    
}
