package championatcom.tennis;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import xls.DocumentXls;

import java.io.IOException;
import java.util.*;

public class WritePlayer {
    public static void main(String[] args) throws IOException {

        Parser parser = new Parser();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Players");

        int rownum = 0;
        final Row[] row = new Row[1];
        row[0] = sheet.createRow(rownum);
        final Cell[] cell = new Cell[1];

        DocumentXls.generateDocumentHeader(workbook, row[0], "Имя","Имя English", "Дата рождения:", "Рост:", "Вес:", "Рука:", "Гражданство:","Профессионал с","Лучшее место в карьере",
                "Дата лучшего рейтинга","Место проживания","Место рождения", "Рейтинг ATP");

        int count = 0;
        int errors = 0;
        int i = 500;
        while (i < 1000) {
            try {
                Document doc = Jsoup.connect("https://www.championat.com/tennis/player/" + i + ".html#profile").get();

                String name = parser.parseName(doc);
                String engName = parser.parseEnglishName(doc);
                Map<String, String> map1 = parser.parseData(doc);
                Map<String,String> map2 = parser.parseStat(doc);
                map1.put("Имя", name);
                map1.put("Имя English", engName);
                List<Map<String, String>> list = new ArrayList<>();
                map1.putAll(map2);
                list.add(map1);

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

            } catch (HttpStatusException e) {
                System.out.println("Page " + i + " not found. Try next");
            } catch (Exception e) {
                System.out.println("Something wrong with " + i);
                e.printStackTrace();
            }
            i++;
        }

        DocumentXls.saveDataInFile(workbook, "C:/demo/TennisPlayers2.xls",
                "/Users/nicholasg/TennisPlayers.xls");

    }
}
