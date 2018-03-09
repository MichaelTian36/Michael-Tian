package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

public class WorldGenerator {

    static Random r;
    private static Room[] rooms;

    /* Generates a number of random rooms in the world*/
    /*Length and Width are set granted that they are smaller than the world's size.*/
    static void randomRooms(TETile[][] world) {
        int tiles = world.length * world[0].length;
        int amount = tiles / 200;
        int n = RandomUtils.uniform(r, amount - 3, amount + 3);
        rooms = new Room[n];
        for (int i = n; i > 0; i--) {
            int lim = 50;
            int l = RandomUtils.uniform(r, 2, 11);
            int w = RandomUtils.uniform(r, 2, 11);
            Room room = new Room(l, w, r);
            int x = RandomUtils.uniform(r, 2, world.length - room.getWidth() - 1);
            int y = RandomUtils.uniform(r, 2, world[0].length - room.getLength() - 1);
            Position p = new Position(x, y);
            while (!room.checkEmpty(world, p) && lim > 0) {
                p.setX(RandomUtils.uniform(r, 2, world.length - room.getWidth() - 1));
                p.setY(RandomUtils.uniform(r, 2, world[0].length - room.getLength() - 1));
                lim--;
            }
            if (lim < 0) {
                continue;
            }
            room.genRoom(world, p);
            rooms[i - 1] = room;
        }
    }

    /*Generates hallways in a cyclic way so that everything is connected */
    static void randomHallways(TETile[][] world) {
        for (int i = 0; i < rooms.length - 1; i++) {
            rooms[i].genHallways(world, rooms[i + 1]);
        }
        rooms[rooms.length - 1].genHallways(world, rooms[0]);
    }

    /*Generates the walls surrounding floor */
    static void genWalls(TETile[][] world) {
        for (int x = 1; x < world.length - 1; x++) {
            for (int y = 1; y < world[0].length - 1; y++) {
                if (world[x][y] == Tileset.FLOOR) {
                    for (int yy = -1; yy <= 1; yy++) { // neighbor checking
                        for (int xx = -1; xx <= 1; xx++) {
                            if (world[x + xx][y + yy] == Tileset.NOTHING) {
                                world[x + xx][y + yy] = Tileset.WALL;
                            }
                        }
                    }
                }
            }
        }
    }
}