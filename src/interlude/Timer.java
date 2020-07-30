package interlude;

public class Timer {

    private static Timer timer;
    private int counter;
    private int time;
    public static boolean catchStory;
    public static boolean buttonShow;
    private int restartTime ;
    private int restartCounter ;
    
    private Timer() {
        this.counter = 0 ;
        this.restartCounter = 0 ;
        catchStory = false ;

    }

    public static Timer getInstance() {
        if (timer == null) {
            timer = new Timer();
        }
        return timer;
    }
    public void timerDef()
    {
        this.time = 0;
        this.counter = 0;
        this.restartCounter = 0;
        this.restartTime = 0;
        catchStory = false;
        buttonShow = false;
    }
    public void start() {
        counter++;
        if (counter%60==0) {
            time++;
        }
//        System.out.println(Timer.getInstance().getTime());
    }

    public void restart() {
        restartCounter++;
        if (restartCounter % 60 == 0) {
            restartTime++;
        }
    }

    public boolean startStory() {
        if (this.time < 3) {
            return true;
        }
        return false;
    }

    public boolean Squirrelshow() {
        if (this.time >= 3 && time < 4) {
            return true;
        }
        return false;
    }

    public boolean Squirrelstop() {
        if (this.time >= 4 && catchStory==false) {
            return true;
        }
        return false;
    }

    public boolean runCatch() {
        restart() ;
        if (catchStory == true && restartTime<= 4) {
            return true;
        }
        return false;
    }
    public boolean catchActor(){
        if(restartTime> 4 && restartTime <= 7){
            return  true ;
        }
        return false ;      
        
    }
    public boolean  catchBack(){
        if(restartTime> 7){
            return true ;
        }
        return false ;
    }

    public boolean ChoiceButton() {
        if (this.time < 3) {
            return true;
        }
        return false;
    }

    public int getCounter() {
        return this.counter;
    }

    public int getTime() {
        return time;
    }
}
