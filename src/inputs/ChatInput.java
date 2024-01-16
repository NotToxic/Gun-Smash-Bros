package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ui.DisplayPanel;

public class ChatInput implements KeyListener{

    public static DisplayPanel displayPanel;
    public static int page = 0;

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused
      }
    
    @Override
    public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
        
      if (key == KeyEvent.VK_P) {
        System.out.println("he");
      }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {

    }

    public ChatInput(){
       
    }
}
