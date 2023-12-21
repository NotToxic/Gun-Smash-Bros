package guns;

public class Bullet {
    int size = 10;
    int speed = 10;
    int x;
    int y;

    public void bulletMove(){
        this.x += speed;
    }

    public Bullet(int x, int y){
        this.x = x;
        this.y = y;
    }
}
