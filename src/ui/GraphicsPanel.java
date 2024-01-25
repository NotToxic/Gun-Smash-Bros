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

public abstract class GraphicsPanel extends JPanel implements ActionListener{
  
  public static DisplayPanel displayPanel;

  public static BufferedImage imgMap = null;  
  public String[][] strMapArray;

  public Crate c;
  public static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
  public static ArrayList<Crate> crateList = new ArrayList<Crate>();
  //public Player player1 = new Player(300, 0, 45, 90, this);

  public UIButton backButton;

  public JTextArea chatArea = new JTextArea();
  public JScrollPane scrollArea = new JScrollPane(chatArea);
  public JTextField chatField = new JTextField();

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
  }

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

  public void paintMap(String[][] strMap, Graphics2D g2D, BufferedImage Background1){

    g2D.drawImage(Background1, 0, 0, null);

    //get rid of this part later
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
      addKeyListener(new ChatInput("chat", displayPanel));
      setFocusable(true);
      requestFocusInWindow();
      setLayout(null);
    });
  }
}
