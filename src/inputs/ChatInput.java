package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GunSmashBros;
import ui.DisplayPanel;

public class ChatInput implements KeyListener{

    private DisplayPanel displayPanel;
    private String path;
    public int chatState = 0;

    @Override
    // Unused
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
        
      if (key == KeyEvent.VK_ENTER) {
        if (chatState == 0) {
          displayPanel.gamePanel.scrollArea.setVisible(true);
          chatState = 1;
        } 
        else if (chatState == 2) {
          displayPanel.gamePanel.scrollArea.setVisible(false);
          chatState = 0;
        }
      }
    }
    
    @Override
    // Unused
    public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();
        
      if (key == KeyEvent.VK_ENTER) {
        if (chatState == 1) {
          chatState = 2;
        }
      }
    }

    public ChatInput(String path, DisplayPanel displayPanel){
       this.path = path;
       this.displayPanel = displayPanel;
    }
}
