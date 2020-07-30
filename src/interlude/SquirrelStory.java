package interlude;

import java.awt.Graphics;
import util.Delay;
import util.Global;

public class SquirrelStory extends InterludeObject{
  

    private Delay delay;
    private SquirrelRender render ; 
    

    public  SquirrelStory(int x, int y) {
        super(x, y, Global.SQUIRREL_SIZE_W, Global.SQUIRREL_SIZE_H);
        delay = new Delay(20);
        render =  new SquirrelRender() ;
    }
    public  void moveLeft(){
        this.x -= 2 ;
    }
    public  void moveRight(){
        this.x += 4 ;
    }
    
    
    @Override
    public void update() {
        if(Timer.getInstance().Squirrelshow())
        moveLeft() ;
        if(Timer.catchStory==true)
        moveLeft() ;
        if(Timer.getInstance().catchBack())
        moveRight() ;    
        render.update();
    }

    @Override
    public void paint(Graphics g) {
        render.paint(g, x, y);
    }


    
}
