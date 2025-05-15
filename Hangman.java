import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Hangman {

    // Hangman stages
    static String getHangman(int wrongGuess) {
        return switch (wrongGuess) {
            case 0 -> """
                    +---+
                    |   |
                        |
                        |
                        |
                        |
                    =========""";
            case 1 -> """
                    +---+
                    |   |
                    O   |
                        |
                        |
                        |
                    =========""";
            case 2 -> """
                    +---+
                    |   |
                    O   |
                    |   |
                        |
                        |
                    =========""";
            case 3 -> """
                    +---+
                    |   |
                    O   |
                   /|   |
                        |
                        |
                    =========""";
            case 4 -> """
                    +---+
                    |   |
                    O   |
                   /|\\  |
                        |
                        |
                    =========""";
            case 5 -> """
                    +---+
                    |   |
                    O   |
                   /|\\  |
                   /    |
                        |
                    =========""";
            case 6 -> """
                    +---+
                    |   |
                    O   |
                   /|\\  |
                   / \\  |
                        |
                    =========""";
            default -> "";
        };
    }

    static void printWithDelay(String text, int millis) {
        for (char ch : text.toCharArray()) {
            System.out.print(ch);
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        try {
            File myObj = new File("word.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                words.add(myReader.nextLine().toLowerCase());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please ensure 'word.txt' is in the correct directory.");
            return;
        }

        if (words.isEmpty()) {
            System.out.println("Word list is empty. Add some words to 'word.txt'.");
            return;
        }

        String word = words.get((int) (Math.random() * words.size()));
        ArrayList<Character> wordState = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            wordState.add('_');
        }

        Scanner scanner = new Scanner(System.in);
        int wrongGuess = 0;

        printWithDelay("**********************", 30);
        printWithDelay("🎮 Welcome to Hangman 🎮", 50);
        printWithDelay("**********************", 30);

        while (wrongGuess < 6) {
            System.out.println();
            System.out.println(getHangman(wrongGuess));
            System.out.print("Word: ");
            for (char c : wordState) {
                System.out.print(c + " ");
            }
            System.out.println();

            System.out.print("Enter your guess (a-z): ");
            char guess = scanner.next().toLowerCase().charAt(0);

            if (word.indexOf(guess) >= 0) {
                System.out.println("✅ Correct guess!");
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == guess) {
                        wordState.set(i, guess);
                    }
                }

                if (!wordState.contains('_')) {
                    printWithDelay("\n🎉 Congratulations! You won! 🎉", 50);
                    printWithDelay("The word was: " + word, 40);
                    animateWin();
                    return;
                }
            } else {
                wrongGuess++;
                int chancesLeft = 6 - wrongGuess;
                System.out.println("❌ Wrong guess! " + chancesLeft + " chances left.");
                animateWrong();
            }
        }

        // If the player lost
        System.out.println(getHangman(wrongGuess));
        printWithDelay("\n💀 You lost the game!", 50);
        printWithDelay("The correct word was: " + word, 40);
        animateLoss();
    }

    static void animateWrong() {
        String dots = "Loading next stage";
        for (int i = 0; i < 3; i++) {
            System.out.print(dots);
            for (int j = 0; j < 3; j++) {
                System.out.print(".");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.print("\r" + " ".repeat(30) + "\r");
        }
    }

    static void animateWin() {
        String[] fireworks = {
            "        *     *     *",
            "      *   * *   * *   *",
            "    *  🎆 🎇 🎆 🎇  *",
            "  * *     *     *     * *"
        };
        for (String line : fireworks) {
            System.out.println(line);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static void animateLoss() {
        String skull = """
                ______
              /      \\
             |  💀 💀  |
             |   👃   |
              \\__👄__/
            R.I.P. Try again!
            """;
        printWithDelay(skull, 20);
    }
}
