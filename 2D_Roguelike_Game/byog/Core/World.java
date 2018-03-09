package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Random;

public class World implements Serializable {

    private int width;
    private int height;
    private TETile[][] world;
    private int[] player1;
    private int[] player2;
    private long hash;
    private Random r;
    boolean twoP;

    public World(int width, int height, boolean twoP, long hash) {
        this.width = width;
        this.height = height;
        this.hash = hash;
        this.r = new Random(hash);
        this.twoP = twoP;
        genWorld();
        genPlayer();
    }

    /*Re-initialize world for save */
    public void init() {
        this.r = new Random(this.hash); //resetting random so world will look same
        genWorld();
        world[player1[0]][player1[1]] = Tileset.P1;
        if (player2 != null) {
            world[player2[0]][player2[1]] = Tileset.P2;
        }
    }

    public TETile[][] getWorld() {
        return this.world;
    }

    private void genPlayer() {
        int x = RandomUtils.uniform(r, this.width);
        int y = RandomUtils.uniform(r, this.height);
        while (world[x][y] != Tileset.FLOOR) {
            x = RandomUtils.uniform(r, this.width);
            y = RandomUtils.uniform(r, this.height);
        }
        world[x][y] = Tileset.P1;
        this.player1 = new int[]{x, y};
        this.player1 = new int[] {x, y};

        if (this.twoP) {
            while (world[x][y] != Tileset.FLOOR) {
                x = RandomUtils.uniform(r, this.width);
                y = RandomUtils.uniform(r, this.height);
            }
            world[x][y] = Tileset.P2;
            this.player2 = new int[]{x, y};
            this.player2 = new int[] {x, y};
        }
    }

    private void genWorld() {
        this.world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                this.world[x][y] = Tileset.NOTHING;
            }
        }
        WorldGenerator.r = this.r;
        WorldGenerator.randomRooms(this.world);
        WorldGenerator.randomHallways(this.world);
        WorldGenerator.genWalls(this.world);
    }

    void movePlayer(int x, int y, int p) {
        int[] player;
        TETile playerTile;
        if (p == 1) {
            player = this.player1;
            playerTile = Tileset.P1;
        } else if (p == 2) {
            player = this.player2;
            playerTile = Tileset.P2;
        } else {
            return;
        }
        int px = player[0];
        int py = player[1];
        if (this.world[px + x][py + y] == Tileset.FLOOR) {
            this.world[px][py] = Tileset.FLOOR;
            this.world[px + x][py + y] = playerTile;
            player[0] += x;
            player[1] += y;
        }
    }
}