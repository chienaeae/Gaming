package sence;

import background.*;
import util.CommandSolver;
import camera.Camera;
import choiceWindow.*;
import controllers.*;
import controllers.HurdleController.Level;
import static controllers.HurdleController.Level.LEVEL_THREE;
import static controllers.HurdleController.Level.LEVEL_TWO;
import enemies.*;
import gameTile.*;
import gameobj.*;
import interlude.*;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import renderer.*;
import util.*;
import static util.Global.*;

public class MainScene extends Scene {

    //關卡
    private HurdleController hurdle;
    //GameOver
    private PlayAgainWindow againW;
    private boolean isGameOver;
    private int state;
    //MissionComplete
    private Level level;
    private Delay hurdleSlowDelay;
    private boolean levelProgressing;
    private ScoreWindow scoreW;
    //關卡轉場動畫
    private ChangeSceneWindow csWindow;
    //轉換場景
    private Mask mask;
    private boolean changing;
    private Delay sceneDelay;
    //操作說明
    private Introduce introduce;
    //結局過場動畫
    private boolean isEndingAnimation;

    public MainScene(SceneController sceneController, Actor.ActorChoice actorChoice) {
        super(sceneController);
        this.hurdle = new HurdleController(Level.LEVEL_ONE, new Actor(actorChoice));
        this.level = Level.LEVEL_ONE;
        this.changing = false;
        this.isEndingAnimation = false;
        this.introduce = new Introduce();
    }
    public void checkGameOver() {
        if(this.hurdle.checkOver() && !this.isGameOver)
        {
            this.isGameOver = true;
            this.againW = new PlayAgainWindow();
        }
    }
    public void checkMission() {
        if (this.hurdle.checkMissionComplete() && this.hurdleSlowDelay.isTrig()) {
            this.hurdle.setSlowMotion(this.hurdle.slowMotion() + 1);
            if (this.hurdle.slowMotion() >= 10) {
                this.levelProgressing = true;
                this.hurdleSlowDelay.stop();
                this.scoreW = new ScoreWindow(this.hurdle.actorChoice(), this.hurdle.actorLife(), this.hurdle.actorCoin());
            }
        }
    }
    public void sceneChangeSet() {
        offMusic();
        this.changing = true;
        this.mask.autoMode(false);
        this.mask.whiteEffect(false);
        this.sceneDelay = new Delay(70);
        this.mask.trigger();
    }
    public void sceneChange() {
        if (this.sceneDelay == null) {
            return;
        }
        if(this.sceneDelay.isTrig() && this.changing && !this.isEndingAnimation)
        {
            this.sceneController.changeScene(new MenuScene(this.sceneController));
        }
        else if(this.sceneDelay.isTrig() && this.changing && this.isEndingAnimation)
        {
            this.sceneController.changeScene(new EndingScene(this.sceneController, this.hurdle.actorChoice()));
        }
    }
    public void musicSwitch() {
        switch(this.level)
        {
             case LEVEL_ONE:
                AudioRC.getInstance().tryGetAudio(AudioPath.LEVEL_ONE).loop();
                break;
            case LEVEL_TWO:
                AudioRC.getInstance().tryGetAudio(AudioPath.LEVEL_TWO).loop();
                break;
            case LEVEL_THREE:
                AudioRC.getInstance().tryGetAudio(AudioPath.LEVEL_THREE).loop();
                break;
        }
    }
    @Override
    public void sceneBegin() {
        this.isGameOver = false;
        this.levelProgressing = false;
        this.mask = new Mask(true);
        this.mask.whiteEffect(true);
        this.mask.trigger();
        this.hurdleSlowDelay = new Delay(15);
        musicSwitch();
    }
    @Override
    public void sceneUpdate() {
        sceneChange();
        checkGameOver();
        checkMission();
        if (this.levelProgressing) {
            this.scoreW.update();
        }
        if (this.isGameOver) {
            this.againW.update();
        } else if (!this.levelProgressing) {
            this.hurdle.hurdleUpdate();
        }
        this.mask.update();
        introduce.update();

    }
    @Override
    public void sceneEnd() {

    }
    public void paint(Graphics g) {
        this.hurdle.hurdlePaint(g);
        if (this.levelProgressing) {
            this.scoreW.paint(g, null);
        }
        if (this.isGameOver) {
            this.againW.paint(g, null);
        }
        this.mask.paint(g);
        this.introduce.paint(g);
    }
    private void switchLevel()
    {
        switch(this.level)
        {
            case LEVEL_ONE:
                this.level = LEVEL_TWO;
                this.hurdle.initHurdle(LEVEL_TWO);
                sceneBegin();
                break;
            case LEVEL_TWO:
                this.level = LEVEL_THREE;
                this.hurdle.initHurdle(LEVEL_THREE);
                sceneBegin();
                break;
            case LEVEL_THREE:
                sceneChangeSet();
                this.level = null;
                this.isEndingAnimation = true;
        }
        this.hurdle.setLevelChangeStatus(this.scoreW.changedLife(), this.scoreW.changedCoin());
        this.scoreW = null;
        this.levelProgressing = false;
    }
    public void offMusic()
    {
        switch(this.level)
        {
            case LEVEL_ONE:
                AudioRC.getInstance().tryGetAudio(AudioPath.LEVEL_ONE).stop();
                break;
            case LEVEL_TWO:
                AudioRC.getInstance().tryGetAudio(AudioPath.LEVEL_TWO).stop();
                break;
            case LEVEL_THREE:
                AudioRC.getInstance().tryGetAudio(AudioPath.LEVEL_THREE).stop();
                break;
        }
    }
    private void levelStateSwitch(int commandCode)
    {
        if(scoreW!=null)
        {
            scoreW.keyReleased(commandCode);
        }

        if(commandCode == -2 && levelProgressing)
        {
            state = scoreW.state();
            AudioRC.getInstance().play(AudioPath.CONFIRM);
            switch(state)
            {
                 case 1:
                    sceneChangeSet();
                    break;
                case 2:
                    offMusic();
                    switchLevel();
                    this.scoreW = null;
                    break;
            }
        }
    }
    private void gameOverStateSwitch(int commandCode)
    {
        if(againW!=null)
        {
            this.againW.keyReleased(commandCode);
        }
        if(commandCode == -2 && this.isGameOver)
        {
            this.state = againW.state();
            AudioRC.getInstance().play(AudioPath.CONFIRM);
            switch (this.state)
            {
                case 1:
                    offMusic();
                    this.hurdle = new HurdleController(Level.LEVEL_ONE, new Actor(hurdle.actorChoice()));
                    this.level = Level.LEVEL_ONE;
                    this.againW = null;
                    sceneBegin();
                    break;
                case 2:
                    sceneChangeSet();
                    break;
            }
        }
    }
    @Override
    public CommandSolver.KeyListener getKeyListener() {
        return new CommandSolver.KeyListener() {

            @Override
            public void keyPressed(int commandCode, long trigTime) {
                hurdle.keyPressed(commandCode, trigTime);
            }

            @Override
            public void keyReleased(int commandCode, long trigTime) {
                switch (commandCode) {
                    case Global.VK_ENTER:
                        if (introduce.getState() == 0) {
                            introduce.setState(1);
                        }
                        break;
                    case Global.VK_SPACE:
                        if (introduce.getState() == 0) {
                            introduce.setState(1);
                        }
                        break;
                    case Global.VK_LEFT:
                        if (introduce.getState() == 0) {
                            introduce.setState(1);
                        }
                        break;
                    case Global.VK_RIGHT:

                        if (introduce.getState() == 0) {
                            introduce.setState(1);
                        }
                        break;
                }

                if (introduce.getState() == 1) {
                    hurdle.keyReleased(commandCode, trigTime);
                    gameOverStateSwitch(commandCode);
                    levelStateSwitch(commandCode);
                }
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
                hurdle.mouseTrig(e, state, trigTime);
            }
        };
    }
}
