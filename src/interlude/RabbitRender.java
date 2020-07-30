package interlude;

import controllers.ImageResourceController;
import java.awt.Graphics;
import sence.ChoiceScene;
import util.Delay;
import util.Global;
import util.ImagePath;

public class RabbitRender {

    private Delay delay;
    private int step;
    private int animation;
    private int idleStep;
    private int runStep;
    private int fallStep;
    private int hurtStep ;
    
    public RabbitRender() {
        delay = new Delay(0);
        step = 0;
        animation = 0;
    }

    public void update(State state) {
        delay.resetDelayFrame(10);
        if (delay.isTrig()) {
            idleStep = (idleStep + 1) % State.RabbitState.idle.getImgNum();
            runStep = (runStep + 1) % State.RabbitState.run.getImgNum();
            fallStep = (fallStep + 1) % State.RabbitState.fall.getImgNum();
            hurtStep = (hurtStep + 1) % State.RabbitState.hurt.getImgNum();
            animation = (animation + 1) % 2;
        }
    }

    public void paint(Graphics g, State state, int x, int y) {
        
        if (Timer.getInstance().startStory()) {
            g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    0, 0, 900, 900, null);
            g.drawImage(state.getImg(), x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                    37 * idleStep, 0, 37 * (idleStep + 1), 32, null);
        } else if (Timer.getInstance().Squirrelshow() || Timer.getInstance().Squirrelstop()) {
            g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    1100, 0, 2000, 900, null);
            g.drawImage(state.getImg(), x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                    37 * idleStep, 0, 37 * (idleStep + 1), 32, null);
        } else if (Timer.getInstance().catchActor() || Timer.getInstance().runCatch()) {
            g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    1100, 0, 2000, 900, null);
            g.drawImage(state.getImg(), x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                    37 * idleStep, 0, 37 * (idleStep + 1), 32, null);
        } else if (Timer.getInstance().catchBack()&& !ChoiceScene.isFox) {
            g.drawImage(State.RabbitState.hurt.getImg(), x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                    37 * hurtStep, 0, 37 * (hurtStep + 1), 32, null);
        }else if (Timer.getInstance().catchBack()&& ChoiceScene.isFox){
             g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    1100, 1100, 2000, 2000, null);
             g.drawImage(State.RabbitState.run.getImg(), x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                    37 * runStep, 0, 37 * (runStep + 1), 32, null);
        }
        
        if (Timer.buttonShow==true && Timer.catchStory==false) {
            if (ChoiceScene.isFox) {
                g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.BUTTON_BLACK),
                        350, 150, 200, 200, null);
                g.drawImage(State.RabbitState.run.getImg(),
                        350, 150, 550, 340, // 200  190
                        37 * runStep, 0, 37 * (runStep + 1), 32, null);
            } else if (!ChoiceScene.isFox) {
                g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.BUTTON_WHITE),
                        350, 150, 200 + animation * 20, 200 + animation * 20, null);
                g.drawImage(State.RabbitState.fall.getImg(),
                        350, 150, 550, 340, // 200  190
                        37 * fallStep, 0, 37 * (fallStep + 1), 32, null);
            }
        }
    }
}
