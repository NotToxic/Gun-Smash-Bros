package main;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import inputs.KeyInputs;
import player.Player;
import ui.DisplayPanel;
import ui.UIButton;
import guns.Bullet;

// Comment
public class GamePanel extends JPanel{

  DisplayPanel displayPanel;
  Player player = new Player(300, 500, 45, 90, this);
  UIButton backButton;
  UIButton chatButton;
  BufferedReader map1CSV;
  String[][] strMap;

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 1280, 720);

    g2d.setColor(Color.BLACK);
    player.move(strMap);
    paintMap(strMap, g2d);
    g2d.fillRect(player.x, player.y, player.width, player.height);

    ArrayList bullets = player.getBulletList();
    for (int i = 0; i < bullets.size(); i++){
      Bullet b = (Bullet)bullets.get(i);
      if (b.isVisible() == true){
        b.bulletMove();
        g.setColor(Color.RED);
        g.fillOval(b.getX(), b.getY(), 10, 10);
      } else {
        bullets.remove(i);
      }
    }

    if (player.getDead()){
      player.deathTimer -= 1;
      System.out.println(player.deathTimer);
      if (player.deathTimer == 0){
        player.respawn();
      }
    }
  }

  public GamePanel(DisplayPanel displayPanel) {

    this.displayPanel = displayPanel;
    backButton = new UIButton("BACK", "menu", displayPanel);
    //chatButton = new UIButton("Chat","",)

    setPreferredSize(new Dimension(1280, 720));

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
          requestFocusInWindow();
      }
    });

    SwingUtilities.invokeLater(() -> {
      addKeyListener(new KeyInputs(player));
      setFocusable(true);
      requestFocusInWindow();
      setLayout(null);
    });
    backButton.setSize(200,200);
    backButton.setLocation(100,100);
    add(backButton);
    strMap = loadMap("Gun-Smash-Bros/src/CPTMap1Rev.csv");
  }

  public static String[][] loadMap(String strMapName){
    BufferedReader mapCSV;
    int intCount;
    int intCounter;
    String strLine;
    String[] strSplit;
    String[][] map = new String[90][160];


    try{
      mapCSV = new BufferedReader(new FileReader(strMapName));
      for(intCount = 0; intCount < 90; intCount++){
        strLine = mapCSV.readLine();
        strSplit = strLine.split(",");

        for(intCounter = 0; intCounter < 160; intCounter++){
          map[intCount][intCounter] = strSplit[intCounter];
        }

      }

      mapCSV.close();

    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }catch(IOException e){
      System.out.println("error");
    }
    System.out.println(map[0][0]);
    return map;
  }
  public void paintMap(String[][] strMap, Graphics2D g2D){

    g2D.setColor(Color.BLACK);
    int intCount;
    int intCounter;
   // int intX = 0;
   // int intY = 0;
    for(intCount = 0; intCount < 90; intCount++){
      //intX = 0;
      
      for(intCounter = 0; intCounter < 160; intCounter++){
          if(strMap[intCount][intCounter].equals("s")){
            
          }else if(strMap[intCount][intCounter].equals("p")){
            g2D.fillRect(intCounter * 8, intCount * 8, 8, 8);
          }
         // intX = intX + 8;
        }
        //intY = intY + 8;
    }


  }

}

