package interlude;

import controllers.ImageResourceController;
import java.awt.Graphics;
import sence.ChoiceScene;
import util.Delay;
import util.Global;
import util.ImagePath;

public class FoxRender 
{
    private Delay delay;
    private int idleStep;
    private int runStep;
    private int jumpStep;
    private int animation;
    private int hurtStep ;
    
        public FoxRender() {
        delay = new Delay(0);
        idleStep = 0;
    }

    public void update(State state) {
        delay.resetDelayFrame(10);
        if (delay.isTrig()) {
            idleStep = (idleStep + 1) % State.FoxState.idle.getImgNum();
            runStep = (runStep + 1) % State.FoxState.run.getImgNum();
            jumpStep = (jumpStep + 1) % State.FoxState.jump.getImgNum();
            hurtStep = (hurtStep + 1) % State.FoxState.hurt.getImgNum();
            animation = (animation + 1) % 2;
        }

    }

    public void paint(Graphics g, State state, int x, int y) {
        //撠店瘜⊥部
        if (Timer.getInstance().startStory()) {
            g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    0, 0, 900, 900, null);
               g.drawImage(State.FoxState.idle.getImg(),
                x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                33 * idleStep, 0, 33 * (idleStep + 1), 32, null);

        } else if (Timer.getInstance().Squirrelshow() ) {
            g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    1100, 0, 2000, 900, null);
               g.drawImage(State.FoxState.idle.getImg(),
                x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                33 * idleStep, 0, 33 * (idleStep + 1), 32, null);
        }else if (Timer.getInstance().Squirrelstop()){
            g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    0, 1100, 900, 2000, null);
               g.drawImage(State.FoxState.idle.getImg(),
                x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                33 * idleStep, 0, 33 * (idleStep + 1), 32, null);
        }else if (Timer.getInstance().catchActor() || Timer.getInstance().runCatch()) {
            g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    1100, 0, 2000, 900, null);
              g.drawImage(State.FoxState.idle.getImg(),
                x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                33 * idleStep, 0, 33 * (idleStep + 1), 32, null);
        } else if (Timer.getInstance().catchBack()&& ChoiceScene.isFox) {
            g.drawImage(State.FoxState.hurt.getImg(), x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                    33 * hurtStep, 0, 33 * (hurtStep + 1), 32, null);
        }else if (Timer.getInstance().catchBack()&& !ChoiceScene.isFox){
            g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.EMOJI),
                    x, y - 50, x + Global.UNIT_SIZE_W + 3 * animation, y - 50 + Global.UNIT_SIZE_H + 3 * animation,
                    1100, 1100, 2000, 2000, null);
            g.drawImage(State.FoxState.run.getImg(), x, y, x + Global.UNIT_SIZE_W, y + Global.UNIT_SIZE_H,
                    33 * runStep, 0, 33 * (runStep + 1), 32, null);
        }
        
        if (Timer.buttonShow==true&&Timer.catchStory==false) {
            if (!ChoiceScene.isFox) {
                g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.BUTTON_BLACK),
                        100, 150, 200, 200, null);
                g.drawImage(State.FoxState.run.getImg(),
                        100, 130, 300, 340,
                        33 * runStep, 0, 33 * (runStep + 1), 32, null);
            } else if (ChoiceScene.isFox) {
                g.drawImage(ImageResourceController.getInstance().tryGetImage(ImagePath.BUTTON_WHITE),
                        100, 150, 200 + animation * 20, 200 + animation * 20, null);
                g.drawImage(State.FoxState.jump.getImg(),
                        100, 130, 300, 340,
                        33 * jumpStep, 0, 33 * (jumpStep + 1), 32, null);
            }
        }

    }
}
