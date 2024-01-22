package player;

import main.GamePanel;
import player.Player;
import guns.Crate;

public class collisionHandler {

    Player player;
    Crate crate;

    public collisionHandler(Player player){
        this.player = player;
    }

    public collisionHandler(Crate crate){
        this.crate = crate;
    }

    public void collision(String[][] map){
    if (y > 800){
      dead = true;
    }

    try{
      for (int i = 0; i < GamePanel.bulletList.size(); i++){
        Bullet b = (Bullet)GamePanel.bulletList.get(i);
        if (b.getX() > x && b.getX() < x+45 && b.getY() > y && b.getY() < y+90){
          if (b.getDirection().equals("left")){
            xSpeed -= b.getKnockback();
          } else if (b.getDirection().equals("right")){
            xSpeed += 20;
          }
          System.out.println("hit");
          GamePanel.bulletList.remove(i);
        }
      }
    } catch (NullPointerException e){}

    try{
      Crate c = (Crate)GamePanel.crateList.get(0);
      if ((c.x >= x && c.x <= x+45 && c.y >= y && c.y <= y+90) || (c.x+48 > x && c.x+48 < x+45 && c.y+45 >= y && c.y+45 <= y+90)){
        gun = new Gun(c.gunName);
        GamePanel.crateList.remove(0);
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
}
