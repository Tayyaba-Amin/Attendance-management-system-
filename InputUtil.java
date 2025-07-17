// util/InputUtil.java
package util;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class InputUtil {
    private static Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // consume invalid input
            System.out.print(prompt);
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return input;
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public static List<Integer> getMultipleChoiceInput(String prompt, int maxOptions) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        String[] parts = input.split(",");
        List<Integer> choices = new ArrayList<>();
        
        for (String part : parts) {
            try {
                int choice = Integer.parseInt(part.trim());
                if (choice >= 1 && choice <= maxOptions) {
                    choices.add(choice - 1); // convert to 0-based index
                }
            } catch (NumberFormatException e) {
                // ignore invalid entries
            }
        }
        return choices;
    }
}