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

    /**Constructs a bullet.
     * @param x the x position of the bullet
     * @param y the y position of the bullet
     * @param direction the direction the bullet is going in
     * @param baseSpeed the baseSpeed of the bullet
     * @param size how large the bullet is
     * @param knockback how far the player gets knockedback when hit
    */
    public Bullet(int x, int y, String direction, double baseSpeed, int size, double knockback){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.size = size;
        this.knockback = knockback;
        if (this.direction.equals("right")){
            this.speed = baseSpeed;
        } else if (this.direction.equals("left")){
            //multiply the basespeed by -1 so that it heads in the opposite direction
            this.speed = baseSpeed * -1;
        }
        visible = true;
    }

    /**Returns the visible variable for this bullet 
     * @return visible which determins if the bullet is off the screen or not
    */
    public boolean isVisible(){
        return visible;
    }

    /**Returns the x position of this bullet 
     * @return x coordinate of the bullet
    */
    public int getX(){
        return x;
    }

    /**Returns the y position of this bullet 
     * @return y coordinate of the bullet
    */
    public int getY(){
        return y;
    }

    /**Returns the direction of this bullet
     * @return direction which is which direction the bullet is heading towrads
    */
    public String getDirection(){
        return direction;
    }

    /**Returns the knockback of this bullet
     * @return knockback which is how far the player gets moved whenever they get hit
     */
    public double getKnockback(){
        return knockback;
    }

    /**Returns the size of this bullet
     * @return size which is how large the bullet is
     */
    public int getSize(){
        return size;
    }
}
