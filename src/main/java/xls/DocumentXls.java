package xls;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DocumentXls {

    /**
     * Generate xls-header for each names
     *
     * @param workbook
     * @param row
     * @param names
     */
    public static void generateDocumentHeader(HSSFWorkbook workbook, Row row, String... names) {
        HSSFCellStyle style = createStyleForTitle(workbook);
        Cell cell;
        int i = 0;
        for (String name : names) {
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(name);
            cell.setCellStyle(style);
            i++;
        }
    }

    /**
     * Added bold-font to header in style tytle
     *
     * @param workbook
     * @return
     */
    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    /**
     * Save parsed data in file for any OS
     *
     * @param workbook
     * @param winPathPath
     * @param unixPath
     * @throws IOException
     */
    public static void saveDataInFile(HSSFWorkbook workbook, String winPathPath, String unixPath) throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        try {
            if (os.contains("win")) {
                File file = new File(winPathPath);
                DocumentXls.writeToFile(workbook, file);
            } else {
                File file = new File(unixPath);
                DocumentXls.writeToFile(workbook, file);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(HSSFWorkbook workbook, File file) throws IOException {
        file.getParentFile().mkdirs();
        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        System.out.println("Created file: " + file.getAbsolutePath());
    }
}
