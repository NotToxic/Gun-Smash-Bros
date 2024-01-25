package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GunSmashBros;
import ui.DisplayPanel;
import ui.GraphicsPanel;

/**Class To Contain Componets for ChatField usage */
public class ChatInput implements KeyListener{

    private GraphicsPanel graphicsPanel;
    public int chatState = 0;

    /**Key Inputs While On Display/Game Panel to use Chat Feature */
    @Override
    // Unused
    public void keyTyped(KeyEvent e) {}
    
    @Override
    public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
      //Opening of Chat Field
      if (key == KeyEvent.VK_ENTER) {
        if (chatState == 0) {
          graphicsPanel.scrollArea.setVisible(true);
          graphicsPanel.chatField.setVisible(true);
          graphicsPanel.chatField.requestFocus();
          chatState = 1;
        } 
      }
    }
    
    @Override
    // Unused
    public void keyReleased(KeyEvent e) {
      int key = e.getKeyCode();    
      if (key == KeyEvent.VK_ENTER) {
        if (chatState == 1) {
          chatState = 0;
        }
      }
    }

    public ChatInput(GraphicsPanel graphicsPanel){
       this.graphicsPanel = graphicsPanel;
    }
}
