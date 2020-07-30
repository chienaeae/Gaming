package interlude;

import controllers.ImageResourceController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Delay;
import util.Global;

public class SquirrelRender {
    
    private Delay delay;
    private int step;
    private  int animation ;
    private BufferedImage idle [] ;
    private BufferedImage run [] ;    
    private BufferedImage jump [] ;    
    private int idleImgIndex ;
    private int runImgIndex ;
    private int jumpImgIndex ;

    public SquirrelRender() {
        delay = new Delay(10);
        step = 0;
        animation = 0 ;
        idleImgIndex = 0 ;
        runImgIndex = 0 ;
        idle = new BufferedImage [8] ;
        for(int i = 0 ; i < 8 ; i++){
            idle[i] = ImageResourceController.getInstance().tryGetImage
        ("/resources/squirrel/idle/player-idle-"+String.valueOf(i+1)+".png");
        }
        run =new BufferedImage [6] ;
          for(int i = 0 ; i < 6 ; i++){
            run[i] = ImageResourceController.getInstance().tryGetImage
        ("/resources/squirrel/run/player-run-"+String.valueOf(i+1)+".png"); 
        }
        jump = new BufferedImage [4] ;
        for(int i = 0 ; i < 4 ; i++){
            jump[i] = ImageResourceController.getInstance().tryGetImage
        ("/resources/squirrel/jump/player-jump-"+String.valueOf(i+1)+".png");
        }
        
    }
 
    
    public void update() {
        delay.resetDelayFrame(10);
        
        if (delay.isTrig()) {
            idleImgIndex = ( idleImgIndex+1 ) % 8 ;
            runImgIndex = (runImgIndex+1) % 6 ;
             jumpImgIndex = (jumpImgIndex+1) % 4 ;
            animation = (animation+1) % 2;
        }


    }

    public void paint(Graphics g,  int x, int y) {
        
     if(Timer.getInstance().Squirrelshow()){
         g.drawImage(run[runImgIndex], x, y, Global.SQUIRREL_SIZE_W, Global.SQUIRREL_SIZE_H, null) ;
     }else if(Timer.getInstance().Squirrelstop()&& Timer.catchStory == false){
         g.drawImage(idle[runImgIndex], x, y, Global.SQUIRREL_SIZE_W, Global.SQUIRREL_SIZE_H, null) ;
     }else if(Timer.getInstance().runCatch()){
           g.drawImage(run[runImgIndex], x, y, Global.SQUIRREL_SIZE_W, Global.SQUIRREL_SIZE_H, null) ;
     }else if(Timer.getInstance().catchActor()|| Timer.getInstance().catchBack()){
            g.drawImage(jump[jumpImgIndex], x, y, Global.SQUIRREL_SIZE_W, Global.SQUIRREL_SIZE_H, null) ;
            
        }
    }
}
