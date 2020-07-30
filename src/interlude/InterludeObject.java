package interlude;

import java.awt.Graphics;

public abstract class InterludeObject {

    protected int x;
    protected int y;
    protected int w;
    protected int h;
    

    public InterludeObject( int x, int y, int w, int h) {
        
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
    }

    public boolean isCollision(InterludeObject obj) {
        if (getRight() < obj.getLeft()) {
            return false;
        }
        if (getLeft() > obj.getRight()) {
            return false;
        }
        
        if (getTop() > obj.getBottom()) {
            return false;
            
        }
        if (getBottom() < obj.getTop()) {
            return false;
        }
        return true;
    }
    
    public abstract void update() ;
    public abstract void paint(Graphics g) ;

    public int getRight() {
        return this.getX() + this.getW();
    }

    public int getLeft() {
        return this.getX();
    }

    public int getTop() {
        return this.getY();
    }

    public int getBottom() {
        return this.getY() + this.getH();
    }
    
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
    
}
