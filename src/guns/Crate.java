package guns;

public class Crate {

    public int size = 45;
    public int x; 
    public int y = 0-size;
    double ySpeed = 0;
    public boolean visible = true;
    public String gunName;
    String[][] map;

    public Crate(String[][] map, String gunName, int x){
        this.x = x;
        this.gunName = gunName;
        this.map = map;
    }

    public void move(){
        ySpeed += 0.7;

        if (y > 730){
            visible = false;
        }
        collision(map);
        y += ySpeed;
    }

    public void collision(String[][] map){
        int platformDistance = platform(map, x, y);
        if (platformDistance == 0 && ySpeed > 0){
          ySpeed = 0;
        } else if (platformDistance > 0 && ySpeed > 0){
          if (platformDistance < ySpeed){
            ySpeed = 0;
            y += platformDistance;
          }
        }
    }

  public int platform(String[][] map, int x, int y){
    try{
      for (int i = 0; i < 6; i++){
        if (map[(y+size+(8*i))/8][x/8].equals("p") || map[(y+size+(8*i))/8][(x+size)/8].equals("p")){
          return i*8;
        }
      }
      return -1;
    } catch (IndexOutOfBoundsException e){
      return -1;
    }
  }
    
}
