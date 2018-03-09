package byog.Core;

import byog.TileEngine.TERenderer;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class WorldGeneratorDemo {
    /*This should be bigger than 12*/
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        // initialize tiles
        World w = new World(WIDTH, HEIGHT, false, 9090);
        // draws the world to the screen
        ter.renderFrame(w.getWorld());
    }
}