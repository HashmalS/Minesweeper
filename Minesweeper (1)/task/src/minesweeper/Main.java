package minesweeper;

import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {
    enum Status {
        DEFAULT('.'),
        MINE('X'),
        OPEN('/'),
        NUMBER('1'),
        BORDER('-'),
        MARKED('*');

        char displayedCharacter;

        Status(char displayedCharacter) {
            this.displayedCharacter = displayedCharacter;
        }
    }

    static final Scanner scanner = new Scanner(System.in);
    static final Random random = new Random();
    static final int FIELD_SIZE = 11;
    static int NUMBER_OF_MINES;

    static char[][] FIELD = new char[FIELD_SIZE][FIELD_SIZE];
    static Status[][] STATUSES = new Status[FIELD_SIZE][FIELD_SIZE];

    public static void main(String[] args) {
        generateField();
        findMines();
        hideMines();
        //printStatuses();
        gameLoop();
        //printField();
        //printEntireField();
    }

    public static void generateField() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                FIELD[i][j] = '.';
                STATUSES[i][j] = Status.DEFAULT;
            }
        }

        for (int i = 0; i < FIELD_SIZE; i++) {
            STATUSES[0][i] = Status.BORDER;
            STATUSES[FIELD_SIZE - 1][i] = Status.BORDER;
            STATUSES[i][0] = Status.BORDER;
            STATUSES[i][FIELD_SIZE - 1] = Status.BORDER;
        }

        System.out.print("How many mines do you want? ");
        NUMBER_OF_MINES = scanner.nextInt();

        int minesPlaced = 0;
        do {
            int i = random.nextInt(9) + 1;
            int j = random.nextInt(9) + 1;
            if (FIELD[i][j] != 'X') {
                FIELD[i][j] = 'X';
                STATUSES[i][j] = Status.MINE;
                minesPlaced++;
            }
        } while (minesPlaced < NUMBER_OF_MINES);
    }

    public static void printField() {
        System.out.println(" |123456789|\n-|---------|");
        for (int i = 1; i < FIELD_SIZE - 1; i++) {
            System.out.printf("%d|", i);
            for (int j = 1; j < FIELD_SIZE - 1; j++) {
                System.out.print(STATUSES[i][j] != Status.MINE ? FIELD[i][j] : '.');
                //System.out.print(FIELD[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    public static void printStatuses() {
        System.out.println(" |123456789|\n-|---------|");
        for (int i = 1; i < FIELD_SIZE - 1; i++) {
            System.out.printf("%d|", i);
            for (int j = 1; j < FIELD_SIZE - 1; j++) {
                System.out.print(STATUSES[i][j]);
            }
            System.out.println("|");
        }
        System.out.println("-|---------|");
    }

    public static boolean isValid(int x, int y) {
        return STATUSES[y][x] != Status.MINE &&
                STATUSES[y][x] != Status.BORDER &&
                STATUSES[y][x] != Status.NUMBER;
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
                    if (FIELD[i][j] > '0' && FIELD[i][j] < '9') {
                        STATUSES[i][j] = Status.NUMBER;
                    }
                }
            }
        }
    }

    public static char checkSurroundings(int x, int y) {
        int count = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (STATUSES[i][j] == Status.MINE) {
                    count++;
                }
            }
        }
        return count == 0 ? '.' : (char) ('0' + count);
    }

    public static void hideMines() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (FIELD[i][j] == 'X') {
                    FIELD[i][j] = '.';
                }
            }
        }
    }

    private static void openMines() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (STATUSES[i][j] == Status.MINE) {
                    STATUSES[i][j] = Status.OPEN;
                    FIELD[i][j] = 'X';
                }
            }
        }
    }

    public static void mark(int x, int y) {
        FIELD[y][x] = FIELD[y][x] != '.' ? '.' : '*';
    }

    public static void unmark(int x, int y) {
        FIELD[y][x] = '.';
    }

    public static boolean open(int x, int y) {
        boolean isAlive = true;
        if (STATUSES[y][x] == Status.MINE || STATUSES[y][x] == Status.MARKED) {
            isAlive = false;
            openMines();
            printField();
            System.out.println("You stepped on a mine and failed!");
        } else if (STATUSES[y][x] == Status.NUMBER) {
            STATUSES[y][x] = Status.OPEN;
        } else if (STATUSES[y][x] == Status.DEFAULT) {
            STATUSES[y][x] = Status.OPEN;
            FIELD[y][x] = '/';
            IntStream.of(x + 1, x - 1).forEach(i -> open(i, y));
            IntStream.of(y + 1, y - 1).forEach(i -> open(x, i));
            open(x + 1, y + 1);
            open(x - 1, y - 1);
            open(x + 1, y - 1);
            open(x - 1, y + 1);
        }

        return isAlive;
    }

    public static void gameLoop() {
        boolean alive = true;

        int minesMarked = 0;
        printField();
        do {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            String command = scanner.nextLine().trim();
            //System.out.println(command);
            switch (command) {
                case "free":
                    alive = open(x, y);
                    printField();
                    break;
                case "mine":
                    if (STATUSES[y][x] == Status.DEFAULT || STATUSES[y][x] == Status.NUMBER) {
                        mark(x, y);
                    } else if (STATUSES[y][x] == Status.MINE || STATUSES[y][x] == Status.MARKED) {
                        mark(x, y);
                        STATUSES[y][x] = Status.MARKED;
                        minesMarked++;
                    } else if (FIELD[y][x] == '*') {
                        unmark(x, y);
                    }
                    printField();
                    break;
            }

        } while (minesMarked < NUMBER_OF_MINES && alive);

        if (alive) {
            System.out.print("Congratulations! You found all the mines!");
        }
    }
}
