package championatcom;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public String parseHomeTeam(Document doc) {
        return doc.body().getElementsByClass("match-info__team").first().getElementsByClass("match-info__team-name").first().text();
    }

    public String parseHostTeam(Document doc) {
        return doc.body().getElementsByClass("match-info__team").last().getElementsByClass("match-info__team-name").first().text();
    }

    public String parseResult(Document doc) {
        return doc.body().getElementsByClass("match-info__count-total").first().text();
    }

    public String parseDateOfMatch(Document doc) {
        return doc.body().getElementsByClass("match-info__date fav-item js-fav-item").first().text();
    }

    public String parseTour(Document doc) {
        return doc.body().getElementsByClass("match-info__stage").first().text();
    }

    public List<Integer> parseCalendar(Document doc) {
        List<Integer> linksId = new ArrayList<>();
        doc.body().getElementsByClass("stat-results__link").forEach(el -> {
            if (el.childNodeSize() != 0) {
                linksId.add(Integer.valueOf(el.getElementsByTag("a").first().attr("href").split("/")[6]));
            }
        });
        return linksId;
    }
}
