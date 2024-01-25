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
import java.io.File;
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

/**Parent Class for GamePanel and TutorialPanel*/
public abstract class GraphicsPanel extends JPanel implements ActionListener{
  
  /**Display panel to swap through JPanels*/
  public static DisplayPanel displayPanel;
  /**SsmHandler to manage sent and received data*/
  public static ssmHandler ssmh = null;

  /**Create player model for game*/
  public Player player1 = new Player(75, -100, 45, 90, this);

  /**Background image of map*/
  public BufferedImage imgMap = null;
  /**String array that contains platform information of selected map*/ 
  public String[][] strArrayMap;

  /**Initialize crate that contains gun*/ 
  public Crate c;
  /**List of bullets that need to be drawn*/ 
  public static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  /**List of crates that are loaded into the game*/ 
  public static ArrayList<Crate> crateList = new ArrayList<Crate>();

  /**Button to return to main menu*/ 
  public UIButton backButton;

  /**Text area to display chat messages*/
  public JTextArea chatArea = new JTextArea();
  /**Scroll pane to contain text area*/
  public JScrollPane scrollArea = new JScrollPane(chatArea);
  /**Area to send text messages*/
  public JTextField chatField = new JTextField();

  /**Action listener to detect when chat opens
   * @param e event that actionlistener catches, detects when text messages are sent
  */
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

  /**Method that sets the background image and background string array variables
   * @param strMapName is the selected map of choice, either "CPTMap1" or "CPTMap2"
  */
  public void loadMap(String strMapName) {
    strArrayMap = loadMapCSV(strMapName);
    imgMap = loadMapImage(strMapName);
  }

  /**Method that reads CSV data files for maps
   * @param strMapName is the selected map of choice, either "CPTMap1" or "CPTMap2"
   * @return the data from the CSV file as a string array
  */
  public String[][] loadMapCSV(String strMapName) {;
    String strLine;
    String[] strSplit;
    String[][] map = new String[90][160];

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

  /**Method that gets map image files
   * @param strMapName is the selected map of choice, either "CPTMap1" or "CPTMap2"
   * @return the BufferedImage from inputstream
  */
  public BufferedImage loadMapImage(String strMapName) {
    BufferedImage imgMap = null;
    try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/maps/" + strMapName + ".png")) {
      imgMap = ImageIO.read(is);
    } catch (IOException e) {
      System.out.println("Error reading map image");
    } 
    if(imgMap == null){
      try{
        imgMap = ImageIO.read(new File("/assets/objects/characterLeft.png"));
      }catch(IOException e){
        System.out.println("cannot load image");
      }
    }
    return imgMap;
  }

  /**Draws the map and platforms where the character can stand
   * @param strArrayMap data from CSV file in selected map
   * @param g2d the graphics component to paint images
   * @param imgMap the background image from the selected map
  */
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

  /**Method to draw player models
   * @param g  the graphics component to paint images
   * @param player the player to be drawn - use to find direction and bufferedimage of player
  */
  public void drawPlayer(Graphics g, Player player) {
    g.drawImage(player.getCharModel(), player.x, player.y, null);
  }

  /**Method to draw gun models
   * @param g  the graphics component to paint images
   * @param player get the image associated with the player's gun and correct x and y positions
  */
  public void drawGun(Graphics g, Player player) {
    g.drawImage(player.gun.imgGun, player.gun.getGunX(player), player.gun.getGunY(player), null);
  }

  /**Method to draw gun bullets
   * @param g  the graphics component to paint images
  */
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

  /**Method to generate a random crate*/
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
          c = new Crate(strArrayMap, "lightGuy", x);
          crateList.add(c);
          System.out.println("light");
          ssmh.sendCrate(x, "lightGuy");
        } else {
          c = new Crate(strArrayMap, "heavyGuy", x);
          crateList.add(c);
          System.out.println("heavy");
          ssmh.sendCrate(x, "heavyGuy");
        }
      }
    } 
  }

  /**Method to generate a crate but specified place and type
   * @param crateType specifies if crate will give a heavy or light gun
   * @param xLocation specifies the x position of the crate when it spawns
  */
  public void spawnCrates(String crateType, int xLocation){
    if (crateList.size() == 0) {
      c = new Crate(strArrayMap, crateType, xLocation);
      crateList.add(c);
    }
  }

  /**Method to generate a crate but specified place and type
   * @param g the graphics component to paint images
  */
  public void drawCrates(Graphics g) {
    if (crateList.size() == 1){
      c = (Crate)crateList.get(0);
      if (c.visible){
        c.move();
        g.drawImage(c.imgCrate, c.x, c.y, null);
      } else {
        crateList.remove(0);
      }
    }
  }

  /**Constructor for GraphicsPanel
   * @param displayPanel to link up to other JPanels
  */
  public GraphicsPanel(DisplayPanel displayPanel) {
    GraphicsPanel.displayPanel = displayPanel;

    //Set size of panel
    setPreferredSize(new Dimension(1280, 720));

    //Set size and location of JComponents and add to panel
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
