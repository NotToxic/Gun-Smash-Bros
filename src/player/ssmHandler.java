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
/**ssmHandler Class to Manage ssm sent and recieved data to organize Player info and Characters on Host And Client Screens */
public class ssmHandler {
  
  public int playerID;
  private Player player;
  GamePanel gamePanel;
  DisplayPanel displayPanel;

  /**String for incoming data */
  String data;
  String[] dataSplit; 
  //Unique ID For Sent Movement Data
  public String ID = "<>:*{}.data";

  public ssmHandler (int playerID, Player player, GamePanel gamePanel, DisplayPanel displayPanel) {
    this.playerID = playerID;
    this.player = player;
    this.gamePanel = gamePanel;
    this.displayPanel = displayPanel;
  }

  /**Host Mode Method To Manage Connection */
  public static void hostMode(DisplayPanel displayPanel, ActionListener listener, int port) {
    GunSmashBros.ssm = new SuperSocketMaster(port, listener);
    GunSmashBros.ssm.connect();
    try{
      GunSmashBros.ssm2 = new SuperSocketMaster(port+1, listener);
      GunSmashBros.ssm2.connect();
    } catch (IllegalArgumentException e){
      GunSmashBros.ssm2 = new SuperSocketMaster(port-1, listener);
      GunSmashBros.ssm2.connect();
    }

    displayPanel.gamePanel.addKeyListener(new KeyInputs(displayPanel.gamePanel.player1));
    displayPanel.gamePanel.ssmh = new ssmHandler(1, displayPanel.gamePanel.player1, displayPanel.gamePanel, displayPanel);
    System.out.println("Socket started in server mode");
  }

  /**Client Mode Method to Manage Connection via Client Side */
  public static void clientMode(DisplayPanel displayPanel, ActionListener listener, int port, String IP) {
    GunSmashBros.ssm = new SuperSocketMaster(IP, port, listener);
    GunSmashBros.ssm.connect();
    try{
      GunSmashBros.ssm2 = new SuperSocketMaster(IP, port+1, listener);
      GunSmashBros.ssm2.connect();
    } catch (IllegalArgumentException e){
      GunSmashBros.ssm2 = new SuperSocketMaster(IP, port-1, listener);
      GunSmashBros.ssm2.connect();
    }
    
    displayPanel.gamePanel.addKeyListener(new KeyInputs(displayPanel.gamePanel.player2));
    displayPanel.gamePanel.ssmh = new ssmHandler(2, displayPanel.gamePanel.player2, displayPanel.gamePanel, displayPanel);
    System.out.println("Socket started in client mode");
  }

  /**Disconnect Functionality - Disconnecting ssm usage */
  public static void disconnect() {
    GunSmashBros.ssm.disconnect();
    GunSmashBros.ssm2.disconnect();
  }

  /**sendData Method to manage sent data from Device - Host or Client */
  public void sendData() {
    /**Send data: ID + player + game/chat + location of player + player shot + direction + gunName*/
    GunSmashBros.ssm.sendText(ID + "," + playerID + "," + "game" + "," + player.x + "," + player.y + "," + player.ySpeed + "," + player.xSpeed + "," + player.shoot + "," + player.direction + "," + player.gun.gunName + "," + player.lives + "," + gamePanel.strMapName);
  }

  /**ssm 2nd Implementation specfic to Chat Game Feature - Send over chat info */
  public void sendMsg(int playerID, String chatMessage){
    GunSmashBros.ssm2.sendText(ID + "," + playerID + "," + "chat" + "," + chatMessage);
  }

  /**ss2 Usage specfic to crate details */
  public void sendCrate(int crateX, String gunType){
    GunSmashBros.ssm2.sendText(ID + "," + playerID + "," + "crate" + "," + gunType + "," + crateX);
  }

  /**ssm Object Breakdown - Taking Recieved ssm Data to reconstruct via set properties on respective player screen for Opponent character/Common Fields*/

  /*ssm2 - Spliting of Crate Details, Sent Chat Data */
  public void getOtherData(){
    data = GunSmashBros.ssm2.readText();
    if (data != null){
      dataSplit = data.split(",");
      if (dataSplit[0].equals(ID)) {
        // Update for player1
        if (dataSplit[1].equals("1")) {
          switch (dataSplit[2]) {
            case "crate":
              gamePanel.c = new Crate(gamePanel.strArrayMap, dataSplit[3], Integer.parseInt(dataSplit[4]));
              gamePanel.crateList.add(gamePanel.c);
              System.out.println("Crate: " + dataSplit[3] + "," + dataSplit[4]);
              break;
            case "chat":
              displayPanel.gamePanel.chatArea.append("Opponent: " + dataSplit[3] + "\n");
              break;
          }
        } else if (dataSplit[1].equals("2")){
          switch (dataSplit[2]){
            case "crate":
              Crate c = new Crate(gamePanel.strArrayMap, dataSplit[3], Integer.parseInt(dataSplit[4]));
              System.out.println(dataSplit[3] + "," + dataSplit[4]);
              gamePanel.crateList.add(c);
              break;
            case "chat":
              displayPanel.gamePanel.chatArea.append("Opponent: " + dataSplit[3] + "\n");
              break;
          }
        }
      }
    }
  }

  /**ssm, Splitting of Player location, x and y, Life Data, Gun Type Data, and Directional Data */
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
              gamePanel.player1.ySpeed = Double.parseDouble(dataSplit[5]);
              //gamePanel.player1.xSpeed = Double.parseDouble(dataSplit[6]);
              gamePanel.player1.shoot = Boolean.valueOf(dataSplit[7]);
              gamePanel.player1.direction = dataSplit[8];
              gamePanel.player1.gun = new Gun(dataSplit[9]);
              gamePanel.player1.lives = Integer.parseInt(dataSplit[10]);
              gamePanel.strMapName = dataSplit[11];
              break;
          }
        } else if (dataSplit[1].equals("2")){
          switch (dataSplit[2]){
            case "game":
              gamePanel.player2.x = Integer.parseInt(dataSplit[3]);
              gamePanel.player2.y = Integer.parseInt(dataSplit[4]);
              gamePanel.player2.ySpeed = Double.parseDouble(dataSplit[5]);
              //gamePanel.player2.xSpeed = Double.parseDouble(dataSplit[6]);
              gamePanel.player2.shoot = Boolean.valueOf(dataSplit[7]);
              gamePanel.player2.direction = dataSplit[8];
              gamePanel.player2.gun = new Gun(dataSplit[9]);
              gamePanel.player2.lives = Integer.parseInt(dataSplit[10]);
              break;
          }
        }
      }
    }
  }
}
