/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interlude;

import choiceWindow.Word;
import controllers.ImageResourceController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Delay;
import util.ImagePath;

/**
 *
 * @author chill
 */
public class Introduce {
   

    private BufferedImage[] img;
   
    private int state; 
    private Delay delay;
    private int animation ;
    

    public Introduce() {
   
        
        img = new BufferedImage[3];
        img[0] = ImageResourceController.getInstance().tryGetImage(ImagePath.INTRODUCE_1);
        delay = new Delay(20) ;
        this.state = 0 ;
        this.animation = 0 ;
    }

    public void update() {
        if (delay.isTrig()) {
           animation = (animation + 1) % 2;
        }

    }

    public void paint(Graphics g) {
           
        switch (state) {
            case 0:
                g.drawImage(img[0], 350, 100, 754, 587, null) ;
            case 1:
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}

