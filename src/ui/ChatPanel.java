package ui;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import main.GamePanel;
import main.GunSmashBros;
import ssm.SuperSocketMaster;

public class ChatPanel extends JPanel implements ActionListener{

  public GamePanel ChatPanel;
    	public static JTextArea chatArea = new JTextArea();
		JScrollPane thescroll = new JScrollPane(chatArea);
		JTextField sendField = new JTextField();
		UIButton backButton;

  public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == sendField){
				System.out.println("send: "+sendField.getText());
				//ssm.sendText(ssm.getMyAddress() + "," + sendField.getText());
				GunSmashBros.ssm.sendText( sendField.getText());
				chatArea.append(sendField.getText() + "\n");
				sendField.setText("");
		} 
	}
  
	// Constructor
	public ChatPanel(DisplayPanel displayPanel){

		SwingUtilities.invokeLater(() -> {
            setFocusable(true);
            requestFocusInWindow();
            setLayout(null);
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
