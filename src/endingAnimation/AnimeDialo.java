package endingAnimation;

import choiceWindow.Word;
import controllers.ImageResourceController;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import util.Delay;
import util.ImagePath;

public abstract class AnimeDialo 
{
    public class Line
    {
        private String str;  //台詞
        private int index;   //說話者
        
        public Line(String str, int index)
        {
            this.str = str;
            this.index = index;
        }
    }
    public static final int ICON_POSITION_X = 115;
    public static final int ICON_POSITION_Y = 525;
    public static final int ICON_WIDTH = 100;
    public static final int ICON_HEIGHT = 100;
    public static final int PRESS_X = 915;
    public static final int PRESS_Y = 615;
    public static final int PRESS_WIDTH = 150;
    public static final int PRESS_HEIGHT = 24;
    //子類別改寫
    protected BufferedImage[] roleIcon;
    protected Line[] lines;
    //父類別定義
    private int currentLineIndex;
    private Delay waitDelay;
    private Delay pressAnimeDelay;
    private int pressCurrIndex;
    private boolean isActive;
    private BufferedImage pressIcon;
    private Word word;
    
    public AnimeDialo(int startTime)   // 1 >>> 0.1秒
    {
        this.waitDelay = new Delay(startTime*3);
        this.pressIcon = ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_PRESS_HINT);
        this.isActive = false;
        this.currentLineIndex = 0;
        initRoleIcon();
        initLines();
        this.word = new Word(240, 600, this.lines[this.currentLineIndex].str, "UD Digi Kyokasho NK-B", 30);
    }
    public abstract void initRoleIcon();
    public abstract void initLines();
    
    public boolean isActive()
    {
        return this.isActive;
    }
    private void pressAnimeUpdate()
    {
        if(this.pressAnimeDelay == null)
        {
            return;
        }
        if(this.pressAnimeDelay.isTrig())
        {
            this.pressCurrIndex = (this.pressCurrIndex + 1) % 2;
        }
    }
    private void countAcive()
    {
        if(this.waitDelay.isTrig())
        {
            this.isActive = true;
            this.waitDelay.stop();
            this.pressCurrIndex = 0;
            this.pressAnimeDelay = new Delay(20);
        }
        
    }
    public void keyReleased(int keyCode)
    {
        if(!this.isActive)
        {
            return;
        }
        if(keyCode == -1 || keyCode == -2 || keyCode == 4 || keyCode == 5)
        {
            if(this.currentLineIndex == this.lines.length-1)
            {
                this.isActive = false;
            }
            else
            {
                lineClick();
            }
        }
    }
    private void lineClick()
    {
        if(this.currentLineIndex >= this.lines.length-1)
        {
            this.currentLineIndex = this.lines.length-1;
        }
        else
        {
            this.currentLineIndex++;
            this.word.setTitle(this.lines[this.currentLineIndex].str);
        }
    }
    public void update()
    {
        if(!this.isActive)
        {
            countAcive();
            return;
        }
        pressAnimeUpdate();
    }
    public void paint(Graphics g)
    {
        if(this.isActive)
        {
            g.drawImage(this.pressIcon,
                    PRESS_X, PRESS_Y, PRESS_WIDTH + this.pressCurrIndex * 3, PRESS_HEIGHT + this.pressCurrIndex, null);
            g.drawImage(this.roleIcon[this.lines[this.currentLineIndex].index],
                    ICON_POSITION_X, ICON_POSITION_Y, ICON_WIDTH, ICON_HEIGHT, null);
            this.word.paint(g);
        }
    }
}
