package interlude;

import choiceWindow.Word;
import controllers.ImageResourceController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Delay;
import util.ImagePath;

public class Dialog {

    private BufferedImage[] img;
    private Word[] sentence ;
    private int state; 
    private Delay delay;
    private int animation ;
    

    public Dialog() {
        sentence = new Word[4] ;
        sentence[0] =new Word(240, 600, "Hey, Fox." +"   "+"I'm going to take an creature as a sacrifice to me.",
                "UD Digi Kyokasho NK-B", 30) ;
         sentence[1]=new Word(240, 600, "either you or your rabbit girlfriend must sacrifice your life.",
                "UD Digi Kyokasho NK-B", 30) ;
         sentence[2]=new Word(240, 600,"Whom do you choose?",
                "UD Digi Kyokasho NK-B", 30) ;
         sentence[3]=new Word(240, 600, "Whom do I choose?",
                "UD Digi Kyokasho NK-B", 30) ;
        
        img = new BufferedImage[3];
        img[0] = ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX);
        img[1] = ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_SQUIRREL);
        img[2] = ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_PRESS_HINT);
        delay = new Delay(20) ;
        state = 0 ;
        animation = 0 ;
    }

    public void update() {
        if (delay.isTrig()) {
           animation = (animation + 1) % 2;
        }

    }

    public void paint(Graphics g) {
        
        if(Timer.getInstance().Squirrelstop()){
             g.drawImage(img[2], 915, 615, 150 + animation * 3, 24 + animation, null);
        }
        
        if(Timer.getInstance().Squirrelstop() & state==0){
            g.drawImage(img[1], 115, 525, 100, 100, null);
            sentence[0].paint(g);
        }
           
        switch (state) {
            case 1:
                g.drawImage(img[1], 115, 525, 100, 100, null) ;
                sentence[1].paint(g);
                break;
           
            case 2:
                g.drawImage(img[1], 115, 525, 100, 100, null) ;
                sentence[2].paint(g);
                break;
                
            case 3:
               g.drawImage(img[0], 115, 525, 100, 100, null) ;
                sentence[3].paint(g);
                break;

        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
