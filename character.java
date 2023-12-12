public class character {
    // Properties
    int intPosX;
    int intPosY;
    int intDefX;
    int intDefY;
    int intJumpCounter = 0;

    // Methods
    
    // Movement in the x-axis
    public void deflectionY(char charKeyPressed, boolean blnKeyReleased){
        if (blnKeyReleased && charKeyPressed == 'w'){
            if (charKeyPressed == 'w' && intJumpCounter < 2){
                intJumpCounter++;
                intDefY = -200;
                movement();
                intDefY = 0;
            } 
        }
    }
    public void deflectionX(char charKeyPressed, boolean blnKeyReleased){
        if (blnKeyReleased && (charKeyPressed == 'a' || charKeyPressed == 'd')){
            intDefX = 0;
        } else {
            if (charKeyPressed == 'd'){
                intDefX = 10;
            }
            if (charKeyPressed == 'a'){
                intDefX = -10;
            }
        }    
    }

    public void movement(){
        intPosX += intDefX;
        intPosY += intDefY;
        if (intPosY < 630){
            intDefY += 1;
        } 
        else if (intPosY >= 630){
            intDefY = 0;
            intJumpCounter = 0;
        }
    }

    public void outOfBounds(){
        if (intPosY <= 0){
            intPosY = 0;
        } else if (intPosY >= 630){
            intPosY = 630;
        }
        if (intPosX <= 0){
            intPosX = 0;
        } else if (intPosX >= 1230){
            intPosX = 1230;
        }

    }

    // Constructor
    public character(int intPosX, int intPosY){
        this.intPosX = intPosX;
        this.intPosY = intPosY;
    }
}
