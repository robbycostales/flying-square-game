public class Game {
    private Grid grid;
    private int userRow;
    private int msElapsed;
    private int timesGet;
    private int timesAvoid;

    public Game(int rows, int cols) {
        grid = new Grid(rows, cols);
        userRow = 0;
        msElapsed = 0;
        timesGet = 0;
        timesAvoid = 0;
        updateTitle();
        grid.setImage(new Location(userRow, 0), "user.gif");
    }

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

    public void handleKeyPress() {
    }

    public void populateRightEdge() {
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
        Game game = new Game(5,10);
        game.play();
    }

    public static void main(String[] args) {
        test();
    }
}