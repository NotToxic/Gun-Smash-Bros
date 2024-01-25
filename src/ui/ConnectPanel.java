package ui;

import java.awt.*;
import javax.swing.*;

import main.GunSmashBros;

import java.awt.event.ActionListener;

/**JPanel class that displays screen for map selection and socket connection*/
public class ConnectPanel extends JPanel{

    /**DisplayPanel to link up with all the other JPanels*/
    public DisplayPanel displayPanel;

    /**JLabel to label text for the IP address*/
    JLabel ipLabel = new JLabel("IP:", JLabel.CENTER);
    /**JLabel to label text the port number*/
    JLabel portLabel = new JLabel("Port:", JLabel.CENTER);
    /**JLabel to show the host IP address*/
    JLabel ipDisplay = new JLabel("IP: Not Connected", JLabel.CENTER);
    /**JLabel to show the port number*/
    JLabel portDisplay = new JLabel("Port: Not Connected", JLabel.CENTER);
    /**JLabel that shows if ssm is connected*/
    JLabel connectDisplay = new JLabel("Not Connected", JLabel.CENTER);
    
    /**JTextField for entering IP address*/
    public JTextField ipField = new JTextField();
    /**JTextField for entering port number*/
    public JTextField portField = new JTextField();

    /**UIButton to start hosting server*/
    public UIButton hostButton = new UIButton("Host");
    /**UIButton to join server*/
    public UIButton joinButton = new UIButton("Join");
    /**UIButton to disconnect*/
    public UIButton disconnectButton = new UIButton("Disconnect");

    /**UIButton to go back to main menu*/
    UIButton backButton;

    /**UIButton to select map 1*/
    public UIButton gameButton1 = new UIButton("Map 1: Fight In The Sky");
    /**UIButton to select map 2*/
    public UIButton gameButton2 = new UIButton("Map 2: Fight At Our School!");

    /**Host Mode 
     * Disables join, host buttons
     * Allows you to disconnect
     * Allows you to start playing*/
    public void hostMode() {
        ipDisplay.setText("IP: " + GunSmashBros.ssm.getMyAddress());
        portDisplay.setText("Port: " + portField.getText());
        connectDisplay.setText("Hosting");
        disconnectButton.setVisible(true);
        joinButton.setEnabled(false);
        hostButton.setEnabled(false);
        displayPanel.menuPanel.playButton.setEnabled(true);
    }

    /**Client Mode 
     * Disables join, host buttons
     * Allows you to disconnect
     * Allows you to start playing */
    public void cilentMode() {
        ipDisplay.setText("IP: " + ipField.getText());
        portDisplay.setText("Port: " + portField.getText());
        connectDisplay.setText("Connected");
        disconnectButton.setVisible(true);
        joinButton.setEnabled(false);
        hostButton.setEnabled(false);
        displayPanel.menuPanel.playButton.setEnabled(true);
    }

    /**Allows you to join and host again; disables playing*/
    public void disconnect() {
        connectDisplay.setText("Not Connected");
        disconnectButton.setVisible(false);
        joinButton.setEnabled(true);
        hostButton.setEnabled(true);
        displayPanel.menuPanel.playButton.setEnabled(false);
    }

    /**Constructor for ConnectPanel
     * @param displayPanel adds ConnectPanel to the chain of other JPanels linked to displayPanel
     * @param listener listens for actions, used on the buttons
    */
    public ConnectPanel(DisplayPanel displayPanel, ActionListener listener){
        this.displayPanel = displayPanel;

        /**Set size of the panel*/
        setPreferredSize(new Dimension(1280,720));

        backButton = new UIButton("Back", "menu", displayPanel);

        /**Set size and location of JComponents*/
        ipLabel.setSize(25,25);
        ipLabel.setLocation(315, 425);

        portLabel.setSize(50, 25);
        portLabel.setLocation(895, 425);

        ipField.setSize(450,90);
        ipField.setLocation(100,450);

        portField.setSize(450,90);
        portField.setLocation(680,450);
        
        ipDisplay.setSize(900, 90);
        ipDisplay.setLocation(190,160);

        portDisplay.setSize(900, 90);
        portDisplay.setLocation(190,230);

        connectDisplay.setSize(900, 90);
        connectDisplay.setLocation(190, 300);

        hostButton.setSize(450,125);
        hostButton.setLocation(680,545);

        joinButton.setSize(450,125);
        joinButton.setLocation(100,545);

        backButton.setSize(200,50);
    	backButton.setLocation(0,0);

        disconnectButton.setSize(200, 50);
        disconnectButton.setLocation(1040,30);
        disconnectButton.setVisible(false);

        gameButton1.setSize(200, 100);
        gameButton1.setLocation(500,0);
        gameButton1.setEnabled(false);

        gameButton2.setSize(200, 100);
        gameButton2.setLocation(800,0);
        gameButton2.setEnabled(false);

        /**Add all JComponents to panel*/
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
        add(gameButton1);
        add(gameButton2);

        /**Add action listeners to know when buttons are pressed*/
        gameButton1.addActionListener(listener);
        gameButton2.addActionListener(listener);
        hostButton.addActionListener(listener);
        joinButton.addActionListener(listener);
        disconnectButton.addActionListener(listener);

        SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            requestFocusInWindow();
            setLayout(null);
        });
    }
}
