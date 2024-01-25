package guns;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

/**This class allows for the creation of Crates*/
public class Crate {

  /**The BufferedImage for the crate. */
  public BufferedImage imgCrate;

  /**How wide and tall the crate is. */
  public int size = 48;
  /**The crate's x position. */
  public int x; 
  /**The crate's y position. */
  public int y = 0-size;
  /**The crate's ySpeed. */
  double ySpeed = 0;
  /**If false, the crate has been touched by a player. */
  public boolean visible = true;
  /**What gun the crate holds. */
  public String gunName;
  /**The map that the crate will spawn into. */
  String[][] map;

  /**Crate Constructing Method*/
  public Crate(String[][] map, String gunName, int x){
      this.x = x;
      this.gunName = gunName;
      this.map = map;

      try (InputStream is = Crate.class.getResourceAsStream("/assets/objects/crate.png")) {
        imgCrate = ImageIO.read(is);
      } catch (IOException e) {
        System.out.println("cannot find gun image");
      }
  }

  /**Crate falling method. */
  public void move(){
      ySpeed += 0.7;

      if (y > 730){
          visible = false;
      }
      collision(map);
      y += ySpeed;
  }

  /**Crate collision method to stop fall when touching a platform. */
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

  /**Returns how close the crate is to the platform. */
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

  /**Returns the x position of the crate. */
  public int getX(){
    return x;
  }
  /**Returns the y position of the crate. */
  public int getY(){
    return y;
  }
  /**Returns the size of the crate. */
  public int getSize(){
    return size;
  }
    
}
