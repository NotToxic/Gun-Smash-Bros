package guns;

import java.io.*;
import java.util.ArrayList;

import main.GamePanel;

public class Gun{
    public String gunName;
    int bulletSize;
    double bulletSpeed;
    double bulletKnockback;
    int fireRate;

    private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    int x;
    int y;
    
    public Gun(String gunName){
        this.gunName = gunName;
        setStats(gunName);
    }

    public void shoot(int x, int y, String direction){
        if (direction.equals("right")){
            Bullet b = new Bullet(x+47+bulletSize, y + 45, direction, bulletSpeed, bulletSize, bulletKnockback);
            GamePanel.bulletList.add(b);
        } else if (direction.equals("left")){
            Bullet b = new Bullet(x-bulletSize-2, y + 45, direction, bulletSpeed, bulletSize, bulletKnockback);
            GamePanel.bulletList.add(b);
        }
    }

    public int getFireRate(){
        return fireRate;
    }

    public static ArrayList getBulletList(){
        return bullets;
    }

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
}
