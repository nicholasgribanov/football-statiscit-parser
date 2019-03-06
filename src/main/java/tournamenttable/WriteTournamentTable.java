package tournamenttable;

import game.TournamentTable;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
import java.util.ArrayList;
import java.util.List;

public class WriteTournamentTable {
    public static void main(String[] args) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Tables");

        HSSFCellStyle style = DocumentXls.createStyleForTitle(workbook);
        int rownum = 0;
        Row row;
        row = sheet.createRow(rownum);
        Cell cell;
        DocumentXls.generateDocumentHeader(style, row, "Тур", "Команда", "Позиция","Выиграно игр","Ничья","Проиграно игр","Забито голов","Пропущено голов","Очки");

        int i = 1;
        int count = 0;

        while (i <= 1) {
            try {
                Document doc = Jsoup.connect("https://news.sportbox.ru/Vidy_sporta/Futbol/Russia/premier_league/stats?tour=" + i)
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

                for (TournamentTable tt : tournamentTables) {
                    rownum++;
                    row = sheet.createRow(rownum);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(tt.getTourNumber());

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(tt.getTeam());

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue(tt.getPosition());

                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(tt.getWinGames());

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue(tt.getDrawGames());

                    cell = row.createCell(5, CellType.STRING);
                    cell.setCellValue(tt.getLoseGames());

                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(tt.getGoalBalls());

                    cell = row.createCell(7, CellType.STRING);
                    cell.setCellValue(tt.getMissedBalls());

                    cell = row.createCell(8, CellType.STRING);
                    cell.setCellValue(tt.getPoints());
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

        DocumentXls.saveDataInFile(workbook, "C:/demo/Tour.xls", "/Users/nicholasg/Tour.xls");
    }




}
