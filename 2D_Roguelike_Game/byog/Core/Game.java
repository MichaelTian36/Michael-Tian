package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;

public class Game {
    private static TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;
    private static World world;
    private static String currMouseTile = "";

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public static void playWithKeyboard() {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Arial", Font.BOLD, 50));
        StdDraw.text(.5, .8, "CS61B GAME");
        StdDraw.setFont(new Font("Arial", Font.BOLD, 16));
        StdDraw.text(.5, .6, "New Game (N)");
        StdDraw.text(.5, .55, "Load Game (L)");
        StdDraw.text(.5, .5, "Quit (Q)");
        StdDraw.show();
        char select = solicitKeys(new char[] {'n', 'l', 'q'});
        if (select == 'n') {
            String seed = "";
            StdDraw.clear(Color.BLACK);
            StdDraw.text(.5, .7, "Enter seed. Press S to finalize.");
            StdDraw.show();
            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                    seed += key;
                    StdDraw.clear(Color.BLACK);
                    StdDraw.text(.5, .7, "Enter seed. Press S to finalize.");
                    StdDraw.text(.5, .5, seed);
                    StdDraw.show();
                    if (key == 's') {
                        break;
                    }
                }
            }
            StdDraw.clear(Color.BLACK);
            StdDraw.text(.5, .7, "Two player mode?");
            StdDraw.text(.5, .6, "(Y)   (N)");
            StdDraw.show();
            char yn = solicitKeys(new char[] {'y', 'n'});
            if (yn == 'y') {
                playWithInputString("2n" + seed);
            } else {
                playWithInputString("n" + seed);
            }
        } else if (select == 'l') {
            playWithInputString("l");
        } else {
            System.exit(0);
        }
        play();
    }

    private static void play() {
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(world.getWorld());
        String mouseTile = currMouseTile;
        mouseOver();
        refreshText();
        while (true) {
            mouseOver();
            if (!Game.currMouseTile.equals(mouseTile)) {
                StdDraw.clear();
                ter.renderFrame(world.getWorld());
                refreshText();
                mouseTile = currMouseTile;
            }
            if (StdDraw.hasNextKeyTyped()) {
                char action = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (action == ':') {
                    while (true) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char exit = Character.toLowerCase(StdDraw.nextKeyTyped());
                            if (exit == 'q') {
                                SavingLoading.save(world);
                                playWithKeyboard();
                            } else {
                                move(exit);
                                ter.renderFrame(world.getWorld());
                                refreshText();
                                break;
                            }
                        }
                    }
                } else {
                    move(action);
                    ter.renderFrame(world.getWorld());
                    refreshText();
                }
            }
        }
    }
    private static void refreshText() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(2, HEIGHT - .75, Game.currMouseTile);
        StdDraw.show();
    }

    private static void mouseOver() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        if (x < WIDTH && y < HEIGHT) {
            TETile t = Game.world.getWorld()[x][y];
            Game.currMouseTile = t.description();
        }
    }

    private static char solicitKeys(char[] keys) {
        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                for (char k : keys) {
                    if (key == k) {
                        return key;
                    }
                }
            }
        }
    }

    private static void move(char key) {
        switch (key) {
            case 'w':
                Game.world.movePlayer(0, 1, 1);
                return;
            case 's':
                Game.world.movePlayer(0, -1, 1);
                return;
            case 'a':
                Game.world.movePlayer(-1, 0, 1);
                return;
            case 'd':
                Game.world.movePlayer(1, 0, 1);
                return;
            default:
                break;
        }
        if (Game.world.twoP) {
            switch (key) {
                case 'i':
                    Game.world.movePlayer(0, 1, 2);
                    return;
                case 'k':
                    Game.world.movePlayer(0, -1, 2);
                    return;
                case 'j':
                    Game.world.movePlayer(-1, 0, 2);
                    return;
                case 'l':
                    Game.world.movePlayer(1, 0, 2);
                    return;
                default:
                    return;
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] playWithInputString(String input) {

        input = input.toLowerCase();

        String toHash = ""; // the string of integers that will be seeded.
        String commands = ""; // Records what direction commands are after S
        long hash;
        boolean twoP = false;

        if (input.charAt(0) == '2') { // if there's a two at the beginning, add 2 players
            twoP = true;
            input = input.substring(1);
        } //this command is needed to test input string of two players.
        if (input.charAt(0) == 'l') {
            Game.world = (World) SavingLoading.load();
            Game.world.init();
            commands = input.substring(1);
        } else { //case first = N
            //reads integers until comes to S
            for (int i = 1; i < input.length(); i++) {
                char num = input.charAt(i);
                if (num != 's') {
                    toHash += num;
                } else {
                    commands = input.substring(i);
                    break;
                }
            }
            hash = toHash.hashCode();
            Game.world = new World(WIDTH, HEIGHT, twoP, hash);
        }
        // coordinates movements after player is put at origin. Stops at :Q
        for (int i = 0; i < commands.length(); i++) {
            char key = commands.charAt(i);
            if (key == 'q') {
                SavingLoading.save(Game.world);
                break;
            }
            move(key);
        }
        return Game.world.getWorld();
    }

    public static void main(String[] args) {
        //playWithKeyboard();
        //playWithInputString("2NS:Q");
        //playWithInputString("LLLLLLL");
        //c1
        //playWithInputString("N999SDDDWWWDDD");
        //c2
        //playWithInputString("N999SDDD:Q");
        //playWithInputString("L");
        //playWithInputString("LWWWDDDKKKKKKKK");
        //c3
        //playWithInputString("2N999SDDD:Q");
        //playWithInputString("LWWW:Q");
        //playWithInputString("LDDD:Q");
        //c4
        //playWithInputString("N999SDDD:Q");
        //playWithInputString("L:Q");
        //playWithInputString("L:Q");
        //playWithInputString("LWWWDDD");

    }
}