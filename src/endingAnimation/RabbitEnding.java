package endingAnimation;

import background.Background;
import background.Layer;
import camera.CameraEvent;
import controllers.ImageResourceController;
import static endingAnimation.FoxEnding.DURATION;
import static endingAnimation.FoxEnding.FOX_JUMP;
import static endingAnimation.FoxEnding.FOX_SPEED;
import gameTile.TileMap;
import gameobj.GameObject;
import java.awt.image.BufferedImage;
import renderer.FoxRenderer;
import renderer.RabbitRenderer;
import renderer.Renderer;
import util.ImagePath;

public class RabbitEnding extends InterAnimation{
    
    public static final int DURATION = 560;
    
    public RabbitEnding()
    {
        super(new TileMap(ImagePath.LEVEL_THREE_MAP_PNG,ImagePath.LEVEL_THREE_TILE_SHEET,ImagePath.LEVEL_THREE_MAP_TXT),DURATION);
    }
    @Override
    public void roleInit()
    {
        Role fox = new Role(1502, 289, Role.RoleList.FOX, new FoxRenderer(0), FOX_SPEED, FOX_JUMP, Renderer.Dir.LEFT);
        fox.initAct(new Role.IActing() {
            @Override
            public Event[] initEvents() 
            {
                Event[] events = new Event[]{
                    new Event(80, Event.keyCode.IDLE),
                    new Event(10, Event.keyCode.LEFT),
                    new Event(120, Event.keyCode.IDLE),
                    new Event(10, Event.keyCode.RIGHT),
                    new Event(65, Event.keyCode.IDLE),    //26.5 秒
                    new Event(3, Event.keyCode.LEFT),
                    new Event(35, Event.keyCode.IDLE),  
                    new Event(5, Event.keyCode.LEFT), 
                    new Event(20, Event.keyCode.IDLE), 
                    new Event(45, Event.keyCode.LEFT), 
                    new Event(3, Event.keyCode.RIGHT), 
                    new Event(20, Event.keyCode.IDLE), 
                    new Event(20, Event.keyCode.LEFT), 
                    new Event(30, Event.keyCode.IDLE),       //44秒
                    new Event(10, Event.keyCode.LEFT), 
                    new Event(40, Event.keyCode.IDLE), 
                    new Event(3, Event.keyCode.RIGHT), 
                    new Event(80, Event.keyCode.IDLE), 
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
        Role rabbit = new Role(411, 460, Role.RoleList.RABBIT, new RabbitRenderer(0));
        rabbit.initAct(new Role.IActing() {
            @Override
            public Event[] initEvents() 
            {
                Event[] events = new Event[]{
                    new Event(12, Event.keyCode.IDLE),
                    new Event(4, Event.keyCode.LEFT),
                    new Event(7, Event.keyCode.IDLE),
                    new Event(4, Event.keyCode.RIGHT),
                    new Event(12, Event.keyCode.IDLE),
                    new Event(5, Event.keyCode.LEFT),
                    new Event(4, Event.keyCode.UP),
                    new Event(4, Event.keyCode.LEFT),
                    new Event(3, Event.keyCode.IDLE),
                    new Event(1, Event.keyCode.RIGHT),
                    new Event(65, Event.keyCode.IDLE),      //11秒
                    new Event(8, Event.keyCode.RIGHT), 
                    new Event(50, Event.keyCode.IDLE), 
                    new Event(4, Event.keyCode.UP), 
                    new Event(45, Event.keyCode.IDLE),       
                    new Event(10, Event.keyCode.RIGHT),     //23秒
                    new Event(10, Event.keyCode.IDLE),       
                    new Event(10, Event.keyCode.RIGHT),
                    new Event(40, Event.keyCode.IDLE),      
                    new Event(5, Event.keyCode.RIGHT),    //30秒
                    new Event(30, Event.keyCode.IDLE),       
                    new Event(5, Event.keyCode.RIGHT),       //33秒
                    new Event(40, Event.keyCode.IDLE),            
                    new Event(1, Event.keyCode.LEFT),  
                    new Event(20, Event.keyCode.IDLE),       
                    new Event(3, Event.keyCode.LEFT),       
                    new Event(60, Event.keyCode.IDLE),       //44秒      
                    new Event(4, Event.keyCode.UP),     
                    new Event(5, Event.keyCode.LEFT),       
                    new Event(20, Event.keyCode.IDLE),   
                    new Event(4, Event.keyCode.UP),     
                    new Event(5, Event.keyCode.LEFT),      
                    new Event(10, Event.keyCode.IDLE),       
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
            new CameraEvent(75,1),
            new CameraEvent(45,0),
            new CameraEvent(30,1),
            new CameraEvent(20,0),
            new CameraEvent(20,1),
            new CameraEvent(30,0),
            new CameraEvent(65,1),
            new CameraEvent(60,0),
            new CameraEvent(30,1),
        };
        this.camera.initCameraEvent(ce);
    }
    @Override
    public void dialoInit()
    {
        addDialo(new AnimeDialo(39){
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
                    new AnimeDialo.Line("Mike! Mike!", 1),
                    new AnimeDialo.Line("I killed it", 1),
                    new AnimeDialo.Line("It's safe now!", 1),
                };
            }
            
        });
        addDialo(new AnimeDialo(70){
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
                    new AnimeDialo.Line("Mike, where are you?", 1),
                    new AnimeDialo.Line("It's safe.", 1),
                    new AnimeDialo.Line("You can come now", 1),
                };
            }
            
        });
        addDialo(new AnimeDialo(105){
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
                    new AnimeDialo.Line("It's you? Judy?", 0),
                    new AnimeDialo.Line("You said you killed it?", 0),
                    new AnimeDialo.Line("I can't believe it", 0),
                };
            }
            
        });
        addDialo(new AnimeDialo(145){
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
                    new AnimeDialo.Line("Yes, my love", 1),
                    new AnimeDialo.Line("I came for saving you. And I've killed that monster.", 1),
                    new AnimeDialo.Line("You can come back home now!", 1),
                };
            }
            
        });
        addDialo(new AnimeDialo(160){
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
                    new AnimeDialo.Line("Really?", 0),
                    new AnimeDialo.Line("It's safe?", 0)
                };
            }
            
        });
        addDialo(new AnimeDialo(180){
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
                    new AnimeDialo.Line("Yes, you don't need to be afraid", 1),
                    new AnimeDialo.Line("There is no fear anymore", 1),
                    new AnimeDialo.Line("Let's go home, my love.", 1)
                };
            }
            
        });
        addDialo(new AnimeDialo(210){
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
                    new AnimeDialo.Line("Sorry, but I can't...", 0),
                };
            }
            
        });
        addDialo(new AnimeDialo(230){
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
                    new AnimeDialo.Line("Why?", 1),
                    new AnimeDialo.Line("You have to tell me", 1)
                };
            }
            
        });
        addDialo(new AnimeDialo(280){
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
                    new AnimeDialo.Line("Go home, Mike", 1)
                };
            }
            
        });
        addDialo(new AnimeDialo(300){
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
                    new AnimeDialo.Line("We can't just leave...", 0),
                    new AnimeDialo.Line("I saw animals cring and dying here", 0),
                    new AnimeDialo.Line("Even though the squirrel isn't around anymore", 0),
                    new AnimeDialo.Line("The trouble still exists", 0),
                    new AnimeDialo.Line("The forest is dying for it", 0),
                };
            }
            
        });
        addDialo(new AnimeDialo(320){
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
                    new AnimeDialo.Line("But it is not our business no longer", 1),
                    new AnimeDialo.Line("I am gonna take you home", 1),
                    new AnimeDialo.Line("We will stay out of it forever", 1),
                };
            }
            
        });
        addDialo(new AnimeDialo(335){
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
                    new AnimeDialo.Line("In fact, we can't...", 0),
                    new AnimeDialo.Line("Since the day I were here, I am involed", 0)
                };
            }
            
        });
        addDialo(new AnimeDialo(420){
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
                    new AnimeDialo.Line("The only way to get away from it", 0),
                    new AnimeDialo.Line("That is to fight", 0),
                    new AnimeDialo.Line("Oh God!", 1)
                };
            }
            
        });
        addDialo(new AnimeDialo(450){
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
                    new AnimeDialo.Line("I know you hate that definitely", 0),
                    new AnimeDialo.Line("For it, I won't ask you to go with me", 0),
                    new AnimeDialo.Line("...even if I wished...(sobing)", 0)
                };
            }
            
        });
        addDialo(new AnimeDialo(490){
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
                    new AnimeDialo.Line("Oh, Mike..., you are such a silly", 1)
                };
            }
            
        });
        addDialo(new AnimeDialo(520){
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
                    new AnimeDialo.Line("Mike, you know...", 1),
                    new AnimeDialo.Line("I will always fight for you", 1),
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
                    new AnimeDialo.Line("Judy, you do?", 0),
                    new AnimeDialo.Line("That's a must", 1),
                };
            }
            
        });
        addDialo(new AnimeDialo(545){
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
                    new AnimeDialo.Line("My love, I love you so much", 0),
                    new AnimeDialo.Line("You are always in my mind", 0),
                    new AnimeDialo.Line("no matter rain or shine round, also the same...", 0),
                    new AnimeDialo.Line("I love you too.", 1),
                    new AnimeDialo.Line("I promise you, I will protect you for my whole life", 1),
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
