package ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import inputs.ChatInput;
import main.GamePanel;
import ssm.SuperSocketMaster;

/**The class that links all JPanels together in a network for seamless change between panels */
public class DisplayPanel extends JPanel{

  /**SuperSocketMaster for network connection */
  SuperSocketMaster ssm;
  /**The GamePanel for the main game */
  public GamePanel gamePanel = new GamePanel(this);
  /**The MenuPanel, used in the main menu */
  public MenuPanel menuPanel = new MenuPanel(this);
  /**The HelpPanel, used in the helpScreen */
  public HelpPanel helpPanel = new HelpPanel(this);
  /**The TutorialPanel, used in the tutorial */
  public TutorialPanel tutorialPanel = new TutorialPanel(this);
  /**The ConnectPanel, used in the connect screen, null because we need to construct with an actionListener */
  public ConnectPanel connectPanel = null;
  /**The WinPanel, used in the win screen, null because we need to construct an actionListener */
  public WinPanel winPanel = null;

  /**Layered Panel Layout, sets the layout of all JPanels*/
  public CardLayout display = new CardLayout();

  /**A method that changes panel being displayed by DisplayPanel
   * @param path which panel the method switches to
   */
  public void changePanel(String path){
      display.show(DisplayPanel.this, path);
  } 

  /**Adding All Listening and Encapsulated Components i.e Panels - Constructor
   * @param listener for any actionListeners required to construct the connect and win panels
   * @param ssm for any network connections
  */
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
