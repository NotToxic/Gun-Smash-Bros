import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class newPanel extends JPanel{
    // Properties
    character char1 = new character(0, 630);
    
    // Methods
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,1280,720);

        g.setColor(Color.RED);
        char1.movement();
        char1.outOfBounds();
        g.fillRect(char1.intPosX, char1.intPosY, 50, 90);
    }

}
