import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * CS18000 - Fall 2018
 * <p>
 * Project 2 - Reversi
 * <p>
 * Implementation of the game mechanics in Reversi
 *
 * @author Shreya Roy
 * @version 10/17/18
 */

public class Game {

    public Point ptP;
    private final char[][] board;
    public int wScore;
    public int bScore;
    public int remaining;
    private final char[] boardX = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};

    public Game() {
        board = new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', 'W', 'B', '_', '_', '_'},
                {'_', '_', '_', 'B', 'W', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'},
                {'_', '_', '_', '_', '_', '_', '_', '_'}};
    }

    public void displayBoard(Game b) {
        /**
         * Display the current board along with the current positions of the pieces
         * @param b The game board to be displayed
         */
        System.out.println("  1 2 3 4 5 6 7 8");
        int count = 0;
        for (int x = 0; x < b.board.length; x++) {
            System.out.print(boardX[count]);
            for (int y = 0; y < b.board[0].length; y++) {
                System.out.print(" " + b.board[x][y]);
            }
            System.out.println();
            count++;
        }
        System.out.println();
    }

    //There are three cases black win = -1, white win = 1, draw = 0

    public int gameResult(Point[] whitePlaceableLocations, Point[] blackPlaceableLocations) {
        /**
         * Update the score and determine the game result
         * @param whitePlaceableLocations Array of possible moves for white
         * @param blackPlaceableLocations Array of possible moves for black
         * @return The integer corresponding to the game result: -1 for black win, 0 for draw, 1 for white win
         */
        boolean full = true;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (!(board[x][y] == 'B' || board[x][y] == 'W'))
                    full = false;
            }
        }
        if (full || whitePlaceableLocations[0] == null && blackPlaceableLocations[0] == null) {
            if (wScore > bScore)
                return 1;
            else if (wScore == bScore)
                return 0;
            else
                return -1;
        } else
            return 2;
    }

    public Point[] getPlaceableLocations(char player, char opponent) {
        /**
         * Check the board for and fill the placeablePositions array with valid moves that the player can make
         * @param player Current player
         * @param opponent player's opponent
         * @return placeablePositions array, with corresponding point for a valid location and
         *      (-1,-1) for an invalid location
         */

        Point[] placeablePositions = new Point[64];
        int pos = 0;
        int count1 = 0;
        int count2 = 0;
        for (int x = 0; x < placeablePositions.length; x++) {
            if (board[count1][count2] == player) {
                int temp = count1;
                int temp2 = count2;
                if (count2 < 7 && board[count1][count2 + 1] == opponent) {
                    while (temp2 < 6 && board[count1][temp2 + 2] == opponent)
                        temp2++;
                    if (temp2 < 6 && board[count1][temp2 + 2] != 'B' && board[count1][temp2 + 2] != 'W') {
                        placeablePositions[pos] = new Point(count1, temp2 + 2);
                        //System.out.println("1) " + placeablePositions[pos].x + "," + placeablePositions[pos].y);
                        pos++;
                    }
                    temp2 = count2;
                }
                if (count2 > 0 && board[count1][count2 - 1] == opponent) {
                    while (temp2 > 1 && board[count1][temp2 - 2] == opponent)
                        temp2--;
                    if (temp2 > 1 && board[count1][temp2 - 2] != 'B' && board[count1][temp2 - 2] != 'W') {
                        placeablePositions[pos] = new Point(count1, temp2 - 2);
                        //System.out.println("2) " + placeablePositions[pos].x + "," + placeablePositions[pos].y);
                        pos++;
                    }
                    temp2 = count2;
                }
                if (count1 < 7 && board[count1 + 1][count2] == opponent) {
                    while (temp < 6 && board[temp + 2][count2] == opponent)
                        temp++;
                    if (temp < 6 && board[temp + 2][count2] != 'B' && board[temp + 2][count2] != 'W') {
                        placeablePositions[pos] = new Point(temp + 2, count2);
                        //System.out.println("3) " + placeablePositions[pos].x + "," + placeablePositions[pos].y);
                        pos++;
                    }
                    temp = count1;
                }
                if (count1 > 0 && board[count1 - 1][count2] == opponent) {
                    while (temp > 1 && board[temp - 2][count2] == opponent)
                        temp--;
                    if (temp > 1 && board[temp - 2][count2] != 'B' && board[temp - 2][count2] != 'W') {
                        placeablePositions[pos] = new Point(temp - 2, count2);
                        //System.out.println("4) " + placeablePositions[pos].x + "," + placeablePositions[pos].y);
                        pos++;
                    }
                    temp = count1;
                }
                if (count1 < 7 && count2 < 7 && board[count1 + 1][count2 + 1] == opponent) {
                    while (temp < 6 && temp2 < 6 && board[temp + 2][temp2 + 2] == opponent) {
                        temp++;
                        temp2++;
                    }
                    if (temp < 6 && temp2 < 6 && board[temp + 2][temp2 + 2] != 'B' &&
                            board[temp + 2][temp2 + 2] != 'W') {
                        placeablePositions[pos] = new Point(temp + 2, temp2 + 2);
                        ///System.out.println("5) " + placeablePositions[pos].x + "," + placeablePositions[pos].y);
                        pos++;
                    }
                    temp = count1;
                    temp2 = count2;
                }
                if (count2 < 7 && count1 > 0 && board[count1 - 1][count2 + 1] == opponent) {
                    while (temp > 1 && temp2 < 6 && board[temp - 2][temp2 + 2] == opponent) {
                        temp--;
                        temp2++;
                    }
                    if (temp > 1 && temp2 < 6 && board[temp - 2][temp2 + 2] != 'B' &&
                            board[temp - 2][temp2 + 2] != 'W') {
                        placeablePositions[pos] = new Point(temp - 2, temp2 + 2);
                        //System.out.println("6) " + placeablePositions[pos].x + "," + placeablePositions[pos].y);
                        pos++;
                    }
                    temp = count1;
                    temp2 = count2;
                }
                if (count1 < 7 && count2 > 0 && board[count1 + 1][count2 - 1] == opponent) {
                    while (temp < 6 && temp2 > 1 && board[temp + 2][temp2 - 2] == opponent) {
                        temp++;
                        temp2--;
                    }
                    if (temp < 6 && temp2 > 1 && board[temp + 2][temp2 - 2] != 'B' &&
                            board[temp + 2][temp2 - 2] != 'W') {
                        placeablePositions[pos] = new Point(temp + 2, temp2 - 2);
                        //System.out.println("7) " + placeablePositions[pos].x + "," + placeablePositions[pos].y);
                        pos++;
                    }
                    temp = count1;
                    temp2 = count2;
                }
                if (count1 > 0 && count2 > 0 && board[count1 - 1][count2 - 1] == opponent) {
                    while (temp > 1 && temp2 > 1 && board[temp - 2][temp2 - 2] == opponent) {
                        temp--;
                        temp2--;
                    }
                    if (temp > 1 && temp2 > 1 && board[temp - 2][temp2 - 2] != 'B' &&
                            board[temp - 2][temp2 - 2] != 'W') {
                        placeablePositions[pos] = new Point(temp - 2, temp2 - 2);
                        //System.out.println("8) " + placeablePositions[pos].x + "," + placeablePositions[pos].y);
                        pos++;
                    }
                    temp = count1;
                    temp2 = count2;
                }
            }
            count1++;
            if (count1 == 8) {
                count1 = 0;
                count2++;
            }
        }
        return placeablePositions;
    }

    public void showPlaceableLocations(Point[] locations, char player, char opponent) {
        /**
         * Display the board with valid moves for the player
         * @param locations Array containing placeable locations for the player
         * @param player Current player
         * @param opponent player's opponent
         */
        boolean run = true;
        int count = 0;
        while (run) {
            if (locations[count] == null) {
                run = false;
            } else {
                board[locations[count].x][locations[count].y] = '*';
            }
            count++;
        }
        displayBoard(this);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y] == '*')
                    board[x][y] = '_';
            }
        }
    }

    public void placeMove(Point p, char player, char opponent) {
        /**
         * Place a piece on the board at the location by the point p and update the board accordingly
         * @param p Point corresponding to the location of the piece to be placed
         * @param player Current player
         * @param opponent player's opponent
         */
        board[p.x][p.y] = player;
        int add = 1;
        if (p.y < 7 && board[p.x][p.y + 1] == opponent) {
            boolean isPlayer = false;
            while (add < 7 - p.y && board[p.x][p.y + add] == opponent) {
                add++;
                if (board[p.x][p.y + add] == player)
                    isPlayer = true;
            }
            if (isPlayer) {
                while (add > 0) {
                    board[p.x][p.y + add] = player;
                    add--;
                }
            }
            add = 1;
        }
        if (p.y > 0 && board[p.x][p.y - 1] == opponent) {
            boolean isPlayer = false;
            while (add < p.y && board[p.x][p.y - add] == opponent) {
                add++;
                if (board[p.x][p.y - add] == player)
                    isPlayer = true;
            }
            if (isPlayer) {
                while (add > 0) {
                    board[p.x][p.y - add] = player;
                    add--;
                }
            }
            add = 1;
        }
        if (p.x < 7 && board[p.x + 1][p.y] == opponent) {
            boolean isPlayer = false;
            while (add < 7 - p.x && board[p.x + add][p.y] == opponent) {
                add++;
                if (board[p.x + add][p.y] == player)
                    isPlayer = true;
            }
            if (isPlayer) {
                while (add > 0) {
                    board[p.x + add][p.y] = player;
                    add--;
                }
            }
            add = 1;
        }
        if (p.x > 0 && board[p.x - 1][p.y] == opponent) {
            boolean isPlayer = false;
            while (add < p.x && board[p.x - add][p.y] == opponent) {
                add++;
                if (board[p.x - add][p.y] == player)
                    isPlayer = true;
            }
            if (isPlayer) {
                while (add > 0) {
                    board[p.x - add][p.y] = player;
                    add--;
                }
            }
            add = 1;
        }
        if (p.x < 7 && p.y < 7 && board[p.x + 1][p.y + 1] == opponent) {
            boolean isPlayer = false;
            while (add < 7 - p.x && add < 7 - p.y && board[p.x + add][p.y + add] == opponent) {
                add++;
                if (board[p.x + add][p.y + add] == player)
                    isPlayer = true;
            }
            if (isPlayer) {
                while (add > 0) {
                    board[p.x + add][p.y + add] = player;
                    add--;
                }
            }
            add = 1;
        }
        if (p.x > 0 && p.y < 7 && board[p.x - 1][p.y + 1] == opponent) {
            boolean isPlayer = false;
            while (add < p.x && add < 7 - p.y && board[p.x - add][p.y + add] == opponent) {
                add++;
                if (board[p.x - add][p.y + add] == player)
                    isPlayer = true;
            }
            if (isPlayer) {
                while (add > 0) {
                    board[p.x - add][p.y + add] = player;
                    add--;
                }
            }
            add = 1;
        }
        if (p.x < 7 && p.y > 0 && board[p.x + 1][p.y - 1] == opponent) {
            boolean isPlayer = false;
            while (add < 7 - p.x && add < p.y && board[p.x + add][p.y - add] == opponent) {
                add++;
                if (board[p.x + add][p.y - add] == player)
                    isPlayer = true;
            }
            if (isPlayer) {
                while (add > 0) {
                    board[p.x + add][p.y - add] = player;
                    add--;
                }
            }
            add = 1;
        }
        if (p.x > 0 && p.y > 0 && board[p.x - 1][p.y - 1] == opponent) {
            boolean isPlayer = false;
            while (add < p.x && add < p.y && board[p.x - add][p.y - add] == opponent) {
                add++;
                if (board[p.x - add][p.y - add] == player)
                    isPlayer = true;
            }
            if (isPlayer) {
                while (add > 0) {
                    board[p.x - add][p.y - add] = player;
                    add--;
                }
            }
            add = 1;
        }
    }

    public void updateScores() {
        /**
         * Update the scores (number of pieces of a player's color) of each player
         */
        wScore = 0;
        bScore = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[0].length; y++) {
                if (board[x][y] == 'B')
                    bScore++;
                else if (board[x][y] == 'W')
                    wScore++;
            }
        }
    }
}