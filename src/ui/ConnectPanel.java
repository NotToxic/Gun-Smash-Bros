package ui;

import java.awt.*;
import javax.swing.*;

import main.GunSmashBros;

import java.awt.event.ActionEvent;
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
    public UIButton hostButton = new UIButton("Host");
    public UIButton joinButton = new UIButton("Join");
    public UIButton disconnectButton = new UIButton("Disconnect");
    UIButton backButton;
    UIButton gameButton1 = new UIButton("Map 1: Fight In The Sky");
    UIButton gameButton2 = new UIButton("Map 2: Fight At Our School!");
    public static String strMapName = null;

    public void host(ActionListener listener){
        GunSmashBros.ssm = new SuperSocketMaster(Integer.parseInt(portField.getText()), listener);
        GunSmashBros.ssm.connect();
        ipDisplay.setText("IP: " + GunSmashBros.ssm.getMyAddress());
        portDisplay.setText("Port: " + portField.getText());
        connectDisplay.setText("Hosting");
        disconnectButton.setVisible(true);
        joinButton.setEnabled(false);
        hostButton.setEnabled(false);
        displayPanel.menuPanel.playButton.setEnabled(true);
    }
    public void connect(ActionListener listener){
        GunSmashBros.ssm = new SuperSocketMaster(ipField.getText(), Integer.parseInt(portField.getText()), listener);
        GunSmashBros.ssm.connect();
        ipDisplay.setText("IP: " + ipField.getText());
        portDisplay.setText("Port: " + portField.getText());
        connectDisplay.setText("Connected");
        disconnectButton.setVisible(true);
        joinButton.setEnabled(false);
        hostButton.setEnabled(false);
        displayPanel.menuPanel.playButton.setEnabled(true);
    }
    public void disconnect(){
        
        GunSmashBros.ssm.disconnect();
        disconnectButton.setVisible(false);
        joinButton.setEnabled(true);
        hostButton.setEnabled(true);
        displayPanel.menuPanel.playButton.setEnabled(false);
    }

    public void mapChosen(ActionListener listener){
        if(listener == gameButton1){
            strMapName = "CPTMap1.csv";
            System.out.println(strMapName);
        }else if(listener == gameButton2){
            strMapName = "CPTMap2.csv";
            System.out.println(strMapName);
        }
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
        gameButton2.setSize(200, 100);
        gameButton2.setLocation(800,0);

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

        gameButton1.addActionListener(listener);
        gameButton2.addActionListener(listener);
        hostButton.addActionListener(listener);
        joinButton.addActionListener(listener);
        disconnectButton.addActionListener(listener);
    }
}
