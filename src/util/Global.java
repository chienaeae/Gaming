package util;

public class Global 
{
    public enum UNIT_SIZE
    {
        //面板
        Heart(51,51,51,51,17,17),
        
        //箭頭
        Curser(48,48,48,48,16,16),
        WoodCurser(48,48,48,48,48,48),
        CircleCurser(48,48,48,48,48,48),
        
        //小型物件
        Carrot(51,57,40,40,17,19),
        Cherry(63,63,45,45,21,21),
        Coin(49,49,35,35,16,16),
        Star(39,39,26,26,13,13),
        Gem(45,39,35,28,15,13),
        Item_Feedback(64,64,64,64,32,32),
        
        //互動物件
        Chest(120,96,100,78,40,32),
        Fire(150,150,150,150,64,64),
        Torch(150,150,150,150,64,64),
        
        //Tile
        Tile(48,48,48,48,16,16),
        
        //怪物
        Opossum(108,84,86,45,36,28),
        Bee(111,96,75,40,37,32),
        Eagle(120,126,80,63,40,41),
        Frog(105,96,70,35,35,32),
        Piranha(183,135,125,102,61,45),
        Slug(96,63,60,35,32,21),
        Squirrel(270,174,80,60,90,58),
        death(120,124,40,41,40,41);
        
        private int w;
        private int h;
        private int colliderW;
        private int colliderH;
        private int imgW;
        private int imgH;
        
        private UNIT_SIZE(int w, int h, int colliderW, int colliderH,  int imgW, int imgH)
        {
            this.w = w;
            this.h = h;
            this.colliderW = colliderW;
            this.colliderH = colliderH;
            this.imgW = imgW;
            this.imgH = imgH;
        }
        public int getWidth()
        {
            return this.w;
        }
        public int getHeight()
        {
            return this.h;
        }
        public int getColliderW()
        {
            return this.colliderW;
        }
        public int getColliderH()
        {
            return this.colliderH;
        }
        public int getImgW()
        {
            return this.imgW;
        }
        public int getImgH()
        {
            return this.imgH;
        }
    }
    public enum Frame//363,131
    {
        X(1280,1264), //-16  1280 1264
        Y(720,681);  //-39   720 681
        
        private int frameSize;
        private int screenSize;
        private Frame(int frameSize, int screenSize)
        {
            this.frameSize = frameSize;
            this.screenSize = screenSize;
        }
        public int getFrameSize()
        {
            return this.frameSize;
        }
        public int getScreenSize()
        {
            return this.screenSize;
        }
    }
    
    public static final boolean IS_DEBUG = false; 
    public static void log(String str)
    {
        if(IS_DEBUG)
        {
            System.out.println(str);
        }
    }
    public static final int ITEM_INTERACT_BUTTOM = 6;
    
    public static final int VK_A = 0;
    public static final int VK_D = 1;
    public static final int VK_W = 2;
    public static final int VK_S = 3;
    public static final int VK_LEFT = 4;
    public static final int VK_RIGHT = 5;
    public static final int VK_UP = 6;
    public static final int VK_DOWN = 7;
    public static final int VK_SPACE = -1;
    public static final int VK_ENTER = -2;
    public static final int VK_Z = 10;
    public static final int VK_X = 11;
    public static final int VK_C = 12;
    public static final int VK_T = 100;
    public static final int VK_Y = 101;
    public static final int VK_U = 102;
    public static final int VK_I = 103;
    
    //邏輯更新時間
    public static final int UPDATE_TIME_PER_SEC = 60;
    public static final int NANO_PER_UPDATE = 1000000000 / UPDATE_TIME_PER_SEC;
    //動畫更新時間
    public static final int FRAME_LIMIT = 60;
    public static final int LIMIT_DELTA_TIME = 1000000000 / FRAME_LIMIT;
    
    
    //Interlude
    public static final int  UNIT_SIZE_W = 66 ;
    public static final int  UNIT_SIZE_H =  64;
    public static final int  SQUIRREL_SIZE_W =  90*2;
    public static final int  SQUIRREL_SIZE_H =  58*2;
    
    public static boolean random(int rate)
    {
        return random(1,100) < rate;
    }
    public static int random(int min, int max)
    {
        return (int)(Math.random() * (max-min+1) + min);
    }
}