package byog.Core;

public class Position {
    private int x;
    private int y;

    public Position(int xCoordinate, int yCoordinate) {
        this.x = xCoordinate;
        this.y = yCoordinate;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int xCoordinate) {
        x = xCoordinate;
    }

    public void setY(int yCoordinate) {
        y = yCoordinate;
    }
}