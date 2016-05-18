package gomoku.strategies;

import gomoku.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiConsumer;


/**
 * Created by Vidim888 on 16/05/2016.
 */
public class VadimAndEvaMariaStrategy implements ComputerStrategy {

    private static int width;

    private static int height;

    private static int empty;

    private static int ourPlayer;

    private static int otherPlayer;

    private static double tempScore;

    private int[] coords = new int[2];

    private int MAX_COUNT = 16;

    private int[][] boardScores;

    private boolean[][] boardCriticalPoints;

    private static int counter;

    private void estimateFunction(int[][] board) {
        BiConsumer<ArrayList<Integer>, Integer> analyzer = (ourList, ourIndex) -> {
            tempScore = 0;
            //System.out.println(ourList.size() + ", " + ourIndex);
            int center = ourList.get(ourIndex);
            switch (counter) { // x - other, o - our, q - not empty, . - empty, , - center
                case 0: // ,qqqq
                    if (ourList.size() - ourIndex > 4) {
                        boolean fiveLength = true;
                        int neightboor = ourList.get(ourIndex + 1);
                        if (neightboor != empty) {
                            for (int i = ourIndex + 2; i < ourIndex + 5; i++) {
                                if (ourList.get(i) != neightboor) {
                                    fiveLength = false;
                                    break;
                                }
                            }
                            if (fiveLength) {
                                if (neightboor == otherPlayer) {
                                    tempScore = 10000;
                                } else {
                                    tempScore = 20000;
                                }
                            }
                        }
                    }
                    break;
                case 1: // qqqq,
                    if (ourIndex > 3) {
                        boolean fiveLength = true;
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            for (int i = ourIndex - 2; i > ourIndex - 5; i--) {
                                if (ourList.get(i) != neightboor) {
                                    fiveLength = false;
                                    break;
                                }
                            }
                            if (fiveLength) {
                                if (neightboor == otherPlayer) {
                                    tempScore = 10000;
                                } else {
                                    tempScore = 20000;
                                }
                            }
                        }
                    }
                    break;
                case 2: // qq,qq
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex + 1) != neightboor || ourList.get(ourIndex + 2) != neightboor ||
                                    ourList.get(ourIndex - 2) != neightboor) {
                            } else {
                                if (neightboor == otherPlayer) {
                                    tempScore = 10000;
                                } else {
                                    tempScore = 20000;
                                }
                            }
                        }
                    }
                    break;
                case 3: // q,qqq
                    if (ourIndex > 0 && ourList.size() - ourIndex > 3) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex + 1) != neightboor || ourList.get(ourIndex + 2) != neightboor ||
                                    ourList.get(ourIndex + 3) != neightboor) {
                            } else {
                                if (neightboor == otherPlayer) {
                                    tempScore = 10000;
                                } else {
                                    tempScore = 20000;
                                }
                            }
                        }
                    }
                    break;
                case 4: // qqq,q
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex + 1) != neightboor || ourList.get(ourIndex - 2) != neightboor ||
                                    ourList.get(ourIndex - 3) != neightboor) {
                            } else {
                                if (neightboor == otherPlayer) {
                                    tempScore = 10000;
                                } else {
                                    tempScore = 20000;
                                }
                            }
                        }
                    }
                    break;
                case 5: // .qq,q.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 2) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex - 2) == neightboor && ourList.get(ourIndex + 1) == neightboor &&
                                    ourList.get(ourIndex - 3) == empty && ourList.get(ourIndex + 2) == empty) {
                                if (neightboor == otherPlayer) {
                                    tempScore = 5000;
                                } else {
                                    tempScore = 2000;
                                }
                            }
                        }
                    }
                    break;
                case 6: // .q,qq.
                    if (ourIndex > 1 && ourList.size() - ourIndex > 3) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex + 2) == neightboor && ourList.get(ourIndex + 1) == neightboor &&
                                    ourList.get(ourIndex - 2) == empty && ourList.get(ourIndex + 3) == empty) {
                                if (neightboor == otherPlayer) {
                                    tempScore = 5000;
                                } else {
                                    tempScore = 2000;
                                }
                            }
                        }
                    }
                    break;
                case 7: // .,qqq.
                    if (ourList.size() - ourIndex > 4 && ourIndex > 0) {
                        boolean fourLength = true;
                        //System.out.println("lol");
                        int neightboor = ourList.get(ourIndex + 1);
                        if (neightboor != empty) {
                            for (int i = ourIndex + 2; i < ourIndex + 4; i++) {
                                //System.out.println("lol2");
                                if (ourList.get(i) != neightboor) {
                                    fourLength = false;
                                    break;
                                }
                            }
                            if (fourLength && ourList.get(ourIndex - 1) == empty) {
                                //System.out.println("What?");
                                if (ourList.get(ourIndex + 4) == empty) {
                                    if (neightboor == otherPlayer) {
                                        tempScore = 5000;
                                    } else {
                                        tempScore = 2000;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case 8: // .qqq,.
                    if (ourIndex > 3 && ourList.size() - ourIndex > 1) {
                        boolean fourLength = true;
                        //System.out.println("lol");
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            for (int i = ourIndex - 2; i > ourIndex - 4; i--) {
                                //System.out.println("lol2");
                                if (ourList.get(i) != neightboor) {
                                    fourLength = false;
                                    break;
                                }
                            }
                            if (fourLength && ourList.get(ourIndex + 1) == empty) {
                                if (ourList.get(ourIndex - 4) == empty) {
                                    //System.out.println("What?");
                                    if (neightboor == otherPlayer) {
                                        tempScore = 5000;
                                    } else {
                                        tempScore = 2000;
                                    }
                                }
                            }
                        }
                    }
                    break;
                /////////////////////////////////////////// Down goes non critical cases.
                case 9: // .o,o.
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2) {
                        if (ourList.get(ourIndex + 1) == ourList.get(ourIndex - 1) && ourList.get(ourIndex - 1) != empty &&
                                ourList.get(ourIndex + 2) == empty && ourList.get(ourIndex - 2) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = 111;
                                if (ourIndex > 2 && ourList.get(ourIndex - 3) == empty || ourList.size() - ourIndex > 3 && ourList.get(ourIndex + 3) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case 10: // .oo,.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1) {
                        if (ourList.get(ourIndex - 1) == ourList.get(ourIndex - 2) && ourList.get(ourIndex - 1) != empty &&
                                ourList.get(ourIndex + 1) == empty && ourList.get(ourIndex - 3) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = 110;
                                if (ourIndex > 3 && ourList.get(ourIndex - 4) == empty || ourList.size() - ourIndex > 2 && ourList.get(ourIndex + 2) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case 11: // .,oo.
                    if (ourIndex > 0 && ourList.size() - ourIndex > 3) {
                        if (ourList.get(ourIndex + 1) == ourList.get(ourIndex + 2) && ourList.get(ourIndex + 1) != empty &&
                                ourList.get(ourIndex - 1) == empty && ourList.get(ourIndex + 3) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = 109;
                                if (ourIndex > 1 && ourList.get(ourIndex - 1) == empty || ourList.size() - ourIndex > 4 && ourList.get(ourIndex + 4) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case 12: // .x,x.
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2) {
                        if (ourList.get(ourIndex + 1) == ourList.get(ourIndex - 1) && ourList.get(ourIndex - 1) != empty &&
                                ourList.get(ourIndex + 2) == empty && ourList.get(ourIndex - 2) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = 82;
                                if (ourIndex > 2 && ourList.get(ourIndex - 3) == empty || ourList.size() - ourIndex > 3 && ourList.get(ourIndex + 3) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case 13: // .xx,.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1) {
                        if (ourList.get(ourIndex - 1) == ourList.get(ourIndex - 2) && ourList.get(ourIndex - 1) != empty &&
                                ourList.get(ourIndex + 1) == empty && ourList.get(ourIndex - 3) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = 81;
                                if (ourIndex > 3 && ourList.get(ourIndex - 4) == empty || ourList.size() - ourIndex > 2 && ourList.get(ourIndex + 2) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case 14: // .,xx.
                    if (ourIndex > 0 && ourList.size() - ourIndex > 3) {
                        if (ourList.get(ourIndex + 1) == ourList.get(ourIndex + 2) && ourList.get(ourIndex + 1) != empty &&
                                ourList.get(ourIndex - 1) == empty && ourList.get(ourIndex + 3) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = 80;
                                if (ourIndex > 1 && ourList.get(ourIndex - 1) == empty || ourList.size() - ourIndex > 4 && ourList.get(ourIndex + 4) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case 15: // o, || ,o
                    if (ourIndex > 0 && ourList.size() - ourIndex > 1) {
                        if (ourList.get(ourIndex - 1) == ourPlayer) {
                            tempScore = 20;
                        }
                        if (ourList.get(ourIndex + 1) == ourPlayer) {
                            tempScore += 20;
                        }
                    }
                    break;
                case 16:
                    int space = -1;
                    int our = 0;
                    for (int i = ourIndex; i < ourList.size(); i++) {
                        if (ourList.get(i) == otherPlayer) {
                            break;
                        } else if (ourList.get(i) == ourPlayer) {
                            our++;
                        }
                        space++;
                    }
                    for (int i = ourIndex; i >= 0; i--) {
                        if (ourList.get(i) == otherPlayer) {
                            break;
                        } else if (ourList.get(i) == ourPlayer) {
                            our++;
                        }
                        space++;
                    }
                    tempScore = 5 * space + our;
            }
        };
        ArrayList<Integer> eachRow;
        counter = 0;
        boolean toBreak = false;
        while (counter <= MAX_COUNT) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (board[i][j] == empty) {
                        for (int diagonals = 0; diagonals < 4; diagonals++) {
                            eachRow = new ArrayList<Integer>();
                            int before = 0;
                            for (int k = -5; k <= 5; k++) {
                                boolean wasInField = true;
                                if (diagonals == 0) {
                                    if (i + k >= 0 && i + k < height && j + k >= 0 && j + k < width) {
                                        eachRow.add(board[i + k][j + k]);
                                    } else {
                                        wasInField = false;
                                    }
                                } else if (diagonals == 1) {
                                    if (i + k >= 0 && i + k < height) {
                                        eachRow.add(board[i + k][j]);
                                    } else {
                                        wasInField = false;
                                    }
                                } else if (diagonals == 2) {
                                    if (i + k >= 0 && i + k < height && j - k >= 0 && j - k < width) {
                                        eachRow.add(board[i + k][j - k]);
                                    } else {
                                        wasInField = false;
                                    }
                                } else if (diagonals == 3) {
                                    if (j + k >= 0 && j + k < width) {
                                        eachRow.add(board[i][j + k]);
                                    } else {
                                        wasInField = false;
                                    }
                                }
                                if (!wasInField) {
                                    if (k < 0) {
                                        before--;
                                    }
                                }
                            }
                            //System.out.println("lol3  " + i + ", " + j + ", " + diagonals);
                            analyzer.accept(eachRow, 5 + before);
                            if (counter <= 8 && tempScore != 0) {
                                //System.out.println(tempScore);
                                boardCriticalPoints[i][j] = true;
                                boardScores[i][j] += tempScore;
                                /*coords[0] = j;
                                coords[1] = i;
                                System.out.println(i + ", " + j);
                                toBreak = true;
                                break;*/
                            } else if (counter > 8) {
                                boardScores[i][j] += tempScore;
                            }
                        }
                        if (toBreak) {
                            break;
                        }
                    }
                }
                if (toBreak) {
                    break;
                }
            }
            if (toBreak) {
                break;
            }
            counter++;
        }
        if (!toBreak) {
            int maxScore = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (boardCriticalPoints[i][j] && boardScores[i][j] > maxScore) {
                        maxScore = boardScores[i][j];
                        coords[0] = j;
                        coords[1] = i;
                        toBreak = true;
                    }
                }
            }
            if (!toBreak) {
                for (int i = 0; i < height; i++) {
                    for (int j = 0; j < width; j++) {
                        if (boardScores[i][j] > maxScore) {
                            maxScore = boardScores[i][j];
                            coords[0] = j;
                            coords[1] = i;
                            toBreak = true;
                        }
                    }
                }
            }
            while(!toBreak) {
                int row = new Random().nextInt(height);
                int col = new Random().nextInt(width);
                if (board[row][col] == empty) {
                    coords[0] = col;
                    coords[1] = row;
                    break;
                }
            }
        }
    }

    /**
     * Takes the game state and return the best move
     *
     * @param board  Board state
     * @param player Player indicator. Which player's
     *               strategy it is. Possible values: SimpleBoard.PLAYER_*.
     * @return A location where to make computer's move.
     * @see SimpleBoard
     * @see Location
     */
    @Override
    public Location getMove(SimpleBoard board, int player) {
        width = board.getWidth();
        height = board.getHeight();
        empty = SimpleBoard.EMPTY;
        ourPlayer = player;
        boardScores = new int[height][width];
        boardCriticalPoints = new boolean[height][width];
        if (SimpleBoard.PLAYER_BLACK == player) {
            otherPlayer = SimpleBoard.PLAYER_WHITE;
        } else {
            otherPlayer = SimpleBoard.PLAYER_BLACK;
        }
        int[][] b = board.getBoard();
        estimateFunction(b);
        /*for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (b[i][j] == empty) {
                    System.out.print(boardScores[i][j] + " ");
                } else if (b[i][j] == ourPlayer) {
                    System.out.print("OO ");
                } else {
                    System.out.print("XX ");
                }
            }
            System.out.println();
        }*/
        if (b[coords[1]][coords[0]] != empty) {
            System.out.println("lol  " + coords[1] + ", " + coords[0]);
            boolean toBreak = false;
            while(true) {
                int row = new Random().nextInt(height);
                int col = new Random().nextInt(width);
                if (b[row][col] == empty) {
                    coords[0] = col;
                    coords[1] = row;
                    break;
                }
            }
            /*for (int row = 0; row < height; row++) {
                for (int col = 0; col < width; col++) {
                    if (b[row][col] == SimpleBoard.EMPTY) {
                        coords[0] = col;
                        coords[1] = row;
                        toBreak = true;
                        break;
                    }
                }
                if (toBreak) {
                    break;
                }
            }*/
            System.out.println("lol2  " + coords[1] + ", " + coords[0]);
        }
        return new Location(coords[1], coords[0]);
    }

    /**
     * Name will be shown during the play.
     * This method should be overridden to
     * show student's name.
     *
     * @return Name of the player
     */
    @Override
    public String getName() {
        return "Vadim and Eva Maria";
    }
}
