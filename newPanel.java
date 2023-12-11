import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class newPanel extends JPanel{
    // Properties

    // Methods
    public void paintComponent(Graphics g){
        g.setColor(Color.RED);

        g.fillRect(500, 900, 100, 100);
    }

}
