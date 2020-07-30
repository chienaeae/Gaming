package interlude;

import java.awt.Graphics;
import sence.ChoiceScene;
import util.Delay;
import util.Global;

public class RabbitStory extends InterludeObject{

    private Delay delay;
    private RabbitRender render ; 
    

    public RabbitStory(int x, int y) {
        super(x, y, Global.UNIT_SIZE_W, Global.UNIT_SIZE_H);
        delay = new Delay(20);
        render =  new RabbitRender() ;
    }
    public void move(){
        this.x +=2 ;
    }
    public void moveSlow(){
        this.x += 1 ;
    }

    @Override
    public void update() {
       render.update(State.RabbitState.idle);
       if (Timer.getInstance().catchBack() && !ChoiceScene.isFox) {
            move();
        } else if (Timer.getInstance().catchBack() && ChoiceScene.isFox) {
             moveSlow();
         }
    }

    @Override
    public void paint(Graphics g) {
        render.paint(g, State.RabbitState.idle, x, y);
    }
    
}
