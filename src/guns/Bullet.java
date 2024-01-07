package guns;

public class Bullet {
    int size = 10;
    int speed = 10;
    int x;
    int y;
    boolean visible;

    public void bulletMove(){
        this.x += speed;
        if (x > 1280 || x < 0){
            visible = false;
        }
    }

    public Bullet(int x, int y, String direction){
        this.x = x;
        this.y = y;
        if (direction.equals("right")){
            this.speed = 10;
        } else if (direction.equals("left")){
            this.speed = -10;
        }
        visible = true;
    }

    public boolean isVisible(){
        return visible;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
