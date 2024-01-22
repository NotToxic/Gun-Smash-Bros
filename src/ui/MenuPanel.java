package ui;

import java.awt.Dimension;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{

    public DisplayPanel displayPanel;

    public UIButton playButton;
    public UIButton connectButton;

    public MenuPanel(DisplayPanel displayPanel){
        this.displayPanel = displayPanel;
        playButton = new UIButton("PLAY", "game", displayPanel);
        connectButton = new UIButton("CONNECT", "connect", displayPanel);

        setLayout(null);

        playButton.setSize(200, 100);
        playButton.setLocation(50, 300);
        playButton.setEnabled(false);

        connectButton.setSize(200, 100);
        connectButton.setLocation(50, 420);

        add(playButton);
        add(connectButton);
        setFocusable(true);
        requestFocusInWindow();
        setLayout(null);
        setPreferredSize(new Dimension(1280,720));
    }
}
