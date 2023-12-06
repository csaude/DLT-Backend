package dlt.dltbackendmaster.reports;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Classe responsável pela formatacao de documentos excel gerados nos relatorios
 * diversos
 * 
 * @author Francisco da Conceicao Alberto Macuacua
 *
 */
public class ExcelDocumentFormatting {
	private String documentPath;

	public ExcelDocumentFormatting(String documentPath) {
		this.documentPath = documentPath;
	}

	public void execute() {
		try (FileInputStream fileIn = new FileInputStream(documentPath); Workbook workbook = new XSSFWorkbook(fileIn)) {

			Sheet sheet = workbook.getSheetAt(0);

			Font font = workbook.createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 10);
			CellStyle style = workbook.createCellStyle();
			style.setFont(font);

			// Apply style to all cells in the sheet
			for (Row row : sheet) {
				for (Cell cell : row) {
					cell.setCellStyle(style);
				}
			}

			// Centralize and format second and sixth rows
			formatRow(sheet, 0, HorizontalAlignment.LEFT, true);
			formatRow(sheet, 1, HorizontalAlignment.LEFT, true);
			formatRow(sheet, 2, HorizontalAlignment.LEFT, true);
			formatRow(sheet, 3, HorizontalAlignment.CENTER, true);
			formatRow(sheet, 4, HorizontalAlignment.LEFT, true);

			try (FileOutputStream fileOut = new FileOutputStream(documentPath)) {
				workbook.write(fileOut);
				System.out.println("Excel file has been formatted successfully!");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void formatRow(Sheet sheet, int rowIndex, HorizontalAlignment alignment, boolean bold) {
		Row row = sheet.getRow(rowIndex);
		if (row != null) {
			Font font = sheet.getWorkbook().createFont();
			font.setFontName("Arial");
			font.setFontHeightInPoints((short) 12);
			font.setBold(bold);

			CellStyle style = sheet.getWorkbook().createCellStyle();
			style.setAlignment(alignment);
			style.setFont(font);

			for (Cell cell : row) {
				cell.setCellStyle(style);
			}
		}
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
}
