package guns;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import player.Player;

/**Gun Class to Oriente Gun Details and Gun Types */
public class Gun{ 
    
    /**The BufferedImage of the gun. */
    public BufferedImage imgGun = null;
    /**The name of the gun. */
    public String gunName;
    /**The size of the bullets shot by the gun. */
    int bulletSize;
    /**The speed the bullets fly at. */
    double bulletSpeed;
    /**The knockback of the bullets shot. */
    double bulletKnockback;
    /**How many frames before the next shot. */
    int fireRate;

    /**The bullet list of the gun. */
    private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    int x;
    int y;
    
    /**Create a standard gun
     * @param gunName The name of the gun, which leads to different stats
    */
    public Gun(String gunName){
        this.gunName = gunName;
        setStats(gunName);
        setImage(gunName, "right");
    }

    /**Shoot functionality and Method; Usage of Bullet class properties for respective gun types
     * @param x where the bullet shoots from on the x axis
     * @param y where the bullet shoots from on the y axis
     * @param direction which way the bullets goes
     */
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
    
    /**Returns the fireRate of the gun 
     * @return fireRate is how long it takes before you getto shoot another bullet
    */
    public int getFireRate(){
        return fireRate;
    }
    /**Returns the bulletList of the gun
     * @return bullets is the list of bullets on the screen
    */
    public static ArrayList getBulletList(){
        return bullets;
    }

    /**Setting of Gun features via Array based on bulletStatsCSV 
     * @param gunName get the name of the gun for different stats
    */
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
    
    /**Gun Visual's and Setting of Gun Orientation Images 
     * @param gunName the name of the gun
     * @param direction the direction the gun is facing in to load a different image
    */
    public void setImage(String gunName, String direction) {
        if (gunName.equals("lightGuy")) {
            if (direction.equals("right")) {
                try (InputStream is = Gun.class.getClassLoader().getResourceAsStream("./assets/objects/lightGuyRight.png")) {
                    imgGun = ImageIO.read(is);
                } catch (IOException e) {
                    System.out.println("Error reading light guy image");
                } 
                if (imgGun == null){
                    try{
                        imgGun = ImageIO.read(new File("/assets/objects/crate.png"));
                    }catch(IOException e){
                        System.out.println("cannot find gun image");
                    }
                }
            } else {
                try (InputStream is = Gun.class.getClassLoader().getResourceAsStream("./assets/objects/lightGuyLeft.png")) {
                    imgGun = ImageIO.read(is);
                } catch (IOException e) {
                    System.out.println("Error reading light guy image");
                } 
                if (imgGun == null){
                    try{
                        imgGun = ImageIO.read(new File("/assets/objects/crate.png"));
                    }catch(IOException e){
                        System.out.println("cannot find gun image");
                    }
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
                if (imgGun == null){
                    try{
                        imgGun = ImageIO.read(new File("/assets/objects/crate.png"));
                    }catch(IOException e){
                        System.out.println("cannot find gun image");
                    }
                }
            } else {
                try (InputStream is = GamePanel.class.getClassLoader().getResourceAsStream("./assets/objects/heavyGuyLeft.png")) {
                    imgGun = ImageIO.read(is);
                } catch (IOException e) {
                    System.out.println("Error reading heavy guy image");
                } 
                if (imgGun == null){
                    try{
                        imgGun = ImageIO.read(new File("/assets/objects/crate.png"));
                    }catch(IOException e){
                        System.out.println("cannot find gun image");
                    }
                }
            }
        }
    }

    /**Returns the xPosition of the gun in Respect to Player Location 
     * @return x the x position of the gun
     * @param player used for the player's position
    */
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

    /**Returns the yPosition of the gun relative to the player 
        * @return y the y position of the gun
        * @param player used for the player's position
    */
    public int getGunY(Player player) {
        int GunY = player.y + 32;
        return GunY;
    }
}
