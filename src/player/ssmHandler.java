package player;

import guns.Gun;
import main.GamePanel;
import ui.DisplayPanel;
import main.GunSmashBros;
import ui.ChatPanel;

//Use ssm to find and set values for player positions
public class ssmHandler {
  
  public int playerID;
  private Player player;
  private int otherPlayerID;
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

  public void sendData() {
    //Send data, ID + playerID + data type + data value

    //Send x and y position
    System.out.println("Sent");
    //GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "x" + "," + player.x); 
    //GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "y" + "," + player.y);

    //Send data: ID + player + game/chat + location of player + player shot + direction
    GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "game" + "," + player.x + "," + player.y + "," + player.shoot + "," + player.direction);
  }

  public void getData() {
    data = GunSmashBros.ssm.readText();
    System.out.println(GunSmashBros.ssm.readText());
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
              break;
            case "chat":
              displayPanel.chatPanel.chatArea.append("Opponent: " + dataSplit[3]);
              System.out.println("c");
              break;
          }
        } else if (dataSplit[1].equals("2")){
          switch (dataSplit[2]){
            case "game":
              gamePanel.player2.x = Integer.parseInt(dataSplit[3]);
              gamePanel.player2.y = Integer.parseInt(dataSplit[4]);
              gamePanel.player2.shoot = Boolean.valueOf(dataSplit[5]);
              gamePanel.player2.direction = dataSplit[6];
              break;
            case "chat":
              System.out.println("c");
              displayPanel.chatPanel.chatArea.append("Opponent: " + dataSplit[3]);
              break;
          }
        }
      }
    }
  }
}
