package ui;

import java.awt.*;
import javax.swing.*;

public class ConnectPanel extends JPanel{

    public DisplayPanel displayPanel;

    JLabel ipLabel = new JLabel("IP:");
    JLabel portLabel = new JLabel("Port:");
    JLabel ipDisplay = new JLabel("IP: Not Connected");
    JLabel portDisplay = new JLabel("Port: Not Connected");
    JTextField ipField = new JTextField();
    JTextField portField = new JTextField();
    JButton hostButton = new JButton("Host");
    JButton joinButton = new JButton("Join");
    UIButton backButton;

    public ConnectPanel(DisplayPanel displayPanel){
        this.displayPanel = displayPanel;
        backButton = new UIButton("Back", "menu", displayPanel);

        SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            requestFocusInWindow();
            setLayout(null);
        });

        setPreferredSize(new Dimension(1280,720));

        ipLabel.setSize(25,25);
        ipLabel.setLocation(315, 425);

        portLabel.setSize(50, 25);
        portLabel.setLocation(895, 425);

        ipField.setSize(450,90);
        ipField.setLocation(100,450);

        portField.setSize(450,90);
        portField.setLocation(680,450);
        
        ipDisplay.setSize(90, 900);
        ipDisplay.setLocation(190,275);

        portDisplay.setSize(90, 900);
        portDisplay.setLocation(190,370);

        hostButton.setSize(450,125);
        hostButton.setLocation(680,545);

        joinButton.setSize(450,125);
        joinButton.setLocation(100,545);

        backButton.setSize(200,50);
    	backButton.setLocation(0,0);

        add(ipField);
        add(portField);
        add(ipLabel);
        add(portLabel);
        add(ipDisplay);
        add(portDisplay);
        add(hostButton);
        add(joinButton);
        add(backButton);
    }
}
