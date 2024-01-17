package main;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

import inputs.ChatInput;
import inputs.KeyInputs;
import player.Player;
import ui.ChatPanel;
import ui.DisplayPanel;
import ui.UIButton;
import guns.Bullet;

// Comment
public class GamePanel extends JPanel{
  BufferedImage Background1 = null;

  public static DisplayPanel displayPanel;
  Player player = new Player(300, 500, 45, 90, this);
  UIButton backButton;
  UIButton chatButton;
  BufferedReader map1CSV;
  String[][] strMap;

  public static ArrayList bulletList;

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 1280, 720);

    g2d.setColor(Color.BLACK);
    player.move(strMap);
    paintMap(strMap, g2d, Background1);
    g2d.fillRect(player.x, player.y, player.width, player.height);

    bulletList = player.getBulletList();
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
    chatButton = new UIButton("Chat","chat",displayPanel);

    setPreferredSize(new Dimension(1280, 720));

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
          requestFocusInWindow();
      }
    });

    SwingUtilities.invokeLater(() -> {
      addKeyListener(new ChatInput());
      addKeyListener(new KeyInputs(player));
      setFocusable(true);
      requestFocusInWindow();
      setLayout(null);

    });

    try{
      Background1 = ImageIO.read(new File("Gun-Smash-Bros/src/CPTMap1.png"));
    }catch (IOException e){
    }
    
    if(Background1 == null){
      System.out.println("Cant fine image");
    }


    backButton.setSize(100,50);
    backButton.setLocation(0,0);
    add(backButton);
    chatButton.setSize(100,50);
    chatButton.setLocation(1180,670);
    add(chatButton, "chat");
    strMap = loadMap("CPTMap1Rev.csv");
  }

  public static String[][] loadMap(String strMapName){
    BufferedReader mapCSV;
    int intCount;
    int intCounter;
    String strLine;
    String[] strSplit;
    String[][] map = new String[90][160];


    try{
      mapCSV = new BufferedReader(new FileReader("Gun-Smash-Bros/src/" + strMapName));
      for(intCount = 0; intCount < 90; intCount++){
        strLine = mapCSV.readLine();
        strSplit = strLine.split(",");

        for(intCounter = 0; intCounter < 160; intCounter++){
          map[intCount][intCounter] = strSplit[intCounter];
        }

      }

      mapCSV.close();

    }catch(FileNotFoundException e){
      System.out.println("Mac file not found");
    }catch(IOException e){
      System.out.println("error");
    }

    try{
      mapCSV = new BufferedReader(new FileReader("src/" + strMapName));
      for(intCount = 0; intCount < 90; intCount++){
        strLine = mapCSV.readLine();
        strSplit = strLine.split(",");

        for(intCounter = 0; intCounter < 160; intCounter++){
          map[intCount][intCounter] = strSplit[intCounter];
        }

      }

      mapCSV.close();

    }catch(FileNotFoundException e){
      System.out.println("Windows file not found");
    }catch(IOException e){
      System.out.println("error");
    }

    System.out.println(map[0][0]);
    return map;
  }
  public void paintMap(String[][] strMap, Graphics2D g2D, BufferedImage Background1){

    g2D.drawImage(Background1, 0, 0, null);

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

