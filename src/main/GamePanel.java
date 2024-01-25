package main;

/**Neccesary Imports*/

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

import inputs.ChatInput;
import player.ssmHandler;
import player.Player;
import ui.DisplayPanel;
import ui.GraphicsPanel;
import ui.UIButton;
import guns.Bullet;
import guns.Crate;

// Comment
public class GamePanel extends GraphicsPanel implements ActionListener {

  public Player player2 = new Player(800, 0, 45, 90, this);

  UIButton backButton;
  public JLabel p1Lives = new JLabel("Host: " + player1.lives + " lives");
  public JLabel p2Lives = new JLabel("Client: " + player2.lives + " lives");

  public String strMapName = "CPTMap1.csv";
  public String strPreMapName = "CPTMap1.csv";

  Font labelFont;
  int stringWidth;
  int componentWidth;
  double widthRatio;
  int newFontSize;
  int componentHeight;
  int fontSizeToUse;

  public void actionPerformed(ActionEvent e){
		if(e.getSource() == chatField){
			ssmh.sendMsg(ssmh.playerID, chatField.getText());
			if (!chatField.getText().equals("")){
				chatArea.append("You: " + chatField.getText() + "\n");
			}
			chatField.setText("");
      scrollArea.setVisible(false);
      chatField.setVisible(false);
      requestFocus();
		} else if (e.getSource() == player1.deathTimer){
      playerRespawn(player1);
    } else if (e.getSource() == player2.deathTimer){
      playerRespawn(player2);
    }
  }

  /**paintComponent method: Controls drawing of all background game map images, along with platforms */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 1280, 720);

    g2d.setColor(Color.BLACK);

    p1Lives.setText("Host :" + player1.lives + " lives");
    p2Lives.setText("Client :" + player2.lives + " lives");


    player1.move(strArrayMap);
    player2.move(strArrayMap);

    /**Data Transfer */
    try{
      ssmh.sendData();
      ssmh.getGameData();
    } catch (NullPointerException e){}

    checkMapChange();
    paintMap(strArrayMap, g2d, imgMap);
    
    //g2d.fillRect(player1.x, player1.y, player1.width, player1.height); 
    //g2d.fillRect(player2.x, player2.y, player2.width, player2.height);

    /**Drawing of Player characters and respective character components - Changes at constant movement/variable changes */
    drawPlayer(g2d, player1);
    drawGun(g2d, player1);

    drawPlayer(g2d, player2);
    drawGun(g2d, player2);

    drawBullets(g2d);
    spawnCrates();
    drawCrates(g2d);

    /**Player death functionality; Testing timers and components to outline winner */
    if (player1.getDead() && player1.deathTimer.isRunning() == false){
      playerDeath(player1);
    }
    if (player2.getDead() && player2.deathTimer.isRunning() == false){
      playerDeath(player2);
    }
    if (player1.lives == 0){
      displayPanel.winPanel.victory("Client");
    }
    if (player2.lives == 0){
      displayPanel.winPanel.victory("Host");
    }

  }
  /**Main Game Panel Constructor */
  public GamePanel(DisplayPanel displayPanel) {
    
    super(displayPanel);

    p1Lives.setSize(200, 100);
    p1Lives.setLocation(400, 0);
    setFontSize(p1Lives);
  
    p2Lives.setSize(200, 100);
    p2Lives.setLocation(800, 0);
    setFontSize(p2Lives);

    add(p1Lives);
    add(p2Lives);

    loadMap("CPTMap1");
  }

  /**Map Change and Map Selection Method, Based on Connect Panel button selection, specific map CSV and PNG is selected */
  public void checkMapChange(){
    if (!strPreMapName.equals(strMapName)){
      System.out.println("Check 1");
      if (strMapName.equals("CPTMap1")){
        try {
          imgMap = ImageIO.read(new File("assets/maps/CPTMap1.png"));
          System.out.println("Check 2");
        } catch (IOException ex) {}
      } else {
        try {
          imgMap= ImageIO.read(new File("assets/maps/CPTMap2.png"));
          System.out.println("Check 2");
        } catch (IOException ex) {}
      }
      loadMap(strMapName);
      strPreMapName = strMapName;
    }
  }

  /**Player Death and Respawn Methods */
  public void playerDeath(Player player){
    player.deathTimer.start();
    player.lives--;
    System.out.println(player.lives);
  }

  public void playerRespawn(Player player){
    player.respawn();
    player.deathTimer.stop();
  }

  public void setFontSize(JLabel label){
    labelFont = label.getFont();

    stringWidth = label.getFontMetrics(labelFont).stringWidth(label.getText());
    componentWidth = label.getWidth();

    // Set the label's font size to the newly determined size.
    label.setFont(new Font(labelFont.getName(), Font.PLAIN, 25));
  }
}

