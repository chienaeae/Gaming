package interlude;

import java.awt.Graphics;
import sence.ChoiceScene;
import util.Delay;
import util.Global;

public class FoxStory extends InterludeObject{

    private Delay delay;
    private FoxRender render;

    public FoxStory(int x, int y) {
        super(x, y, Global.UNIT_SIZE_W, Global.UNIT_SIZE_H);
        delay = new Delay(10);
        render = new FoxRender();
    }

    public void move() {
        this.x += 2;
    }
    public void moveSlow(){
        this.x += 1 ;
    }

    @Override
    public void update() {
        if (Timer.getInstance().catchBack() && ChoiceScene.isFox) {
            move();
        } else if (Timer.getInstance().catchBack() && !ChoiceScene.isFox) {
            moveSlow();
         }
        render.update(State.FoxState.climb);
    }

    @Override
    public void paint(Graphics g) {
        render.paint(g, State.FoxState.idle, x, y);
    }

    
}
