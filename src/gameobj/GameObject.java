package gameobj;

import camera.Camera;
import graph.Rect;
import java.awt.Color;
import java.awt.Graphics;
import util.Global;

public abstract class GameObject
{
//    private static final int MIN_VEL_X = 
    
    protected Rect collider;
    protected Rect rect;
    
    protected double accX;
    protected double accY;
    protected double velX;
    protected double velY;
    
    public GameObject(int x, int y, int width, int height, int colliderWidth, int colliderHeight)
    {
        this.rect = Rect.genWithCenter(x, y, width, height);
        this.collider = Rect.genWithCenter(x, y, colliderWidth, colliderHeight);
    }
    public GameObject(int x, int y, int width, int height, boolean isBindCollider)
    {
        this.rect = Rect.genWithCenter(x, y, width, height);
        if(isBindCollider)
        {
            this.collider = this.rect;
        }
        else
        {
            this.collider = Rect.genWithCenter(x, y, width, height);
        }
    }
    public int getX()
    {
        return this.rect.centerX();
    }
    public int getY()
    {
        return this.rect.centerY();
    }
    public int width()
    {
        return this.rect.width();
    }
    public int height()
    {
        return this.rect.height();
    }
    public int colliderTop()
    {
        return this.collider.top();
    }
    public int colliderDown()
    {
        return this.collider.down();
    }
    public int colliderLeft()
    {
        return this.collider.left();
    }
    public int colliderRight()
    {
        return this.collider.right();
    }
    public int colliderWidth()
    {
        return this.collider.width();
    }
    public int colliderHeight()
    {
        return this.collider.height();
    }
    public int rectTop()
    {
        return this.rect.top();
    }
    public int rectDown()
    {
        return this.rect.down();
    }
    public int rectLeft()
    {
        return this.rect.left();
    }
    public int rectRight()
    {
        return this.rect.right();
    }
    public int rectHeight()
    {
        return this.rect.height();
    }
    public int rectWidth()
    {
        return this.rect.width();
    }
    public void setX(int x)
    {
        this.rect.offset(x - this.rect.centerX(), 0);
        this.collider.offset(x - this.collider.centerX(), 0);
    }
    public void setY(int y)
    {
        this.rect.offset(0, y - this.rect.centerY());
        this.collider.offset(0, y - this.collider.centerY());
    }
    public boolean isCollision(GameObject obj)
    {
        if(this.collider == null || obj.collider == null)
        {
            return false;
        }
        return Rect.interesects(this.collider, obj.collider);
    }
    public int sideCollsion(GameObject obj2, double G)
    {
        if(this.collider.down() + this.velY >= obj2.collider.top()
                && this.collider.left() < obj2.collider.right()
                && this.collider.right() > obj2.collider.left()
                && this.collider.top() < obj2.collider.top()
                && this.collider.down() - G < obj2.collider.top())
        {
            return 1; //腳部碰觸
        }
        if(this.collider.top() + this.velY <= obj2.collider.down()
                && this.collider.left() < obj2.collider.right()
                && this.collider.right() > obj2.collider.left()
                && this.collider.down() > obj2.collider.down()
                && this.collider.top() + G > obj2.collider.down())
        {
            return 2; //頭部碰觸
        }
        if(this.collider.left() + this.velX <= obj2.collider.right()
                && this.collider.top() < obj2.collider.down()
                && this.collider.down() > obj2.collider.top()
                && this.collider.right() > obj2.collider.right()
                && this.collider.left() >= obj2.collider.right())
        {
            return 3; //左邊碰觸
        }
        if(this.collider.right() + this.velX >= obj2.collider.left()
                && this.collider.top() < obj2.collider.down()
                && this.collider.down() > obj2.collider.top()
                && this.collider.left() < obj2.collider.left()
                && this.collider.right() <= obj2.collider.left())
        {
            return  4; //右邊碰觸
        }
        return -1;
    }
    public boolean step(GameObject obj2)
    {
        if(velY > 0 
                && this.collider.down() >= obj2.collider.top()
                && this.collider.left()< obj2.collider.right()
                && this.collider.right()> obj2.collider.left()
                && this.collider.top() < obj2.collider.top())
        {
            return true;
        }
        else if(velY >= 0 
                && this.collider.down() <= obj2.collider.top()
                && nextFrameY() + colliderHeight()/2 >= obj2.collider.top()
                && this.collider.left() < obj2.collider.right()
                && this.collider.right() > obj2.collider.left())
        {
            return true;
        }
        return false;
    }
    public boolean downCollsion(GameObject obj2, double g)
    {
        if(velY >= 0 
                && this.collider.down() >= obj2.collider.top()
                && this.collider.left() + g < obj2.collider.right()
                && this.collider.right() - g > obj2.collider.left()
                && this.collider.top() < obj2.collider.top())
        {
            return true;
        }
        if(velY >= 0 
                && this.collider.down() <= obj2.collider.top()
                && nextFrameY() + colliderHeight()/2 >= obj2.collider.top()
                && this.collider.left() + g < obj2.collider.right()
                && this.collider.right() - g > obj2.collider.left())
        {
            return true;
        }
        return false;
    }
    public boolean topCollsion(GameObject obj2, double g)
    {
        if(velY <= 0 
                && this.collider.top() <= obj2.collider.down()
                && this.collider.left() +g< obj2.collider.right()
                && this.collider.right() -g> obj2.collider.left()
                && this.collider.down() > obj2.collider.down())
        {
            return true;
        }
        if (velY <= 0
                && this.collider.top() >= obj2.collider.down()
                && nextFrameY() - colliderHeight()/2 <= obj2.collider.down()
                && this.collider.left() + g < obj2.collider.right()
                && this.collider.right() - g > obj2.collider.left())
        {
            return true;
        }
        return false;
    }
    public boolean rightCollsion(GameObject obj2, double g)
    {
        if (velX >= 0
                && this.collider.right() <= obj2.collider.left()
                && nextFrameX() + colliderWidth()/2 >= obj2.collider.left()
                && this.collider.top() +g< obj2.collider.down()
                && this.collider.down() -g> obj2.collider.top())
        {
            return true;
        }
        return false;
    }
    public boolean leftCollsion(GameObject obj2, double g)
    {
        if (velX <= 0
                && this.collider.left() >= obj2.collider.right()
                && nextFrameX() - colliderWidth()/2 <= obj2.collider.right()
                && this.collider.top() +g < obj2.collider.down()
                && this.collider.down() -g > obj2.collider.top())
        {
            return true;
        }
        return false;
    }
    public int currentFrameX()
    {
        return this.getX();
    }
    public int currentFrameY()
    {
        return this.getY();
    }
    public int nextFrameX()
    {
        double to = this.velX + this.accX;
        if(Math.abs(to)<1)
        {
            to = 0;
        }
        return (int)(this.getX() + to);
    }
    public int nextFrameY()
    {
        double to = (this.velY + this.accY)*0.85;
        if(Math.abs(to) < 0.5 )
        {
            this.velY = 0;
        }
        return (int)(this.getY() + to);
    }
    public void zeroVelX()
    {
        this.velX = 0;
    }
    public void zeroVelY()
    {
        this.velY = 0;
    }
    public void zeroAccX()
    {
        this.accX = 0;
    }
    public void zeroAccY()
    {
        this.accY = 0;
    }
    public void addAccX(double accX)
    {
        this.accX += accX;
    }
    public void addAccY(double accY)
    {
        this.accY += accY;
    }
    public void addVelX(double accX)
    {
        this.velX += accX;
    }
    public void addVelY(double accY)
    {
        this.velY += accY;
    }
    public void setVelX(double velX)
    {
        this.velX = velX;
    }
    public void setVelY(double velY)
    {
        this.velY = velY;
    }
    public double velX()
    {
        return this.velX;
    }
    public double velY()
    {
        return this.velY;
    }
    public void speedControl()
    {
        this.velX += this.accX;
        this.velX = this.velX * 0.85;
        if(Math.abs(this.velX) < 1)
        {
            this.velX = 0;
        }
        this.velY += this.accY;
        this.velY = this.velY * 0.85;
        if(Math.abs(this.velY) < 0.5 )
        {
            this.velY = 0;
        }
        this.setX(nextFrameX());
        this.setY(nextFrameY());
    }
    public void gravityAcc()
    {
        this.accY += Actor.GRAVITY;
    }
    public void paint(Graphics g, Camera camera)
    {
        if(camera.inRange(this))
        {
            paintComponent(g, camera);
            if(Global.IS_DEBUG  && camera!=null)
            {
//                g.drawString(this.colliderLeft() + "",
//                        this.rect.left() - camera.getCameraX() + 1, this.rect.top() - camera.getCameraY() + 12);
//                g.drawString(this.colliderTop() + "",
//                        this.rect.left() - camera.getCameraX() + 1, this.rect.top() - camera.getCameraY() + 46);
//                g.setColor(Color.red);
//                g.drawRect(this.rect.left() - camera.getCameraX(), this.rect.top() - camera.getCameraY(), this.rect.width(), this.rect.height());
//                g.setColor(Color.blue);
//                g.drawRect(this.collider.left() - camera.getCameraX(), this.collider.top() - camera.getCameraY(), this.collider.width(), this.collider.height());
//                g.setColor(Color.black);
            }
        }
    }
    public void paint(Graphics g)
    {
        paintComponent(g,null);
    }
    public abstract void update();
    public abstract void paintComponent(Graphics g, Camera camera);
}
