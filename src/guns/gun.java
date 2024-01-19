package guns;

import java.io.*;
import java.util.ArrayList;

import main.GamePanel;

public class Gun{
    String gunName;
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
            Bullet b = new Bullet(x + 60, y + 45, direction, bulletSpeed, bulletSize, bulletKnockback);
            GamePanel.bulletList.add(b);
        } else if (direction.equals("left")){
            Bullet b = new Bullet(x - 15, y + 45, direction, bulletSpeed, bulletSize, bulletKnockback);
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
        BufferedReader bulletStatsCSV;
        String strLine;
        String strSplit[];
        String[][] stats = new String[3][5];

        try{
            bulletStatsCSV = new BufferedReader(new FileReader("Gun-Smash-Bros/src/bulletStats.csv"));
            for(int i = 0; i < 3; i++){
                strLine = bulletStatsCSV.readLine();
                strSplit = strLine.split(",");

                for(int a = 0; a < 5; a++){
                    stats[i][a] = strSplit[a];   
                }
            }

            bulletStatsCSV.close();

        }catch(FileNotFoundException e){
            System.out.println("Mac file not found");
        }catch(IOException e){
            System.out.println("error");
        }

        try{
            bulletStatsCSV = new BufferedReader(new FileReader("src/bulletStats.csv"));
            for(int i = 0; i < 3; i++){
            strLine = bulletStatsCSV.readLine();
            strSplit = strLine.split(",");

            for(int a = 0; a < 5; a++){
                stats[i][a] = strSplit[a];
            }

            }

            bulletStatsCSV.close();

        }catch(FileNotFoundException e){
            System.out.println("Windows file not found");
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
