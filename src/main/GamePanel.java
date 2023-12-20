package main;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

import inputs.KeyInputs;
import player.Player;

public class GamePanel extends JPanel{

  Player player = new Player(300, 500, 45, 90, this);

  JButton backButton = new JButton("Back");
  BufferedReader map1CSV;

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.WHITE);
    g2d.fillRect(0, 0, 1280, 720);

    g2d.setColor(Color.BLACK);
    player.move();
    
    g2d.fillRect(player.x, player.y, player.width, player.height);
  }

  public GamePanel() {
    setPreferredSize(new Dimension(1280, 720));
    SwingUtilities.invokeLater(() -> {
      addKeyListener(new KeyInputs(player));
      setFocusable(true);
      requestFocusInWindow();
      setLayout(null);
    });
    backButton.setSize(200,200);
    backButton.setLocation(100,100);
    add(backButton);

    
    try{ 
      map1CSV = new BufferedReader(new FileReader("CPTMap1.csv"));
      System.out.println("File FOund");
    }catch(FileNotFoundException e){
      System.out.println("File Not found");
    }
    
    String[][] strMap = new String[160][90];

    String strLine;
    String strSplit[];
    int intCount;
    int intCounter;

    for(intCount = 0; intCount <= 89; intCount++){
      try{
        strLine = map1CSV.readLine();
        strSplit = strLine.split(",");
          for(intCounter = 0; intCounter <=159; intCounter++){
            strMap[intCount][intCounter] = strSplit[intCounter];
          }
          map1CSV.close();
        }catch(IOException e){
          System.out.println("Error");
        }
    }

    int intCount2;
    int intCount3;

    for(intCount2 = 0; intCount2 <=159; intCount2++){
      for(intCount3 = 0; intCount3 <= 89; intCount3++){
        System.out.println(strMap[intCount2][intCount3]);
      }

    }
  
  }
}

