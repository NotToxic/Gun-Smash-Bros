package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;
import ui.ChatPanel;
import ui.DisplayPanel;

public class ChatInput implements KeyListener{

    public static DisplayPanel displayPanel;
    public ChatPanel chatPanel = new ChatPanel(displayPanel);
    public GamePanel gamePanel = new GamePanel(displayPanel);
    public static int page = 0;

    public void keyTyped(KeyEvent e) {
        // Unused
      }
    
      @Override
      public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_P){
          System.out.println("Key pressed");
          page = 1;
            //if(page = true){
             //   displayPanel.display.show(chatPanel, "chat");
            //    page = false;
           //     System.out.println("p pressed");
           // }else if(page = false){
           //     displayPanel.display.show(gamePanel, "game");
           //     System.out.println("p pressed");
           //     page = true;

           // }
        }
        
      }
    
      @Override
      public void keyReleased(KeyEvent e) {

      }
}
