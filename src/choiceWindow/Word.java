package choiceWindow;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;

public class Word 
{
    private String title;
    private String font;
    private int size;
    private int[] color;
    private int pointX;
    private int pointY;
    
    private Color titleColor;
    private Font titleFont;
    
    public Word(int x, int y, String title, String font, int size)
    {
        this.color = new int[]{255,255,255};
        this.pointX = x;
        this.pointY = y;
        this.title = title;
        this.font = font;
        this.size = size;
        this.titleColor = new Color(this.color[0],
                this.color[1], 
                this.color[2]);
        this.titleFont = new Font(this.font,
                                Font.BOLD,
                                this.size);
    }
    public void wordHandle()
    {
        this.titleColor = new Color(this.color[0],
                this.color[1], 
                this.color[2]);
        this.titleFont = new Font(this.font,
                                Font.BOLD,
                                this.size);
    }
    public void setPosition(int x, int y)
    {
        this.pointX = x;
        this.pointY = y;
    }
    public void setColor(int red, int green, int blue)
    {
        this.color[0] = red;
        this.color[1] = green;
        this.color[2] =blue;
    }
    public int size()
    {
        return this.size;
    }
    public void setFont(String font)
    {
        this.font = font;
    }
    public void setSize(int size)
    {
        this.size = size;
    }
    public void setTitle(String str)
    {
        this.title = str;
    }
    public void test()
    {
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        for ( int i = 0; i < fonts.length; i++ )
        {
          System.out.println(fonts[i]);
        }
    }
    public void paint(Graphics g)
    {
        wordHandle();
        g.setFont(this.titleFont);
        g.setColor(this.titleColor);
        g.drawString(this.title, this.pointX, this.pointY);
    }
}
