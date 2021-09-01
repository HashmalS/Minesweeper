package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static final Random random = new Random();

    public static void main(String[] args) {
        char[][] field = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = '.';
            }
        }

        System.out.print("How many mines do you want? ");
        int numberOfMines = scanner.nextInt();

        int minesPlaced = 0;
        do {
            int i = random.nextInt(8);
            int j = random.nextInt(8);
            if (field[i][j] != 'X') {
                field[i][j] = 'X';
                minesPlaced++;
            }
        } while (minesPlaced < numberOfMines);



        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }
}
