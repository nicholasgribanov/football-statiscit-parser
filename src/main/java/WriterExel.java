import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WriterExel {
    public static void main(String[] args) throws IOException, InterruptedException {
        Match match = new Match();
        Parser parser = new Parser();
        int i=1380660295;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Players");

        int rownum = 0;
        Cell cell;
        Row row;
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);

        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Матч");
        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Команда");
        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Игрок");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Позиция");
        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Запасной?");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Забил?");
        cell.setCellStyle(style);

        while (i<1380660407) {
            Document doc = Jsoup.connect("https://news.sportbox.ru/Vidy_sporta/Futbol/Russia/premier_league/stats/turnir_14586/game_" + i)
                    .proxy("chr-proxy.severstal.severstalgroup.com", 8080).get();

            match.setHomeTeam(parser.parseHomeTeam(doc));
            match.setHostTeam(parser.parseHostTeam(doc));
            match.setResult(parser.parseResult(doc));

            List<String> listOfPlayersHome = parser.parseHomeStartPlayers(doc);
            List<String> homePlayersScored = parser.parseScoredPlayerHome(doc);
            List<String> homeSubIn = parser.parseHomeSubInPlayersHome(doc);
            List<String> homeSubOut = parser.parseSubNotPlayersHome(doc);
            List<Player> playersHome = new ArrayList<>();
            listOfPlayersHome.forEach(player -> playersHome.add(new Player(player)));
            homeSubIn.forEach(player -> playersHome.add(new Player(player)));
            homeSubIn.addAll(homeSubOut);
            playersHome.forEach(player -> {
                player.setMatch(match);
                player.setTeam(new Team(match.getHomeTeam()));
                player.setScored(homePlayersScored);
                player.setSub(homeSubIn);
            });

            List<String> listOfPlayersHost = parser.parseHostStartPlayers(doc);
            List<String> hostPlayersScored = parser.parseScoredPlayerHost(doc);
            List<String> hostSubIn = parser.parseHomeSubInPlayersHost(doc);
            List<String> hostSubOut = parser.parseSubNotPlayersHost(doc);
            List<Player> playersHost = new ArrayList<>();
            listOfPlayersHost.forEach(player -> playersHost.add(new Player(player)));
            hostSubIn.forEach(player -> playersHost.add(new Player(player)));
            hostSubIn.addAll(hostSubOut);
            playersHost.forEach(player -> {
                player.setMatch(match);
                player.setTeam(new Team(match.getHostTeam()));
                player.setScored(hostPlayersScored);
                player.setSub(hostSubIn);
            });




            List<Player> allPlayers = new ArrayList<>();
            allPlayers.addAll(playersHome);
            allPlayers.addAll(playersHost);
            for (Player player : allPlayers) {
                rownum++;
                row = sheet.createRow(rownum);
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(player.getMatch().toString());

                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(player.getTeam().getName());

                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(player.getName());

                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue("");

                cell = row.createCell(4, CellType.STRING);
                cell.setCellValue(player.isSub() ? "ДА" : "НЕТ");

                cell = row.createCell(5, CellType.STRING);
                cell.setCellValue(player.isScored() ? "ДА" : "НЕТ");


            }
            i++;
        }


        File file = new File("C:/demo/Players.xls");
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
