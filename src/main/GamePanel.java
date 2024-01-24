package main;

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
import ui.UIButton;
import guns.Bullet;
import guns.Crate;

// Comment
public class GamePanel extends JPanel implements ActionListener {

  public static BufferedImage imgMapBackground = null;

  public static DisplayPanel displayPanel;
  public Player player1 = new Player(300, 0, 45, 90, this);
  public Player player2 = new Player(800, 0, 45, 90, this);
  public static ssmHandler ssmh;
  UIButton backButton;
  UIButton chatButton;
  public JLabel p1Lives = new JLabel("Host: " + player1.lives + " lives");
  public JLabel p2Lives = new JLabel("Client: " + player2.lives + " lives");
  public JTextArea chatArea = new JTextArea();
  public JScrollPane scrollArea = new JScrollPane(chatArea);
  public JTextField chatField = new JTextField();

  BufferedReader map1CSV;
  public String[][] strMap;
  public int image = 1;
  public String strMapName = "CPTMap1.csv";
  public String strPreMapName = "CPTMap1.csv";

  public static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  // The crate has a 1/100000 to spawn
  public static ArrayList<Crate> crateList = new ArrayList<Crate>();
  public Crate c;

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
		}
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 1280, 720);

    g2d.setColor(Color.BLACK);

    p1Lives.setText("Host :" + player1.lives + " lives");
    p1Lives.setText("Client :" + player2.lives + " lives");


    player1.move(strMap);
    player2.move(strMap);

    try{
      ssmh.sendData();
      ssmh.getGameData();
    } catch (NullPointerException e){}

    checkMapChange();
    paintMap(strMap, g2d, imgMapBackground);
    
    //g2d.fillRect(player1.x, player1.y, player1.width, player1.height); 
    //g2d.fillRect(player2.x, player2.y, player2.width, player2.height);

    g.drawImage(player1.getCharModel(), player1.x, player1.y, null);
    g.drawImage(player1.gun.imgGun, player1.gun.getGunX(player1), player1.gun.getGunY(player1), null);

    g.drawImage(player2.getCharModel(), player2.x, player2.y, null);
    g.drawImage(player2.gun.imgGun, player2.gun.getGunX(player2), player2.gun.getGunY(player2), null);

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
      if (crateList.size() == 0 && ssmh.playerID == 1){
        if ((int)(Math.random() * 100) + 1 == 1){
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
            ssmh.sendCrate(x, "lightGuy");
          } else {
            Crate c = new Crate(strMap, "heavyGuy", x);
            crateList.add(c);
            System.out.println("heavy");
            ssmh.sendCrate(x, "heavyGuy");
          }
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
      player1.deathTimer -= 1;
      System.out.println(player1.deathTimer);
      if (player1.lives == 1){
        player1.respawn();
        displayPanel.winPanel.victory("Client");
      }
      if (player1.deathTimer == 0){
        if (displayPanel.gamePanel.ssmh.playerID == 1){
          player1.lives--;
        }
        player1.respawn();
      }
    }
    if (player2.getDead()){
      player2.deathTimer -= 1;
      System.out.println(player2.deathTimer);
      if (player2.lives == 1){
        player2.respawn();
        displayPanel.winPanel.victory("Host");
      }
      if (player2.deathTimer == 0){
        if (displayPanel.gamePanel.ssmh.playerID == 2){
          player2.lives--;
        }
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
      imgMapBackground = ImageIO.read(new File("assets/maps/CPTMap"+image+".png"));
    }catch (IOException e){}
    strMap = loadMap("CPTMap1");
    
    if(imgMapBackground == null){
      System.out.println("Cant find image");
    }

    chatArea.setOpaque(true);
    chatArea.setFocusable(false);

    p1Lives.setSize(200, 100);
    p1Lives.setLocation(700, 100);
    p2Lives.setSize(200, 100);
    p2Lives.setLocation(900, 100);
    add(p1Lives);
    add(p2Lives);

    scrollArea.setSize(500, 260);
    scrollArea.setLocation(0,420);
    scrollArea.setOpaque(true);
    scrollArea.setVisible(false);
    add(scrollArea);

    chatField.setSize(500, 40);
    chatField.setLocation(0, 680);
    chatField.setVisible(false);
    chatField.addActionListener(this);
    add(chatField);

    backButton.setSize(100,50);
    backButton.setLocation(0,0);
    add(backButton);
    chatButton.setSize(100,50);
    chatButton.setLocation(1180,670);
    add(chatButton, "chat");
    //strMap = loadMap(strMapName);

  }

  public static String[][] loadMap(String strMapName){;
    String strLine;
    String[] strSplit;
    String[][] map = new String[90][160];

    // Use InputStream and InputStreamReader to read the file
    try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/maps/" + strMapName + ".png")) {
      imgMapBackground = ImageIO.read(is);
    } catch (IOException e) {
      System.out.println("Error reading map image");
    } 

    // Use InputStream and InputStreamReader to read the file
    try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/maps/" + strMapName + ".csv");
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader mapCSV = new BufferedReader(isr)) {
      
        for (int i = 0;i < 90; i++) {
          strLine = mapCSV.readLine();
          strSplit = strLine.split(",");

          for (int j = 0; j < 160; j++) {
              map[i][j] = strSplit[j];
          }
      }

      mapCSV.close();

    } catch (IOException e) {
      System.out.println("Error reading map file");
    } catch (NullPointerException e) {
      System.out.println("CSV was modified");
    }
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

  public void checkMapChange(){
    if (!strPreMapName.equals(strMapName)){
      System.out.println("Check 1");
      String strLoadMapName;
      if (strMapName.equals("CPTMap1")){
        try {
          imgMapBackground = ImageIO.read(new File("assets/maps/CPTMap1.png"));
          System.out.println("Check 2");
        } catch (IOException ex) {}
      } else {
        try {
          imgMapBackground = ImageIO.read(new File("assets/maps/CPTMap2.png"));
          System.out.println("Check 2");
        } catch (IOException ex) {}
      }
      strMap = loadMap(strMapName);
      strPreMapName = strMapName;
    }
  }
}

