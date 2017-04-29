import java.util.Random;


public class Game {
    private Grid grid;
    private int userRow;
    private int userCol;
    private int msElapsed;

    //scoring variables
    private int timesGetA;
    private int timesGetB;
    private int timesGetC;
    private int aVal;
    private int bVal;
    private int cVal;
    // tallied when hits an object that should end the game
    private int timesAvoid;

    // grid dimensions
    private static final int NUMCOL = 70;
    private static final int NUMROW = 35;

    // Screen update every ___ milliseconds
    private static final int MIL = 50;
    private static final int scrollSPEED = 2;

    // controlling keys
    private static final int downPRESS = 40;
    private static final int upPRESS = 38;
    private static final int shootPRESS = 32;
    // start on spacebar == stop
    private static int curKEY = 32;

    // set images
    private static final String imUser = "blue_square.gif";
    private static final String imSHOOT = "Blank.gif" ;
    private static final String imA = "coin.gif";
    private static final String imB = "redcoin.gif";
    private static final String imC = "bluecoin.gif";
    private static final String imAvoid = "trippy.gif";

    // random variables init
    private Random rand = new Random();
    // for out of a few
    private static int choice;
    // for out of 100
    private static int probability;
    // density
    private static int density = 50;

    private Game(int rows, int cols) {
        // grid stores and displays images
        grid = new Grid(rows, cols);
        // userRow keeps track of user's row on left edge of grid
        userRow = 2;
        // userCol keeps track of user's col
        userCol = 2;

        // since start of game
        msElapsed = 0;
        timesGetA = 0;
        timesGetB = 0;
        timesGetC = 0;
        // values for each A, B, C object
        aVal = 50;
        bVal = 75;
        cVal = 750;

        timesAvoid = 0;
        updateTitle();
        redrawUser(userRow, userCol, userRow, userCol);
    }

    // main game method
    private void play() {
        // during game:
        while (!isGameOver()) {
            grid.pause(MIL);
            handleKeyPress();
            if (msElapsed % (MIL * scrollSPEED) == 0) {
                scrollLeft();
                handleProjectiles();
                populateRightEdge();
                redrawUser(userRow, userCol, userRow, userCol);
            }
            updateTitle();
            msElapsed += 100;
        }
        // after game:
        grid.frameExit();
        test();
        // open menu
    }

    // handles user collisions
    private void redrawUser(int prevRow, int prevCol, int newRow, int newCol){
        int i, j;
        // deletes in previous position - ALWAYS SHOULD HAPPEN
        for (i = -1; i <=1; ++i){
            for (j = -1; j <=1; ++j){
                if (!(grid.getImage(new Location(newRow+i, newCol+j)) == null)){
                    switch (grid.getImage(new Location(newRow+i, newCol+j))) {
                        case imAvoid:
                            timesAvoid += 1;
                            break;
                        case imA:
                            timesGetA += 1;
                            break;
                        case imB:
                            timesGetB += 1;
                            break;
                        case imC:
                            timesGetC += 1;
                            break;
                        default:
                            break;
                    }
                }
                grid.setImage(new Location(prevRow+i, prevCol+j), null);
            }
        }
        // creates in new position
        for (i = -1; i <=1; ++i){
            for (j = -1; j <=1; ++j){
                if (!(grid.getImage(new Location(newRow+i, newCol+j)) == null)){
                    switch (grid.getImage(new Location(newRow+i, newCol+j))) {
                        case imAvoid:
                            timesAvoid += 1;
                            break;
                        case imA:
                            timesGetA += 1;
                            break;
                        case imB:
                            timesGetB += 1;
                            break;
                        case imC:
                            timesGetC += 1;
                            break;
                        default:
                            break;
                    }
                }
                grid.setImage(new Location(newRow+i, newCol+j), imUser);
            }
        }
    }

    private void moveBULLET(int prevRow, int prevCol, int newRow, int newCol){
        grid.setImage(new Location(prevRow, prevCol), null);
        grid.setImage(new Location(prevRow, prevCol-1), null);

        if (newCol < NUMCOL){
            grid.setImage(new Location(newRow, newCol), imSHOOT);
            grid.setImage(new Location(newRow, newCol-1), imSHOOT);
        }
    }

    private void handleProjectiles(){
        int i, j;
        for(i = NUMROW - 1; i >= 0 ; i--) {
            for (j = NUMCOL -1; j >= 0; j--) {
                if (grid.getImage(new Location(i, j)) == imSHOOT){
                    // j-1 because we found the right pixel, and need to consider the left
                    moveBULLET(i, j, i, j+4);
                }
            }
        }
    }

    private void handleKeyPress() {
        int key = grid.checkLastKeyPressed();

        if (key == shootPRESS){
            moveBULLET(userRow, userCol+3, userRow, userCol+3);
        }

        if (key != -1 && key != shootPRESS){
            curKEY = key;
        }
        switch(curKEY) {
            // if up-key
            case upPRESS:
                // if at top of screen
                if(userRow < 2) {
                    break;
                // if running into object to avoid
                } else if (grid.getImage(new Location(userRow-1, userCol)) == imAvoid ||
                        grid.getImage(new Location(userRow-1, userCol+1)) == imAvoid ||
                        grid.getImage(new Location(userRow-1, userCol-1)) == imAvoid){
                    System.out.println("up error");
                    timesAvoid +=1;
                    redrawUser(userRow, userCol, userRow-1, userCol);
                    userRow -= 1;
                } else {
                    redrawUser(userRow, userCol, userRow-1, userCol);
                    userRow -= 1;
                }
                break;
            // if down-key
            case downPRESS:
                // if at bottom of screen
                if(userRow > NUMROW-3) {
                    break;
                // if running into object to avoid
                } else if (grid.getImage(new Location(userRow+1, userCol)) == imAvoid ||
                        grid.getImage(new Location(userRow+1, userCol+1)) == imAvoid ||
                        grid.getImage(new Location(userRow+1, userCol-1)) == imAvoid){
                    System.out.println("down error");
                    timesAvoid +=1;
                    redrawUser(userRow, userCol, userRow+1, userCol);
                    userRow += 1;
                } else {
                    redrawUser(userRow, userCol, userRow+1, userCol);
                    userRow += 1;
                }
                break;
            default:
                break;
        }
    }

    private void populateRightEdge() {
        int i;
        for ( i = 1; i <= NUMROW -2; ++i ){
            probability = rand.nextInt(100);
            choice = rand.nextInt(50);
            if (choice == 0) {
                if (0 <= probability && probability < 50) {
                    // create avoid
                    grid.setImage(new Location(i, NUMCOL-2), imAvoid);
                } else if (50 <= probability && probability < 80){
                    // create imA
                    grid.setImage(new Location(i, NUMCOL-2), imA);
                } else if (80 <= probability && probability < 99){
                    // create imB
                    grid.setImage(new Location(i, NUMCOL-2), imB);
                }
                else if (99 <= probability && probability < 100){
                    // create imC
                    grid.setImage(new Location(i, NUMCOL-2), imC);
                }
            }
        }
    }

    private void scrollLeft(){
        int i, j;
        // goes through all points on screen
        for(i = 0; i < NUMROW; i++){
            for (j = 0; j < NUMCOL; j++){
                // if at way left of screen, it disappears
                if (j == 0) {
                    grid.setImage(new Location(i, j), null);
                // if the location is null, we don't need to do anything
                } else if (grid.getImage(new Location(i, j)) == null) {
                // if not null, and not at way left:
                } else {
                    switch (grid.getImage(new Location(i, j))) {
                        case imAvoid:
                            grid.setImage(new Location(i, j), null);
                            grid.setImage(new Location(i, j - 1), imAvoid);
                            break;
                        case imA:
                            grid.setImage(new Location(i, j), null);
                            grid.setImage(new Location(i, j - 1), imA);
                            break;
                        case imB:
                            grid.setImage(new Location(i, j), null);
                            grid.setImage(new Location(i, j - 1), imB);
                            break;
                        case imC:
                            grid.setImage(new Location(i, j), null);
                            grid.setImage(new Location(i, j - 1), imC);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    private int getScore() {
        // dot product of values of objects and the amount of times each has been collected
        return aVal*timesGetA + bVal*timesGetB + cVal*timesGetC + getTime()*5;
    }

    private int getTime() {
        return msElapsed / 1000;
    }

    private void updateTitle() {
        grid.setTitle("Game:  " + getScore() + " points" + "   |   Duration: " + getTime() + " seconds" );
    }

    private boolean isGameOver() {
        if (timesAvoid < 1) {
            return false;
        } else {
            return true;
        }
    }

    private static void test() {
        Game game = new Game(NUMROW, NUMCOL);
        game.play();
    }

    public static void main(String[] args) {
        test();
    }
}