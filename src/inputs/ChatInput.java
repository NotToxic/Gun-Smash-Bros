package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GunSmashBros;
import ui.DisplayPanel;

public class ChatInput implements KeyListener{

    private DisplayPanel displayPanel;
    private String path;

    @Override
    // Unused
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
        
      if (key == KeyEvent.VK_ENTER) {
        displayPanel.gamePanel.scrollArea.setVisible(true);
      }
    }
    
    @Override
    // Unused
    public void keyReleased(KeyEvent e) {}

    public ChatInput(String path, DisplayPanel displayPanel){
       this.path = path;
       this.displayPanel = displayPanel;
    }
}
