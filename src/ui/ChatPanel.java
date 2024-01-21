package ui;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import main.GunSmashBros;
import main.GamePanel;
import ssm.SuperSocketMaster;
import player.ssmHandler;
import inputs.ChatInput;
import inputs.KeyInputs;

public class ChatPanel extends JPanel implements ActionListener{
	public static JTextArea chatArea = new JTextArea();
	public static DisplayPanel displayPanel;
	JScrollPane thescroll = new JScrollPane(chatArea);
	JTextField sendField = new JTextField();
	UIButton backButton;
	ChatInput chatInput;
	ssmHandler ssh;

	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == sendField){
			System.out.println(GamePanel.ssh.ID + "," + GamePanel.ssh.playerID + "," + "chat" + "," + sendField.getText());
			this.ssh.sendText(GamePanel.ssh.ID, GamePanel.ssh.playerID, sendField.getText());
			chatArea.append("You: " + sendField.getText() + "\n");
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

	public void chatMessages(int playerNum){
		if (playerNum == 1) {
			this.ssh = new ssmHandler(1, displayPanel.gamePanel.player1, this, displayPanel);
		}
		else if (playerNum == 2) {
			this.ssh = new ssmHandler(2, displayPanel.gamePanel.player2, this, displayPanel);
		}
	}

}
