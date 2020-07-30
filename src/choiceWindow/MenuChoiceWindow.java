package choiceWindow;

import camera.Camera;
import controllers.AudioRC;
import java.awt.Graphics;
import sence.MainScene;
import sence.Scene;
import util.AudioPath;
import util.ImagePath;

public class MenuChoiceWindow 
{
    private Word start;
    private Word quit;
    private Curser curser;
    private int state;    //(0)未選擇  (1)START  (2)QUIT

    public MenuChoiceWindow()
    {
        this.state = 0;
        init();
    }
    public void init()
    {
        this.start = new Word(200,475,"START","Sitka Text",60);
        this.start.setColor(255, 255, 255);
        this.quit = new Word(200,545,"QUIT","Sitka Text",60);
        this.quit.setColor(255, 255, 255);
    }
    public int state()
    {
        return this.state;
    }
    public void curseControl()
    {
        if(this.state ==1)
        {
            this.curser.setVelY(-10);
            if(this.curser.getY()<=450)
            {
                this.curser.zeroVelY();
            }
            this.curser.speedControl();
        }
        else if(this.state ==2)
        {
            this.curser.setVelY(10);
            if(this.curser.getY()>=520)
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
            this.start.setColor(253, 236, 166);
            this.start.setSize(66);
            this.quit.setColor(255, 255, 255);
            this.quit.setSize(60);
        }
        else if(this.state ==2)
        {
            this.start.setColor(255, 255, 255);
            this.start.setSize(60);
            this.quit.setColor(253 ,236 , 166);
            this.quit.setSize(66);
        }
    }
    public void keyReleased(int keyCode)  // 6上 7下
    {
        if(keyCode == 6 || keyCode == 7)  AudioRC.getInstance().play(AudioPath.SELECTION);
        if(this.state==0)
        {
            switch(keyCode)
            {
                case 6:
                    this.state = 1;
                    this.curser = new Curser(170, 450, Curser.Texture.NORMAL);
                    break;
                case 7:
                    this.state = 2;
                    this.curser = new Curser(170, 520, Curser.Texture.NORMAL);
                    break;
            }
        }
        else if(this.state==1)
        {
            switch(keyCode)
            {
                case 6:
                    this.state = 2;
                    break;
                case 7:
                    this.state = 2;
                    break;
            }
        }
        else if(this.state==2)
        {
            switch(keyCode)
            {
                case 6:
                    this.state = 1;
                    break;
                case 7:
                    this.state = 1;
                    break;
            }
        }
    }
    public void update()
    {
        curseControl();
        wordControl();
    }
    public void paint(Graphics g, Camera camera)
    {
        this.start.paint(g);
        this.quit.paint(g);
        if(this.curser!=null)
        {
            this.curser.paint(g, camera);
        }
    }
}
