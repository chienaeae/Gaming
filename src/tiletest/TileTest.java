package tiletest;

import controllers.GameKernel;
import gameTile.TileMap;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import util.CommandSolver;
import util.Global;

public class TileTest {
    
    public static void main(String[] args)
    {
        JFrame j= new JFrame();
        GI gi = new GI();
        int[][] commands = new int[][]{
                    {KeyEvent.VK_W, Global.VK_W},
                    {KeyEvent.VK_A, Global.VK_A},
                    {KeyEvent.VK_S, Global.VK_S},
                    {KeyEvent.VK_D, Global.VK_D},
                    {KeyEvent.VK_RIGHT, Global.VK_RIGHT},
                    {KeyEvent.VK_LEFT, Global.VK_LEFT},
                    {KeyEvent.VK_UP, Global.VK_UP},
                    {KeyEvent.VK_DOWN, Global.VK_DOWN},
                    {KeyEvent.VK_T, Global.VK_T},
                    {KeyEvent.VK_Y, Global.VK_Y},
                    {KeyEvent.VK_U, Global.VK_U},
                    {KeyEvent.VK_I, Global.VK_I},
                    {KeyEvent.VK_Z, Global.VK_Z},
                    {KeyEvent.VK_X, Global.VK_X},
                    {KeyEvent.VK_C, Global.VK_C},
                    {KeyEvent.VK_SPACE, Global.VK_SPACE},
                    {KeyEvent.VK_ENTER, Global.VK_ENTER}};
        
        GameKernel gk = new GameKernel.Builder(gi, Global.NANO_PER_UPDATE, Global.LIMIT_DELTA_TIME )
                .initListener(commands)
                .enableKeyboardTrack(gi).enableMouseTrack(gi)
                .keyCleanMode()
                .gen();
        
        j.setTitle("AA");
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(Global.Frame.X.getFrameSize(), Global.Frame.Y.getFrameSize());
        j.setResizable(false);
        j.add(gk);
        j.setVisible(true);
        
        gk.run(Global.IS_DEBUG);
    }
}
