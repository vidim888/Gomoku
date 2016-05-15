package gomoku.strategies;

import gomoku.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiConsumer;

/**
 * Important!
 * This is an example strategy class.
 * You should not overwrite this.
 * Instead make your own class, for example:
 * public class AgoStrategy implements ComputerStrategy
 *
 * and add all the logic there. The created strategy
 * should be visible under player selection automatically.
 *
 * This file here might be overwritten in future versions.
 *
 */
public class VadimAndEvaStrategy implements ComputerStrategy {

    private static double[][][] smartList; // empty, me, other

    private static int[][] myBoard;

    private static void readCoef() {
        smartList = new double[11][11][3];
        FileReader fileReader = null;
        try {
            fileReader = new FileReader("src/gomoku/strategies/TheList.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            String rawInput = "";
            while ((line = bufferedReader.readLine()) != null) {
                rawInput += line;
            }
            String[] splitInput = rawInput.split(" ");
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    for (int k = 0; k < 3; k++) {
                        /*Layer 1: 0, 3, 6, ...
                          Layer 2: 1, 4, 7, ...
                          Layer 3: 2, 5, 8, ...*/
                        smartList[i][j][k] = Double.parseDouble(splitInput[33 * i + 3 * j + k]);
                    }
                }
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCoef() {
        try {
            FileWriter writer = new FileWriter("src/gomoku/strategies/TheList.txt");
            String toWrite = "";
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 11; j++) {
                    for (int k = 0; k < 3; k++) {
                        if (i == 10 && j == 10 && k == 2) {
                            toWrite += smartList[i][j][k];
                        } else {
                            toWrite += smartList[i][j][k] + " ";
                        }
                    }
                }
            }
            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int width;

    private static int height;

    private static int empty;

    private static int ourPlayer;

    private static int otherPlayer;

    private static int MAX_DEPTH = 2;

    private static double tempScore;

    private static int[] coords = new int[2];

    private static double recursionLoop(int depth, int[][] board) {
        if (depth >= MAX_DEPTH) {
            return estimateFunction(board);
        }
        double minMax;
        if (depth % 2 == 0) {
            minMax = Double.MIN_VALUE;
        } else {
            minMax = Double.MAX_VALUE;
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == empty) {
                    if (depth % 2 == 0) {
                        board[i][j] = ourPlayer;
                        double estimated = recursionLoop(depth + 1, board);
                        board[i][j] = empty;
                        if (estimated > minMax) {
                            minMax = estimated;
                            if (depth == 0) {
                                coords[0] = i;
                                coords[1] = j;
                            }
                        }
                    } else {
                        board[i][j] = otherPlayer;
                        double estimated = recursionLoop(depth + 1, board);
                        board[i][j] = empty;
                        if (estimated < minMax) {
                            minMax = estimated;
                            if (depth == 0) {
                                coords[0] = i;
                                coords[1] = j;
                            }
                        }
                    }
                }
            }
        }
        return minMax;
    }

    private static double estimateFunction(int[][] board) {
        BiConsumer<ArrayList<Integer>, Integer> analyzer = (ourList, ourIndex) -> {
            tempScore = 0;
            /*for (int i = 0; i < 5; i++) {
                int ourCounter = 0;
                int otherCounter = 0;
                for (int j = i; j < i + 5; j++) {
                    if (ourList.get(j) == ourPlayer) {
                        ourCounter++;
                    } else if (ourList.get(j) == otherPlayer) {
                        otherCounter++;
                    }
                }
                if (ourCounter == 5) {
                    tempScore = Double.MAX_VALUE;
                    break;
                } else if (otherCounter == 5) {
                    tempScore = Double.MIN_VALUE;
                    break;
                }
                tempScore += Math.pow(10, ourCounter);
                tempScore -= Math.pow(10, otherCounter);
            }*/
            
        };
        ArrayList<Integer> eachRow;
        double scoreToReturn = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int diagonals = 0; diagonals < 4; diagonals++) {
                    eachRow = new ArrayList<Integer>();
                    int indexOfCenter = 4;
                    int beforeOrAfter = 0;
                    for (int k = -4; k <= 4; k++) {
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
                            if (i - k >= 0 && i - k < height && j - k >= 0 && j - k < width) {
                                eachRow.add(board[i - k][j - k]);
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
                                beforeOrAfter = -1;
                            } else if (k > 0) {
                                beforeOrAfter = 1;
                            }
                        }
                    }
                    if (beforeOrAfter == -1) {
                        analyzer.accept(eachRow, eachRow.size() - 4);
                    } else {
                        analyzer.accept(eachRow, 4);
                    }
                    if (tempScore == Double.MAX_VALUE || tempScore == Double.MIN_VALUE) {
                        return tempScore;
                    }
                    scoreToReturn += tempScore;
                }
            }
        }
        return scoreToReturn;
    }

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
        recursionLoop(0, b);
        return new Location(coords[0], coords[1]);
        /*if (myBoard == null) {
            myBoard = b;
        } else {
            int lastTurn[] = new int[2];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (myBoard[i][j] != b[i][j]) {
                        lastTurn[0] = i;
                        lastTurn[1] = j;
                    }
                }
            }
            for (int y = -5; y <= 5; y++) {
                for (int x = -5; x <= 5; x++) {
                    if (lastTurn[0] + y >= 0 && lastTurn[0] + y < height && lastTurn[1] + x >= 0 && lastTurn[1] + x < width) {
                        if (b[lastTurn[0] + y][lastTurn[1] + x] == SimpleBoard.EMPTY) {
                            smartList[y + 5][x + 5][0] += (1 - smartList[y + 5][x + 5][0]) * 0.1;
                            smartList[y + 5][x + 5][1] -= smartList[y + 5][x + 5][1] * 0.1;
                            smartList[y + 5][x + 5][2] -= smartList[y + 5][x + 5][2] * 0.1;
                        } else if (b[lastTurn[0] + y][lastTurn[1] + x] == player) {
                            smartList[y + 5][x + 5][0] -= smartList[y + 5][x + 5][0] * 0.1;
                            smartList[y + 5][x + 5][1] += (1 - smartList[y + 5][x + 5][1]) * 0.1;
                            smartList[y + 5][x + 5][2] -= smartList[y + 5][x + 5][2] * 0.1;
                        } else {
                            smartList[y + 5][x + 5][0] -= smartList[y + 5][x + 5][0] * 0.1;
                            smartList[y + 5][x + 5][1] -= smartList[y + 5][x + 5][1] * 0.1;
                            smartList[y + 5][x + 5][2] += (1 - smartList[y + 5][x + 5][2]) * 0.1;
                        }
                    }
                }
            }
            myBoard = b;
        }
        if (smartList == null) {
            readCoef();
        } else {

            writeCoef();
        }
        double maxMulti = 0;
        int bestCoord[] = new int[2];
        for (int row = height - 1; row >= 0; row--) {
            for (int col = width - 1; col >= 0; col--) {
                if (b[row][col] == SimpleBoard.EMPTY) {
                    double tempMulti = 1;
                    for (int x = -5; x <= 5; x++) {
                        for (int y = -5; y <= 5; y++) {
                            if (x == 0 && y == 0) {

                            } else if (row + y >= 0 && row + y < height && col + x >= 0 && col + x < width) {
                                if (b[row + y][col + x] == SimpleBoard.EMPTY) {
                                    tempMulti += smartList[y + 5][x + 5][0] * (1.1 - (Math.abs(x) + Math.abs(y)) / 10.0);
                                } else if (b[row + y][col + x] == player) {
                                    tempMulti += smartList[y + 5][x + 5][1] * (1.1 - (Math.abs(x) + Math.abs(y)) / 10.0);
                                } else {
                                    tempMulti += smartList[y + 5][x + 5][2] * (1.1 - (Math.abs(x) + Math.abs(y)) / 10.0);
                                }
                            } else {
                                tempMulti += 0.1;
                            }
                        }
                    }
                    System.out.println(tempMulti);
                    if (tempMulti > maxMulti) {
                        maxMulti = tempMulti;
                        bestCoord[0] = row;
                        bestCoord[1] = col;
                    }
                }
            }
        }
        myBoard[bestCoord[0]][bestCoord[1]] = player;
        return new Location(bestCoord[0], bestCoord[1]);*/
    }

    @Override
    public String getName() {
        return "Vadim and Eva";
    }

}

