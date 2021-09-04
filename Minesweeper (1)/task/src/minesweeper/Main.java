package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static final Random random = new Random();
    static final int FIELD_SIZE = 11;

    static char[][] FIELD = new char[FIELD_SIZE][FIELD_SIZE];

    public static void main(String[] args) {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                FIELD[i][j] = '.';
            }
        }

        generateField();
        findMines();
        printField();
        //printEntireField();
    }

    public static void generateField() {
        System.out.print("How many mines do you want? ");
        int numberOfMines = scanner.nextInt();

        int minesPlaced = 0;
        do {
            int i = random.nextInt(9) + 1;
            int j = random.nextInt(9) + 1;
            if (FIELD[i][j] != 'X') {
                FIELD[i][j] = 'X';
                minesPlaced++;
            }
        } while (minesPlaced < numberOfMines);
    }

    public static void printField() {
        for (int i = 1; i < FIELD_SIZE - 1; i++) {
            for (int j = 1; j < FIELD_SIZE - 1; j++) {
                System.out.print(FIELD[i][j]);
            }
            System.out.println();
        }
    }

    public static void printEntireField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(FIELD[i][j]);
            }
            System.out.println();
        }
    }

    public static void findMines() {
        for (int i = 1; i < FIELD_SIZE - 1; i++) {
            for (int j = 1; j < FIELD_SIZE - 1; j++) {
                if (FIELD[i][j] != 'X') {
                    FIELD[i][j] = checkSurroundings(i, j);
                }
            }
        }
    }

    public static boolean isMine(char c) {
        return c == 'X';
    }

    public static char checkSurroundings(int x, int y) {
        int count = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (isMine(FIELD[i][j])) {
                    count++;
                }
            }
        }
        return count == 0 ? '.' : (char) ('0' + count);
    }
}
