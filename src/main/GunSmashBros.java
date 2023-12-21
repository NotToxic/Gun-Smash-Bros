package main;

import java.awt.event.*;

import javax.swing.*;

import ui.DisplayPanel;


public class GunSmashBros extends JFrame implements ActionListener {

  DisplayPanel displayPanel = new DisplayPanel();

  Timer gameTimer = new Timer(1000/60, this);

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == gameTimer) {
      displayPanel.repaint();
    }
  }

  public GunSmashBros() {
    setContentPane(displayPanel);
    
    pack();
    setTitle("heheh");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    gameTimer.start();
  }

  public static void main(String[] args) {
    new GunSmashBros();
  }
}
