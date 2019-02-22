package player;

import game.GameStatisctic;

public class Match {
    private String homeTeam;
    private String hostTeam;
    private String result;
    private GameStatisctic gameStatisctic;


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
        return homeTeam + " " + result + " " + hostTeam;
    }

    public GameStatisctic getGameStatisctic() {
        return gameStatisctic;
    }

    public void setGameStatisctic(GameStatisctic gameStatisctic) {
        this.gameStatisctic = gameStatisctic;
    }
}
