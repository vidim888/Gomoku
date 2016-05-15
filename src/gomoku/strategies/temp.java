package gomoku.strategies;

import gomoku.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Random;

/**
 * Created by Vidim888 on 27/04/2016.
 */
public class temp {

    private static final int LIST_LENGTH = 363;
    private static Random rand = new Random();

    public static void main(String[] args) {
        System.out.println(Double.MIN_VALUE + " " + Double.MAX_VALUE);
        //Write into file.
        try {
            FileWriter writer = new FileWriter("src/gomoku/strategies/TheList.txt");
            String toWrite = "";
            for (int i = 0; i < LIST_LENGTH; i++) {
                double randomNr = rand.nextDouble();
                if (i < LIST_LENGTH - 1) {
                    toWrite += randomNr + " ";
                } else {
                    toWrite += randomNr;
                }
            }
            writer.write(toWrite);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        double[][][] smartList = new double[11][11][3];

        //Read from file.
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

}
