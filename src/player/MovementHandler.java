package player;

import main.GamePanel;
import main.GunSmashBros;

//Use ssm to find and set values for player positions
public class MovementHandler {
  
  private int playerID;
  private Player player;
  private int otherPlayerID;
  GamePanel gamePanel;
  String data;
  String[] dataSplit; 
  String ID = "<>:*{}.data";

  public MovementHandler (int playerID, Player player, GamePanel gamePanel) {
    this.playerID = playerID;
    this.player = player;
    this.gamePanel = gamePanel;
  }

  public void sendData() {
    //Send data, ID + playerID + data type + data value

    //Send x and y position
    System.out.println("Sent");
    GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "x" + "," + player.x); 
    GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "y" + "," + player.y);
  }

  public void getData() {
    System.out.println("Received");
    data = GunSmashBros.ssm.readText();
    if (data != null) {
      System.out.println("not null");
      dataSplit = data.split(",");
      if (dataSplit[0].equals(ID)) {
        System.out.println("Id");

        // Update for player1
        if (dataSplit[1].equals("1")) {
          switch (dataSplit[2]) {
            case "x":
              gamePanel.player1.x = Integer.parseInt(dataSplit[3]);
              System.out.println("x:" + gamePanel.player1.x);
              break;
            case "y":
              gamePanel.player1.y = Integer.parseInt(dataSplit[3]);
              System.out.println("y:" + gamePanel.player1.y);
              break;
          }
        }

        // Update for player2
        else {
          switch (dataSplit[2]) {
            case "x":
              gamePanel.player2.x = Integer.parseInt(dataSplit[3]);
              System.out.println("x:" + gamePanel.player2.x);
              break;
            case "y":
              gamePanel.player2.y = Integer.parseInt(dataSplit[3]);
              System.out.println("y:" + gamePanel.player2.y);
              break;
          }
        }
      }
    }
  }
}
