package project;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Введите операцию: ");
                String operation = sc.nextLine();
                if(operation.equals("quit")) break;
                System.out.println(Calculator.calculate(operation));
            } catch (Exception ex) {
                System.out.println("Ошибка: " + ex.getMessage());
            }
        }
        sc.close();
        System.out.println("Завершение работы ");
    }
}
