public class GameRound {

    private Difficulty difficulty;
    private int attempts;
    private boolean won;
    private int score;

    public GameRound(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void calculateScore() {

        if (won) {
            int baseScore = 100 - ((attempts - 1) * 10);

            if (baseScore < 20) {
                baseScore = 20;
            }

            score = baseScore + difficulty.getBonus();
        }
    }

    public int getScore() {
        return score;
    }

    public int getAttempts() {
        return attempts;
    }

    public boolean isWon() {
        return won;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }
}