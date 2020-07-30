package gameTile;

import camera.Camera;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import util.Global;

public class TileMap 
{
    //root path
    private String mapRoot;
    private String tileSheetRoot;
    private String txtMapRoot;
    
    //map
    private int[][] map;           //txt map
    private BufferedImage BMP;
    private int[][][] ImageRGBs;   //rgb map
    private int numWidth;
    private int numHeight;
    private int width;
    private int height;
    //tileSheet
    private BufferedImage[][] tileImage;
    private int SheetWidth;
    private int SheetHeight;
    //load tile
    private Tile[][] tiles;
    
    public TileMap(String mapRoot, String tileSheetRoot, String txtMapRoot)
    {
        this.mapRoot = mapRoot;
        this.tileSheetRoot = tileSheetRoot;
        this.txtMapRoot = txtMapRoot;
        readRGBMap();
        readTileImage();
        loadTile();
    }
    private void readRGBMap()
    {
        try{
            BMP = ImageIO.read(getClass().getResource(this.mapRoot));
        }catch(IOException e)
        {
        }
        this.numWidth = BMP.getWidth();
        this.numHeight = BMP.getHeight();
        this.width = numWidth * Global.UNIT_SIZE.Tile.getWidth();
        this.height = numHeight * Global.UNIT_SIZE.Tile.getHeight();
        this.map = new int[numHeight][numWidth];
        this.ImageRGBs = new int [numHeight][numWidth][3];
        
        for(int i=0;i<numHeight;i++)
        {
            for(int j=0;j<numWidth;j++)
            {
                int pixel = BMP.getRGB(j, i);
                //轉成2進位 八個一組   ">>"
                int r = (0x00ff0000 & pixel) >> 16;
                int g = (0x0000ff00 & pixel) >> 8;
                int b = (0x000000ff & pixel);
//                if(Global.IS_DEBUG)
//                {
//                    System.out.print(r + " " + g + " " + b + ", ");
//                }
                this.ImageRGBs[i][j][0] = r;
                this.ImageRGBs[i][j][1] = g;
                this.ImageRGBs[i][j][2] = b;
            }
//            System.out.println();
        }
        
        try{
            InputStream in = getClass().getResourceAsStream(this.txtMapRoot);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String delims = "\\t+";
            for(int i = 0; i < numHeight; i++) 
            {
                String line = br.readLine();
                String[] tokens = line.split(delims);
                for(int j = 0; j < numWidth; j++) 
                {
//                    if(tokens[j].equals(" "))
//                    {
//                        map[i][j] = 99;
//                    }
//                    else
                    {
                        map[i][j] = Integer.parseInt(tokens[j]);
                    }
                    System.out.print(map[i][j] +  "\t");
                }
                System.out.println();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        Global.log("total width:" + this.width);
        Global.log("total height:" + this.height);
        Global.log("map width:" + numWidth);
        Global.log("map hight:" + numHeight);
    }
    private void readTileImage()
    {
        Sprite spr = new Sprite(Global.UNIT_SIZE.Tile.getImgW(), Global.UNIT_SIZE.Tile.getImgH(), this.tileSheetRoot);
        this.SheetWidth = spr.imageWidth();
        this.SheetHeight = spr.imageHeight();
        this.tileImage = new BufferedImage[6][(this.SheetWidth / Global.UNIT_SIZE.Tile.getImgW()) + 1];
        for(int i=0; i<this.SheetWidth / Global.UNIT_SIZE.Tile.getImgW(); i++)
        {
            //Type: normal
            this.tileImage[0][i] = spr.getSprite(i, 0);
            //Type: can jump
            this.tileImage[1][i] = spr.getSprite(i, 1);
            //Type: can hurt
            this.tileImage[2][i] = spr.getSprite(i, 2);
            //Type: can climb
            this.tileImage[3][i] = spr.getSprite(i, 3);
            //Type: apparent
            this.tileImage[4][i] = spr.getSprite(i, 4);
            this.tileImage[5][i] = spr.getSprite(i, 5);
        }
    }
    private void loadTile()
    {
        tiles = new Tile[numHeight][numWidth];
        
        for(int i=0; i<numHeight ;i++)
        {
            for(int j=0; j<numWidth ;j++)
            {
                int setPoint = this.map[i][j];
                int div = 1;
                while( setPoint/10 > 0)
                {
                    setPoint = setPoint/10;
                    div = div *10;
                }
                int setPointType = this.map[i][j] / div;
                int setPointIndex = this.map[i][j] % div;
                if(setPointType == 1)
                {
                    tiles[i][j] = new Tile(j,i,this.tileImage[--setPointType][setPointIndex],Tile.TileType.NORMAL);
                }
                else if(setPointType == 2)
                {
                    tiles[i][j] = new Tile(j,i,this.tileImage[--setPointType][setPointIndex],Tile.TileType.CANJUMP);
                }
                else if(setPointType == 3)
                {
                    tiles[i][j] = new Tile(j,i,this.tileImage[--setPointType][setPointIndex],Tile.TileType.CANHURT);
                }
                else if(setPointType == 4 && setPointIndex==0)
                {
                    tiles[i][j] = new Tile(j,i,this.tileImage[--setPointType][setPointIndex],Tile.TileType.CLIMB_HEAD);
                }
                else if(setPointType == 4 && setPointIndex==1)
                {
                    tiles[i][j] = new Tile(j,i,this.tileImage[--setPointType][setPointIndex],Tile.TileType.CLIMB_NODE);
                }
                else if(setPointType == 5)
                {
                    tiles[i][j] = new Tile(j,i,this.tileImage[--setPointType][setPointIndex],Tile.TileType.APPARENT);
                }
                else if(setPointType == 6)
                {
                    tiles[i][j] = new Tile(j,i,this.tileImage[--setPointType][setPointIndex],Tile.TileType.APPARENT);
                }
                else if(setPointType == 9)
                {
                    tiles[i][j] = new Tile(j,i,this.tileImage[this.tileImage.length-1][this.SheetWidth / Global.UNIT_SIZE.Tile.getImgW()-1],Tile.TileType.APPARENT);
                }
            }
        }
    }
    public Tile[][] tiles()
    {
        return this.tiles;
    }
    public int worldWidth()
    {
        return this.width;
    }
    public int worldHeight()
    {
        return this.height;
    }
    public void update()
    {
        
    }
    public void paint(Graphics g,Camera camera)
    {
        for(int i=0; i<numHeight ;i++)
        {
            for(int j=0; j<numWidth ;j++)
            {
                tiles[i][j].paint(g, camera);
            }
        }
        
        //for testTileSheet
//        for(int i=0; i<this.SheetWidth / Global.UNIT_SIZE.Tile.getWidth(); i++)
//        {
//            g.drawImage(this.tileImage[0][i], (i*Global.UNIT_SIZE.Tile.getWidth()) - cameraX, Global.UNIT_SIZE.Tile.getHeight()*0 - cameraY, null);
//            g.drawImage(this.tileImage[1][i], (i*Global.UNIT_SIZE.Tile.getWidth()) - cameraX, Global.UNIT_SIZE.Tile.getHeight()*1 - cameraY, null);
//            g.drawImage(this.tileImage[2][i], (i*Global.UNIT_SIZE.Tile.getWidth()) - cameraX, Global.UNIT_SIZE.Tile.getHeight()*2 - cameraY, null);
//        }
    }
}
