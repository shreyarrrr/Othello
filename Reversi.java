import java.util.Scanner;

/**
 * CS18000 - Fall 2018
 * <p>
 * Project 2 - Reversi
 * <p>
 * Abstraction of and launcher for a Reversi game
 *
 * @author Shreya Roy
 * @version 10/17/18
 */
public class Reversi {

    private static int turn = 0;
    private static Scanner read;
    private static boolean call = true;
    private static boolean noMoves = false;

    public static boolean isEmpty(Point[] p) {
        /**
         * Check whether any valid moves can be played
         * @param P The game board to be checked
         * @return true if there are valid moves; false if there are no valid moves
         */
        return p == null;
    }
    //Check whether a Point is the Points array or not

    public static boolean contains(Point[] points, Point p) {
        /**
         * Check whether the board already contains a point corresponding to a previous move
         * @param points The game board to be checked
         * @param p The point to be checked for validity
         * @return true if the board contains the point; false if the board does not contain the point
         */
        boolean a = false;
        for (int count = 0; count < points.length; count++) {
            if (p.equals(points[count]))
                a = true;
        }
        return a;
    }

    public static void start(Game g) {
        /**
         * Handle input corresponding to the sequence of moves entered by the user
         * Check the validity of the move, update the board, and print out the updated board after each move
         * Determine and print out the result after the game is completed
         * @param g The Reversi game currently in play
         */
        char player;
        char opponent;
        read = new Scanner(System.in);
        while (call) {
            if (turn % 2 == 0) {
                player = 'B';
                opponent = 'W';
            } else {
                opponent = 'B';
                player = 'W';
            }

            Point[] locations = g.getPlaceableLocations(player, opponent);
            Point[] otherPlay = g.getPlaceableLocations(opponent, player);
            if (locations[0] == null) {
                g.updateScores();
                int end;
                if (turn % 2 == 0)
                    end = g.gameResult(otherPlay, locations);
                else
                    end = g.gameResult(locations, otherPlay);
                if (end != 2) {
                    call = false;
                    g.showPlaceableLocations(locations, player, opponent);
                } else {
                    if (turn % 2 == 0) {
                        System.out.println("Black needs to skip... Passing to White");
                    } else {
                        System.out.println("White needs to skip... Passing to Black");
                    }
                }
                if (end == 1)
                    System.out.println("White wins: " + g.wScore + ":" + g.bScore);
                else if (end == 0)
                    System.out.println("It is a draw.");
                else if (end == -1)
                    System.out.println("White wins: " + g.wScore + ":" + g.bScore);
            } else {
                boolean run = false;
                if (isEmpty(locations))
                    call = false;
                else
                    run = true;
                g.showPlaceableLocations(locations, player, opponent);
                if (turn % 2 == 0) {
                    System.out.println("Place move (Black): row then column:");
                } else {
                    System.out.println("Place move (White): row then column:");
                }

                Point p;
                int i = 0;
                String moveS = "";
                while (run) {
                    moveS = read.next();
                    if (moveS.equals("exit")) {
                        System.out.println("Exiting!");
                        call = false;
                        run = false;
                    } else {
                        int move = Integer.parseInt(moveS);
                        boolean sel = true;
                        while (sel) {
                            if (locations[i] == null)
                                sel = false;
                            else if (locations[i].x == move / 10 - 1 && locations[i].y == move % 10 - 1) {
                                p = new Point(move / 10 - 1, move % 10 - 1);
                                g.placeMove(p, player, opponent);
                                run = false;
                            }
                            i++;
                        }
                        if (run) {
                            if (turn % 2 == 0) {
                                System.out.println("Invalid move!\nPlace move (Black): row then column:");
                            } else {
                                System.out.println("Invalid move!\nPlace move (White): row then column:");
                            }
                            i = 0;
                        }
                    }
                }
                if (!moveS.equals("exit")) {
                    g.updateScores();
                    if (turn % 2 == 0)
                        System.out.println("Black: " + g.bScore + " White: " + g.wScore + "\n");
                    else
                        System.out.println("White: " + g.wScore + " Black: " + g.bScore + "\n");

                }
            }
            turn++;
        }
    }
    public static void main(String[] args) {
        Game b = new Game();
        start(b);
    }
}