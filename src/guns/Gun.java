package guns;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import player.Player;

/**Gun Class to Oriente Gun Details and Gun Types */
public class Gun{
    //Properties and Unique Bullet Features
    public BufferedImage imgGun = null;
    public String gunName;
    int bulletSize;
    double bulletSpeed;
    double bulletKnockback;
    int fireRate;

    private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    int x;
    int y;
    
    //Setting of Gun
    public Gun(String gunName){
        this.gunName = gunName;
        setStats(gunName);
        setImage(gunName, "right");
    }

    /**Shoot functionality and Method; Usage of Bullet class properties for respective gun types. */
    public void shoot(int x, int y, String direction){
        if (direction.equals("right")){
            Bullet b = new Bullet(x+47+bulletSize, y + 45, direction, bulletSpeed, bulletSize, bulletKnockback);
            System.out.println("player: " + x + ". Bullet: " + (x+bulletSize+47));
            GamePanel.bulletList.add(b);
        } else if (direction.equals("left")){
            Bullet b = new Bullet(x-bulletSize-2, y + 45, direction, bulletSpeed, bulletSize, bulletKnockback);
            GamePanel.bulletList.add(b);
            System.out.println("player: " + x + ". Bullet: " + (x-bulletSize-2));
        }
    }
    //Firerate method
    public int getFireRate(){
        return fireRate;
    }
    //Bullet List Array
    public static ArrayList getBulletList(){
        return bullets;
    }

    /**Setting of Gun features via Array based on bulletStatsCSV */
    public void setStats(String gunName){
        String[][] stats = new String[3][5];

        try (InputStream is = Gun.class.getResourceAsStream("/assets/objects/bulletStats.csv");
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader bulletStatsCSV = new BufferedReader(isr)) {

            String strLine;

            for (int i = 0; i < 3; i++) {
                strLine = bulletStatsCSV.readLine();
                if (strLine == null) {
                    break;  // End of file reached
                }
                String[] strSplit = strLine.split(",");

                for (int a = 0; a < 5; a++) {
                    stats[i][a] = strSplit[a];
                }
            }

            bulletStatsCSV.close();

        }catch(FileNotFoundException e){
        }catch(IOException e){
            System.out.println("error");
        }

        for (int i = 0; i < 3; i++){
            if (stats[i][0].equals(gunName)){
                bulletSize = Integer.parseInt(stats[i][1]);
                bulletSpeed = Double.parseDouble(stats[i][2]);
                fireRate = Integer.parseInt(stats[i][3]);
                bulletKnockback = Double.parseDouble(stats[i][4]);
                break;
            }
        }
    }
    
    /**Gun Visual's and Setting of Gun Orientation Images */
    public void setImage(String gunName, String direction) {
        if (gunName.equals("lightGuy")) {
            if (direction.equals("right")) {
                try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/objects/lightGuyRight.png")) {
                    imgGun = ImageIO.read(is);
                } catch (IOException e) {
                    System.out.println("Error reading light guy image");
                } 
            } else {
                try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/objects/lightGuyLeft.png")) {
                    imgGun = ImageIO.read(is);
                } catch (IOException e) {
                    System.out.println("Error reading light guy image");
                } 
            }
        }
        else if (gunName.equals("heavyGuy")) {
            if (direction.equals("right")) {
                try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/objects/heavyGuyRight.png")) {
                    imgGun = ImageIO.read(is);
                } catch (IOException e) {
                    System.out.println("Error reading heavy guy image");
                } 
            } else {
                try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/objects/heavyGuyLeft.png")) {
                    imgGun = ImageIO.read(is);
                } catch (IOException e) {
                    System.out.println("Error reading heavy guy image");
                } 
            }
        }
    }

    /**Gun Location In Respect to Player Location */
    public int getGunX(Player player) {
        int GunX = 0;
        if (player.direction.equals("right")) {
            GunX = player.x + player.width - 10;
        }
        else if (player.direction.equals("left")) {
            GunX = player.x - 15;
        }
        return GunX;
    }

    public int getGunY(Player player) {
        int GunY = player.y + 32;
        return GunY;
    }
}
