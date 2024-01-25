package player;

import ui.GraphicsPanel;
import guns.Bullet;
import guns.Gun;
import guns.Crate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

/**Class to Handle Character/Player Info And Details */
public class Player {

  /**the graphics panel for the actionListener */
  private GraphicsPanel graphicsPanel;

  /**The BufferedImgaes for when the player is looking left */
  BufferedImage imgPlayerLeft = null;
  /**The BufferedImgaes for when the player is looking right */
  BufferedImage imgPlayerRight = null;

  /**The player's x position */
  public int x;
  /**The player's y position */
  public int y;
  /**The player's width */
  public int width;
  /**The player's heigth */
  public int height;

  /**The player's xSpeed */
  double xSpeed;
  /**The player's ySpeed */
  double ySpeed;

  /**True if the player's pressing right (d)*/
  public boolean keyRight;
  /**True if the player's pressing left (a)*/
  public boolean keyLeft;
  /**True if the player's pressing up (w)*/
  public boolean keyUp;
  /**True if the player's pressing down (s)*/
  public boolean keyDown;

  /**The direction that the player's looking at */
  public String direction = "right";
  /**True if the player is shooting */
  public boolean shoot;
  /**How long until the player can shoot again */
  public int shootTimer;

  /** True if the player has jumped twice*/
  public boolean doubleJumped = false;

  /**True if the player is off the map */
  public boolean dead = false;
  /**Starts when the player dies */
  public Timer deathTimer;
  /**Players start out with 5 lives */
  public int lives = 5;
  /**How many frames until the player can move again when hit by a bullet */
  public int hitTimer = 0;

  /**How many times the player can jump */
  public int jumpCounter = 0;

  /**The Gun the player's using */
  public Gun gun = new Gun("lightGuy");

  /**Player Constructor */
  public Player(int x, int y, int width, int height, GraphicsPanel graphicsPanel) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;

    this.graphicsPanel = graphicsPanel;
    
    deathTimer = new Timer(1500, graphicsPanel);

    try (InputStream is = Crate.class.getResourceAsStream("/assets/objects/characterLeft.png")) {
      imgPlayerLeft = ImageIO.read(is);
    } catch (IOException e) {
      System.out.println("cannot find left char image");
    }

    try (InputStream is = Crate.class.getResourceAsStream("/assets/objects/characterRight.png")) {
      imgPlayerRight = ImageIO.read(is);
    } catch (IOException e) {
      System.out.println("cannot find right char image");
    }
  }

  /**Reads the inputs of the player, and increases their speed */
  public void move(String[][] map) {
    /**If no clear direction is being pressed, speed slows down by 70% per frame */
    if (keyLeft && keyRight || !keyLeft && !keyRight) {
      xSpeed *= 0.7;
    }
    else if (keyRight && hitTimer == 0) {
      xSpeed ++;
    }
    else if (keyLeft && hitTimer == 0) {
      xSpeed --;
    } else if (hitTimer != 0){
      hitTimer -= 1;
    }

    if (xSpeed > 0 && xSpeed < 0.75) {
      xSpeed = 0;
    }
    if (xSpeed < 0 && xSpeed > -0.75) {
      xSpeed = 0;
    }

    if (xSpeed > 8 && hitTimer == 0) xSpeed -= 1;
    if (xSpeed < -8 && hitTimer == 0) xSpeed += 1;

    /**Game's acceleration */
    ySpeed += 0.7;
  
    /**Players can only jump twice before hitting the ground */
    if (keyUp && jumpCounter < 2) {
      jumpCounter++;
      ySpeed = -12.5;
      keyUp = false;
    }
    /**Checks to see if the player wants to go down a platform */
    if (keyDown && platform(map, x, y) == 0){
      y += 6;
      ySpeed = 2.25;
      jumpCounter = 1;
      if (ySpeed != 0){
      }
    } 
    
    x += xSpeed;
    y += ySpeed;

    //*If the player can shoot and they decide the shoot */
    if (shoot && shootTimer == 0){
      gun.shoot(x, y, direction);
      shootTimer = gun.getFireRate();
    } else if (shootTimer != 0){
      if (shootTimer == 10){
        shoot = false;
      }
      shootTimer -= 1;
    }
    
    collision(map);
  }

  /**Player Collision Control 
   * @param map used for the location of the platforms
  */
  public void collision(String[][] map){
    if (y > 800){
      dead = true;
    }

    //If the player hits a bullet
    try{
      for (int i = 0; i < graphicsPanel.bulletList.size(); i++){
        Bullet b = (Bullet)graphicsPanel.bulletList.get(i);
        String hitDirection = collisionHandler(b);
        if (hitDirection != null){
          System.out.println("hit");
          hitTimer = (int)b.getKnockback();
          graphicsPanel.bulletList.remove(i);
          if (hitDirection.equals("left")){
            xSpeed -= b.getKnockback();
          } else if (hitDirection.equals("right")){
            xSpeed += b.getKnockback();
          }
        } 
      }
    } catch (NullPointerException e){}

    //If the player touches a crate
    try{
      Crate c = (Crate)graphicsPanel.crateList.get(0);
      if (collisionHandler(c)){
        gun = new Gun(c.gunName);
        graphicsPanel.crateList.remove(0);
      }
    } catch (NullPointerException e){
    } catch (IndexOutOfBoundsException ex){}
    

    // Variable to see how far away the player is from the platform
    int platformDistance = platform(map, x, y);
    if (platformDistance == 0 && ySpeed > 0){
      ySpeed = 0;
      jumpCounter = 0;
    } else if (platformDistance > 0 && ySpeed > 0){
      if (platformDistance < ySpeed){
        ySpeed = 0;
        jumpCounter = 0;
        y += platformDistance;
      }
    }
  }

  /** Method to return how far away the player is from a platform
   * @param map to see how close the crate is to platforms
   * @param x where the crate is relative to the map on the x-axis
   * @param y where the crate is relative to the map on the y-axis
  */
  public int platform(String[][] map, int x, int y){
    try{
      for (int i = 0; i < 6; i++){
        if (map[(y+90+(8*i))/8][x/8].equals("p") || map[(y+90+(8*i))/8][(x+45)/8].equals("p")){
          return i*8;
        }
      }
      return -1;
    } catch (IndexOutOfBoundsException e){
      return -1;
    }
  }

  /**Implementation of collisionHandler to manage bullet location plus collision, checking to see if the sides of the player and bullet overlap
   * @param b gets the bullets's size and location in order to determine a square for the collision
   * @return boolean returns true if the player is overlapping with the bullet, and false if not
  */
  public String collisionHandler(Bullet b){
    if (b.getX() > x && b.getX() < x+45 && b.getY() > y && b.getY() < y+90){
      return b.getDirection();
    } else if (b.getX()+b.getSize() > x && b.getX()+b.getSize() < x+45 && b.getY() > y && b.getY() < y+90){
      return b.getDirection();
    } else if (b.getX()+b.getSize() > x && b.getX()+b.getSize() < x+45 && b.getY()+b.getSize()> y && b.getY()+b.getSize() < y+90){
      return b.getDirection();
    } else if (b.getX() > x && b.getX() < x+45 && b.getY()+b.getSize()> y && b.getY()+b.getSize() < y+90){
      return b.getDirection();
    }
    return null;
  }
  /**Implementation of collisionHandler to manage crate location plus collision, checking to see if the sides of the player and crate overlap
   * @param c gets the crate's size and location in order to determine a square for the collision
   * @return boolean returns true if the player is overlapping with the crate, and false if not
  */
  public boolean collisionHandler(Crate c){
    if (c.getX() > x && c.getX() < x+45 && c.getY() > y && c.getY() < y+90){
      return true;
    } else if (c.getX()+c.getSize() > x && c.getX()+c.getSize() < x+45 && c.getY() > y && c.getY() < y+90){
      return true;
    } else if (c.getX()+c.getSize() > x && c.getX()+c.getSize() < x+45 && c.getY()+c.getSize()> y && c.getY()+c.getSize() < y+90){
      return true;
    } else if (c.getX() > x && c.getX() < x+45 && c.getY()+c.getSize()> y && c.getY()+c.getSize() < y+90){
      return true;
    }
    return false;
  }
  /**Character Image Settings*/
  public BufferedImage getCharModel() {
    if (direction.equals("right")) {
      gun.setImage(gun.gunName, "right");
      return imgPlayerRight;
    } else {
      gun.setImage(gun.gunName, "left");
      return imgPlayerLeft;
    }
  }

  /**Get the gun's bulletList */
  public ArrayList getBulletList(){
    return Gun.getBulletList();
  }
  
  /**Returns true if the player is off the map 
   * @return dead checks to see if the player has fallen off the map or not
  */
  public boolean getDead(){
    return dead;
  }
  /**A method to respawn the player*/
  public void respawn(){
    // max - min
    int range = 0;
    int max = 0;
    int min = 0;
    // Sets a upper and lower bound for the coordinate where the player can spawn
    if(graphicsPanel.ssmh == null) {
      min = 50;
      max = 100;
    }
    else if (graphicsPanel.ssmh.playerID == 1){
      min = 100;
      max = 650;
    } else if (graphicsPanel.ssmh.playerID == 2){
      max = 1150;
      min = 650;
    } 
    // randomNum + min
    range = max - min + 1;
    //x spawn is randomized
    x = (int)(Math.random() * range) + min;
    y = -100;
    gun = new Gun("lightGuy");
    ySpeed = 0;
    dead = false;
  }
}