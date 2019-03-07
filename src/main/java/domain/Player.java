package domain;

import java.util.List;

public class Player {

    private Match match;
    private Team team;
    private String name;
    private String position;
    private boolean isSub;
    private boolean isScored;

    public Player(String name) {
        this.name = name;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isSub() {
        return isSub;
    }

    public void setSub(List<String> subList) {
        if (subList.contains(name))
            isSub = true;
    else
        isSub = false;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(List<String> scoredPlayer) {
        if (scoredPlayer.contains(this.name))
            isScored = true;
    else
        isScored = false;
    }
}
