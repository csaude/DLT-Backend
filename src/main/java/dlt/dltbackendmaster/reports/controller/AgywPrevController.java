package dlt.dltbackendmaster.reports.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import dlt.dltbackendmaster.reports.AgywPrevReport;
import dlt.dltbackendmaster.reports.domain.NewlyEnrolledAgywAndServices;
import dlt.dltbackendmaster.reports.domain.ResultObject;
import dlt.dltbackendmaster.service.DAOService;

/**
 * Controller resposável pela comunicação dos dados do relatório
 * 
 * @author Hamilton Mutaquiha
 *
 */
@RestController
@RequestMapping("/api/agyw-prev")
public class AgywPrevController {
	private static final int MAX_ROWS_NUMBER = 1000000;

	private static final String SHEET_LABEL = "Página";

	private static final String REPORTS_HOME = System.getProperty("user.dir") + "/webapps/reports";

	private static final String BENEFICIARIAS_SEM_VULNERABILIDADES_ESPECIFICAS = "DLT2.0_BENEFICIARIAS_SEM_ VULNERABILIDADES_ESPECIFICAS_POR";

	private static final String NEW_ENROLLED_REPORT_NAME = "DLT2.0_NOVAS_RAMJ_VULNERABILIDADES_E_SERVICOS_POR";
	private static final String NEW_ENROLLED_SUMMARY_REPORT_NAME = "DLT2.0_RESUMO_NOVAS_RAMJ_VULNERABILIDADES_E_SERVICOS_POR";

	private static final String VULNERABILITIES_AND_SERVICES_REPORT_NAME = "DLT2.0_BENEFICIARIAS_VULNERABILIDADES_E_SERVICOS_POR";
	private static final String VULNERABILITIES_AND_SERVICES_SUMMARY_REPORT_NAME = "DLT2.0_BENEFICIARIAS_VULNERABILIDADES_E_SERVICOS_RESUMO_POR";

	private final DAOService service;

	@Autowired
	public AgywPrevController(DAOService service) {
		this.service = service;
	}

	@SuppressWarnings("null")
	@GetMapping(produces = "application/json")
	public ResponseEntity<Map<Integer, Map<String, ResultObject>>> get(
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") String startDate,
			@RequestParam(name = "endDate") String endDate, @RequestParam(name = "reportType") int reportType) {

		AgywPrevReport report = new AgywPrevReport(service);

		try {
			Map<Integer, Map<String, ResultObject>> reportObject = report.getAgywPrevResultObject(districts, startDate,
					endDate, reportType);

			return new ResponseEntity<>(reportObject, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static <T> List<List<T>> splitList(List<T> originalList, int chunkSize) {
		List<List<T>> sublists = new ArrayList<>();
		for (int i = 0; i < originalList.size(); i += chunkSize) {
			int end = Math.min(originalList.size(), i + chunkSize);
			sublists.add(originalList.subList(i, end));
		}
		return sublists;
	}

	public static String serializeToJson(List<NewlyEnrolledAgywAndServices> objects) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(objects);

			// Remove trailing commas
			jsonString = removeTrailingComma(jsonString);

			return jsonString;
		} catch (Exception e) {
			e.printStackTrace();
			return "[]"; // Return an empty array on failure
		}
	}

	private static String removeTrailingComma(String jsonString) {
		// Remove trailing comma within arrays
		jsonString = jsonString.replaceAll(",\\s*]", "]");
		// Remove trailing comma within objects
		jsonString = jsonString.replaceAll(",\\s*}", "}");

		return jsonString;
	}

	public void createDirectory(String directoryPath) {
		// Create a Path object for the directory
		Path dirPath = Paths.get(directoryPath);

		try {
			// Create the directory if it does not exist
			if (!Files.exists(dirPath)) {
				Files.createDirectories(dirPath);
				System.out.println("Directory created successfully.");
			} else {
				System.out.println("Directory already exists.");
			}
		} catch (IOException e) {
			System.err.println("Error creating the directory: " + e.getMessage());
		}
	}

	@SuppressWarnings("null")
	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> downloadFile(@RequestParam(name = "filePath") String filePath) throws IOException {
		File file = new File(filePath);
		Resource resource = new FileSystemResource(file);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + file.getName());

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}


	@SuppressWarnings("null")
	@GetMapping(produces = "application/json", path = "/getBeneficiariesNoVulnerabilities")
	public ResponseEntity<String> getBeneficiariesNoVulnerabilities(
			@RequestParam(name = "province") String province, @RequestParam(name = "districts") Integer[] districts,
			@RequestParam(name = "startDate") Long startDate, @RequestParam(name = "endDate") Long endDate,
			@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "username") String username) {

		AgywPrevReport report = new AgywPrevReport(service);

		boolean isEndOfCycle = false;

		Date today = new Date();
		SimpleDateFormat sdfToday = new SimpleDateFormat("yyyy-MM-dd");
		String formattedToday = sdfToday.format(today);

		Date initialDate = new Date(startDate); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedInitialDate = sdf.format(initialDate);

		Date finalDate = new Date(endDate);
		SimpleDateFormat sdfFinal = new SimpleDateFormat("yyyy-MM-dd");
		String formattedFinalDate = sdfFinal.format(finalDate);

		createDirectory(REPORTS_HOME + "/" + username);

		String generatedFilePath = REPORTS_HOME + "/" + username + "/" + BENEFICIARIAS_SEM_VULNERABILIDADES_ESPECIFICAS + "_"
				+ province.toUpperCase() + "_" + formattedInitialDate + "_" + formattedFinalDate + "_" + formattedToday + ".xlsx";

		try {
			// Set up streaming workbook
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			workbook.setCompressTempFiles(true); // Enable compression of temporary files

			// Create a sheet
			Sheet sheet = workbook.createSheet(SHEET_LABEL);
			// Create font for bold style
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);

			// Apply bold font style to the cells in the header row
			CellStyle boldCellStyle = workbook.createCellStyle();
			boldCellStyle.setFont(boldFont);

			// Apply bold font style to the cells in the header row
			CellStyle alignCellStyle = workbook.createCellStyle();
			// alignCellStyle.setFont(boldFont);
			alignCellStyle.setAlignment(HorizontalAlignment.CENTER);

			// Define Title
			String titleHeaders = "LISTA DE RAMJ REGISTADAS NO DLT NO PERÍODO EM CONSIDERAÇÃO SEM REGISTO DE VULNERABILIDADES ESPECIFICAS ";
			// Create a header row
			Row titleRow = sheet.createRow(0);
			// Write Title
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue(titleHeaders);

			// Merge the cells for the title
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 16));

			// Define Initial Date
			String initialDateHeaders[] = { "Data de Início:", formattedInitialDate };
			// Create a header row
			Row initialHeaderRow = sheet.createRow(2);
			// Write headers
			for (int i = 0; i < initialDateHeaders.length; i++) {
				Cell cell = initialHeaderRow.createCell(i);
				cell.setCellValue(initialDateHeaders[i]);
			}

			// Define Final Date
			String finalDateHeaders[] = { "Data de Fim:", formattedFinalDate };
			// Create a header row
			Row finalHeaderRow = sheet.createRow(3);
			// Write headers
			for (int i = 0; i < finalDateHeaders.length; i++) {
				Cell cell = finalHeaderRow.createCell(i);
				cell.setCellValue(finalDateHeaders[i]);
			}

			// Create a header row
			Row sessionRow = sheet.createRow(4);
			// Write Title and Merge cells for session headers

			Cell cell1 = sessionRow.createCell(0);
			cell1.setCellValue("Informação Demográfica");
			cell1.setCellStyle(alignCellStyle);

			// Merge cells for session headers
			sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 16)); // Merge first 17 columns
			
			// Define headers
			String[] headers = { "Província", "Distrito", "Onde Mora", "Ponto de Entrada", "Organização", "Data de Inscrição",
					"Data de Registo", "Registado Por", "Data da Última Actualização", "Actualizado Por", "NUI", "Sexo", 
					"Idade (Registo)", "Idade (Actual)", "Faixa Etária (Registo)", "Faixa Etária (Actual)", "Data de Nascimento" };

			// Create a header row
			Row headerRow = sheet.createRow(5);
			// Write headers
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			int rowCount = 6; // start from row 1 (row 0 is for headers)
			int currentSheet;

			for (currentSheet = 0; currentSheet < currentSheet + 1; currentSheet++) {
				if (!isEndOfCycle) {
					// Insert data rows from the reportObjectList
					List<Object> reportObjectList = report.getBeneficiariesNoVulnerabilities(districts,
							new Date(startDate), new Date(endDate), currentSheet, pageSize);

					if (reportObjectList.size() < MAX_ROWS_NUMBER) {
						isEndOfCycle = true;
					}

					if (currentSheet != 0) {
						rowCount = 0;
						sheet = workbook.createSheet(SHEET_LABEL + currentSheet);
					}
					for (Object reportObject : reportObjectList) {
						Row row = sheet.createRow(rowCount++);
						// Write values to cells based on headers
						for (int i = 0; i < headers.length; i++) {
							Object value = getValueAtIndex(reportObject, i); // You need to implement this method
							if (value != null) {
								row.createCell(i).setCellValue(String.valueOf(value));
							}
						}
					}
				} else {
					break;
				}
			}

			// Write the workbook content to a file
			FileOutputStream fileOut = new FileOutputStream(generatedFilePath);
			workbook.write(fileOut);
			fileOut.close();

			// Dispose of temporary files backing this workbook on disk
			workbook.dispose();

			// Close the workbook
			workbook.close();

			System.out.println("Excel file has been created successfully ! - path: " + generatedFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(generatedFilePath, HttpStatus.OK);
	}

	@GetMapping(produces = "application/json", path = "/getNewlyEnrolledAgywAndServicesSummary")
	public ResponseEntity<String> getNewlyEnrolledAgywAndServicesSummary(
			@RequestParam(name = "province") String province, @RequestParam(name = "districts") Integer[] districts,
			@RequestParam(name = "startDate") Long startDate, @RequestParam(name = "endDate") Long endDate,
			@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "username") String username) {

		AgywPrevReport report = new AgywPrevReport(service);

		boolean isEndOfCycle = false;

		Date initialDate = new Date(startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedInitialDate = sdf.format(initialDate);

		Date finalDate = new Date(endDate);
		SimpleDateFormat sdfFinal = new SimpleDateFormat("yyyy-MM-dd");
		String formattedFinalDate = sdfFinal.format(finalDate);

		createDirectory(REPORTS_HOME + "/" + username);

		String generatedFilePath = REPORTS_HOME + "/" + username + "/" + NEW_ENROLLED_SUMMARY_REPORT_NAME + "_"
				+ province.toUpperCase() + "_" + formattedInitialDate + "_" + formattedFinalDate + "_" + ".xlsx";

		try {
			// Set up streaming workbook
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			workbook.setCompressTempFiles(true); // Enable compression of temporary files

			// Create a sheet
			Sheet sheet = workbook.createSheet(SHEET_LABEL);
			// Create font for bold style
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);

			// Apply bold font style to the cells in the header row
			CellStyle boldCellStyle = workbook.createCellStyle();
			boldCellStyle.setFont(boldFont);

			// Apply bold font style to the cells in the header row
			CellStyle alignCellStyle = workbook.createCellStyle();
			// alignCellStyle.setFont(boldFont);
			alignCellStyle.setAlignment(HorizontalAlignment.CENTER);

			// Define Title
			String titleHeaders = "RELATÓRIO RESUMO DE RAMJ REGISTADAS NO DLT NO PERÍODO EM CONSIDERAÇÃO, SUAS VULNERABILIDADES E SERVIÇOS RECEBIDOS ";
			// Create a header row
			Row titleRow = sheet.createRow(0);
			// Write Title
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue(titleHeaders);

			// Merge the cells for the title
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 39));

			// Define Initial Date
			String initialDateHeaders[] = { "Data de Início:", formattedInitialDate };
			// Create a header row
			Row initialHeaderRow = sheet.createRow(1);
			// Write headers
			for (int i = 0; i < initialDateHeaders.length; i++) {
				Cell cell = initialHeaderRow.createCell(i);
				cell.setCellValue(initialDateHeaders[i]);
			}

			// Define Final Date
			String finalDateHeaders[] = { "Data de Fim:", formattedFinalDate };
			// Create a header row
			Row finalHeaderRow = sheet.createRow(2);
			// Write headers
			for (int i = 0; i < finalDateHeaders.length; i++) {
				Cell cell = finalHeaderRow.createCell(i);
				cell.setCellValue(finalDateHeaders[i]);
			}

			// Create a header row
			Row sessionRow = sheet.createRow(3);
			// Write Title and Merge cells for session headers

			Cell cell1 = sessionRow.createCell(0);
			cell1.setCellValue("Informação Demográfica");
			cell1.setCellStyle(alignCellStyle);

			Cell cell2 = sessionRow.createCell(17);
			cell2.setCellValue("Vulnerabilidades Gerais");
			cell2.setCellStyle(alignCellStyle);

			Cell cell3 = sessionRow.createCell(30);
			cell3.setCellValue("Serviços e Sub-Serviços");
			cell3.setCellStyle(alignCellStyle);

			// Merge cells for session headers
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 16)); // Merge first 17 columns
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 17, 29)); // Merge next 13 columns
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 30, 39)); // Merge last 10 columns

			// Define headers
			String[] headers = { "Província", "Distrito", "NUI", "Idade Actual", "Faixa Etária Actual",
					"Número de Vulnerabilidades", "Incluida no Indicador AGYW_PREV / Beneficiaria DREAMS ?",
					"Referências Clinicas não atendidas", "Referências Comunitárias não atendidas",
					"Sessões de Recursos Sociais", "Data da Última Sessão: Recursos Sociais",
					"Sessões de Prevenção do HIV", "Data da Última Sessão: HIV", "Sessões de Prevenção do VBG",
					"Data da Última Sessão: VBG", "Sessões Educativas Incluindo SSR (No SAAJ) Primário para: 10-14",
					"Data da Última Sessão: SAAJ", "Sessões de Literacia Financeira",
					"Data da Última Sessão: Literacia Financeira",
					"Aconselhamento e Testagem em Saúde Primário para: 15+ ou Sexualmente Activas",
					"Data da Última Sessão: ATS",
					"Promoção e Provisão de Preservativos Primário para: 15+ ou Sexualmente Activas",
					"Data da Última Sessão: Promoção e Provisão de Preservativos",
					"Promoção e Provisão de Contracepção", "Data da Última Sessão: Promoção e Provisão de Contracepção",
					"Abordagens Sócio Económicas Combinadas",
					"Data da Última Sessão: Abordagens Sócio Económicas Combinadas", "Subsídio Escolar",
					"Data da Última Sessão: Subsídio Escolar", "Cuidados Pós Violência (Comunitários)",
					"Data da Última Sessão: Cuidados Pós Violência (Comunitários)", "Cuidados Pós Violência (Clinicos)",
					"Data da Última Sessão: Cuidados Pós Violência (Clinicos)", "Outros Serviços do SAAJ",
					"Data da Última Sessão: Outros Serviços do SAAJ", "PrEP", "Data da Última Sessão: PrEP" };

			// Create a header row
			Row headerRow = sheet.createRow(4);
			// Write headers
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			int rowCount = 5; // start from row 1 (row 0 is for headers)
			int currentSheet;

			for (currentSheet = 0; currentSheet < currentSheet + 1; currentSheet++) {
				if (!isEndOfCycle) {
					// Insert data rows from the reportObjectList
					List<Object> reportObjectList = report.getNewlyEnrolledAgywAndServicesSummary(districts,
							new Date(startDate), new Date(endDate), currentSheet, pageSize);

					if (reportObjectList.size() < MAX_ROWS_NUMBER) {
						isEndOfCycle = true;
					}

					if (currentSheet != 0) {
						rowCount = 0;
						sheet = workbook.createSheet(SHEET_LABEL + currentSheet);
					}
					for (Object reportObject : reportObjectList) {
						Row row = sheet.createRow(rowCount++);
						// Write values to cells based on headers
						for (int i = 0; i < headers.length; i++) {
							Object value = getValueAtIndex(reportObject, i); // You need to implement this method
							if (value != null) {
								row.createCell(i).setCellValue(String.valueOf(value));
							}
						}
					}
				} else {
					break;
				}
			}

			// Write the workbook content to a file
			FileOutputStream fileOut = new FileOutputStream(generatedFilePath);
			workbook.write(fileOut);
			fileOut.close();

			// Dispose of temporary files backing this workbook on disk
			workbook.dispose();

			// Close the workbook
			workbook.close();

			System.out.println("Excel file has been created successfully ! - path: " + generatedFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(generatedFilePath, HttpStatus.OK);
	}

	@GetMapping(path = "/getBeneficiariesVulnerabilitiesAndServices")
	public ResponseEntity<String> getBeneficiariesVulnerabilitiesAndServices(
			@RequestParam(name = "province") String province, @RequestParam(name = "districts") Integer[] districts,
			@RequestParam(name = "startDate") Long startDate, @RequestParam(name = "endDate") Long endDate,
			@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "username") String username)
			throws IOException {

		AgywPrevReport report = new AgywPrevReport(service);

		boolean isEndOfCycle = false;

		Date initialDate = new Date(startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedInitialDate = sdf.format(initialDate);

		Date finalDate = new Date(endDate);
		SimpleDateFormat sdfFinal = new SimpleDateFormat("yyyy-MM-dd");
		String formattedFinalDate = sdfFinal.format(finalDate);

		createDirectory(REPORTS_HOME + "/" + username);

		String generatedFilePath = REPORTS_HOME + "/" + username + "/" + VULNERABILITIES_AND_SERVICES_REPORT_NAME + "_"
				+ province.toUpperCase() + "_" + formattedInitialDate + "_" + formattedFinalDate + "_" + ".xlsx";

		try {
			// Set up streaming workbook
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			workbook.setCompressTempFiles(true); // Enable compression of temporary files

			// Create a sheet
			Sheet sheet = workbook.createSheet(SHEET_LABEL);
			// Create font for bold style
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);

			// Apply bold font style to the cells in the header row
			CellStyle boldCellStyle = workbook.createCellStyle();
			boldCellStyle.setFont(boldFont);

			// Apply bold font style to the cells in the header row
			CellStyle alignCellStyle = workbook.createCellStyle();
			// alignCellStyle.setFont(boldFont);
			alignCellStyle.setAlignment(HorizontalAlignment.CENTER);

			// Define Title
			String titleHeaders = "LISTA DE BENEFICIARIAS DREAMS, SUAS VULNERABILIDADES E SERVIÇOS RECEBIDOS";
			// Create a header row
			Row titleRow = sheet.createRow(0);
			// Write Title
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue(titleHeaders);

			// Merge the cells for the title
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 39));

			// Define Initial Date
			String initialDateHeaders[] = { "Data de Início:", formattedInitialDate };
			// Create a header row
			Row initialHeaderRow = sheet.createRow(1);
			// Write headers
			for (int i = 0; i < initialDateHeaders.length; i++) {
				Cell cell = initialHeaderRow.createCell(i);
				cell.setCellValue(initialDateHeaders[i]);
			}

			// Define Final Date
			String finalDateHeaders[] = { "Data de Fim:", formattedFinalDate };
			// Create a header row
			Row finalHeaderRow = sheet.createRow(2);
			// Write headers
			for (int i = 0; i < finalDateHeaders.length; i++) {
				Cell cell = finalHeaderRow.createCell(i);
				cell.setCellValue(finalDateHeaders[i]);
			}

			// Create a header row
			Row sessionRow = sheet.createRow(3);
			// Write Title and Merge cells for session headers

			Cell cell1 = sessionRow.createCell(0);
			cell1.setCellValue("Informação Demográfica");
			cell1.setCellStyle(alignCellStyle);

			Cell cell2 = sessionRow.createCell(17);
			cell2.setCellValue("Vulnerabilidades Gerais");
			cell2.setCellStyle(alignCellStyle);

			Cell cell3 = sessionRow.createCell(30);
			cell3.setCellValue("Serviços e Sub-Serviços");
			cell3.setCellStyle(alignCellStyle);

			// Merge cells for session headers
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 16)); // Merge first 17 columns
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 17, 29)); // Merge next 13 columns
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 30, 39)); // Merge last 10 columns

			// Define headers
			String[] headers = { "Província", "Distrito", "Onde Mora", "Ponto de Entrada", "Organização",
					"Data de Inscrição", "Data de Registo", "Registado Por", "Data da Última Actualização",
					"Actualizado Por", "NUI", "Sexo", "Idade (Registo)", "Idade (Actual)", "Faixa Etária (Registo)",
					"Faixa Etária (Actual)", "Data de Nascimento",
					"Incluida no Indicador AGYW_PREV / Beneficiaria DREAMS ?", "Com Quem Mora", "Sustenta a Casa",
					"É Órfã?", "Vai à escola", "Tem Deficiência", "Tipo de Deficiência", "Já foi casada",
					"Já esteve grávida", "Tem filhos", "Está Grávida ou a Amamentar", "Trabalha", "Já fez teste de HIV",
					"Área de Serviço", "Serviço", "Sub-Serviço", "Pacote de Serviço", "Ponto de Entrada de Serviço",
					"Localização do Serviço", "Data do Serviço", "Provedor do Serviço", "Outras Observações",
					"Status" };

			// Create a header row
			Row headerRow = sheet.createRow(4);
			// Write headers
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			int rowCount = 5; // start from row 1 (row 0 is for headers)
			int currentSheet;

			for (currentSheet = 0; currentSheet < currentSheet + 1; currentSheet++) {
				if (!isEndOfCycle) {
					// Insert data rows from the reportObjectList
					List<Object> reportObjectList = report.getBeneficiariesVulnerabilitiesAndServices(districts,
							new Date(startDate), new Date(endDate), currentSheet, pageSize);

					if (reportObjectList.size() < MAX_ROWS_NUMBER) {
						isEndOfCycle = true;
					}

					if (currentSheet != 0) {
						rowCount = 0;
						sheet = workbook.createSheet(SHEET_LABEL + currentSheet);
					}
					for (Object reportObject : reportObjectList) {
						Row row = sheet.createRow(rowCount++);
						// Write values to cells based on headers
						for (int i = 0; i < headers.length; i++) {
							Object value = getValueAtIndex(reportObject, i); // You need to implement this method
							if (value != null) {
								row.createCell(i).setCellValue(String.valueOf(value));
							}
						}
					}
				} else {
					break;
				}
			}

			// Write the workbook content to a file
			FileOutputStream fileOut = new FileOutputStream(generatedFilePath);
			workbook.write(fileOut);
			fileOut.close();

			// Dispose of temporary files backing this workbook on disk
			workbook.dispose();

			// Close the workbook
			workbook.close();

			System.out.println("Excel file has been created successfully ! - path: " + generatedFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(generatedFilePath, HttpStatus.OK);
	}

	@GetMapping(produces = "application/json", path = "/getBeneficiariesVulnerabilitiesAndServicesSummary")
	public ResponseEntity<String> getBeneficiariesVulnerabilitiesAndServicesSummary(
			@RequestParam(name = "province") String province, @RequestParam(name = "districts") Integer[] districts,
			@RequestParam(name = "startDate") Long startDate, @RequestParam(name = "endDate") Long endDate,
			@RequestParam(name = "username") String username) {

		AgywPrevReport report = new AgywPrevReport(service);

		Date initialDate = new Date(startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedInitialDate = sdf.format(initialDate);

		Date finalDate = new Date(endDate);
		SimpleDateFormat sdfFinal = new SimpleDateFormat("yyyy-MM-dd");
		String formattedFinalDate = sdfFinal.format(finalDate);

		createDirectory(REPORTS_HOME + "/" + username);

		String generatedFilePath = REPORTS_HOME + "/" + username + "/"
				+ VULNERABILITIES_AND_SERVICES_SUMMARY_REPORT_NAME + "_" + province.toUpperCase() + "_"
				+ formattedInitialDate + "_" + formattedFinalDate + "_" + ".xlsx";

		try {
			// Set up streaming workbook
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			workbook.setCompressTempFiles(true); // Enable compression of temporary files

			// Create a sheet
			Sheet sheet = workbook.createSheet(SHEET_LABEL);
			// Create font for bold style
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);

			// Apply bold font style to the cells in the header row
			CellStyle boldCellStyle = workbook.createCellStyle();
			boldCellStyle.setFont(boldFont);

			// Apply bold font style to the cells in the header row
			CellStyle alignCellStyle = workbook.createCellStyle();
			// alignCellStyle.setFont(boldFont);
			alignCellStyle.setAlignment(HorizontalAlignment.CENTER);

			// Define Title
			String titleHeaders = "LISTA DE BENEFICIARIAS DREAMS, SUAS VULNERABILIDADES E SERVIÇOS RECEBIDOS";
			// Create a header row
			Row titleRow = sheet.createRow(0);
			// Write Title
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue(titleHeaders);

			// Merge the cells for the title
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 39));

			// Define Initial Date
			String initialDateHeaders[] = { "Data de Início:", formattedInitialDate };
			// Create a header row
			Row initialHeaderRow = sheet.createRow(1);
			// Write headers
			for (int i = 0; i < initialDateHeaders.length; i++) {
				Cell cell = initialHeaderRow.createCell(i);
				cell.setCellValue(initialDateHeaders[i]);
			}

			// Define Final Date
			String finalDateHeaders[] = { "Data de Fim:", formattedFinalDate };
			// Create a header row
			Row finalHeaderRow = sheet.createRow(2);
			// Write headers
			for (int i = 0; i < finalDateHeaders.length; i++) {
				Cell cell = finalHeaderRow.createCell(i);
				cell.setCellValue(finalDateHeaders[i]);
			}

			// Create a header row
			Row sessionRow = sheet.createRow(3);
			// Write Title and Merge cells for session headers

			Cell cell1 = sessionRow.createCell(0);
			cell1.setCellValue("Informação Demográfica");
			cell1.setCellStyle(alignCellStyle);

			Cell cell2 = sessionRow.createCell(17);
			cell2.setCellValue("Vulnerabilidades Gerais");
			cell2.setCellStyle(alignCellStyle);

			Cell cell3 = sessionRow.createCell(30);
			cell3.setCellValue("Serviços e Sub-Serviços");
			cell3.setCellStyle(alignCellStyle);

			// Merge cells for session headers
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 16)); // Merge first 17 columns
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 17, 29)); // Merge next 13 columns
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 30, 39)); // Merge last 10 columns

			// Define headers
			String[] headers = { "Província", "Distrito", "NUI", "Idade Actual", "Faixa Etária Actual",
					"Número de Vulnerabilidades", "Incluida no Indicador AGYW_PREV / Beneficiaria DREAMS ?",
					"Referências Clinicas não atendidas", "Referências Comunitárias não atendidas",
					"Sessões de Recursos Sociais", "Data da Última Sessão: Recursos Sociais",
					"Sessões de Prevenção do HIV", "Data da Última Sessão: HIV", "Sessões de Prevenção do VBG",
					"Data da Última Sessão: VBG", "Sessões Educativas Incluindo SSR (No SAAJ) Primário para: 10-14",
					"Data da Última Sessão: SAAJ", "Sessões de Literacia Financeira",
					"Data da Última Sessão: Literacia Financeira",
					"Aconselhamento e Testagem em Saúde Primário para: 15+ ou Sexualmente Activas",
					"Data da Última Sessão: ATS",
					"Promoção e Provisão de Preservativos Primário para: 15+ ou Sexualmente Activas",
					"Data da Última Sessão: Promoção e Provisão de Preservativos",
					"Promoção e Provisão de Contracepção", "Data da Última Sessão: Promoção e Provisão de Contracepção",
					"Abordagens Sócio Económicas Combinadas",
					"Data da Última Sessão: Abordagens Sócio Económicas Combinadas", "Subsídio Escolar",
					"Data da Última Sessão: Subsídio Escolar", "Cuidados Pós Violência (Comunitários)",
					"Data da Última Sessão: Cuidados Pós Violência (Comunitários)", "Cuidados Pós Violência (Clinicos)",
					"Data da Última Sessão: Cuidados Pós Violência (Clinicos)", "Outros Serviços do SAAJ",
					"Data da Última Sessão: Outros Serviços do SAAJ", "PrEP", "Data da Última Sessão: PrEP" };

			// Create a header row
			Row headerRow = sheet.createRow(4);
			// Write headers
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			int i;
			int rowCount = 5; // start from row 1 (row 0 is for headers)
			int currentSheet = 1;

			for (int index = 0; index < districts.length; index++) {
				// Insert data rows from the reportObjectList
				List<Object> reportObjectList = report.getBeneficiariesVulnerabilitiesAndServicesSummary(
						districts[index], new Date(startDate), new Date(endDate));

				if (rowCount > 1000000) {
					rowCount = 0;
					int seq = currentSheet + 1;
					sheet = workbook.createSheet(SHEET_LABEL + seq);
				}
				for (Object reportObject : reportObjectList) {
					Row row = sheet.createRow(rowCount++);
					// Write values to cells based on headers
					for (i = 0; i < headers.length; i++) {
						Object value = getValueAtIndex(reportObject, i); // You need to implement this method
						if (value != null) {
							row.createCell(i).setCellValue(String.valueOf(value));
						}
					}
				}
			}

			// Write the workbook content to a file
			FileOutputStream fileOut = new FileOutputStream(generatedFilePath);
			workbook.write(fileOut);
			fileOut.close();

			// Dispose of temporary files backing this workbook on disk
			workbook.dispose();

			// Close the workbook
			workbook.close();

			System.out.println("Excel file has been created successfully ! - path: " + generatedFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(generatedFilePath, HttpStatus.OK);
	}

	@GetMapping(path = "/getNewlyEnrolledAgywAndServices")
	public ResponseEntity<String> getNewlyEnrolledAgywAndServices(@RequestParam(name = "province") String province,
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") Long startDate,
			@RequestParam(name = "endDate") Long endDate, @RequestParam(name = "pageSize") int pageSize,
			@RequestParam(name = "username") String username) throws IOException {

		AgywPrevReport report = new AgywPrevReport(service);

		boolean isEndOfCycle = false;

		Date initialDate = new Date(startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedInitialDate = sdf.format(initialDate);

		Date finalDate = new Date(endDate);
		String formattedFinalDate = sdf.format(finalDate); // Using the same formatter for final date

		createDirectory(REPORTS_HOME + "/" + username);

		String generatedFilePath = REPORTS_HOME + "/" + username + "/" + NEW_ENROLLED_REPORT_NAME + "_"
				+ province.toUpperCase() + "_" + formattedInitialDate + "_" + formattedFinalDate + "_" + ".xlsx";

		try {
			// Set up streaming workbook
			SXSSFWorkbook workbook = new SXSSFWorkbook();
			workbook.setCompressTempFiles(true); // Enable compression of temporary files

			// Create a sheet
			Sheet sheet = workbook.createSheet(SHEET_LABEL);
			// Create font for bold style
			Font boldFont = workbook.createFont();
			boldFont.setBold(true);

			// Apply bold font style to the cells in the header row
			CellStyle boldCellStyle = workbook.createCellStyle();
			boldCellStyle.setFont(boldFont);

			// Apply bold font style to the cells in the header row
			CellStyle alignCellStyle = workbook.createCellStyle();
			// alignCellStyle.setFont(boldFont);
			alignCellStyle.setAlignment(HorizontalAlignment.CENTER);

			// Define Title
			String titleHeaders = "LISTA DE RAMJ REGISTADAS NO DLT NO PERÍODO EM CONSIDERAÇÃO, SUAS VULNERABILIDADES E SERVIÇOS RECEBIDOS ";
			// Create a header row
			Row titleRow = sheet.createRow(0);
			// Write Title
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue(titleHeaders);

			// Merge the cells for the title
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 39));

			// Define Initial Date
			String initialDateHeaders[] = { "Data de Início:", formattedInitialDate };
			// Create a header row
			Row initialHeaderRow = sheet.createRow(1);
			// Write headers
			for (int i = 0; i < initialDateHeaders.length; i++) {
				Cell cell = initialHeaderRow.createCell(i);
				cell.setCellValue(initialDateHeaders[i]);
			}

			// Define Final Date
			String finalDateHeaders[] = { "Data de Fim:", formattedFinalDate };
			// Create a header row
			Row finalHeaderRow = sheet.createRow(2);
			// Write headers
			for (int i = 0; i < finalDateHeaders.length; i++) {
				Cell cell = finalHeaderRow.createCell(i);
				cell.setCellValue(finalDateHeaders[i]);
			}

			// Create a header row
			Row sessionRow = sheet.createRow(3);
			// Write Title and Merge cells for session headers

			Cell cell1 = sessionRow.createCell(0);
			cell1.setCellValue("Informação Demográfica");
			cell1.setCellStyle(alignCellStyle);

			Cell cell2 = sessionRow.createCell(17);
			cell2.setCellValue("Vulnerabilidades Gerais");
			cell2.setCellStyle(alignCellStyle);

			Cell cell3 = sessionRow.createCell(30);
			cell3.setCellValue("Serviços e Sub-Serviços");
			cell3.setCellStyle(alignCellStyle);

			// Merge cells for session headers
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 16)); // Merge first 17 columns
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 17, 29)); // Merge next 13 columns
			sheet.addMergedRegion(new CellRangeAddress(3, 3, 30, 39)); // Merge last 10 columns

			// Define headers
			String[] headers = { "Província", "Distrito", "Onde Mora", "Ponto de Entrada", "Organização",
					"Data de Inscrição", "Data de Registo", "Registado Por", "Data da Última Actualização",
					"Actualizado Por", "NUI", "Sexo", "Idade (Registo)", "Idade (Actual)", "Faixa Etária (Registo)",
					"Faixa Etária (Actual)", "Data de Nascimento",
					"Incluida no Indicador AGYW_PREV / Beneficiaria DREAMS ?", "Com Quem Mora", "Sustenta a Casa",
					"É Órfã?", "Vai à escola", "Tem Deficiência", "Tipo de Deficiência", "Já foi casada",
					"Já esteve grávida", "Tem filhos", "Está Grávida ou a Amamentar", "Trabalha", "Já fez teste de HIV",
					"Área de Serviço", "Serviço", "Sub-Serviço", "Pacote de Serviço", "Ponto de Entrada de Serviço",
					"Localização do Serviço", "Data do Serviço", "Provedor do Serviço", "Outras Observações",
					"Status" };

			// Create a header row
			Row headerRow = sheet.createRow(4);
			// Write headers
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			int rowCount = 5; // start from row 1 (row 0 is for headers)
			int currentSheet;

			for (currentSheet = 0; currentSheet < currentSheet + 1; currentSheet++) {
				if (!isEndOfCycle) {
					List<Object> reportObjectList = report.getNewlyEnrolledAgywAndServices(districts,
							new Date(startDate), new Date(endDate), currentSheet, pageSize);

					if (reportObjectList.size() < MAX_ROWS_NUMBER) {
						isEndOfCycle = true;
					}
					if (currentSheet != 0) {
						rowCount = 0;
						sheet = workbook.createSheet(SHEET_LABEL + currentSheet);
					}
					for (Object reportObject : reportObjectList) {
						Row row = sheet.createRow(rowCount++);
						// Write values to cells based on headers
						for (int i = 0; i < headers.length; i++) {
							Object value = getValueAtIndex(reportObject, i); // You need to implement this method
							if (value != null) {
								row.createCell(i).setCellValue(String.valueOf(value));
							}
						}
					}
				} else {
					break;
				}
			}
			// Write the workbook content to a file
			FileOutputStream fileOut = new FileOutputStream(generatedFilePath);
			workbook.write(fileOut);
			fileOut.close();

			// Dispose of temporary files backing this workbook on disk
			workbook.dispose();

			// Close the workbook
			workbook.close();

			System.out.println("Excel file has been created successfully ! - path: " + generatedFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(generatedFilePath, HttpStatus.OK);
	}

	// Method to retrieve value for a specific index from the reportObject
	private static Object getValueAtIndex(Object reportObject, int index) {
		// Assuming reportObject is an array
		if (reportObject instanceof Object[]) {
			Object[] dataArray = (Object[]) reportObject;
			if (index >= 0 && index < dataArray.length) {
				return dataArray[index];
			}
		}
		return null;
	}

}