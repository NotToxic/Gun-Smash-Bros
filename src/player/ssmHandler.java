package player;

import main.GamePanel;
import ui.DisplayPanel;
import main.GunSmashBros;
import ssm.SuperSocketMaster;

import java.awt.event.ActionListener;

import guns.Gun;
import inputs.KeyInputs;

//Use ssm to find and set values for player positions
public class ssmHandler {
  
  public int playerID;
  private Player player;
  GamePanel gamePanel;
  DisplayPanel displayPanel;

  String data;
  String[] dataSplit; 
  public String ID = "<>:*{}.data";

  public ssmHandler (int playerID, Player player, GamePanel gamePanel, DisplayPanel displayPanel) {
    this.playerID = playerID;
    this.player = player;
    this.gamePanel = gamePanel;
    this.displayPanel = displayPanel;
  }

  public static void hostMode(DisplayPanel displayPanel, ActionListener listener, int port) {
    GunSmashBros.ssm = new SuperSocketMaster(port, listener);
    GunSmashBros.ssm.connect();

    displayPanel.gamePanel.addKeyListener(new KeyInputs(displayPanel.gamePanel.player1));
    GunSmashBros.ssmh = new ssmHandler(1, displayPanel.gamePanel.player1, displayPanel.gamePanel, displayPanel);
    System.out.println("Socket started in server mode");
  }

  public static void clientMode(DisplayPanel displayPanel, ActionListener listener, int port, String IP) {
    GunSmashBros.ssm = new SuperSocketMaster(IP, port, listener);
    GunSmashBros.ssm.connect();
    
    displayPanel.gamePanel.addKeyListener(new KeyInputs(displayPanel.gamePanel.player1));
    GunSmashBros.ssmh = new ssmHandler(2, displayPanel.gamePanel.player2, displayPanel.gamePanel, displayPanel);
    System.out.println("Socket started in client mode");
  }

  public static void disconnect() {
    GunSmashBros.ssm.disconnect();
  }

  public void sendData() {
    //Send data: ID + player + game/chat + location of player + player shot + direction + gunName
    GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "game" + "," + player.x + "," + player.y + "," + player.shoot + "," + player.direction + "," + player.gun.gunName);
  }

  public void sendText(String ID, int playerID, String chatMessage){
    GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "chat" + "," + chatMessage);
    System.out.println("s");
  }

  // A method to a specific index of the ssm message
  public String chatData(int index){
    data = GunSmashBros.ssm.readText();
    if (data != null){
      dataSplit = data.split(",");
      try{
        return dataSplit[index];
      } catch (ArrayIndexOutOfBoundsException e){
        return "";
      }
    } else {
      return null;
    }
  }

  /* 
  public void getChatData(){
    data = GunSmashBros.ssm.readText();
    //System.out.println(GunSmashBros.ssm.readText());
    if (data != null){
      dataSplit = data.split(",");
      if (dataSplit[0].equals(ID)) {
        if (dataSplit.equals("chat")){
          displayPanel.chatPanel.chatArea.append("Opponent: " + dataSplit[3] + "\n");
        }
      }
    }
  }
  */

  public void getGameData() {
    data = GunSmashBros.ssm.readText();
    if (data != null){
      dataSplit = data.split(",");
      if (dataSplit[0].equals(ID)) {
      
        // Update for player1
        if (dataSplit[1].equals("1")) {
          switch (dataSplit[2]) {
            case "game":
              gamePanel.player1.x = Integer.parseInt(dataSplit[3]);
              gamePanel.player1.y = Integer.parseInt(dataSplit[4]);
              gamePanel.player1.shoot = Boolean.valueOf(dataSplit[5]);
              gamePanel.player1.direction = dataSplit[6];
              gamePanel.player1.gun = new Gun(dataSplit[7]);
              break;
          }
        } else if (dataSplit[1].equals("2")){
          switch (dataSplit[2]){
            case "game":
              gamePanel.player2.x = Integer.parseInt(dataSplit[3]);
              gamePanel.player2.y = Integer.parseInt(dataSplit[4]);
              gamePanel.player2.shoot = Boolean.valueOf(dataSplit[5]);
              gamePanel.player2.direction = dataSplit[6];
              gamePanel.player2.gun = new Gun(dataSplit[7]);
              break;
          }
        }
      }
    }
  }
}
