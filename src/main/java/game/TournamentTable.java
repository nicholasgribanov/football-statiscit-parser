package game;

public class TournamentTable {
    private String position;
    private String team;
    private String tourNumber;
    private String winGames;
    private String drawGames;
    private String loseGames;
    private String goalBalls;
    private String missedBalls;
    private String points;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTourNumber() {
        return tourNumber;
    }

    public void setTourNumber(String tourNumber) {
        this.tourNumber = tourNumber;
    }

    public String getWinGames() {
        return winGames;
    }

    public void setWinGames(String winGames) {
        this.winGames = winGames;
    }

    public String getDrawGames() {
        return drawGames;
    }

    public void setDrawGames(String drawGames) {
        this.drawGames = drawGames;
    }

    public String getLoseGames() {
        return loseGames;
    }

    public void setLoseGames(String loseGames) {
        this.loseGames = loseGames;
    }

    public String getGoalBalls() {
        return goalBalls;
    }

    public void setGoalBalls(String goalBalls) {
        this.goalBalls = goalBalls;
    }

    public String getMissedBalls() {
        return missedBalls;
    }

    public void setMissedBalls(String missedBalls) {
        this.missedBalls = missedBalls;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
