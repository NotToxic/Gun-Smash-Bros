package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import ui.ChatPanel;
import ui.DisplayPanel;

public class ChatInput implements KeyListener{

    public static DisplayPanel displayPanel;
    public ChatPanel chatPanel = new ChatPanel(displayPanel);

    public void keyTyped(KeyEvent e) {
        // Unused
      }
    
      @Override
      public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_P){
            displayPanel.display.show(chatPanel, "chat");
        }
        
      }
    
      @Override
      public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
      }
}
