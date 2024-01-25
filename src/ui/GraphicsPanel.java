package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import guns.Bullet;
import guns.Crate;
import inputs.ChatInput;
import main.GamePanel;
import player.Player;
import player.ssmHandler;

public abstract class GraphicsPanel extends JPanel implements ActionListener{
  
  public static DisplayPanel displayPanel;
  public static ssmHandler ssmh = null;

  public Player player1 = new Player(75, -100, 45, 90, this);

  public static BufferedImage imgMap = null;  
  public String[][] strArrayMap;

  public Crate c;
  public static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  public static ArrayList<Crate> crateList = new ArrayList<Crate>();
  //public Player player1 = new Player(300, 0, 45, 90, this);

  public UIButton backButton;

  public JTextArea chatArea = new JTextArea();
  public JScrollPane scrollArea = new JScrollPane(chatArea);
  public JTextField chatField = new JTextField();

  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == chatField) {
			if (!chatField.getText().equals("")){
				chatArea.append("You: " + chatField.getText() + "\n");
			}
			chatField.setText("");
      scrollArea.setVisible(false);
      chatField.setVisible(false);
      requestFocus();
		}
  }

  public static String[][] loadMap(String strMapName) {;
    String strLine;
    String[] strSplit;
    String[][] map = new String[90][160];

    // Use InputStream and InputStreamReader to read the file
    try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/maps/" + strMapName + ".png")) {
      imgMap = ImageIO.read(is);
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

  public void paintMap(String[][] strArrayMap, Graphics2D g2d, BufferedImage imgMap){

    g2d.drawImage(imgMap, 0, 0, null);

    //get rid of this part later
    g2d.setColor(Color.BLACK);
    int intCount;
    int intCounter;
   // int intX = 0;
   // int intY = 0;
    for(intCount = 0; intCount < 90; intCount++){
      //intX = 0;
      
      for(intCounter = 0; intCounter < 160; intCounter++){
          if(strArrayMap[intCount][intCounter].equals("s")){
            
          }else if(strArrayMap[intCount][intCounter].equals("p")){
            g2d.fillRect(intCounter * 8, intCount * 8, 8, 8);
          }
         // intX = intX + 8;
        }
        //intY = intY + 8;
    }
  }

  public void drawPlayer(Graphics g, Player player) {
    g.drawImage(player.getCharModel(), player.x, player.y, null);
  }

  public void drawGun(Graphics g, Player player) {
    g.drawImage(player.gun.imgGun, player.gun.getGunX(player), player.gun.getGunY(player), null);
  }

  public void drawBullets(Graphics g) {
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
  }

  public void spawnCrates(){
    if (crateList.size() == 0 && ssmh.playerID == 1){
      if ((int)(Math.random() * 100) + 1 == 1){
        // max - min
        int range = 950 - 250 + 1;
        // randomNum + min
        int x = (int)(Math.random() * range) + 250;
        // if random number rounds to 0, spawn a lightgun crate
        int gunType = (int)(Math.random() * 2) + 1;
        if (gunType == 1){
          Crate c = new Crate(strArrayMap, "lightGuy", x);
          crateList.add(c);
          System.out.println("light");
          ssmh.sendCrate(x, "lightGuy");
        } else {
          Crate c = new Crate(strArrayMap, "heavyGuy", x);
          crateList.add(c);
          System.out.println("heavy");
          ssmh.sendCrate(x, "heavyGuy");
        }
      }
    } 
  }

  public void spawnCrates(String crateType, int xLocation){
    if (crateList.size() == 0) {
      Crate c = new Crate(strArrayMap, crateType, xLocation);
      crateList.add(c);
    }
  }

  public void drawCrates(Graphics g) {
    if (crateList.size() == 1){
      Crate c = (Crate)crateList.get(0);
      if (c.visible){
        c.move();
        g.drawImage(c.imgCrate, c.x, c.y, null);
      } else {
        crateList.remove(0);
      }
    }
  }

  public GraphicsPanel(DisplayPanel displayPanel) {
    GraphicsPanel.displayPanel = displayPanel;

    setPreferredSize(new Dimension(1280, 720));

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

    backButton = new UIButton("BACK", "menu", displayPanel);
    backButton.setSize(100,50);
    backButton.setLocation(0,0);
    add(backButton);

    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
          requestFocusInWindow();
      }
    });

    SwingUtilities.invokeLater(() -> {
      addKeyListener(new ChatInput(this));
      setFocusable(true);
      requestFocusInWindow();
      setLayout(null);
    });
  }
}
