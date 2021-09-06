import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        while (i != 0) {
            System.out.println(i % 2 == 0 ? "even" : "odd");
            i = scanner.nextInt();
        }
    }
}