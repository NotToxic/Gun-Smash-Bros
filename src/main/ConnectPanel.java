package src.main;

import java.awt.*;
import javax.swing.*;

public class ConnectPanel extends JPanel{
    JTextField ipField = new JTextField();
    JTextField portField = new JTextField();
    JButton hostButton = new JButton();
    JButton joinButton = new JButton();

    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.drawRect(0,0,1280,720);
    }

    public ConnectPanel(){

        setPreferredSize(new Dimension(1280,720));

        ipField.setSize(200,200);
        ipField.setLocation(0,0);

        portField.setSize(200,200);
        portField.setLocation(0,200);

        hostButton.setSize(200,200);
        hostButton.setLocation(0,200);

        joinButton.setSize(200,200);
        joinButton.setLocation(200,200);

        ipField.add(this);
        portField.add(this);
        hostButton.add(this);
        joinButton.add(this);
    }
}
