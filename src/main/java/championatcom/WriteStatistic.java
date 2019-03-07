package championatcom;

import domain.Match;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xls.DocumentXls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class WriteStatistic {
    public static void main(String[] args) throws IOException {

        Match match = new Match();
        Parser parser = new Parser();

        Document cal = Jsoup.connect("https://www.championat.com/football/_france/tournament/1032/calendar/")
                .maxBodySize(Integer.MAX_VALUE).get();
        List<Integer> links = parser.parseCalendar(cal);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Game");

        int rownum = 0;
        final Row[] row = new Row[1];
        row[0] = sheet.createRow(rownum);
        final Cell[] cell = new Cell[1];

        DocumentXls.generateDocumentHeader(workbook, row[0], "Тур", "Дата", "Матч", "Команда", "Голов", "Атаки", "Опасные атаки",
                "Удары по воротам", "Удары в створ", "Штанги/перекладины", "Фолы", "Угловые",
                "Офсайды", "% владения мячом", "Заблокированные удары", "Штрафные удары", "Удары от ворот",
                "Ауты", "Предупреждения", "Удаления");

        int count = 0;
        int errors = 0;
        for (int j : links) {
            try {
                Document doc = Jsoup.connect("https://www.championat.com/football/_france/tournament/1032/match/" + j)
                        .get();

                match.setHomeTeam(parser.parseHomeTeam(doc));
                match.setHostTeam(parser.parseHostTeam(doc));
                match.setResult(parser.parseResult(doc));
                match.setMatchDate(parser.parseDateOfMatch(doc));
                match.setTour(parser.parseTour(doc));

                Map<String, String> map1 = new LinkedHashMap<>();
                Map<String, String> map2 = new LinkedHashMap<>();
                map1.put("Матч", match.toString());
                map2.put("Матч", match.toString());
                map1.put("Команда", match.getHomeTeam());
                map2.put("Команда", match.getHostTeam());
                map1.put("Дата", match.getMatchDate());
                map2.put("Дата", match.getMatchDate());
                map1.put("Тур", match.getTour());
                map2.put("Тур", match.getTour());
                map1.put("Голов", match.getResult().split(":")[0].trim().replaceAll("ДВ",""));
                map2.put("Голов", match.getResult().split(":")[1].trim().replaceAll("ДВ",""));

                Elements element = doc.body().getElementsByClass("stat-graph");
                Elements element1 = element.last().getElementsByClass("stat-graph__row");
                element1.forEach(el -> {
                    map1.put(getStatValue(el, "stat-graph__title"), getStatValue(el, "stat-graph__value _left"));
                    map2.put(getStatValue(el, "stat-graph__title"), getStatValue(el, "stat-graph__value _right"));
                });

                List<Map<String, String>> list = new ArrayList<>();
                list.add(map1);
                list.add(map2);

                for (Map<String, String> map : list) {
                    rownum++;

                    final int[] k = {0};
                    int finalRownum = rownum;
                    final Row[] row1 = new Row[1];
                    row1[0] = sheet.createRow(finalRownum);
                    map.forEach((key, value) -> {

                        row[0].cellIterator().forEachRemaining(cell1 -> {
                            if (cell1.getStringCellValue().equals(key))
                                k[0] = cell1.getColumnIndex();
                        });
                        cell[0] = row1[0].createCell(k[0], CellType.STRING);
                        cell[0].setCellValue(value);
                        k[0]++;
                    });
                }

                System.out.println("Parsed " + j + " : " + ++count);

            } catch (HttpStatusException e) {
                System.out.println("Page " + j + " not found. Try next");
            } catch (Exception e) {
                System.out.println("Something wrong with " + j);
                e.printStackTrace();
                if (++errors > 10) break;
            }
        }
        DocumentXls.saveDataInFile(workbook, "C:/demo/MatchesEngkang20162017.xls",
                                                "/Users/nicholasg/MatchesFrance20142015.xls");
    }

    private static String getStatValue(Element el, String s) {
        if (el.getElementsByClass(s).isEmpty()) {
            s += " _empty";
            return el.getElementsByClass(s).first().text();
        }
        return el.getElementsByClass(s).first().text();
    }

}
