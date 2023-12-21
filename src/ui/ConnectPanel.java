package ui;

import java.awt.*;
import javax.swing.*;

public class ConnectPanel extends JPanel{

    public DisplayPanel displayPanel;

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

        ipField.setSize(200,200);
        ipField.setLocation(200,300);

        portField.setSize(200,200);
        portField.setLocation(700,300);

        hostButton.setSize(200,200);
        hostButton.setLocation(200,500);

        joinButton.setSize(200,200);
        joinButton.setLocation(700,500);

        backButton.setSize(200,200);
        backButton.setLocation(50,50);

        add(ipField);
        add(portField);
        add(hostButton);
        add(joinButton);
        add(backButton);
    }
}
