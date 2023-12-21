package main;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;

import inputs.KeyInputs;
import player.Player;
import ui.DisplayPanel;
import ui.UIButton;

public class GamePanel extends JPanel{

  DisplayPanel displayPanel;
  Player player = new Player(300, 500, 45, 90, this);

<<<<<<< Updated upstream
  UIButton backButton;
=======
  JButton backButton = new JButton("Back");
  BufferedReader map1CSV;
  String[][] strMap;
>>>>>>> Stashed changes

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 1280, 720);

    g2d.setColor(Color.BLACK);
    player.move();
    paintMap(strMap, g2d);
    g2d.fillRect(player.x, player.y, player.width, player.height);
  }

  public GamePanel(DisplayPanel displayPanel) {

    this.displayPanel = displayPanel;
    backButton = new UIButton("BACK", "menu", displayPanel);

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
<<<<<<< Updated upstream
=======

   // int intCount2;
   // int intCount3;

    strMap = loadMap("Gun-Smash-Bros/src/CPTMap1.csv");

   // for(intCount2 = 0; intCount2 <=159; intCount2++){
     //    for(intCount3 = 0; intCount3 <= 89; intCount3++){
     //      System.out.println(strMap[intCount2][intCount3]);
     //    }
     //  }
  
>>>>>>> Stashed changes
  }

  public static String[][] loadMap(String strMapName){
    BufferedReader mapCSV;
    int intCount;
    int intCounter;
    String strLine;
    String[] strSplit;
    String strMap[][] = new String[90][160];


    try{
      mapCSV = new BufferedReader(new FileReader(strMapName));
      for(intCount = 0; intCount < 90; intCount++){
        strLine = mapCSV.readLine();
        strSplit = strLine.split(",");

        for(intCounter = 0; intCounter < 160; intCounter++){
          strMap[intCount][intCounter] = strSplit[intCounter];
        }

      }

      mapCSV.close();

    }catch(FileNotFoundException e){
      System.out.println("File not found");
    }catch(IOException e){
      System.out.println("error");
    }
    return strMap;
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

