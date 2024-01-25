package guns;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
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

  /**Crate Constructing Method
   * @param map For platform collision detection
   * @param gunName the gun that the crate holds inside of it
   * @param x the x position of the crate
  */
  public Crate(String[][] map, String gunName, int x){
      this.x = x;
      this.gunName = gunName;
      this.map = map;

      //Try to load the image

      InputStream imageclass1 = null;
      imageclass1 = this.getClass().getResourceAsStream("/assets/objects/crate.png");
      if (imageclass1 == null){
      }else{
        try{
            imgCrate = ImageIO.read(imageclass1);
        }catch(IOException e){
            System.out.println("Unable to load image from jar");
        }
      }
      if (imgCrate == null){
        try{
            imgCrate = ImageIO.read(new File("/assets/objects/crate.png"));
        } catch(IOException e){
            System.out.println("Unable to load image");
        }
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

  /**Crate collision method to stop fall when touching a platform
   * @param map for the platform collision
  */
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

  /**Returns how close the crate is to the platform
   * @param map to see how close the crate is to platforms
   * @param x where the crate is relative to the map on the x-axis
   * @param y where the crate is relative to the map on the y-axis
  */
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

  /**Returns the x position of the crate
   * @return x the x position of the crate
  */
  public int getX(){
    return x;
  }
  /**Returns the y position of the crate
   * @return y the y position of the crate
   */
  public int getY(){
    return y;
  }
  /**Returns the size of the crate
   * @return size the size of the crate (width and height are the same)
   */
  public int getSize(){
    return size;
  }
    
}
