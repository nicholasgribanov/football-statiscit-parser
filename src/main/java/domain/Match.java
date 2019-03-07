package domain;

public class Match {
    private String homeTeam;
    private String hostTeam;
    private String result;
    private GameStatisctic gameStatisctic;
    private String matchDate;
    private String tour;


    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getHostTeam() {
        return hostTeam;
    }

    public void setHostTeam(String hostTeam) {
        this.hostTeam = hostTeam;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return homeTeam + " " + result.replaceAll("ДВ", "") + " " + hostTeam;
    }

    public GameStatisctic getGameStatisctic() {
        return gameStatisctic;
    }

    public void setGameStatisctic(GameStatisctic gameStatisctic) {
        this.gameStatisctic = gameStatisctic;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }
}
