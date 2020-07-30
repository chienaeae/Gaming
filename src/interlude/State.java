package interlude;

import controllers.ImageResourceController;
import java.awt.image.BufferedImage;
import static util.ImagePath.FOX_CLIMB;
import static util.ImagePath.FOX_CROUCH;
import static util.ImagePath.FOX_HURT;
import static util.ImagePath.FOX_IDLE;
import static util.ImagePath.FOX_JUMP;
import static util.ImagePath.FOX_RUN;
import static util.ImagePath.RABBIT_CLIMB;
import static util.ImagePath.RABBIT_DUCK;
import static util.ImagePath.RABBIT_FALL;
import static util.ImagePath.RABBIT_HURT;
import static util.ImagePath.RABBIT_IDLE;
import static util.ImagePath.RABBIT_JUMP;
import static util.ImagePath.RABBIT_RUN;

public interface State {
    
     public int getImgNum();
     public BufferedImage getImg();
    
    public enum FoxState implements State {

        climb(FOX_CLIMB, 3),
        crouch(FOX_CROUCH, 2),
        hurt(FOX_HURT, 2),
        idle(FOX_IDLE, 4),
        jump(FOX_JUMP, 2),
        run(FOX_RUN, 6);

        private BufferedImage img;
        private String imgPath;
        private int imageNum;

        private FoxState(String imgPath, int imageNum) {
            this.imgPath = imgPath;
            this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
            this.imageNum = imageNum;
        }

        public int getImgNum() {
            return this.imageNum;
        }

        public BufferedImage getImg() {
            return this.img;
        }

    }

 public enum RabbitState implements State{

     
        climb(RABBIT_CLIMB, 4),
        duck(RABBIT_DUCK, 4),
        fall(RABBIT_FALL, 4),
        hurt(RABBIT_HURT, 2),
        idle(RABBIT_IDLE, 9),
        jump(RABBIT_JUMP, 4),
        run(RABBIT_RUN, 8);

        private BufferedImage img;
        private String imgPath;
        private int imageNum;

        private RabbitState(String imgPath, int imageNum) {
            this.imgPath = imgPath;
            this.img = ImageResourceController.getInstance().tryGetImage(imgPath);
            this.imageNum = imageNum;
        }

        public int getImgNum() {
            return this.imageNum;
        }

        public BufferedImage getImg() {
            return this.img;
        }
        
        }
    
}
