package background;

import camera.Camera;
import java.awt.Graphics;
import java.util.ArrayList;

public class LayerManger 
{
    private ArrayList<Layer> layers;
    
    public LayerManger()
    {
        this.layers = new ArrayList<>();
    }
    public void add(Layer layer)
    {
        this.layers.add(layer);
    }
    public void update()
    {
        for(int i=0;i<this.layers.size();i++)
        {
            this.layers.get(i).update();
        }
    }
    public void setY(int newY)
    {
        for(int i=0;i<layers.size();i++)
        {
            this.layers.get(i).setY(newY);
        }
    }
    public int getY()
    {
        return this.layers.get(0).getY();
    }
    public void paint(Graphics g, Camera camera)
    {
        for(int i=0;i<this.layers.size();i++)
        {
            this.layers.get(i).paint(g, camera);
        }
    }
}
