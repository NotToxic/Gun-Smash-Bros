package player;


//Use ssm to find and set va
public class MovementHandler {
  
  private int playerID;
  private Player player;
  private SuperSocketMaster ssm;
  String data;
  String[] dataSplit; 
  String ID = "<>:*{}.data";

  public MovementHandler (int playerID, SuperSocketMaster ssm, Player player) {
    this.playerID = playerID;
    this.ssm = ssm;
    this.player = player;
  }

  public sendData() {
    //Send data, ID + playerID + data type + data value

    //Send x and y position
    ssm.sendText(ID + "," + playerID + "," + "x" + "," + player.x) 
    ssm.sendText(ID + "," + playerID + "," + "y" + "," + player.y) 
  }

  public getData() {
    data = ssm.readText()
    try {
      dataSplit = data.split(",");
    } catch ()
    if 
  }
}
