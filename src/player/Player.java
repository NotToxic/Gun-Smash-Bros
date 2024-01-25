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

  //
  private GraphicsPanel graphicsPanel;

  BufferedImage imgPlayerLeft = null;
  BufferedImage imgPlayerRight = null;

  public int x;
  public int y;
  public int width;
  public int height;
  Rectangle hitbox;

  double xSpeed;
  double ySpeed;

  public boolean keyRight;
  public boolean keyLeft;
  public boolean keyUp;
  public boolean keyDown;

  public String direction = "right";
  public boolean shoot;
  public int shootTimer;

  public boolean doubleJumped = false;

  public boolean dead = false;
  public Timer deathTimer;
  public int lives = 5;
  public int hitTimer = 0;

  public int jumpCounter = 0;

  public Gun gun = new Gun("lightGuy");

  public Player(int x, int y, int width, int height, GraphicsPanel graphicsPanel) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.hitbox = new Rectangle(x, y, width, height);
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

  public void move(String[][] map) {
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

    ySpeed += 0.7;
  
    if (keyUp && jumpCounter < 2) {
      jumpCounter++;
      ySpeed = -12.5;
      keyUp = false;
    }
    if (keyDown && platform(map, x, y) == 0){
      y += 6;
      ySpeed = 2.25;
      jumpCounter = 1;
      if (ySpeed != 0){
      }
    } 
    
    x += xSpeed;
    y += ySpeed;

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

    hitbox.x = x;
    hitbox.y = y;
  }

  /**Player Collision Control */
  public void collision(String[][] map){
    if (y > 800){
      dead = true;
    }

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

  //Implementation of collisionHandler to manage bullet location plus collision
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
  //Implementation of collisionHandler to manage crate location plus collision
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
  //Character Image Setting
  public BufferedImage getCharModel() {
    if (direction.equals("right")) {
      gun.setImage(gun.gunName, "right");
      return imgPlayerRight;
    } else {
      gun.setImage(gun.gunName, "left");
      return imgPlayerLeft;
    }
  }

  public ArrayList getBulletList(){
    return Gun.getBulletList();
  }
  
  //Death
  public boolean getDead(){
    return dead;
  }
  //Respwan Method
  public void respawn(){
    // max - min
    int range = 0;
    int max = 0;
    int min = 0;
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
    x = (int)(Math.random() * range) + min;
    y = -100;
    gun = new Gun("lightGuy");
    ySpeed = 0;
    dead = false;
  }
}