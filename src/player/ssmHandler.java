package player;

import main.GamePanel;
import ui.DisplayPanel;
import main.GunSmashBros;
import ssm.SuperSocketMaster;

import java.awt.event.ActionListener;

import guns.Gun;
import guns.Crate;
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
  String previousMsg = "";

  public ssmHandler (int playerID, Player player, GamePanel gamePanel, DisplayPanel displayPanel) {
    this.playerID = playerID;
    this.player = player;
    this.gamePanel = gamePanel;
    this.displayPanel = displayPanel;
  }

  public static void hostMode(DisplayPanel displayPanel, ActionListener listener, int port) {
    GunSmashBros.ssm = new SuperSocketMaster(port, listener);
    GunSmashBros.ssm.connect();
    GunSmashBros.ssm2 = new SuperSocketMaster(port, listener);
    GunSmashBros.ssm2.connect();

    displayPanel.gamePanel.addKeyListener(new KeyInputs(displayPanel.gamePanel.player1));
    displayPanel.gamePanel.ssmh = new ssmHandler(1, displayPanel.gamePanel.player1, displayPanel.gamePanel, displayPanel);
    System.out.println("Socket started in server mode");
  }

  public static void clientMode(DisplayPanel displayPanel, ActionListener listener, int port, String IP) {
    GunSmashBros.ssm = new SuperSocketMaster(IP, port, listener);
    GunSmashBros.ssm.connect();
    GunSmashBros.ssm2 = new SuperSocketMaster(IP, port, listener);
    GunSmashBros.ssm2.connect();
    
    displayPanel.gamePanel.addKeyListener(new KeyInputs(displayPanel.gamePanel.player2));
    displayPanel.gamePanel.ssmh = new ssmHandler(2, displayPanel.gamePanel.player2, displayPanel.gamePanel, displayPanel);
    System.out.println("Socket started in client mode");
  }

  public static void disconnect() {
    GunSmashBros.ssm.disconnect();
    GunSmashBros.ssm2.disconnect();
  }

  public void sendData() {
    //Send data: ID + player + game/chat + location of player + player shot + direction + gunName
    GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "game" + "," + player.x + "," + player.y + "," + player.shoot + "," + player.direction + "," + player.gun.gunName + "," + gamePanel.strMapName);
  }

  public void sendMsg(int playerID, String chatMessage){
    GunSmashBros.ssm2.sendText(ID + "," + playerID + "," + "chat" + "," + chatMessage);
  }

  public void sendCrate(int crateX, String gunType){
    GunSmashBros.ssm2.sendText(ID + "," + playerID + "," + "crate" + "," + gunType + "," + crateX);
  }

  public void getOtherData(){
    data = GunSmashBros.ssm2.readText();
    if (data != null){
      dataSplit = data.split(",");
      if (dataSplit[0].equals(ID)) {
        // Update for player1
        if (dataSplit[1].equals("1")) {
          switch (dataSplit[2]) {
            case "crate":
              gamePanel.c = new Crate(gamePanel.strMap, dataSplit[3], Integer.parseInt(dataSplit[4]));
              gamePanel.crateList.add(gamePanel.c);
              System.out.println("Crate: " + dataSplit[3] + "," + dataSplit[4]);
              break;
            case "chat":
              previousMsg = dataSplit[3];
              displayPanel.gamePanel.chatArea.append("Opponent: " + dataSplit[3] + "\n");
              break;
          }
        } else if (dataSplit[1].equals("2")){
          switch (dataSplit[2]){
            case "crate":
              gamePanel.c = new Crate(gamePanel.strMap, dataSplit[3], Integer.parseInt(dataSplit[4]));
              System.out.println(dataSplit[3] + " , " + dataSplit[4]);
              gamePanel.crateList.add(gamePanel.c);
              break;
            case "chat":
              previousMsg = dataSplit[3];
              displayPanel.gamePanel.chatArea.append("Opponent: " + dataSplit[3] + "\n");
              break;
          }
        }
      }
    }
  }

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
              gamePanel.strMapName = dataSplit[8];
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
