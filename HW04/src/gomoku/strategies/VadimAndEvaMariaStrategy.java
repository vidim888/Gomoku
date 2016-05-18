package gomoku.strategies;

import gomoku.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.BiConsumer;


/**
 * Gomoku AI by Vadim Shved and Eva Maria Veitmaa.
 */
public class VadimAndEvaMariaStrategy implements ComputerStrategy {
    /**
     * Game board width.
     */
    private static int width;
    /**
     * Game board height.
     */
    private static int height;
    /**
     * Empty field identifier.
     */
    private static int empty;
    /**
     * Our AI field identifier.
     */
    private static int ourPlayer;
    /**
     * Opponent's field identifier.
     */
    private static int otherPlayer;
    /**
     * Temporary score holder.
     */
    private static double tempScore;
    /**
     * Current best coordinates.
     */
    private int[] coords = new int[2];
    /**
     * Maximum loop count.
     */
    private static final int MAX_COUNT = 16;
    /**
     * Temp score.
     */
    private static final int PLAYER_TEMP_SCORE = 10000;
    /**
     * Temp score.
     */
    private static final int PLAYER_TEMP_SCORE_2 = 5000;
    /**
     * Temp score.
     */
    private static final int PLAYER_TEMP_SCORE_3 = 2000;
    /**
     * Used to replace magic nr 3.
     */
    private static final int CASE_3 = 3;
    /**
     * Used to replace magic nr 4.
     */
    private static final int CASE_4 = 4;
    /**
     * Used to replace magic nr 5.
     */
    private static final int CASE_5 = 5;
    /**
     * Used to replace magic nr 6.
     */
    private static final int CASE_6 = 6;
    /**
     * Used to replace magic nr 7.
     */
    private static final int CASE_7 = 7;
    /**
     * Used to replace magic nr 8.
     */
    private static final int CASE_8 = 8;
    /**
     * Used to replace magic nr 9.
     */
    private static final int CASE_9 = 9;
    /**
     * Used to replace magic nr 10.
     */
    private static final int CASE_10 = 10;
    /**
     * Used to replace magic nr 11.
     */
    private static final int CASE_11 = 11;
    /**
     * Used to replace magic nr 12.
     */
    private static final int CASE_12 = 12;
    /**
     * Used to replace magic nr 13.
     */
    private static final int CASE_13 = 13;
    /**
     * Used to replace magic nr 14.
     */
    private static final int CASE_14 = 14;
    /**
     * Used to replace magic nr 15.
     */
    private static final int CASE_15 = 15;
    /**
     * Used to replace magic nr 16.
     * Guys, this is getting ridiculous.
     */
    private static final int CASE_16 = 16;
    /**
     * Used to replace magic nr 111.
     */
    private static final int SCORE_1 = 111;
    /**
     * Used to replace magic nr 82.
     */
    private static final int SCORE_2 = 82;
    /**
     * Used to replace magic nr 20.
     */
    private static final int SCORE_3 = 20;
    /**
     * Used to replace magic nr -5.
     * I'm out...
     */
    private static final int MINUS_FIVE = -5;
    /**
     * Scores on board.
     */
    private int[][] boardScores;
    /**
     * Critical points on board.
     * Either those where AI would
     * win or the opponent.
     * Demand attention and action.
     */
    private boolean[][] boardCriticalPoints;
    /**
     * Case counter.
     */
    private static int counter;

    /**
     * Estimation function.
     * @param board game board
     */
    private void estimateFunction(int[][] board) {
        BiConsumer<ArrayList<Integer>, Integer> analyzer = (ourList, ourIndex) -> {
            tempScore = 0;
            //System.out.println(ourList.size() + ", " + ourIndex);
            int center = ourList.get(ourIndex);
            switch (counter) { // x - other, o - our, q - not empty, . - empty, , - center
                case 0: // ,qqqq
                    if (ourList.size() - ourIndex > CASE_4) {
                        boolean fiveLength = true;
                        int neighbour = ourList.get(ourIndex + 1);
                        if (neighbour != empty) {
                            for (int i = ourIndex + 2; i < ourIndex + CASE_5; i++) {
                                if (ourList.get(i) != neighbour) {
                                    fiveLength = false;
                                    break;
                                }
                            }
                            if (fiveLength) {
                                if (neighbour == otherPlayer) {
                                    tempScore = PLAYER_TEMP_SCORE;
                                } else {
                                    tempScore = 2 * PLAYER_TEMP_SCORE;
                                }
                            }
                        }
                    }
                    break;
                case 1: // qqqq,
                    if (ourIndex > CASE_3) {
                        boolean fiveLength = true;
                        int neighbour = ourList.get(ourIndex - 1);
                        if (neighbour != empty) {
                            for (int i = ourIndex - 2; i > ourIndex - CASE_5; i--) {
                                if (ourList.get(i) != neighbour) {
                                    fiveLength = false;
                                    break;
                                }
                            }
                            if (fiveLength) {
                                if (neighbour == otherPlayer) {
                                    tempScore = PLAYER_TEMP_SCORE;
                                } else {
                                    tempScore = 2 * PLAYER_TEMP_SCORE;
                                }
                            }
                        }
                    }
                    break;
                case 2: // qq,qq
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2) {
                        int neighbour = ourList.get(ourIndex - 1);
                        if (neighbour != empty) {
                            if (ourList.get(ourIndex + 1) != neighbour || ourList.get(ourIndex + 2) != neighbour
                                    || ourList.get(ourIndex - 2) != neighbour) {
                            } else {
                                if (neighbour == otherPlayer) {
                                    tempScore = PLAYER_TEMP_SCORE;
                                } else {
                                    tempScore = 2 * PLAYER_TEMP_SCORE;
                                }
                            }
                        }
                    }
                    break;
                case CASE_3: // q,qqq
                    if (ourIndex > 0 && ourList.size() - ourIndex > CASE_3) {
                        int neighbour = ourList.get(ourIndex - 1);
                        if (neighbour != empty) {
                            if (ourList.get(ourIndex + 1) != neighbour || ourList.get(ourIndex + 2) != neighbour
                                    || ourList.get(ourIndex + CASE_3) != neighbour) {
                            } else {
                                if (neighbour == otherPlayer) {
                                    tempScore = PLAYER_TEMP_SCORE;
                                } else {
                                    tempScore = 2 * PLAYER_TEMP_SCORE;
                                }
                            }
                        }
                    }
                    break;
                case CASE_4: // qqq,q
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1) {
                        int neighbour = ourList.get(ourIndex - 1);
                        if (neighbour != empty) {
                            if (ourList.get(ourIndex + 1) != neighbour || ourList.get(ourIndex - 2) != neighbour
                                    || ourList.get(ourIndex - CASE_3) != neighbour) {
                            } else {
                                if (neighbour == otherPlayer) {
                                    tempScore = PLAYER_TEMP_SCORE;
                                } else {
                                    tempScore = 2 * PLAYER_TEMP_SCORE;
                                }
                            }
                        }
                    }
                    break;
                case CASE_5: // .qq,q.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 2) {
                        int neighbour = ourList.get(ourIndex - 1);
                        if (neighbour != empty) {
                            if (ourList.get(ourIndex - 2) == neighbour && ourList.get(ourIndex + 1) == neighbour
                                    && ourList.get(ourIndex - CASE_3) == empty && ourList.get(ourIndex + 2) == empty) {
                                if (neighbour == otherPlayer) {
                                    tempScore = PLAYER_TEMP_SCORE_2;
                                } else {
                                    tempScore = PLAYER_TEMP_SCORE_3;
                                }
                            }
                        }
                    }
                    break;
                case CASE_6: // .q,qq.
                    if (ourIndex > 1 && ourList.size() - ourIndex > CASE_3) {
                        int neighbour = ourList.get(ourIndex - 1);
                        if (neighbour != empty) {
                            if (ourList.get(ourIndex + 2) == neighbour && ourList.get(ourIndex + 1) == neighbour
                                    && ourList.get(ourIndex - 2) == empty && ourList.get(ourIndex + CASE_3) == empty) {
                                if (neighbour == otherPlayer) {
                                    tempScore = PLAYER_TEMP_SCORE_2;
                                } else {
                                    tempScore = PLAYER_TEMP_SCORE_3;
                                }
                            }
                        }
                    }
                    break;
                case CASE_7: // .,qqq.
                    if (ourList.size() - ourIndex > CASE_4 && ourIndex > 0) {
                        boolean fourLength = true;
                        //System.out.println("lol");
                        int neighbour = ourList.get(ourIndex + 1);
                        if (neighbour != empty) {
                            for (int i = ourIndex + 2; i < ourIndex + CASE_4; i++) {
                                //System.out.println("lol2");
                                if (ourList.get(i) != neighbour) {
                                    fourLength = false;
                                    break;
                                }
                            }
                            if (fourLength && ourList.get(ourIndex - 1) == empty) {
                                //System.out.println("What?");
                                if (ourList.get(ourIndex + CASE_4) == empty) {
                                    if (neighbour == otherPlayer) {
                                        tempScore = PLAYER_TEMP_SCORE_2;
                                    } else {
                                        tempScore = PLAYER_TEMP_SCORE_3;
                                    }
                                }
                            }
                        }
                    }
                    break;
                case CASE_8: // .qqq,.
                    if (ourIndex > CASE_3 && ourList.size() - ourIndex > 1) {
                        boolean fourLength = true;
                        //System.out.println("lol");
                        int neighbour = ourList.get(ourIndex - 1);
                        if (neighbour != empty) {
                            for (int i = ourIndex - 2; i > ourIndex - CASE_4; i--) {
                                //System.out.println("lol2");
                                if (ourList.get(i) != neighbour) {
                                    fourLength = false;
                                    break;
                                }
                            }
                            if (fourLength && ourList.get(ourIndex + 1) == empty) {
                                if (ourList.get(ourIndex - CASE_4) == empty) {
                                    //System.out.println("What?");
                                    if (neighbour == otherPlayer) {
                                        tempScore = PLAYER_TEMP_SCORE_2;
                                    } else {
                                        tempScore = PLAYER_TEMP_SCORE_3;
                                    }
                                }
                            }
                        }
                    }
                    break;
                // Down go non critical cases.
                case CASE_9: // .o,o.
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2) {
                        if (ourList.get(ourIndex + 1).equals(ourList.get(ourIndex - 1))
                                && ourList.get(ourIndex - 1) != empty && ourList.get(ourIndex + 2) == empty
                                && ourList.get(ourIndex - 2) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = SCORE_1;
                                if (ourIndex > 2 && ourList.get(ourIndex - CASE_3) == empty
                                        || ourList.size() - ourIndex > CASE_3
                                        && ourList.get(ourIndex + CASE_3) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case CASE_10: // .oo,.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1) {
                        if (ourList.get(ourIndex - 1).equals(ourList.get(ourIndex - 2))
                                && ourList.get(ourIndex - 1) != empty
                                && ourList.get(ourIndex + 1) == empty && ourList.get(ourIndex - CASE_3) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = SCORE_1 - 1;
                                if (ourIndex > CASE_3 && ourList.get(ourIndex - CASE_4) == empty
                                        || ourList.size() - ourIndex > 2 && ourList.get(ourIndex + 2) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case CASE_11: // .,oo.
                    if (ourIndex > 0 && ourList.size() - ourIndex > CASE_3) {
                        if (ourList.get(ourIndex + 1).equals(ourList.get(ourIndex + 2))
                                && ourList.get(ourIndex + 1) != empty
                                && ourList.get(ourIndex - 1) == empty && ourList.get(ourIndex + CASE_3) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = SCORE_1 - 2;
                                if (ourIndex > 1 && ourList.get(ourIndex - 1) == empty
                                        || ourList.size() - ourIndex > CASE_4
                                        && ourList.get(ourIndex + CASE_4) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case CASE_12: // .x,x.
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2) {
                        if (ourList.get(ourIndex + 1).equals(ourList.get(ourIndex - 1))
                                && ourList.get(ourIndex - 1) != empty
                                && ourList.get(ourIndex + 2) == empty && ourList.get(ourIndex - 2) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = SCORE_2;
                                if (ourIndex > 2 && ourList.get(ourIndex - CASE_3) == empty
                                        || ourList.size() - ourIndex > CASE_3
                                        && ourList.get(ourIndex + CASE_3) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case CASE_13: // .xx,.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1) {
                        if (ourList.get(ourIndex - 1).equals(ourList.get(ourIndex - 2))
                                && ourList.get(ourIndex - 1) != empty && ourList.get(ourIndex + 1) == empty
                                && ourList.get(ourIndex - CASE_3) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = SCORE_2 - 1;
                                if (ourIndex > CASE_3 && ourList.get(ourIndex - CASE_4) == empty
                                        || ourList.size() - ourIndex > 2 && ourList.get(ourIndex + 2) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case CASE_14: // .,xx.
                    if (ourIndex > 0 && ourList.size() - ourIndex > CASE_3) {
                        if (ourList.get(ourIndex + 1).equals(ourList.get(ourIndex + 2))
                                && ourList.get(ourIndex + 1) != empty && ourList.get(ourIndex - 1) == empty
                                && ourList.get(ourIndex + CASE_3) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = SCORE_2 - 2;
                                if (ourIndex > 1 && ourList.get(ourIndex - 1) == empty
                                        || ourList.size() - ourIndex > CASE_4
                                        && ourList.get(ourIndex + CASE_4) == empty) {
                                    tempScore *= 2;
                                }
                            }
                        }
                    }
                    break;
                case CASE_15: // o, || ,o
                    if (ourIndex > 0 && ourList.size() - ourIndex > 1) {
                        if (ourList.get(ourIndex - 1) == ourPlayer) {
                            tempScore = SCORE_3;
                        }
                        if (ourList.get(ourIndex + 1) == ourPlayer) {
                            tempScore += SCORE_3;
                        }
                    }
                    break;
                case CASE_16:
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
                    tempScore = CASE_5 * space + our;
            }
        };
        ArrayList<Integer> eachRow;
        counter = 0;
        boolean toBreak = false;
        while (counter <= MAX_COUNT) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (board[i][j] == empty) {
                        for (int diagonals = 0; diagonals < CASE_4; diagonals++) {
                            eachRow = new ArrayList<Integer>();
                            int before = 0;
                            for (int k = MINUS_FIVE; k <= CASE_5; k++) {
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
                                } else if (diagonals == CASE_3) {
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
                            analyzer.accept(eachRow, CASE_5 + before);
                            if (counter <= CASE_8 && tempScore != 0) {
                                //System.out.println(tempScore);
                                boardCriticalPoints[i][j] = true;
                                boardScores[i][j] += tempScore;
                                /*coords[0] = j;
                                coords[1] = i;
                                System.out.println(i + ", " + j);
                                toBreak = true;
                                break;*/
                            } else if (counter > CASE_8) {
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
            while (!toBreak) {
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
     * Takes the game state and return the best move.
     *
     * @param board  Board state
     * @param player Player indicator. Which player's
     *               strategy it is. Possible values: SimpleBoard.PLAYER_*.
     * @return A location where to make computer's move.
     * @see SimpleBoard
     * @see Location
     */
    @Override
    public final Location getMove(SimpleBoard board, int player) {
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
            while (true) {
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
            //System.out.println("lol2  " + coords[1] + ", " + coords[0]);
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
    public final String getName() {
        return "Vadim and Eva Maria";
    }
}
