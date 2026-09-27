public enum Difficulty {

    EASY(50, 10, 10),
    MEDIUM(100, 7, 20),
    HARD(200, 5, 30);

    private final int maxNumber;
    private final int maxAttempts;
    private final int bonus;

    Difficulty(int maxNumber, int maxAttempts, int bonus) {
        this.maxNumber = maxNumber;
        this.maxAttempts = maxAttempts;
        this.bonus = bonus;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getBonus() {
        return bonus;
    }
}