package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import ssm.SuperSocketMaster;

public class ConnectPanel extends JPanel{

    public DisplayPanel displayPanel;

    JLabel ipLabel = new JLabel("IP:", JLabel.CENTER);
    JLabel portLabel = new JLabel("Port:", JLabel.CENTER);
    JLabel ipDisplay = new JLabel("IP: Not Connected", JLabel.CENTER);
    JLabel portDisplay = new JLabel("Port: Not Connected", JLabel.CENTER);
    public JTextField ipField = new JTextField();
    public JTextField portField = new JTextField();
    public JButton hostButton = new JButton("Host");
    public JButton joinButton = new JButton("Join");
    UIButton backButton;

    public void host(ActionListener listener, SuperSocketMaster ssm){
        ssm = new SuperSocketMaster(Integer.parseInt(portField.getText()), listener);
        ssm.connect();
        ipDisplay.setText("IP: " + ssm.getMyAddress());
        portDisplay.setText("Port: " + portField.getText());
    }
    public void connect(ActionListener listener, SuperSocketMaster ssm){
        ssm = new SuperSocketMaster(ipField.getText(), Integer.parseInt(portField.getText()), listener);
        ssm.connect();
        ipDisplay.setText("IP: " + ipField.getText());
        portDisplay.setText("Port: " + portField.getText());
    }

    public ConnectPanel(DisplayPanel displayPanel, ActionListener listener){
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
        
        ipDisplay.setSize(900, 90);
        ipDisplay.setLocation(190,175);

        portDisplay.setSize(900, 90);
        portDisplay.setLocation(190,270);

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

        hostButton.addActionListener(listener);
        joinButton.addActionListener(listener);
    }
}
