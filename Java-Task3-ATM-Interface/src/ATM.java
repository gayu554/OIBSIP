import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Scanner scanner;
    private ArrayList<Transaction> transactions;
    private Account currentAccount;

    public ATM(Bank bank) {
        this.bank = bank;
        scanner = new Scanner(System.in);
        transactions = new ArrayList<>();
    }

    public void start() {

        System.out.println("================================");
        System.out.println("        WELCOME TO ATM");
        System.out.println("================================");

        if (!login()) {
            System.out.println("\nAccount locked.");
            System.out.println("Thank you for using ATM.");
            return;
        }

        showMenu();
    }

    private boolean login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("\nEnter User ID: ");
            String userId = scanner.nextLine();

            System.out.print("Enter PIN: ");
            int pin;

            try {
                pin = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("PIN must contain numbers only.");
                attempts++;
                continue;
            }

            if (bank.validateUser(userId, pin)) {

                currentAccount = bank.findAccountByUserId(userId);

                System.out.println("\nLogin successful!");
                System.out.println("Welcome, " + currentAccount.getUserId());

                return true;

            } else {

                attempts++;
                System.out.println("Invalid User ID or PIN.");
                System.out.println("Attempts remaining: " + (3 - attempts));
            }
        }

        return false;
    }

    private void showMenu() {

        while (true) {

            System.out.println("\n================================");
            System.out.println("           ATM MENU");
            System.out.println("================================");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            switch (choice) {

                case 1:
                    showTransactionHistory();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    System.out.println("\nThank you for using our ATM.");
                    System.out.println("Goodbye!");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void showTransactionHistory() {

        System.out.println("\n========== TRANSACTION HISTORY ==========");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        System.out.println("=========================================");
        System.out.printf("Current Balance: ₹%.2f%n",
                currentAccount.getBalance());
    }

    private void withdraw() {

        System.out.print("\nEnter withdrawal amount: ");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        if (currentAccount.withdraw(amount)) {

            transactions.add(new Transaction(
                    "WITHDRAW",
                    amount,
                    "Cash withdrawn successfully"
            ));

            System.out.println("Withdrawal successful.");
            System.out.printf("Remaining Balance: ₹%.2f%n",
                    currentAccount.getBalance());

        } else {

            System.out.println("Insufficient Funds.");
            System.out.printf("Available Balance: ₹%.2f%n",
                    currentAccount.getBalance());
        }
    }

    private void deposit() {

        System.out.print("\nEnter deposit amount: ");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        currentAccount.deposit(amount);

        transactions.add(new Transaction(
                "DEPOSIT",
                amount,
                "Amount deposited successfully"
        ));

        System.out.println("Deposit successful.");
        System.out.printf("Current Balance: ₹%.2f%n",
                currentAccount.getBalance());
    }

    private void transfer() {

        System.out.print("\nEnter recipient Account ID: ");
        String recipientId = scanner.nextLine();

        Account recipient = bank.findAccountById(recipientId);

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        if (recipient.getAccountId().equals(currentAccount.getAccountId())) {
            System.out.println("You cannot transfer money to your own account.");
            return;
        }

        System.out.print("Enter transfer amount: ");

        double amount;

        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount.");
            return;
        }

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        if (currentAccount.withdraw(amount)) {

            recipient.deposit(amount);

            transactions.add(new Transaction(
                    "TRANSFER",
                    amount,
                    "Transferred to " + recipient.getAccountId()
            ));

            System.out.println("Transfer successful.");
            System.out.printf("Remaining Balance: ₹%.2f%n",
                    currentAccount.getBalance());

        } else {

            System.out.println("Insufficient Funds.");
        }
    }
}