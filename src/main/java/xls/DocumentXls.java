package xls;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

public class DocumentXls {

    public static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public static void generateDocumentHeader(HSSFCellStyle style, Row row, String... names) {
        Cell cell;
        int i = 0;
        for (String name : names){
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(name);
            cell.setCellStyle(style);
            i++;
        }
    }
}
