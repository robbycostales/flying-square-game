import java.util.Random;
import java.util.Timer;

// IDEAS:

// Larger user - takes up more than one cell
// User can shoot lasers (but not every screen update

// meteors come at user - lasers turn them into "more broken" meteors - will be destroyed at some point
//






public class Game {
    private Grid grid;
    private int userRow;
    private int userCol;
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;

    // grid dimensions
    public static final int NUMCOL = 40;
    public static final int NUMROW = 40;

    // Screen update every ___ milliseconds
    public static final int MIL = 50;
    public static final int scrollSPEED = 2;

    // controlling keys
    public static final int downPRESS = 40;
    public static final int upPRESS = 38;
    public static final int shootPRESS = 32;
    // start on spacebar == stop
    public static int curKEY = 32;
    // reciprocal of new image appearance frequency
    public static final int FREQ = 4;

    // set images
    public String imU11 = "blue_square.gif";
    public String imU12 = "blue_square.gif";
    public String imU13 = "blue_square.gif";
    public String imU21 = "blue_square.gif";
    public String imU22 = "blue_square.gif";
    public String imU23 = "blue_square.gif";
    public String imU31 = "blue_square.gif";
    public String imU32 = "blue_square.gif";
    public String imU33 = "blue_square.gif";

    public String imSHOOT = "Blank.gif" ;


    // random variables init
    public Random rand = new Random();
    public int choice;
    public int randomRow;

    public Game(int rows, int cols) {
        // grid stores and displays images
        grid = new Grid(rows, cols);
        // userRow keeps track of user's row on left edge of grid
        userRow = 2;
        // userCol keeps track of user's col
        userCol = 2;
        // since start of game
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        redrawUser(userRow, userCol, userRow, userCol);
    }

    // main game method
    public void play() {
        while (!isGameOver()) {
            grid.pause(MIL);
            handleKeyPress();
            if (msElapsed % (MIL * scrollSPEED) == 0) {
                scrollLeft();
                handleProjectiles();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }
    }

    public void redrawUser(int prevRow, int prevCol, int newRow, int newCol){
        // deletes in previous position
        grid.setImage(new Location(prevRow, prevCol), null);
        grid.setImage(new Location(prevRow+1, prevCol), null);
        grid.setImage(new Location(prevRow-1, prevCol), null);
        grid.setImage(new Location(prevRow, prevCol+1), null);
        grid.setImage(new Location(prevRow+1, prevCol+1), null);
        grid.setImage(new Location(prevRow-1, prevCol+1), null);
        grid.setImage(new Location(prevRow, prevCol-1), null);
        grid.setImage(new Location(prevRow+1, prevCol-1), null);
        grid.setImage(new Location(prevRow-1, prevCol-1), null);
        // creates in new position
        grid.setImage(new Location(newRow-1, newCol-1), imU11);
        grid.setImage(new Location(newRow-1, newCol), imU12);
        grid.setImage(new Location(newRow-1, newCol+1), imU13);
        grid.setImage(new Location(newRow, newCol-1), imU21);
        grid.setImage(new Location(newRow, newCol), imU22);
        grid.setImage(new Location(newRow, newCol+1), imU23);
        grid.setImage(new Location(newRow+1, newCol-1), imU31);
        grid.setImage(new Location(newRow+1, newCol), imU32);
        grid.setImage(new Location(newRow+1, newCol+1), imU33);
    }

    public void moveBULLET(int prevRow, int prevCol, int newRow, int newCol){
        grid.setImage(new Location(prevRow, prevCol), null);
        grid.setImage(new Location(prevRow, prevCol-1), null);

        if (newCol < NUMCOL){
            grid.setImage(new Location(newRow, newCol), imSHOOT);
            grid.setImage(new Location(newRow, newCol-1), imSHOOT);
        }
    }

    public void handleProjectiles(){
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

    spawnEnemy



    public void handleKeyPress()
    {
        int key = grid.checkLastKeyPressed();

        if (key == shootPRESS){
            moveBULLET(userRow, userCol+3, userRow, userCol+3);
        }

        if (key != -1 && key != shootPRESS){
            curKEY = key;
        }

        System.out.println(key);
        switch(curKEY)
        {
            case upPRESS:
                if(userRow < 2)
                {
                    break;
                } else
                {
//                    grid.setImage(new Location(userRow, userCol), null);
//                    userRow -= 1;
//                    grid.setImage(new Location(userRow, userCol), "user.gif");
                    redrawUser(userRow, userCol, userRow-1, userCol);
                    userRow -= 1;
                }
                break;
            // if down-key
            case downPRESS:
                if(userRow > NUMROW-3)
                {
                    break;
                } else
                {
//                    grid.setImage(new Location(userRow, userCol), null);
//                    userRow += 1;
//                    grid.setImage(new Location(userRow, userCol), "user.gif");
                    redrawUser(userRow, userCol, userRow+1, userCol);
                    userRow += 1;
                }
                break;
            default:
                break;
        }
    }

    public void populateRightEdge()
    {
        choice = rand.nextInt(FREQ);
        // bound includes 0 to NUMCOL-1
        randomRow = rand.nextInt(NUMCOL);
        switch(choice)
        {
            case 0:
                grid.setImage(new Location(randomRow, NUMCOL-1), "avoid.gif");
                break;
            case 1:
                grid.setImage(new Location(randomRow, NUMCOL-1), "get.gif");
                break;
            default:
                break;
        }
    }

    public void scrollLeft(){
        int i, j;
        for(i = 0; i < NUMROW; i++){
            for (j = 0; j < NUMCOL; j++){
                // if at way left of screen, it disappears
                if (j == 0) {
                    // if not the user
                    if (!("user.gif").equals(grid.getImage(new Location(i, j)))) {
                        // make disappear
                        grid.setImage(new Location(i, j), null);
                    }
                } else if (grid.getImage(new Location(i, j)) == null) {
                    continue;
                } else {
                    switch (grid.getImage(new Location(i, j))) {
                        case "get.gif":
                            grid.setImage(new Location(i, j), null);
                            grid.setImage(new Location(i, j - 1), "get.gif");
                            break;
                        case "avoid.gif":
                            grid.setImage(new Location(i, j), null);
                            grid.setImage(new Location(i, j - 1), "avoid.gif");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void handleCollision(Location loc) {
    }

    public int getScore() {
        return 0;
    }

    public void updateTitle() {
        grid.setTitle("Game:  " + getScore());
    }

    public boolean isGameOver() {
        return false;
    }

    public static void test() {
        Game game = new Game(NUMROW, NUMCOL);
        game.play();
    }

    public static void main(String[] args) {
        test();
    }
}