package controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class ImageResourceController 
{
    private static ImageResourceController irc;
    private Map<String, BufferedImage> imgPairs;
    
    private ImageResourceController()
    {
        this.imgPairs = new HashMap<>();   //KEY, VALUE
    }
    
    public static ImageResourceController getInstance(){
        
        if(irc == null)
        {
            irc = new ImageResourceController();
        }
        return irc;
    }
    
    public BufferedImage tryGetImage(String path){
        
        BufferedImage img = this.imgPairs.get(path);
        if(img == null)
        {
            return addImage(path);
        }
        return img;
    }
    
    private BufferedImage addImage(String path){
        try{
            BufferedImage img = ImageIO.read(getClass().getResource(path));
            this.imgPairs.put(path, img);
            return img;
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
