import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private Scanner scanner;
    private Random random;
    private Player player;

    private ArrayList<String> history;
    private int bestScore = 0;

    public Game(Scanner scanner, Player player) {
        this.scanner = scanner;
        this.random = new Random();
        this.player = player;
        this.history = new ArrayList<>();
    }

    public void start() {

        boolean playAgain = true;
        int roundNumber = 1;

        while (playAgain) {

            System.out.println("\n========== ROUND " + roundNumber + " ==========");

            Difficulty difficulty = chooseDifficulty();

            GameRound round = playRound(difficulty);

            displayResult(round, roundNumber);

            roundNumber++;

            System.out.print("\nPlay again? (yes/no): ");
            String answer = scanner.next();

            playAgain = answer.equalsIgnoreCase("yes");
        }

        showHistory();
        showStatistics();
    }

    // Difficulty selection
    private Difficulty chooseDifficulty() {

        while (true) {

            System.out.println("\n========== DIFFICULTY ==========");
            System.out.println("1. Easy   (1-50, 10 attempts)");
            System.out.println("2. Medium (1-100, 7 attempts)");
            System.out.println("3. Hard   (1-200, 5 attempts)");

            System.out.print("Choose difficulty: ");
            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    return Difficulty.EASY;

                case 2:
                    return Difficulty.MEDIUM;

                case 3:
                    return Difficulty.HARD;

                default:
                    System.out.println("\nInvalid choice!");
                    System.out.println("Please enter 1, 2, or 3.");
            }
        }
    }

    // Play one round
    private GameRound playRound(Difficulty difficulty) {

        GameRound round = new GameRound(difficulty);

        int target = random.nextInt(difficulty.getMaxNumber()) + 1;

        int attempts = 0;
        boolean won = false;

        HashSet<Integer> guessedNumbers = new HashSet<>();

        System.out.println("\n=================================");
        System.out.println("Welcome " + player.getName() + "!");
        System.out.println("Guess a number between 1 and "
                + difficulty.getMaxNumber());
        System.out.println("Attempts: " + difficulty.getMaxAttempts());
        System.out.println("=================================");

        while (attempts < difficulty.getMaxAttempts()) {

            System.out.print("\nEnter your guess: ");
            int guess = scanner.nextInt();

            // Range validation
            if (guess < 1 || guess > difficulty.getMaxNumber()) {

                System.out.println("Invalid guess!");
                System.out.println("Please enter a number between 1 and "
                        + difficulty.getMaxNumber());

                continue;
            }

            // Duplicate validation
            if (guessedNumbers.contains(guess)) {

                System.out.println("You already guessed " + guess + "!");
                System.out.println("Try a different number.");

                continue;
            }

            guessedNumbers.add(guess);
            attempts++;

            if (guess > target) {

                System.out.println("Too High!");

            } else if (guess < target) {

                System.out.println("Too Low!");

            } else {

                System.out.println("Correct!");
                won = true;
                break;
            }

            System.out.println("Attempts remaining: "
                    + (difficulty.getMaxAttempts() - attempts));
        }

        round.setAttempts(attempts);
        round.setWon(won);
        round.calculateScore();

        return round;
    }

    // Display round result
    private void displayResult(GameRound round, int roundNumber) {

        System.out.println("\n========== ROUND RESULT ==========");

        if (round.isWon()) {

            player.addWin(round.getScore());

            if (round.getScore() > bestScore) {
                bestScore = round.getScore();
            }

            System.out.println("Result   : WIN");
            System.out.println("Attempts : " + round.getAttempts());
            System.out.println("Score    : " + round.getScore());

            history.add(
                    "Round " + roundNumber
                    + " | " + round.getDifficulty()
                    + " | WON"
                    + " | " + round.getAttempts() + " attempts"
                    + " | " + round.getScore() + " points"
            );

        } else {

            player.addLoss();

            System.out.println("Result   : LOSS");
            System.out.println("You used all attempts.");

            history.add(
                    "Round " + roundNumber
                    + " | " + round.getDifficulty()
                    + " | LOST"
                    + " | " + round.getAttempts() + " attempts"
                    + " | 0 points"
            );
        }
    }

    // Show round history
    private void showHistory() {

        System.out.println("\n=================================");
        System.out.println("          GAME HISTORY");
        System.out.println("=================================");

        for (String record : history) {
            System.out.println(record);
        }
    }

    // Show final statistics
    private void showStatistics() {

        System.out.println("\n=================================");
        System.out.println("         GAME STATISTICS");
        System.out.println("=================================");

        System.out.println("Player         : " + player.getName());
        System.out.println("Rounds Played  : " + player.getRoundsPlayed());
        System.out.println("Wins           : " + player.getWins());
        System.out.println("Losses         : " + player.getLosses());
        System.out.println("Total Score    : " + player.getTotalScore());
        System.out.println("Best Score     : " + bestScore);

        System.out.println("\nThank you for playing!");
    }
}