package src.main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class StartPanel extends JPanel implements ActionListener{
    
    JButton connect = new JButton("Connect");
    JButton play = new JButton("Play");

    public void actionPerformed(ActionEvent e){
    
    }

    public StartPanel(){
        setPreferredSize(new Dimension(1280,720));

        play.setSize(200,200);
        play.setLocation(750,250);
        play.addActionListener(this);

        connect.setSize(200,200);
        connect.setLocation(750, 475);
        connect.addActionListener(this);

        add(play);
        add(connect);
    }
}
