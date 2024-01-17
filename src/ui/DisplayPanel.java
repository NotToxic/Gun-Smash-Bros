package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import inputs.ChatInput;
import main.GamePanel;
import ssm.SuperSocketMaster;
import ui.ChatPanel;

public class DisplayPanel extends JPanel{

    SuperSocketMaster ssm;

    public GamePanel gamePanel = new GamePanel(this, ssm);
    public MenuPanel menuPanel = new MenuPanel(this);
    public ConnectPanel connectPanel = null;
    public ChatPanel chatPanel = new ChatPanel(this);

    public CardLayout display = new CardLayout();

    public void changePanel(String path){
        display.show(DisplayPanel.this, path);
    }

    public DisplayPanel(ActionListener listener, SuperSocketMaster ssm){
      this.ssm = ssm;

      connectPanel = new ConnectPanel(this, listener);

      setFocusable(true);
      requestFocusInWindow();
      setLayout(display);
      addKeyListener(new ChatInput("game", this));
      setPreferredSize(new Dimension(1280,720));

      add(menuPanel, "menu");
		  add(gamePanel, "game");
		  add(connectPanel, "connect");
      add(chatPanel, "chat");

      display.show(this, "menu");
    }
    /* 
    public void openChat(){ 
      if(ChatInput.page == 1){
          display.show(chatPanel, "chat");
          ChatInput.page = 1;
          System.out.println("done");
      }else if(ChatInput.page == 1){
          display.show(gamePanel,"game");
      }
    }
    */
    
    
}
