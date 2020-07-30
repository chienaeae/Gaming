package graph;

public class Rect 
{
    private int left;
    private int right;
    private int top;
    private int down;
    
    public Rect(int left, int right, int top, int down)
    {
        this.left = left;
        this.right = right;
        this.top = top;
        this.down = down;
    }
    public static Rect genWithCenter(int x, int y, int w, int h)
    {
        int left = x - w/2;
        int right = left+ w;
        int top = y - h/2;
        int down = top + h;
        return new Rect(left, right, top, down);
    }
    public boolean intersects(int left, int right, int top, int down)
    {
        if(this.right < left) return false;
        if(this.left > right) return false;
        if(this.top > down) return false;
        if(this.down < top) return false;
        return true;
    }
    public static boolean interesects(Rect a, Rect b)
    {
        return a.intersects(b.left, b.right, b.top, b.down);
    }
    public int centerX()
    {
        return (this.left + this.right) / 2;
    }
    public int centerY()
    {
        return (this.top + this.down) / 2;
    }
    public float exactCenterX()
    {
        return (this.left + this.right) / 2f;
    }
    public float exactCenterY()
    {
        return (this.left + this.right) / 2f;
    }
    public void offset(int dx, int dy)
    {
        this.left += dx;
        this.right += dx;
        this.top += dy;
        this.down += dy;
    }
    public int left()
    {
        return this.left;
    }
    public void setLeft(int left)
    {
        this.left = left;
    }
    public int right()
    {
        return this.right;
    }
    public void setRight(int right)
    {
        this.right = right;
    }
    public int top()
    {
        return this.top;
    }
    public void setTop(int top)
    {
        this.top = top;
    }
    public int down()
    {
        return this.down;
    }
    public void setDown(int down)
    {
        this.down = down;
    }
    public int height()
    {
        return this.down - this.top;
    }
    public int width()
    {
        return this.right - this.left;
    }
}
