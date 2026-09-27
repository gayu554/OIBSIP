import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<>();

        // Sample accounts
        accounts.add(new Account("ACC1001", "user1", 1234, 10000));
        accounts.add(new Account("ACC1002", "user2", 5678, 8000));
        accounts.add(new Account("ACC1003", "user3", 4321, 5000));
    }

    public Account findAccountByUserId(String userId) {

        for (Account account : accounts) {
            if (account.getUserId().equals(userId)) {
                return account;
            }
        }

        return null;
    }

    public Account findAccountById(String accountId) {

        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }

        return null;
    }

    public boolean validateUser(String userId, int pin) {

        Account account = findAccountByUserId(userId);

        return account != null && account.getPin() == pin;
    }
}