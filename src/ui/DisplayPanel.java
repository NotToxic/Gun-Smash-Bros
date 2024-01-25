package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import inputs.ChatInput;
import main.GamePanel;
import ssm.SuperSocketMaster;

public class DisplayPanel extends JPanel{

  //Accessible Panels 
  SuperSocketMaster ssm;
  public GamePanel gamePanel = new GamePanel(this);
  public MenuPanel menuPanel = new MenuPanel(this);
  public HelpPanel helpPanel = new HelpPanel(this);
  public TutorialPanel tutorialPanel = new TutorialPanel(this);
  public ConnectPanel connectPanel = null;
  public WinPanel winPanel = null;

  //Layered Panel Layout
  public CardLayout display = new CardLayout();

  public void changePanel(String path){
      display.show(DisplayPanel.this, path);
  } 

  //Adding All Lisenting and Encapsulated Componets i.e Panels
  public DisplayPanel(ActionListener listener, SuperSocketMaster ssm){
    this.ssm = ssm;
    
    connectPanel = new ConnectPanel(this, listener);
    winPanel = new WinPanel(this, listener);

    setFocusable(true);
    requestFocusInWindow();
    setLayout(display);
    setPreferredSize(new Dimension(1280,720));

    add(menuPanel, "menu");
    add(gamePanel, "game");
    add(connectPanel, "connect");
    add(helpPanel, "help");
    add(tutorialPanel, "tutorial");
    add(winPanel, "win");

    display.show(this, "menu");
  }
  
}
