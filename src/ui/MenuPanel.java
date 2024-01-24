package ui;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.InputStream;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class MenuPanel extends JPanel{

    public DisplayPanel displayPanel;

    public UIButton playButton;
    public UIButton connectButton;
    public UIButton helpButton;
    public UIButton tutorialButton;

    BufferedImage imgMainHomeScreen = null;


    public MenuPanel(DisplayPanel displayPanel){
        this.displayPanel = displayPanel;
        playButton = new UIButton("PLAY", "game", displayPanel);
        connectButton = new UIButton("CONNECT", "connect", displayPanel);
        helpButton = new UIButton("HELP", "help", displayPanel);
        tutorialButton = new UIButton("TUTORIAL", "tutorial", displayPanel);

        setLayout(null);

        InputStream imgClass = null;
        imgClass = this.getClass().getResourceAsStream("/assets/images/CPTHomeScreen.png");
        if(imgClass == null){
			System.out.println("Cannot find home screen image");
		} else {
			try {
				imgMainHomeScreen = ImageIO.read(imgClass);
			} catch(IOException e) {
				System.out.println("Cannot read home screen image file"); //Handle exception
			}
		}

        playButton.setSize(200, 100);
        playButton.setLocation(50, 250);
        playButton.setEnabled(false);

        connectButton.setSize(200, 100);
        connectButton.setLocation(50, 360);

        helpButton.setSize(200, 100);
        helpButton.setLocation(50, 470);

        tutorialButton.setSize(200, 100);
        tutorialButton.setLocation(50, 580);

        add(tutorialButton);
        add(playButton);
        add(connectButton);
        add(helpButton);
        setFocusable(true);
        requestFocusInWindow();
        setLayout(null);
        setPreferredSize(new Dimension(1280,720));
    }
    public void paintComponent(Graphics g){
		super.paintComponent(g);
    g.drawImage(imgMainHomeScreen, 0, 0, null);
  }
}
