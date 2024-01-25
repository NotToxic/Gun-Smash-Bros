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

/** The gamePanel class is used for the main gaim where you fight another player */
public class GamePanel extends GraphicsPanel implements ActionListener {

  /**The client's controlled character */
  public Player player2 = new Player(800, 0, 45, 90, this);

  /**Back button to access the main menu */
  UIButton backButton;
  /**A label displaying how many lives the host player has */
  public JLabel p1Lives = new JLabel("Host: " + player1.lives + " lives");
  /**A label displaying how many lives the client player has */
  public JLabel p2Lives = new JLabel("Client: " + player2.lives + " lives");

  /**The map name */
  public String strMapName = "CPTMap1.csv";
  /**A variable keeping track of the previous map */
  public String strPreMapName = "CPTMap1.csv";

  /**The font of the lives labels */
  Font labelFont;
  /**How long the lives labels are */
  int stringWidth;
  int componentWidth;

  /**A method used for chatting
   * @param e whenever a chat is sent, or a deathTimer goes off
   */
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

  /**paintComponent method: Controls drawing of all background game map images, along with platforms 
   * @param g for drawing images
  */
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

    //Data Transfer
    try{
      ssmh.sendData();
      ssmh.getGameData();
    } catch (NullPointerException e){}

    checkMapChange();
    paintMap(strArrayMap, g2d, imgMap);

    //Drawing of Player characters and respective character components - Changes at constant movement/variable changes
    drawPlayer(g2d, player1);
    drawGun(g2d, player1);

    drawPlayer(g2d, player2);
    drawGun(g2d, player2);

    //Draw the bullets and spawn the crates if possible
    drawBullets(g2d);
    spawnCrates();
    drawCrates(g2d);

    //Player death functionality; Testing timers and components to outline winner
    // If a player's deathTimer is not running and they are out of the map, deem them as dead
    if (player1.getDead() && player1.deathTimer.isRunning() == false){
      playerDeath(player1);
    }
    if (player2.getDead() && player2.deathTimer.isRunning() == false){
      playerDeath(player2);
    }
    // Accessing the victory screen
    if (player1.lives == 0){
      displayPanel.winPanel.victory("Client");
    }
    if (player2.lives == 0){
      displayPanel.winPanel.victory("Host");
    }

  }
  /**Main Game Panel Constructor 
   * @param displayPanel links the GamePanel to the network of other JPanels
  */
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

  /**WHenever a player falls off the map
   * @param player accessing the player deathTimers and subtracting their lives
  */
  public void playerDeath(Player player){
    player.deathTimer.start();
    player.lives--;
    System.out.println(player.lives);
  }

  /**Occurs after a player's death timer has finished
   * @param player respawning them and stopping their death timers
   */
  public void playerRespawn(Player player){
    player.respawn();
    player.deathTimer.stop();
  }

  /**Set the font size of the lives labels
   * @param label reads the label for the font and then enlarges the font
   */
  public void setFontSize(JLabel label){
    labelFont = label.getFont();

    stringWidth = label.getFontMetrics(labelFont).stringWidth(label.getText());
    componentWidth = label.getWidth();

    // Set the label's font size to the newly determined size.
    label.setFont(new Font(labelFont.getName(), Font.PLAIN, 25));
  }
}

