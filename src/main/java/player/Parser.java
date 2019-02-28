package player;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.stream.Collectors;

public class Parser {

    public String parseHomeTeam(Document doc) {
        return doc
                .body()
                .getElementsByClass("b-match__side b-match__side_left one_player")
                .first()
                .getElementsByClass("b-match__team-title")
                .first()
                .text();
    }

    public String parseHostTeam(Document doc) {
        return doc
                .body()
                .getElementsByClass("b-match__side b-match__side_right one_player")
                .first()
                .getElementsByClass("b-match__team-title")
                .first()
                .text();
    }

    public String parseResult(Document doc){
        return doc
                .body()
                .getElementsByClass("match_monitor")
                .first()
                .getElementsByClass("b-match__monitor__count")
                .first()
                .text();
    }

    public String parseDateOfMatch(Document doc) {
        return doc
                .body()
                .getElementsByClass("match_count_date")
                .first()
                .text();
    }

    //PLAYERS
    public List<String> parseHomeStartPlayers(Document doc) {
        return doc.body().getElementsByClass("b-field__zona left").first().getElementsByClass("igrok")
                .tagName("a").eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());
    }

    public List<String> parseHostStartPlayers(Document doc) {
        return doc.body().getElementsByClass("b-field__zona right").first().getElementsByClass("igrok")
                .tagName("a").eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());
    }

    public List<String> parseHomeSubInPlayersHome(Document doc) {
        return doc.body().getElementsByClass("b-field__zamena left match_field_zamen clearfix").first()
                .children().tagName("a").eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());
    }

    public List<String> parseSubNotPlayersHome(Document doc) {
        return doc.body().getElementsByClass("b-field__reserv left match_field_zapas").first()
                .children().tagName("a").eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());
    }

    public List<String> parseHomeSubInPlayersHost(Document doc) {
        return doc.body().getElementsByClass("b-field__zamena right match_field_zamen clearfix").first()
                .children().tagName("a").eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());
    }

    public List<String> parseSubNotPlayersHost(Document doc) {
        return doc.body().getElementsByClass("b-field__reserv right match_field_zapas").first()
                .children().tagName("a").eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());
    }

    //COACH
    public String parseCoachHome(Document doc) {
        List<String> playersAndCoach = doc.body().getElementsByClass("b-teams__side-left").first().getElementsByClass("player-name").eachText();
        return playersAndCoach.get(playersAndCoach.size() - 1);
    }

    public   List<String> getFullPlayersInfoHome(Document doc) {
        return doc.body().getElementsByClass("b-teams__side-left").first().getElementsByClass("b-teams__string")
                .eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());
    }

    public   List<String> getFullPlayersInfoHost(Document doc) {
        return doc.body().getElementsByClass("b-teams__side-right").first().getElementsByClass("b-teams__string")
                .eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());
    }

    //SCORED PLAYERS
    public List<String> parseScoredPlayerHome(Document doc){
        return doc.body().getElementsByClass("b-match__side b-match__side_left match_events_left")
                .first()
                .getElementsByClass("b-match__video-link").tagName("a").eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());

    }

    public List<String> parseScoredPlayerHost(Document doc){
        return doc.body().getElementsByClass("b-match__side b-match__side_right match_events_right")
                .first()
                .getElementsByClass("b-match__video-link").tagName("a").eachText().stream().map(Parser::alphabeticOnlyString).collect(Collectors.toList());

    }


    public static String alphabeticOnlyString(String originalString) {
        int length = originalString.length();
        String resultString = "";
        for (int i = 0; i < length; i++) {
            if (Character.isAlphabetic(originalString.charAt(i))) {
                resultString += originalString.charAt(i);
            }
        }

        return resultString;
    }

}


