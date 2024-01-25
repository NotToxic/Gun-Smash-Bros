package player;

import main.GamePanel;
import player.Player;
import guns.Crate;
import guns.Gun;
import guns.Bullet;

/**Class to Handle Collision with Platoforms and Objects */
public class collisionHandler {

  Player p;
  Crate c;

  public collisionHandler(Player player){
    this.p = player;
  }

  public collisionHandler(Crate crate){
    this.c = crate;
  }

  /**Collision in respect to Map Details */
  public void collision(String[][] map){
    if (p.y > 800){
      p.dead = true;
    }

    /**Collision with Bullets - Using Bullet Class Properties */
    try{
      for (int i = 0; i < GamePanel.bulletList.size(); i++){
        Bullet b = (Bullet)GamePanel.bulletList.get(i);
        String hitDirection = bulletCollision(b);
        if (hitDirection != null){
          System.out.println("hit");
          GamePanel.bulletList.remove(i);
          if (hitDirection.equals("left")){
            p.xSpeed -= b.getKnockback();
          } else if (hitDirection.equals("right")){
            p.xSpeed += b.getKnockback();
          }
        } 
      }
    } catch (NullPointerException e){}

    //Setting of crate functionality - getting new gun from crate collision
    try{
      Crate c = (Crate)GamePanel.crateList.get(0);
      if (crateCollision(c)){
        p.gun = new Gun(c.gunName);
        GamePanel.crateList.remove(0);
      }
    } catch (NullPointerException e){
    } catch (IndexOutOfBoundsException ex){}
    

    // Variable to see how far away the player is from the platform
    int platformDistance = platform(map, p.x, p.y);
    if (platformDistance == 0 && p.ySpeed > 0){
      p.ySpeed = 0;
      p.jumpCounter = 0;
    } else if (platformDistance > 0 && p.ySpeed > 0){
      if (platformDistance < p.ySpeed){
        p.ySpeed = 0;
        p.jumpCounter = 0;
        p.y += platformDistance;
      }
    }
  }

  //Platoform Mapping
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

  /**Bullet Collision */
  public String bulletCollision(Bullet b){
    if (b.getX() > p.x && b.getX() < p.x+45 && b.getY() > p.y && b.getY() < p.y+90){
      return b.getDirection();
    } else if (b.getX()+b.getSize() > p.x && b.getX()+b.getSize() < p.x+45 && b.getY() > p.y && b.getY() < p.y+90){
      return b.getDirection();
    } else if (b.getX()+b.getSize() > p.x && b.getX()+b.getSize() < p.x+45 && b.getY()+b.getSize()> p.y && b.getY()+b.getSize() < p.y+90){
      return b.getDirection();
    } else if (b.getX() > p.x && b.getX() < p.x+45 && b.getY()+b.getSize() > p.y && b.getY()+b.getSize() < p.y+90){
      return b.getDirection();
    }
    return null;
  }

  /**Crate Collision */
  public boolean crateCollision(Crate c){
    if (c.getX() > p.x && c.getX() < p.x+45 && c.getY() > p.y && c.getY() < p.y+90){
      return true;
    } else if (c.getX()+c.getSize() > p.x && c.getX()+c.getSize() < p.x+45 && c.getY() > p.y && c.getY() < p.y+90){
      return true;
    } else if (c.getX()+c.getSize() > p.x && c.getX()+c.getSize() < p.x+45 && c.getY()+c.getSize()> p.y && c.getY()+c.getSize() < p.y+90){
      return true;
    } else if (c.getX() > p.x && c.getX() < p.x+45 && c.getY()+c.getSize()> p.y && c.getY()+c.getSize() < p.y+90){
      return true;
    }
    return false;
  }
}