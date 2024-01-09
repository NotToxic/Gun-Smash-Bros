package ui;

import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.GamePanel;
import ssm.SuperSocketMaster;

public class ChatPanel extends JPanel implements ActionListener{

    public GamePanel ChatPanel;
    JTextArea chatArea = new JTextArea();
	JScrollPane thescroll = new JScrollPane(chatArea);
	JTextField sendField = new JTextField();
    SuperSocketMaster ssm = null;

    public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == sendField){
			System.out.println("send: "+sendField.getText());
			//ssm.sendText(ssm.getMyAddress() + "," + sendField.getText());
			ssm.sendText( sendField.getText());
			chatArea.append(sendField.getText() + "\n");
			sendField.setText("");
		}else if(evt.getSource() == ssm){
            chatArea.append(ssm.readText() + "\n");
		}
	}
    	// Constructor
	public ChatPanel(){
		ChatPanel.setPreferredSize(new Dimension(400, 550));
	
		thescroll.setSize(400, 400);
		thescroll.setLocation(0,0);
		
		sendField.setSize(400, 50);
		sendField.setLocation(0,400);
		sendField.addActionListener(this);

		ChatPanel.add(thescroll);	
		ChatPanel.add(sendField);
	}

}
