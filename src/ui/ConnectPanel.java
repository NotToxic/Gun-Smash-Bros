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
    JLabel connectDisplay = new JLabel("Not Connected", JLabel.CENTER);
    public JTextField ipField = new JTextField();
    public JTextField portField = new JTextField();
    public JButton hostButton = new JButton("Host");
    public JButton joinButton = new JButton("Join");
    public JButton disconnectButton = new JButton("Disconnect");
    UIButton backButton;

    public void host(ActionListener listener, SuperSocketMaster ssm){
        ssm = new SuperSocketMaster(Integer.parseInt(portField.getText()), listener);
        ssm.connect();
        ipDisplay.setText("IP: " + ssm.getMyAddress());
        portDisplay.setText("Port: " + portField.getText());
        disconnectButton.setVisible(true);
        joinButton.setEnabled(false);
        hostButton.setEnabled(false);
    }
    public void connect(ActionListener listener, SuperSocketMaster ssm){
        ssm = new SuperSocketMaster(ipField.getText(), Integer.parseInt(portField.getText()), listener);
        ssm.connect();
        ipDisplay.setText("IP: " + ipField.getText());
        portDisplay.setText("Port: " + portField.getText());
        disconnectButton.setVisible(true);
        joinButton.setEnabled(false);
        hostButton.setEnabled(false);
    }
    public void disconnect(SuperSocketMaster ssm){
        ssm.disconnect();
        disconnectButton.setVisible(false);
        joinButton.setEnabled(true);
        hostButton.setEnabled(true);
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

        connectDisplay.setSize(900, 90);
        connectDisplay.setLocation(190, 365);

        hostButton.setSize(450,125);
        hostButton.setLocation(680,545);

        joinButton.setSize(450,125);
        joinButton.setLocation(100,545);

        backButton.setSize(200,50);
    	backButton.setLocation(0,0);

        disconnectButton.setSize(200, 50);
        disconnectButton.setLocation(1040,30);
        disconnectButton.setVisible(false);

        add(ipField);
        add(portField);
        add(ipLabel);
        add(portLabel);
        add(ipDisplay);
        add(portDisplay);
        add(connectDisplay);
        add(hostButton);
        add(joinButton);
        add(backButton);
        add(disconnectButton);

        hostButton.addActionListener(listener);
        joinButton.addActionListener(listener);
        disconnectButton.addActionListener(listener);
    }
}
