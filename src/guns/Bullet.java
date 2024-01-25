package guns;

/**Bullet Class - Holds Unique Set Bullet Qualities and Functionality - Set Via Gun Class. */
public class Bullet {
    //*The size of the bullet.*/
    int size;
    //*The baseSpeed of the bullet. */
    double baseSpeed;
    //*The constructed speed of the bullet. */
    double speed;
    //*The x position of the bullet. */
    int x;
    //*The y position of the bullet. */
    int y;
    //*The variable which indicates if the bullet is off screen. */
    boolean visible;
    //*indicates which direction the bullet is heading in. */
    String direction;
    //*Indicates how far the player will be knocked back when hit. */
    double knockback;

    /**Bullet movement method. */
    public void bulletMove(){
        this.x += speed;
        if (x > 1280 || x < 0){
            visible = false;
        }
    }

    /**Constructs a bullet.*/
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

    /**Returns the visible variable for this bullet. */
    public boolean isVisible(){
        return visible;
    }

    /**Returns the x position of this bullet. */
    public int getX(){
        return x;
    }

    /**Returns the y position of this bullet. */
    public int getY(){
        return y;
    }

    /**Returns the direction of this bullet. */
    public String getDirection(){
        return direction;
    }

    /**Returns the knockback of this bullet. */
    public double getKnockback(){
        return knockback;
    }

    /**Returns the size of this bullet. */
    public int getSize(){
        return size;
    }
}
