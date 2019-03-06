package game;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class WriteStatistic {
    public static void main(String[] args) throws IOException {

        Match match = new Match();
        Parser parser = new Parser();

        int i = 1380660295;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Game");

        HSSFCellStyle style = createStyleForTitle(workbook);
        int rownum = 0;
        final Row[] row = new Row[1];
        row[0] = sheet.createRow(rownum);
        final Cell[] cell = new Cell[1];
        cell[0] = row[0].createCell(0, CellType.STRING);
        cell[0].setCellValue("Дата");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(1, CellType.STRING);
        cell[0].setCellValue("Матч");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(2, CellType.STRING);
        cell[0].setCellValue("Команда");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(3, CellType.STRING);
        cell[0].setCellValue("Голы");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(4, CellType.STRING);
        cell[0].setCellValue("забитые правой ногой");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(5, CellType.STRING);
        cell[0].setCellValue("забитые левой ногой");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(6, CellType.STRING);
        cell[0].setCellValue("забитые другой частью тела");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(7, CellType.STRING);
        cell[0].setCellValue("забитые головой");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(8, CellType.STRING);
        cell[0].setCellValue("Удар(ы) по воротам");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(9, CellType.STRING);
        cell[0].setCellValue("в створ ворот");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(10, CellType.STRING);
        cell[0].setCellValue("в штангу");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(11, CellType.STRING);
        cell[0].setCellValue("в перекладину");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(12, CellType.STRING);
        cell[0].setCellValue("в сторону ворот (мимо ворот)");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(13, CellType.STRING);
        cell[0].setCellValue("блокированные");
        cell[0].setCellStyle(style);
        cell[0] = row[0].createCell(14, CellType.STRING);
        cell[0].setCellValue("со стандартного положения");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(15, CellType.STRING);
        cell[0].setCellValue("после розыгрыша стандартного положения");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(16, CellType.STRING);
        cell[0].setCellValue("из штрафной");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(17, CellType.STRING);
        cell[0].setCellValue("из-за штрафной");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(18, CellType.STRING);
        cell[0].setCellValue("точности ударов");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(19, CellType.STRING);
        cell[0].setCellValue("Удары отраженные вратарем");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(20, CellType.STRING);
        cell[0].setCellValue("отбитые");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(21, CellType.STRING);
        cell[0].setCellValue("пойманные");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(22, CellType.STRING);
        cell[0].setCellValue("Угловые");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(23, CellType.STRING);
        cell[0].setCellValue("Офсайды");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(24, CellType.STRING);
        cell[0].setCellValue("Предупреждения");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(25, CellType.STRING);
        cell[0].setCellValue("Замены");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(26, CellType.STRING);
        cell[0].setCellValue("% владения мячом");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(27, CellType.STRING);
        cell[0].setCellValue("% точных передач");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(28, CellType.STRING);
        cell[0].setCellValue("опасные моменты");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(29, CellType.STRING);
        cell[0].setCellValue("нарушения");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(30, CellType.STRING);
        cell[0].setCellValue("Удаления");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(31, CellType.STRING);
        cell[0].setCellValue("Положение после тура");
        cell[0].setCellStyle(style);

        cell[0] = row[0].createCell(32, CellType.STRING);
        cell[0].setCellValue("Очки");
        cell[0].setCellStyle(style);

        int count = 0;

        while (i <= 1380660438) {
            try {
                Document doc = Jsoup.connect("https://news.sportbox.ru/Vidy_sporta/Futbol/Russia/premier_league/stats/turnir_14586/game_" + i)
                        .get();

                List<TournamentTable> tournamentTables = new ArrayList<>();
                doc.getElementsByClass("global-table show-t").first().getElementsByTag("tbody").first().getElementsByTag("tr").forEach(element -> {
                    TournamentTable tournamentTable = new TournamentTable();
                    tournamentTable.setPosition(element.text().split(" ")[0]);
                    tournamentTable.setTeam(element.getElementsByClass("info table-link").first().text());
                    String[] texts = element.getElementsByClass("val-t").text().split(" ");
                    tournamentTable.setTourNumber(texts[0]);
                    tournamentTable.setWinGames(texts[1].split("/")[0]);
                    tournamentTable.setDrawGames(texts[1].split("/")[1]);
                    tournamentTable.setLoseGames(texts[1].split("/")[2]);
                    tournamentTable.setGoalBalls(texts[2].split("-")[0]);
                    tournamentTable.setMissedBalls(texts[2].split("-")[1]);
                    tournamentTable.setPoints(texts[3]);
                    tournamentTables.add(tournamentTable);
                });

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

                map1.put("Положение после тура", tournamentTables.stream().filter(tt -> tt.getTeam().equals(map1.get("Команда"))).findFirst().get().getPosition());
                map2.put("Положение после тура", tournamentTables.stream().filter(tt -> tt.getTeam().equals(map2.get("Команда"))).findFirst().get().getPosition());
                map1.put("Очки", tournamentTables.stream().filter(tt -> tt.getTeam().equals(map1.get("Команда"))).findFirst().get().getPoints());
                map2.put("Очки", tournamentTables.stream().filter(tt -> tt.getTeam().equals(map2.get("Команда"))).findFirst().get().getPoints());

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


        File file = new File("C:/demo/Matches.xls");
        file.getParentFile().mkdirs();
        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());
    }

    public static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
}
