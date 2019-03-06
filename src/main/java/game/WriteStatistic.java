package game;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
import player.Match;
import player.Parser;
import xls.DocumentXls;
import java.io.IOException;
import java.util.*;

public class WriteStatistic {
    public static void main(String[] args) throws IOException {

        Match match = new Match();
        Parser parser = new Parser();

        int i = 1380660431;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Game");

        HSSFCellStyle style = DocumentXls.createStyleForTitle(workbook);
        int rownum = 0;
        final Row[] row = new Row[1];
        row[0] = sheet.createRow(rownum);
        final Cell[] cell = new Cell[1];
        DocumentXls.generateDocumentHeader(style, row[0], "Дата", "Матч", "Команда", "Голы", "забитые правой ногой",
                "забитые левой ногой", "забитые другой частью тела", "забитые головой", "Удар(ы) по воротам",
                "в створ ворот", "в штангу", "в перекладину", "в сторону ворот (мимо ворот)", "блокированные",
                "со стандартного положения", "после розыгрыша стандартного положения", "из штрафной",
                "из-за штрафной", "точности ударов", "Удары отраженные вратарем", "отбитые", "пойманные",
                "Угловые", "Офсайды", "Предупреждения", "Замены", "% владения мячом", "% точных передач",
                "опасные моменты", "нарушения", "Удаления");


        int count = 0;

        while (i <= 1380660431) {
            try {
                Document doc = Jsoup.connect("https://news.sportbox.ru/Vidy_sporta/Futbol/Russia/premier_league/stats/turnir_14586/game_" + i)
                        .get();

                match.setHomeTeam(parser.parseHomeTeam(doc));
                match.setHostTeam(parser.parseHostTeam(doc));
                match.setResult(parser.parseResult(doc));
                match.setMatchDate(parser.parseDateOfMatch(doc));

                Elements element = doc.body().getElementsByClass("sb_c_stat_stat");

                Elements element1 = element.first().getElementsByTag("tbody");

                Element element2 = element1.first();
                Map<String, String> map1 = new LinkedHashMap<>();
                Map<String, String> map2 = new LinkedHashMap<>();
                map1.put("Матч", match.toString());
                map2.put("Матч", match.toString());
                map1.put("Команда", match.getHomeTeam());
                map2.put("Команда", match.getHostTeam());
                map1.put("Дата", match.getMatchDate());
                map2.put("Дата", match.getMatchDate());

                Elements elements = element2.children();
                element2.children().forEach(el -> {
                    map1.put(el.getElementsByTag("th").first().text(), el.getElementsByTag("td").first().text());
                    map2.put(el.getElementsByTag("th").first().text(), el.getElementsByTag("td").last().text());
                });
                map1.remove("с игры");
                map2.remove("с игры");
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

                System.out.println("Parsed " + i + " : " + ++count);
                i++;
            } catch (HttpStatusException e) {
                System.out.println("Page " + i + " not found. Try next");
                i++;
            } catch (Exception e) {
                System.out.println("Something wrong with " + i);
                i++;
            }
        }

        DocumentXls.saveDataInFile(workbook, "C:/demo/Matches.xls", "/Users/nicholasg/Matches.xls");

    }


}
