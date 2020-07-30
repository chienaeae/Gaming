package endingAnimation;

import background.Background;
import background.Layer;
import camera.AnimeCamera;
import camera.CameraEvent;
import controllers.ImageResourceController;
import gameTile.TileMap;
import gameobj.GameObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import renderer.FoxRenderer;
import renderer.RabbitRenderer;
import renderer.Renderer;
import util.Delay;
import util.ImagePath;

public class FoxEnding extends InterAnimation{
    
    public static final int DURATION = 590;
    public static final double FOX_SPEED = 3;
    public static final double FOX_JUMP = -35;
    
    public FoxEnding()
    {
        super(new TileMap(ImagePath.LEVEL_THREE_MAP_PNG,ImagePath.LEVEL_THREE_TILE_SHEET,ImagePath.LEVEL_THREE_MAP_TXT), DURATION);
    }
    
    @Override
    public void roleInit()
    {
        Role fox = new Role(1108, 489, Role.RoleList.FOX, new FoxRenderer(0), FOX_SPEED, FOX_JUMP, Renderer.Dir.LEFT);
        fox.initAct(new Role.IActing() {
            @Override
            public Event[] initEvents() 
            {
                Event[] events = new Event[]{
                    new Event(1, Event.keyCode.LEFT),
                    new Event(5, Event.keyCode.IDLE),
                    new Event(1, Event.keyCode.RIGHT),
                    new Event(5, Event.keyCode.IDLE),
                    new Event(1, Event.keyCode.LEFT),
                    new Event(5, Event.keyCode.IDLE),
                    new Event(1, Event.keyCode.RIGHT),   
                    new Event(5, Event.keyCode.IDLE),
                    new Event(1, Event.keyCode.LEFT),   //來回轉頭
                    new Event(5, Event.keyCode.IDLE),
                    new Event(20, Event.keyCode.RIGHT),  //走向一旁 (進入對話，自言自語)
                    new Event(70, Event.keyCode.IDLE),      //*13秒
                    new Event(1, Event.keyCode.LEFT),
                    new Event(10, Event.keyCode.IDLE),   //前進一步，欲言又止
                    new Event(5, Event.keyCode.LEFT),
                    new Event(40, Event.keyCode.IDLE),    //17秒
                    new Event(3, Event.keyCode.LEFT),
                    new Event(30, Event.keyCode.IDLE),
                    new Event(50, Event.keyCode.LEFT),      //25秒
                    new Event(10, Event.keyCode.IDLE),
                    new Event(3, Event.keyCode.RIGHT),
                    new Event(60, Event.keyCode.IDLE),       //32秒
                    new Event(3, Event.keyCode.RIGHT), 
                    new Event(10, Event.keyCode.IDLE),
                    new Event(5, Event.keyCode.LEFT),
                    new Event(10, Event.keyCode.IDLE),
                    new Event(15, Event.keyCode.LEFT),
                    new Event(72, Event.keyCode.IDLE),       //44秒 
                    new Event(5, Event.keyCode.RIGHT), 
                    new Event(50, Event.keyCode.IDLE),        //48秒
                    new Event(7, Event.keyCode.RIGHT),  
                    new Event(40, Event.keyCode.IDLE),        //52秒
                    new Event(2, Event.keyCode.RIGHT),    
                    new Event(70, Event.keyCode.IDLE),        //56秒  
                };
                return events;
            }
            @Override
            public void animationLogic(GameObject self, Renderer renderer) 
            {
                if(self.velY() < 0 )
                {
                    renderer.setMovement(4);
                }
                if(self.velY() > 0 )
                {
                    renderer.setMovement(5);
                }
                if(Math.abs(self.velX()) > 0 && self.velY() == 0 )
                {
                    renderer.setMovement(6);
                }
                if(self.velX() == 0 && self.velY()==0 )
                {
                    renderer.setMovement(0);
                }
            }
            @Override
            public void move(Role role) 
            {
                switch(role.currentEvent())
                {
                    case LEFT:
                        role.setDir(Renderer.Dir.LEFT);
                        role.setVelX(-role.speed());
                        break;
                    case RIGHT:
                        role.setDir(Renderer.Dir.RIGHT);
                        role.setVelX(role.speed());
                        break;
                    case UP:
                        if(role.velY()==0)
                        {
                            role.setVelY(role.jumpHeight());
                        }
                        break;
                    case DOWN:
                        break;
                    case IDLE:
                        break;
                }
            }
            @Override
            public void setFall(Role role) 
            {
                role.setFall(true);
            }
        });
        addRole(fox);
        Role rabbit = new Role(142, 234, Role.RoleList.RABBIT, new RabbitRenderer(0));
        rabbit.initAct(new Role.IActing() {
            @Override
            public Event[] initEvents() 
            {
                Event[] events = new Event[]{
                    new Event(70, Event.keyCode.IDLE),
                    new Event(7, Event.keyCode.RIGHT),   //從箱子上跳下來
                    new Event(3, Event.keyCode.UP),
                    new Event(25, Event.keyCode.RIGHT),   //接近狐狸
                    new Event(45, Event.keyCode.IDLE),     //開口說話      *15秒
                    new Event(8, Event.keyCode.UP),
                    new Event(90, Event.keyCode.IDLE),                   //*25秒
                    new Event(3, Event.keyCode.LEFT), 
                    new Event(40, Event.keyCode.IDLE),                   //*29秒
                    new Event(3, Event.keyCode.LEFT), 
                    new Event(30, Event.keyCode.IDLE),                   //*32秒
                    new Event(15, Event.keyCode.LEFT), 
                    new Event(40, Event.keyCode.IDLE),                    //*37秒
                    new Event(3, Event.keyCode.RIGHT), 
                    new Event(45, Event.keyCode.IDLE), 
                    new Event(20, Event.keyCode.RIGHT), 
                    new Event(30, Event.keyCode.IDLE),                     //*49秒
                    new Event(4, Event.keyCode.LEFT),    
                    new Event(50, Event.keyCode.IDLE),                    //*51秒
                    new Event(4, Event.keyCode.LEFT), 
                    new Event(70, Event.keyCode.IDLE),    
                    
                };
                return events;
            }
            @Override
            public void animationLogic(GameObject self, Renderer renderer) 
            {
                if(self.velY() < 0 )
                {
                    renderer.setMovement(4);
                }
                if(self.velY() > 0 )
                {
                    renderer.setMovement(5);
                }
                if(Math.abs(self.velX()) > 0 && self.velY() == 0 )
                {
                    renderer.setMovement(6);
                }
                if(self.velX() == 0 && self.velY()==0 )
                {
                    renderer.setMovement(0);
                }
            }
            @Override
            public void move(Role role) 
            {
                switch(role.currentEvent())
                {
                    case LEFT:
                        role.setDir(Renderer.Dir.LEFT);
                        role.addVelX(-role.speed());
                        break;
                    case RIGHT:
                        role.setDir(Renderer.Dir.RIGHT);
                        role.addVelX(role.speed());
                        break;
                    case UP:
                        if(role.velY()==0)
                        {
                            role.addVelY(role.jumpHeight());
                        }
                        break;
                    case DOWN:
                        break;
                    case IDLE:
                        break;
                }
            }
            @Override
            public void setFall(Role role) 
            {
                role.setFall(true);
            }
        });
        addRole(rabbit);
    }
    @Override
    public void cameraInit()
    {
        CameraEvent[] ce = new CameraEvent[]{//(0)狐狸(1)兔子
            new CameraEvent(70,0),
            new CameraEvent(120,1),
            new CameraEvent(320,0),
            new CameraEvent(410,1),
            new CameraEvent(600,0),
        };
        this.camera.initCameraEvent(ce);
    }
    @Override
    public void dialoInit()
    {
        addDialo(new AnimeDialo(65){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Oh God!", 0),
                    new AnimeDialo.Line("Where is my darling?", 0)
                };
            }
        });
        addDialo(new AnimeDialo(110){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Mike?", 1),
                    new AnimeDialo.Line("You...you...finally...", 1),
                    new AnimeDialo.Line("I am scared...", 1)
                };
            }
        });
        addDialo(new AnimeDialo(150){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Judy!", 0),
                    new AnimeDialo.Line("My love!", 0),
                    new AnimeDialo.Line("I am looking for you so long.", 0),
                };
            }
        });
        addDialo(new AnimeDialo(170){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Mike.", 1),
                    new AnimeDialo.Line("I missed you.", 1),
                    new AnimeDialo.Line("Me too.", 0),
                    new AnimeDialo.Line("Judy, my love.", 0),
                };
            }
        });
        addDialo(new AnimeDialo(200){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Let's go home, my darling.", 0)
                };
            }
        });
        addDialo(new AnimeDialo(260){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Mike...", 1),
                    new AnimeDialo.Line("I need to say...", 1),
                };
            }
        });
        addDialo(new AnimeDialo(290){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Anything you can say.", 0),
                    new AnimeDialo.Line("Tell me, darling.", 0)
                };
            }
        });
        addDialo(new AnimeDialo(310){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("I'am afraid...", 1),
                    new AnimeDialo.Line("You don't like to hear...", 1),
                    new AnimeDialo.Line("...The fact...", 1),
                    new AnimeDialo.Line("The fact? What is that mean?", 0)
                };
            }
        });
        addDialo(new AnimeDialo(360){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Don't leave!", 0),
                    new AnimeDialo.Line("I love you, I'll accept anything of you", 0)
                };
            }
        });
        addDialo(new AnimeDialo(400){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("That Monster...That devil...", 1),
                    new AnimeDialo.Line("It hurt me so...so...", 1),
                    new AnimeDialo.Line("I can't even find a word...", 1),
                };
            }
        });
        addDialo(new AnimeDialo(415){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Darling, no fear anymore. I killed it.", 0),
                    new AnimeDialo.Line("You don't understand...", 1),
                    new AnimeDialo.Line("If you know, you wouldn't ever touch me again", 1)
                };
            }
        });
        addDialo(new AnimeDialo(465){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Then tell me!", 0),
                    new AnimeDialo.Line("Try me, I swear I'd love you all my life", 0)
                };
            }
        });
        addDialo(new AnimeDialo(500){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("You do?", 1),
                    new AnimeDialo.Line("I...I...I", 1),
                    new AnimeDialo.Line("I love you, but...", 1),
                    new AnimeDialo.Line("I ain't the one I was anymore", 1),
                };
            }
        });
        addDialo(new AnimeDialo(530){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Oh sweetie.", 0),
                    new AnimeDialo.Line("You are so silly", 0),
                    new AnimeDialo.Line("I love you not because who you are.", 0),
                    new AnimeDialo.Line("but because who I am", 0),
                };
            }
        });
        addDialo(new AnimeDialo(550){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("Oh dear.", 1),
                    new AnimeDialo.Line("I can't leave you now, and ever.", 1)
                };
            }
        });
        addDialo(new AnimeDialo(570){
            @Override
            public void initRoleIcon() {
                this.roleIcon = new BufferedImage[]{
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_FOX),
                    ImageResourceController.getInstance().tryGetImage(ImagePath.DIOLOG_RABBIT),
                };
            }
            @Override
            public void initLines() {
                this.lines = new AnimeDialo.Line[]{
                    new AnimeDialo.Line("And, I won't let you either.", 0)
                };
            }
        });
    }
    @Override
    public void layerInit()
    {
        addLayer(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_THREE_LAYER_FOUR, Background.CameraSpeed.FREEZE));
        addLayer(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_THREE_LAYER_THREE, Background.CameraSpeed.SLOW));
        addLayer(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_THREE_LAYER_TWO, Background.CameraSpeed.HIGH));
        addLayer(new Layer(this.map.worldWidth(), this.map.worldHeight(), ImagePath.LEVEL_THREE_LAYER_ONE, Background.CameraSpeed.EXTREME_HIGH));
    }
}
