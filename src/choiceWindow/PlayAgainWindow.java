package choiceWindow;

import camera.Camera;
import controllers.AudioRC;
import controllers.ImageResourceController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.AudioPath;
import util.Global;
import util.ImagePath;

public class PlayAgainWindow 
{
    private BufferedImage panel;
    private Word again;
    private Word backMenu;
    private Curser curser;
    private int state;  //(0)未選擇(1)again(2)backMenu
    
    public PlayAgainWindow()
    {
        this.state = 0;
        this.panel = ImageResourceController.getInstance().tryGetImage(ImagePath.PLAY_AGAIN_PANEL);
        init();
    }
    public void init()
    {
        this.again = new Word(520,535,"TRY AGAIN","Sitka Text",43);
        this.again.setColor(0, 0, 0);
        this.backMenu = new Word(520,590,"BACK MENU","Sitka Text",43);
        this.backMenu.setColor(0, 0, 0);
    }
    public int state()
    {
        return this.state;
    }
    public void keyReleased(int keyCode)  // 6上 7下
    {
        if(keyCode ==6 || keyCode==7)
        {
            AudioRC.getInstance().play(AudioPath.SELECTION);
            System.out.println(this.state);
            switch(this.state)
            {
                case 0:
                    if(keyCode ==6)
                    {
                        this.curser = new Curser(480, 520, Curser.Texture.CIRCLE);
                        this.state = 1;
                        break;
                    }
                    else if(keyCode==7)
                    {
                        this.curser = new Curser(480, 570, Curser.Texture.CIRCLE);
                        this.state = 2;
                        break;
                    }
                case 1:
                    this.state = 2;
                    break;
                case 2:
                    this.state = 1;
                    break;
            }
        }
    }
    public void curseControl()
    {
        if(this.state ==1)
        {
            this.curser.setVelY(-10);
            if(this.curser.getY()<=520)
            {
                this.curser.zeroVelY();
            }
            this.curser.speedControl();
        }
        else if(this.state ==2)
        {
            this.curser.setVelY(10);
            if(this.curser.getY()>=570)
            {
                this.curser.zeroVelY();
            }
            this.curser.speedControl();
        }
    }
    public void wordControl()
    {
        if(this.state ==1)
        {
            this.again.setColor(240, 240, 240);
            this.again.setSize(47);
            this.backMenu.setColor(0, 0, 0);
            this.backMenu.setSize(43);
        }
        else if(this.state ==2)
        {
            this.again.setColor(0, 0, 0);
            this.again.setSize(47);
            this.backMenu.setColor(240, 240, 240);
            this.backMenu.setSize(43);
        }
    }
    public void update()
    {
        curseControl();
        wordControl();
    }
    public void paint(Graphics g, Camera camera)
    {
        g.drawImage(this.panel, 0, 0, Global.Frame.X.getFrameSize(), Global.Frame.Y.getFrameSize(), null);
        this.again.paint(g);
        this.backMenu.paint(g);
        if(this.curser!=null)
        {
            this.curser.paint(g);
        }
    }
}
