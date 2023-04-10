package com.alurkerja.core.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ExcelReader<E> {
    public List<E> read(InputStream file) throws IOException, InvalidFormatException {
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<E> result = new ArrayList<>();
        int i = 0;
        for(Row row : sheet){
            if(i>this.skippedRow()){
                ArrayList<Object> columns = new ArrayList<>();
                int x = 0;
                for(Cell cell : row){
                    while (cell.getColumnIndex() != x) {
                        columns.add("");
                        x++;
                    }
                    switch (cell.getCellType().name()){
                        case "NUMERIC":
                            columns.add(cell.getNumericCellValue());
                            break;
                        case "BOOLEAN":
                            columns.add(cell.getBooleanCellValue());
                            break;
                        case "DATE":
                            columns.add(cell.getDateCellValue());
                            break;
                        default:
                            columns.add(cell.getStringCellValue());
                            break;
                    }
                    x++;
                }
                result.add(this.perLine(columns.toArray()));
            }
            i++;
        }
        return result.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());
    }
    public  abstract int skippedRow();
    public abstract E perLine(Object[] columns);
}
