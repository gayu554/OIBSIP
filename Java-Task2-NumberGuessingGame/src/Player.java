public class Player {

    private String name;
    private int totalScore;
    private int roundsPlayed;
    private int wins;
    private int losses;

    public Player(String name) {
        this.name = name;
    }

    public void addWin(int score) {
        totalScore += score;
        wins++;
        roundsPlayed++;
    }

    public void addLoss() {
        losses++;
        roundsPlayed++;
    }

    public String getName() {
        return name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}