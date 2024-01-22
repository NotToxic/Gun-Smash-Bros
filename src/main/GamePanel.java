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
import player.ssmHandler;
import player.Player;
import ui.ChatPanel;
import ui.ConnectPanel;
import ui.DisplayPanel;
import ui.UIButton;
import guns.Bullet;
import guns.Crate;

// Comment
public class GamePanel extends JPanel{

  BufferedImage Background1 = null;

  public static DisplayPanel displayPanel;
  public Player player1 = new Player(300, 0, 45, 90, this);
  public Player player2 = new Player(800, 0, 45, 90, this);
  public static ssmHandler ssh;
  UIButton backButton;
  UIButton chatButton;
  BufferedReader map1CSV;
  String[][] strMap;

  public static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  // The crate has a 1/100000 to spawn
  public static ArrayList<Crate> crateList = new ArrayList<Crate>();

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 1280, 720);

    g2d.setColor(Color.BLACK);
    player1.move(strMap);
    player2.move(strMap);
    try{
      ssh.sendData();
      ssh.getGameData();
    } catch (NullPointerException e){}
    paintMap(strMap, g2d, Background1);
    g2d.fillRect(player1.x, player1.y, player1.width, player1.height);
    g2d.fillRect(player2.x, player2.y, player2.width, player2.height);

    //System.out.println("2: " + player2.getBulletList().size());
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

    if (crateList != null){
      if (crateList.size() == 0){
        if ((int)(Math.random() * 100) + 1 == 1){
          System.out.println("crate");
          // max - min
          int range = 950 - 250 + 1;
          // randomNum + min
          int x = (int)(Math.random() * range) + 250;
          // if random number rounds to 0, spawn a lightgun crate
          int gunType = (int)(Math.random() * 2) + 1;
          if (gunType == 1){
            Crate c = new Crate(strMap, "lightGuy", x);
            crateList.add(c);
            System.out.println("light");
          } else {
            Crate c = new Crate(strMap, "heavyGuy", x);
            crateList.add(c);
            System.out.println("heavy");
          }
        }
      } else {
        Crate c = (Crate)crateList.get(0);
        if (c.visible){
          c.move();
          g.setColor(Color.RED);
          g.fillRect(c.x, c.y, c.size, c.size);
        } else {
          crateList.remove(0);
        }
      }
    }

    if (player1.getDead()){
      player1.deathTimer -= 1;
      System.out.println(player1.deathTimer);
      if (player1.deathTimer == 0){
        player1.respawn();
      }
    }
    if (player2.getDead()){
      player2.deathTimer -= 1;
      System.out.println(player2.deathTimer);
      if (player2.deathTimer == 0){
        player2.respawn();
      }
    }

  }

  public GamePanel(DisplayPanel displayPanel) {
    
    this.displayPanel = displayPanel;
    backButton = new UIButton("BACK", "menu", displayPanel);
    chatButton = new UIButton("Chat","chat", displayPanel);

    setPreferredSize(new Dimension(1280, 720));

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
          requestFocusInWindow();
      }
    });

    SwingUtilities.invokeLater(() -> {
      addKeyListener(new ChatInput("chat", displayPanel));
      setFocusable(true);
      requestFocusInWindow();
      setLayout(null);

    });

    try{
      Background1 = ImageIO.read(new File("assets/maps/CPTMap1.png"));
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
    strMap = loadMap(ConnectPanel.strMapName);

  }

  public static String[][] loadMap(String strMapName){
    BufferedReader mapCSV;
    int intCount;
    int intCounter;
    String strLine;
    String[] strSplit;
    String[][] map = new String[90][160];


    try{
      mapCSV = new BufferedReader(new FileReader("Gun-Smash-Bros/assets/maps/" + strMapName));
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
      mapCSV = new BufferedReader(new FileReader("assets/maps/" + strMapName));
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

  public void playerControl(int playerNum) {
    if (playerNum == 1) {
      addKeyListener(new KeyInputs(player1));
      ssh = new ssmHandler(1, player1, this, displayPanel);
    }
    else if (playerNum == 2) {
      addKeyListener(new KeyInputs(player2));
      ssh = new ssmHandler(2, player2, this, displayPanel);
    }
  } 

  public void setPlayer(int playerNum){

  }

}

