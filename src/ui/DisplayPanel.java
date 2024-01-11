package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import main.GamePanel;
import ui.ChatPanel;

public class DisplayPanel extends JPanel{

    public GamePanel gamePanel = new GamePanel(this);
    public MenuPanel menuPanel = new MenuPanel(this);
    public ConnectPanel connectPanel = null;
    public ChatPanel chatPanel = new ChatPanel(this);

    CardLayout display = new CardLayout();

    public void changePanel(String path){
        display.show(DisplayPanel.this, path);
    }

    public DisplayPanel(ActionListener listener){

      connectPanel = new ConnectPanel(this, listener);

      setFocusable(true);
      requestFocusInWindow();
      setLayout(display);
      setPreferredSize(new Dimension(1280,720));

      add(menuPanel, "menu");
		  add(gamePanel, "game");
		  add(connectPanel, "connect");
      add(chatPanel, "chat");

      display.show(this, "menu");
    }
}
