package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import guns.Bullet;
import guns.Crate;
import inputs.KeyInputs;
import main.GamePanel;
import main.GunSmashBros;
import player.Player;
import player.ssmHandler;

public class TutorialPanel extends GamePanel{

  public static BufferedImage imgMapBackground = null;
  public static ssmHandler ssmh;
  UIButton backButton;
  public JLabel p1Lives = new JLabel("");
  public JLabel p2Lives = new JLabel("");
  public JTextArea chatArea = new JTextArea();
  public JScrollPane scrollArea = new JScrollPane(chatArea);
  public JTextField chatField = new JTextField();

  BufferedReader map1CSV;
  public String[][] strMap;
  public int image = 1;
  public String strMapName = null;
  public String strPreMapName = null;

  public TutorialPanel(DisplayPanel displayPanel) {
    super(displayPanel);
    addKeyListener(new KeyInputs(player1));
    //need another map later
    strMap = loadMap("TutorialMap");
    System.out.println(strMapName);
    System.out.println(strMap);
    System.out.println(displayPanel.gamePanel.strMap);
  }

  @Override
  public void actionPerformed(ActionEvent e){
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    g2d.setColor(Color.BLACK);


    player1.move(strMap);
    //player2.move(strMap);
    //player2.ihaveissues();



    paintMap(strMap, g2d, imgMapBackground);
    
    g.drawImage(player1.getCharModel(), player1.x, player1.y, null);
    g.drawImage(player1.gun.imgGun, player1.gun.getGunX(player1), player1.gun.getGunY(player1), null);

    g.drawImage(player2.getCharModel(), player2.x, player2.y, null);

    try{
      for (int i = 0; i < bulletList.size(); i++){
        Bullet b = (Bullet)bulletList.get(i);
        if (b.isVisible() == true){
          b.bulletMove();
          g.setColor(Color.RED);
          g.fillOval(b.getX(), b.getY(), b.getSize(), b.getSize());
        } else {
          bulletList.remove(i);
        }
      }
    } catch (NullPointerException e){}
    try{
      if (crateList.size() == 0){
        if ((int)(Math.random() * 100) + 1 == 1){
          // max - min
          int range = 950 - 250 + 1;
          // randomNum + min
          int x = (int)(Math.random() * range) + 250;
          Crate c = new Crate(strMap, "heavyGuy", x);
          crateList.add(c);
          System.out.println("heavy");
          ssmh.sendCrate(x, "heavyGuy");
        }
      } else if (crateList.size() == 1){
        Crate c = (Crate)crateList.get(0);
        if (c.visible){
          c.move();
          g.drawImage(c.imgCrate, c.x, c.y, null);
        } else {
          crateList.remove(0);
        }
      }
    } catch (NullPointerException e){}

    if (player1.getDead()){
      player1.respawn();
    }
    if (player2.getDead()){
      player2.respawn();
    }
  }
}

