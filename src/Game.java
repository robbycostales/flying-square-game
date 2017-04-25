import java.util.Random;

public class Game {
    private Grid grid;
    private int userRow;
    private int userCol;
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;

    public static final int NUMCOL = 10;
    public static final int NUMROW = 10;

    public static final int downPRESS = 40;
    public static final int upPRESS = 38;
    // reciprocal of new image appearance frequency
    public static final int FREQ = 10;

    public Random rand = new Random();
    public int choice;
    public int randomRow;

    public Game(int rows, int cols) {
        // grid stores and displays images
        grid = new Grid(rows, cols);
        // userRow keeps track of user's row on left edge of grid
        userRow = 0;
        // userCol keeps track of user's col
        userCol = 0;
        // since start of game
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, userCol), "user.gif");
    }

    // main game method
    public void play() {
        while (!isGameOver()) {
            grid.pause(100);
            handleKeyPress();
            if (msElapsed % 300 == 0) {
                scrollLeft();
                populateRightEdge();
            }
            updateTitle();
            msElapsed += 100;
        }
    }

    public void handleKeyPress()
    {
        int key = grid.checkLastKeyPressed();
        switch(key)
        {
            // if up-key
            case upPRESS:
                if(userRow == 0)
                {
                    break;
                } else
                {
                    grid.setImage(new Location(userRow, userCol), null);
                    userRow -= 1;
                    grid.setImage(new Location(userRow, userCol), "user.gif");
                }
                break;
            // if down-key
            case downPRESS:
                if(userRow == NUMROW-1)
                {
                    break;
                } else
                {
                    grid.setImage(new Location(userRow, userCol), null);
                    userRow += 1;
                    grid.setImage(new Location(userRow, userCol), "user.gif");
                }
                break;
            default:
                break;
        }
    }

    public void populateRightEdge() {
        choice = rand.nextInt(FREQ);
        randomRow = rand.nextInt(NUMCOL-1);
        switch(choice){
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

    public void scrollLeft() {
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