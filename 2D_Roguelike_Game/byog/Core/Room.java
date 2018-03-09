package byog.Core;

import byog.TileEngine.Tileset;
import byog.TileEngine.TETile;

import java.util.Random;

public class Room {

    private Random r;
    private int length;
    private int width;
    private Position p;

    Room(int l, int w, Random r) {
        length = l;
        width = w;
        this.r = r;
    }

    int getLength() {
        return length;
    }

    int getWidth() {
        return width;
    }

    private static void genLine(TETile[][] world, Position p, int l, int xdir, int ydir) {
        int y = p.getY();
        int x = p.getX();
        for (int i = 0; i < l; i++) {
            world[x][y] = Tileset.FLOOR;
            x += xdir;
            y += ydir;
        }
    }

    void genRoom(TETile[][] world, Position pos) {
        /* Don't make the dimensions less than 4x4. A 3x3 is just a hallway. */
        Position np = new Position(pos.getX(), pos.getY());
        for (int i = 0; i < length; i++) {
            genLine(world, pos, width, 1, 0);
            pos.setY(pos.getY() + 1);
        }
        this.p = np;
    }

    /* Checks if any room will occupy space from another room */
    boolean checkEmpty(TETile[][] world, Position pos2) {
        for (int i = -2; i < width + 2; i++) {
            for (int j = -2; j < length + 2; j++) {
                if (world[pos2.getX() + i][pos2.getY() + j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    /*Creates an angled or straight hallway from one room to another */
    void genHallways(TETile[][] world, Room rm) {
        int x1 = RandomUtils.uniform(r, p.getX(), p.getX() + width);
        int y1 = RandomUtils.uniform(r, p.getY(), p.getY() + length);
        int x2 = RandomUtils.uniform(r, rm.p.getX(), rm.p.getX() + rm.width);
        int y2 = RandomUtils.uniform(r, rm.p.getY(), rm.p.getY() + rm.length);
        Position one = new Position(x1, y1);
        Position two = new Position(x2, y2);
        int xLength = x2 - x1;
        int yLength = y2 - y1;
        int xp = Math.abs(xLength) + 1;
        int yp = Math.abs(yLength) + 1;
        int xDir = Math.max(-1, xLength);
        xDir = Math.min(1, xDir);
        int yDir = Math.max(-1, yLength);
        yDir = Math.min(1, yDir);
        genLine(world, one, xp, xDir, 0);
        genLine(world, two, yp, 0, -yDir);
    }
}