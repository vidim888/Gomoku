package gomoku.strategies;

import gomoku.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
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

    private int MAX_COUNT = 15;

    private static int counter;

    private void estimateFunction(int[][] board) {
        BiConsumer<ArrayList<Integer>, Integer> analyzer = (ourList, ourIndex) -> {
            tempScore = 0;
            //System.out.println(ourList.size() + ", " + ourIndex);
            int center = ourList.get(ourIndex);
            switch (counter) { // x - other, o - our, q - not empty, . - empty, , - center
                case 0: // ,qqqq
                    if (ourList.size() - ourIndex > 4 && center == empty) {
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
                                tempScore = Double.MAX_VALUE;
                            }
                        }
                    }
                    break;
                case 1: // qqqq,
                    if (ourIndex > 3 && center == empty) {
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
                                tempScore = Double.MAX_VALUE;
                            }
                        }
                    }
                    break;
                case 2: // qq,qq
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2 && center == empty) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex + 1) != neightboor || ourList.get(ourIndex + 2) != neightboor ||
                                    ourList.get(ourIndex - 2) != neightboor) {
                            } else {
                                tempScore = Double.MAX_VALUE;
                            }
                        }
                    }
                    break;
                case 3: // q,qqq
                    if (ourIndex > 0 && ourList.size() - ourIndex > 3 && center == empty) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex + 1) != neightboor || ourList.get(ourIndex + 2) != neightboor ||
                                    ourList.get(ourIndex + 3) != neightboor) {
                            } else {
                                tempScore = Double.MAX_VALUE;
                            }
                        }
                    }
                    break;
                case 4: // qqq,q
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1 && center == empty) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex + 1) != neightboor || ourList.get(ourIndex - 2) != neightboor ||
                                    ourList.get(ourIndex - 3) != neightboor) {
                            } else {
                                tempScore = Double.MAX_VALUE;
                            }
                        }
                    }
                    break;
                case 5: // .qq,q.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 2 && center == empty) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex - 2) == neightboor && ourList.get(ourIndex + 1) == neightboor &&
                                    ourList.get(ourIndex - 3) == empty && ourList.get(ourIndex + 2) == empty) {
                                tempScore = Double.MAX_VALUE;
                            }
                        }
                    }
                    break;
                case 6: // .q,qq.
                    if (ourIndex > 1 && ourList.size() - ourIndex > 3 && center == empty) {
                        int neightboor = ourList.get(ourIndex - 1);
                        if (neightboor != empty) {
                            if (ourList.get(ourIndex + 2) == neightboor && ourList.get(ourIndex + 1) == neightboor &&
                                    ourList.get(ourIndex - 2) == empty && ourList.get(ourIndex + 3) == empty) {
                                tempScore = Double.MAX_VALUE;
                            }
                        }
                    }
                    break;
                case 7: // ,qqq.
                    if (ourList.size() - ourIndex > 4 && center == empty) {
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
                            if (fourLength) {
                                //System.out.println("What?");
                                if (ourList.get(ourIndex + 4) == empty) {
                                    tempScore = Double.MAX_VALUE;
                                }
                            }
                        }
                    }
                    break;
                case 8: // .qqq,
                    if (ourIndex > 3 && center == empty) {
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
                            if (fourLength) {
                                if (ourList.get(ourIndex - 4) == empty) {
                                    //System.out.println("What?");
                                    tempScore = Double.MAX_VALUE;
                                }
                            }
                        }
                    }
                    break;
                case 9: // .o,o.
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2 && center == empty) {
                        if (ourList.get(ourIndex + 1) == ourList.get(ourIndex - 1) && ourList.get(ourIndex - 1) != empty &&
                                ourList.get(ourIndex + 2) == empty && ourList.get(ourIndex - 2) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = 2;
                            }
                        }
                    }
                    break;
                case 10: // .oo,.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1 && center == empty) {
                        if (ourList.get(ourIndex - 1) == ourList.get(ourIndex - 2) && ourList.get(ourIndex - 1) != empty &&
                                ourList.get(ourIndex + 1) == empty && ourList.get(ourIndex - 3) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = 2;
                            }
                        }
                    }
                    break;
                case 11: // .,oo.
                    if (ourIndex > 0 && ourList.size() - ourIndex > 3 && center == empty) {
                        if (ourList.get(ourIndex + 1) == ourList.get(ourIndex + 2) && ourList.get(ourIndex + 1) != empty &&
                                ourList.get(ourIndex - 1) == empty && ourList.get(ourIndex + 3) == empty) {
                            if (ourList.get(ourIndex + 1) == ourPlayer) {
                                tempScore = 2;
                            }
                        }
                    }
                    break;
                case 12: // .x,x.
                    if (ourIndex > 1 && ourList.size() - ourIndex > 2 && center == empty) {
                        if (ourList.get(ourIndex + 1) == ourList.get(ourIndex - 1) && ourList.get(ourIndex - 1) != empty &&
                                ourList.get(ourIndex + 2) == empty && ourList.get(ourIndex - 2) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = 2;
                            }
                        }
                    }
                    break;
                case 13: // .xx,.
                    if (ourIndex > 2 && ourList.size() - ourIndex > 1 && center == empty) {
                        if (ourList.get(ourIndex - 1) == ourList.get(ourIndex - 2) && ourList.get(ourIndex - 1) != empty &&
                                ourList.get(ourIndex + 1) == empty && ourList.get(ourIndex - 3) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = 2;
                            }
                        }
                    }
                    break;
                case 14: // .,xx.
                    if (ourIndex > 0 && ourList.size() - ourIndex > 3 && center == empty) {
                        if (ourList.get(ourIndex + 1) == ourList.get(ourIndex + 2) && ourList.get(ourIndex + 1) != empty &&
                                ourList.get(ourIndex - 1) == empty && ourList.get(ourIndex + 3) == empty) {
                            if (ourList.get(ourIndex + 1) == otherPlayer) {
                                tempScore = 2;
                            }
                        }
                    }
                    break;
                case 15: // o,o
                    if (ourIndex > 0 && ourList.size() - ourIndex > 1 && center == empty) {
                        if (ourList.get(ourIndex - 1) == ourPlayer || ourList.get(ourIndex + 1) == ourPlayer) {
                            tempScore = 2;
                        }
                    }
            }
        };
        ArrayList<Integer> eachRow;
        counter = 0;
        boolean toBreak = false;
        while (counter <= MAX_COUNT) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
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
                        if (tempScore != 0) {
                            //System.out.println(tempScore);
                            coords[0] = j;
                            coords[1] = i;
                            System.out.println(i + ", " + j);
                            toBreak = true;
                            break;
                        }
                    }
                    if (toBreak) {
                        break;
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
            while(true) {
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
        if (SimpleBoard.PLAYER_BLACK == player) {
            otherPlayer = SimpleBoard.PLAYER_WHITE;
        } else {
            otherPlayer = SimpleBoard.PLAYER_BLACK;
        }
        int[][] b = board.getBoard();
        estimateFunction(b);
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
