package util;

import com.sun.prism.paint.Color;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Mask 
{
    private boolean whiteEffect;
    private boolean auto;
    private boolean on;
    private float speed;
    private float scale;

    public Mask(boolean on)
    {
        this.speed = 0.03f;
        this.on = on;
        if(this.on)
        {
            this.scale = 0.99f;
        }
        else
        {
            this.scale = 0.01f;
        }
        this.auto = true;
        this.whiteEffect = false;
    }
    public void on()
    {
        this.on = true;
        this.scale = 0.95f;
    }
    public void off()
    {
        this.on = false;
        this.scale = 0.05f;
    }
    public void whiteEffect(boolean isWhiteEffect)
    {
        this.whiteEffect = isWhiteEffect;
    }
    public void autoMode(boolean isAuto)
    {
        this.auto = isAuto;
    }
    public void trigger()
    {
        this.on = true;
    }
    public boolean canChange()
    {
        if(this.on && this.scale >=0.96f)
        {
            return true;
        }
        return false;
    }
    public void lightOn()
    {
        if(this.scale <= 0.96f)
        {
            this.scale += this.speed;
        }
    }
    public void lightOff()
    {
        if(this.scale >= 0.03f)
        {
            this.scale -= this.speed;
        }
    }
    public void logic()
    {
        if(this.on && this.scale >=0.96f && this.auto)
        {
            this.on = false;
        }
        if(this.on)
        {
            lightOn();
        }
        else
        {
            lightOff();
        }
    }
    public void update()
    {
        logic();
    }
    public void paint(Graphics g)
    {
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.scale); //1 >>> 0.1
        Graphics2D g2d = (Graphics2D) g;                                                    //不印
        Composite defaultAC = g2d.getComposite();                                           //0.1 >>> 1
        g2d.setComposite(ac); 
        if(!this.on && this.whiteEffect)
        {
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(0,0,
                Global.Frame.X.getFrameSize(),
                Global.Frame.Y.getFrameSize());
            g.setColor(java.awt.Color.BLACK);
        }
        else
        {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0,0,
                Global.Frame.X.getFrameSize(),
                Global.Frame.Y.getFrameSize());
            g.setColor(java.awt.Color.BLACK);
        }
        g2d.setComposite(defaultAC);
    }
}
