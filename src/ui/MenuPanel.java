package ui;

import java.awt.Dimension;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{

    public DisplayPanel displayPanel;

    public UIButton playButton;
    public UIButton connectButton;
    public UIButton helpButton;

    public MenuPanel(DisplayPanel displayPanel){
        this.displayPanel = displayPanel;
        playButton = new UIButton("PLAY", "game", displayPanel);
        connectButton = new UIButton("CONNECT", "connect", displayPanel);
        helpButton = new UIButton("HELP", "help", displayPanel);

        setLayout(null);

        playButton.setSize(200, 100);
        playButton.setLocation(50, 300);
        playButton.setEnabled(false);

        connectButton.setSize(200, 100);
        connectButton.setLocation(50, 410);

        helpButton.setSize(200, 100);
        helpButton.setLocation(50, 520);

        add(playButton);
        add(connectButton);
        add(helpButton);
        setFocusable(true);
        requestFocusInWindow();
        setLayout(null);
        setPreferredSize(new Dimension(1280,720));
    }
}
