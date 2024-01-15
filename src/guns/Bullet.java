package guns;

public class Bullet {
    int size;
    double baseSpeed;
    double speed;
    int x;
    int y;
    boolean visible;
    String direction;
    double knockback;

    public void bulletMove(){
        this.x += speed;
        if (x > 1280 || x < 0){
            visible = false;
        }
    }

    public Bullet(int x, int y, String direction, double baseSpeed, int size, double knockback){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.size = size;
        this.knockback = knockback;
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

    public double getKnockback(){
        return knockback;
    }

    public int getSize(){
        return size;
    }
}
