import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class newPanel extends JPanel{
    // Properties
    int intChar1X = 0;
    int intChar1Y = 630;

    // Methods
    public void paintComponent(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,1280,720);

        intChar1Y += 2;

        if (intChar1Y <= 0){
            intChar1Y = 0;
        }
        if (intChar1Y >= 630){
            intChar1Y = 630;
        }
        if (intChar1X >= 720){
            intChar1X = 720;
        }
        if (intChar1X <= 0){
            intChar1X = 0;
        }

        g.setColor(Color.RED);
        g.fillRect(intChar1X, intChar1Y, 50, 90);
    }

}
