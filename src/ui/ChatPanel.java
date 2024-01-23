package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import main.GunSmashBros;
import main.GamePanel;

import inputs.ChatInput;

public class ChatPanel extends JPanel implements ActionListener{
	public static JTextArea chatArea = new JTextArea();
	public DisplayPanel displayPanel;
	JScrollPane thescroll = new JScrollPane(chatArea);
	JTextField sendField = new JTextField();
	UIButton backButton;
	ChatInput chatInput;

  	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == sendField){
			System.out.println(GamePanel.ssmh.ID + "," + GamePanel.ssmh.playerID + "," + "chat" + "," + sendField.getText());
			displayPanel.gamePanel.ssmh.sendMsg(GamePanel.ssmh.playerID, sendField.getText());
			if (!sendField.getText().equals("")){
				chatArea.append("You: " + sendField.getText() + "\n");
			}
			sendField.setText("");
		} 
	}
  
	// Constructor
	public ChatPanel(DisplayPanel displayPanel){
		this.displayPanel = displayPanel;

		SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            requestFocusInWindow();
            setLayout(null);
				addKeyListener(new ChatInput("game", displayPanel));
        });

		backButton = new UIButton("BACK TO GAME", "game", displayPanel);
		backButton.setSize(200,50);
		backButton.setLocation(1080,0);
		add(backButton);

		setPreferredSize(new Dimension(1280,720));
	
		thescroll.setSize(740, 620);
		thescroll.setLocation(0,0);
		
		sendField.setSize(740, 100);
		sendField.setLocation(0,620);
		sendField.addActionListener(this);

		add(thescroll);	
		add(sendField);
	}

}
