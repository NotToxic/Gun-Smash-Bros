package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartPanel extends JPanel implements ActionListener{
    
    JButton connectButton = new JButton("Connect");
    JButton playButton = new JButton("Play");

    // comemen
    public void actionPerformed(ActionEvent e){
    
    }

    public StartPanel(){
        SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            requestFocusInWindow();
            setLayout(null);
        });
        setPreferredSize(new Dimension(1280,720));

        playButton.setSize(200,200);
        playButton.setLocation(750,250);

        connectButton.setSize(200,200);
        connectButton.setLocation(750, 475);

        add(playButton);
        add(connectButton);
    }
}
