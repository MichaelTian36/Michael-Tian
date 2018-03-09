package byog.Core;

import java.util.Scanner;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {


    public static void main(String[] args) {

        // System objects
        Scanner in = new Scanner(System.in);

        if (args.length > 1) {
            System.out.println("Can only have one argument - the input string");
            System.exit(0);
        } else if (args.length == 1) {
            Game.playWithInputString(args[0]);
        } else {
            Game.playWithKeyboard();
        }
    }
}