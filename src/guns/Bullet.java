package guns;

public class Bullet {
    int size = 10;
    int baseSpeed = 6;
    int speed;
    int x;
    int y;
    boolean visible;
    String direction = "right";

    public void bulletMove(){
        this.x += speed;
        if (x > 1280 || x < 0){
            visible = false;
        }
    }

    public Bullet(int x, int y, String direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
        if (this.direction.equals("right")){
            this.speed = baseSpeed;
        } else if (this.direction.equals("left")){
            this.speed = baseSpeed * -1;
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

    public String getDirection(){
        return direction;
    }
}
