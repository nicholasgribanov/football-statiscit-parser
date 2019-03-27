package championatcom.tennis;

import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    public String parseName(Document doc) {
        return doc.body().getElementsByClass("tournament-header__title-name").first().text();
    }

    public String parseEnglishName(Document doc) {
        return doc.body().getElementsByClass("tournament-header__title-desc").first().text();
    }

    public Map<String, String> parseData(Document doc) {
        Map<String, String> data = new HashMap<>();
        doc.body().getElementsByClass("tournament-header__facts js-tournament-header-facts").first().getElementsByTag("li").forEach(d -> data.put(d.getElementsByTag("div").first().text(), d.ownText()));
        return data;
    }

    public Map<String, String> parseStat(Document doc) {
        Map<String, String> data = new HashMap<>();
        doc.body().getElementsByClass("table table-stripe table-fold")
                .first()
                .getElementsByTag("tr")
                .forEach(d->data.put(d.getElementsByClass("_label").first().text(), d.getElementsByTag("td").last().text()));
        return data;
    }
}
