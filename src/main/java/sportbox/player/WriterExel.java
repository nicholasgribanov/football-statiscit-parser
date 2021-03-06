package sportbox.player;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import domain.Match;
import domain.Player;
import domain.Team;
import xls.DocumentXls;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class WriterExel {
    public static void main(String[] args) throws IOException, InterruptedException {
        Match match = new Match();
        Parser parser = new Parser();
        int i = 1380660431;

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Players");

        int rownum = 0;
        Row row;
        row = sheet.createRow(rownum);
        Cell cell;
        DocumentXls.generateDocumentHeader(workbook, row, "Дата", "Матч", "Команда", "Игрок", "Позиция", "Запасной?", "Забил?");

        int count = 0;
        while (i <= 1380660431) {
            try {
                Document doc = Jsoup.connect("https://news.sportbox.ru/Vidy_sporta/Futbol/Russia/premier_league/stats/turnir_14586/game_" + i)
                        .get();


                doc.getElementsByClass("b-match__assists-right-block").remove();
                doc.getElementsByClass("b-match__assists-left-block").remove();

                List<String> playersInfoHome = parser.getFullPlayersInfoHome(doc);
                List<String> playersInfoHost = parser.getFullPlayersInfoHost(doc);

                match.setHomeTeam(parser.parseHomeTeam(doc));
                match.setHostTeam(parser.parseHostTeam(doc));
                match.setResult(parser.parseResult(doc));
                match.setMatchDate(parser.parseDateOfMatch(doc));

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
                    playersInfoHome.stream().filter(info -> info.contains(player.getName())).findFirst().ifPresent(info1 -> player.setPosition(info1.substring(info1.length() - 1)));
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
                    playersInfoHost.stream().filter(info -> info.contains(player.getName())).findFirst().ifPresent(info1 -> player.setPosition(info1.substring(0, 1)));
                    player.setSub(hostSubIn);
                });


                List<Player> allPlayers = new ArrayList<>();
                allPlayers.addAll(playersHome);
                allPlayers.addAll(playersHost);
                for (Player player : allPlayers) {
                    rownum++;
                    row = sheet.createRow(rownum);
                    cell = row.createCell(0, CellType.STRING);
                    cell.setCellValue(player.getMatch().getMatchDate());

                    cell = row.createCell(1, CellType.STRING);
                    cell.setCellValue(player.getMatch().toString());

                    cell = row.createCell(2, CellType.STRING);
                    cell.setCellValue(player.getTeam().getName());

                    cell = row.createCell(3, CellType.STRING);
                    cell.setCellValue(player.getName());

                    cell = row.createCell(4, CellType.STRING);
                    cell.setCellValue(player.getPosition());

                    cell = row.createCell(5, CellType.STRING);
                    cell.setCellValue(player.isSub() ? "ДА" : "НЕТ");

                    cell = row.createCell(6, CellType.STRING);
                    cell.setCellValue(player.isScored() ? "ДА" : "НЕТ");


                }
                System.out.println("Parsed " + i + " : " + ++count);
                if (i == 1380660414)
                    i += 2;
                else
                    i++;
            } catch (HttpStatusException e) {
                System.out.println("Page " + i + " not found. Try next");
                i++;
            } catch (Exception e) {
                System.out.println("Something wrong with " + i);
                i++;
            }
        }

        DocumentXls.saveDataInFile(workbook, "C:/demo/Players.xls", "/Users/nicholasg/Players.xls");

    }


}
