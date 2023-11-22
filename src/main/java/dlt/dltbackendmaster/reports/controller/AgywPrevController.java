package dlt.dltbackendmaster.reports.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import dlt.dltbackendmaster.reports.domain.ReportResponse;
import dlt.dltbackendmaster.reports.domain.ResultObject;
import dlt.dltbackendmaster.reports.domain.SummaryNewlyEnrolledAgywAndServices;
import dlt.dltbackendmaster.service.DAOService;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * Controller resposável pela comunicação dos dados do relatório
 * 
 * @author Hamilton Mutaquiha
 *
 */
@RestController
@RequestMapping("/api/agyw-prev")
public class AgywPrevController {
	private static final String REPORTS_HOME = "target/reports";
	private static final String REPORT_TEMPLATE = "/reports/NewEnrolledReportTemplateLandscape.jrxml";
	private static final String REPORT_NAME = "DLT2.0_NOVAS_RAMJ_VULNERABILIDADES_E_SERVICOS_POR";
	private static final String SUMMARY_REPORT_TEMPLATE = "/reports/SummaryNewEnrolledReportTemplateLandscape.jrxml";
	private static final String SUMMARY_REPORT_NAME = "DLT2.0_RESUMO_NOVAS_RAMJ_VULNERABILIDADES_E_SERVICOS_POR";
	private final DAOService service;

	@Autowired
	public AgywPrevController(DAOService service) {
		this.service = service;
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<Map<Integer, Map<String, ResultObject>>> get(
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") String startDate,
			@RequestParam(name = "endDate") String endDate) {

		AgywPrevReport report = new AgywPrevReport(service);

		try {
			Map<Integer, Map<String, ResultObject>> reportObject = report.getAgywPrevResultObject(districts, startDate,
					endDate);

			return new ResponseEntity<>(reportObject, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(produces = "application/json", path = "/countNewlyEnrolledAgywAndServices")
	public ResponseEntity<List<Object>> countNewlyEnrolledAgywAndServices(
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") Long startDate,
			@RequestParam(name = "endDate") Long endDate) {

		AgywPrevReport report = new AgywPrevReport(service);

		try {
			List<Object> reportObject = report.countNewlyEnrolledAgywAndServices(districts, new Date(startDate),
					new Date(endDate));

			return new ResponseEntity<>(reportObject, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(path = "/getNewlyEnrolledAgywAndServices")
	public ResponseEntity<String> getNewlyEnrolledAgywAndServices(@RequestParam(name = "province") String province,
			@RequestParam(name = "districts") Integer[] districts, @RequestParam(name = "startDate") Long startDate,
			@RequestParam(name = "endDate") Long endDate, @RequestParam(name = "pageIndex") int pageIndex,
			@RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "username") String username)
			throws IOException {

		AgywPrevReport report = new AgywPrevReport(service);

		Date initialDate = new Date(startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedInitialDate = sdf.format(initialDate);

		Date finalDate = new Date(endDate);
		SimpleDateFormat sdfFinal = new SimpleDateFormat("yyyy-MM-dd");
		String formattedFinalDate = sdfFinal.format(finalDate);

		createDirectory(REPORTS_HOME + "/" + username);

		String generatedFileName = REPORTS_HOME + "/" + username + "/" + REPORT_NAME + "_" + province.toUpperCase()
				+ "_" + formattedInitialDate + "_" + formattedFinalDate + "_" + pageIndex + "_" + ".xlsx";

		List<NewlyEnrolledAgywAndServices> rows = new ArrayList<>();

		List<Object> reportObjectList = report.getNewlyEnrolledAgywAndServices(districts, new Date(startDate),
				new Date(endDate), pageIndex, pageSize);
		Object[][] reportObjectArray = reportObjectList.toArray(new Object[0][0]);

		int i = 1;
		try {
			for (Object[] obj : reportObjectArray) {
				rows.add(new NewlyEnrolledAgywAndServices(i + "", String.valueOf(obj[0]), String.valueOf(obj[1]),
						String.valueOf(obj[2]), String.valueOf(obj[3]), String.valueOf(obj[4]), String.valueOf(obj[5]),
						String.valueOf(obj[6]), String.valueOf(obj[7]), String.valueOf(obj[8]), String.valueOf(obj[9]),
						String.valueOf(obj[10]), String.valueOf(obj[11]), String.valueOf(obj[12]),
						String.valueOf(obj[13]), String.valueOf(obj[14]), String.valueOf(obj[15]),
						String.valueOf(obj[16]), String.valueOf(obj[17]), String.valueOf(obj[18]),
						String.valueOf(obj[19]), String.valueOf(obj[20]), String.valueOf(obj[21]),
						String.valueOf(obj[22] != null ? obj[22] : ""), String.valueOf(obj[23]),
						String.valueOf(obj[24]), String.valueOf(obj[25]), String.valueOf(obj[26]),
						String.valueOf(obj[27]), String.valueOf(obj[28]), String.valueOf(obj[29]),
						String.valueOf(obj[30]), String.valueOf(obj[31]), String.valueOf(obj[32]),
						String.valueOf(obj[33]), String.valueOf(obj[34]), String.valueOf(obj[35]),
						String.valueOf(obj[36] != null ? obj[36] : ""), String.valueOf(obj[37] != null ? obj[37] : ""),
						String.valueOf(obj[38])));
				i++;
			}

			// Compile the .jrxml template to a .jasper file
			InputStream jrxmlStream = AgywPrevController.class.getResourceAsStream(REPORT_TEMPLATE);
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

			// Convert data to a JRBeanCollectionDataSource
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

			// Create a Map to store report parameters
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("date_start", formattedInitialDate);
			parameters.put("date_end", formattedFinalDate);

			// Generate the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//				configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setAutoFitPageHeight(true);
			configuration.setIgnoreGraphics(false);
			// Set text wrapping
			configuration.setWhitePageBackground(false);
			configuration.setRemoveEmptySpaceBetweenColumns(true);
			configuration.setWrapText(true); // Enable text wrapping

			// Apply the configuration to the exporter
			JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

			// Export the report to XLSX
			JRXlsxExporter exporter = new JRXlsxExporter(jasperReportsContext);
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(generatedFileName));
			exporter.setConfiguration(configuration);
			exporter.exportReport();

			System.out.println(generatedFileName + ": generated and exported to XLSX with borders successfully.");
		} catch (JRException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(generatedFileName, HttpStatus.OK);
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

	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> downloadFile(@RequestParam(name = "filePath") String filePath) throws IOException {
		File file = new File(filePath);
		Resource resource = new FileSystemResource(file);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=" + file.getName());

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	@GetMapping(produces = "application/json", path = "/getNewlyEnrolledAgywAndServicesSummary")
	public ResponseEntity<String> getNewlyEnrolledAgywAndServicesSummary(
			@RequestParam(name = "province") String province, @RequestParam(name = "districts") Integer[] districts,
			@RequestParam(name = "startDate") Long startDate, @RequestParam(name = "endDate") Long endDate,
			@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "nextIndex") int nextIndex,
			@RequestParam(name = "username") String username) {
		String generatedReportResponse;

		Date initialDate = new Date(startDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String formattedInitialDate = sdf.format(initialDate);

		Date finalDate = new Date(endDate);
		SimpleDateFormat sdfFinal = new SimpleDateFormat("yyyy-MM-dd");
		String formattedFinalDate = sdfFinal.format(finalDate);

		createDirectory(REPORTS_HOME + "/" + username);

		String generatedFileName = REPORTS_HOME + "/" + username + "/" + SUMMARY_REPORT_NAME + "_"
				+ province.toUpperCase() + "_" + formattedInitialDate + "_" + formattedFinalDate + "_" + pageNumber
				+ "_" + ".xlsx";

		List<SummaryNewlyEnrolledAgywAndServices> rows = new ArrayList<>();

		AgywPrevReport report = new AgywPrevReport(service);

		List<Object> reportObjectList = report.getNewlyEnrolledAgywAndServicesSummary(districts, new Date(startDate),
				new Date(endDate));
		Object[][] reportObjectArray = reportObjectList.toArray(new Object[0][0]);

		try {
			for (Object[] obj : reportObjectArray) {
				rows.add(new SummaryNewlyEnrolledAgywAndServices(nextIndex + "",
						String.valueOf(obj[0] != null ? obj[0] : ""), String.valueOf(obj[1] != null ? obj[1] : ""),
						String.valueOf(obj[2] != null ? obj[2] : ""), String.valueOf(obj[3] != null ? obj[3] : ""),
						String.valueOf(obj[4] != null ? obj[4] : ""), String.valueOf(obj[5] != null ? obj[5] : ""),
						String.valueOf(obj[6] != null ? obj[6] : ""), String.valueOf(obj[7] != null ? obj[7] : ""),
						String.valueOf(obj[8] != null ? obj[8] : ""), String.valueOf(obj[9] != null ? obj[9] : ""),
						String.valueOf(obj[10] != null ? obj[10] : ""), String.valueOf(obj[11] != null ? obj[11] : ""),
						String.valueOf(obj[12] != null ? obj[12] : ""), String.valueOf(obj[13] != null ? obj[13] : ""),
						String.valueOf(obj[14] != null ? obj[14] : ""), String.valueOf(obj[15] != null ? obj[15] : ""),
						String.valueOf(obj[16] != null ? obj[16] : ""), String.valueOf(obj[17] != null ? obj[17] : ""),
						String.valueOf(obj[18] != null ? obj[18] : ""), String.valueOf(obj[19] != null ? obj[19] : ""),
						String.valueOf(obj[20] != null ? obj[20] : ""), String.valueOf(obj[21] != null ? obj[21] : ""),
						String.valueOf(obj[22] != null ? obj[22] : ""), String.valueOf(obj[23] != null ? obj[23] : ""),
						String.valueOf(obj[24] != null ? obj[24] : ""), String.valueOf(obj[25] != null ? obj[25] : ""),
						String.valueOf(obj[26] != null ? obj[26] : ""), String.valueOf(obj[27] != null ? obj[27] : ""),
						String.valueOf(obj[28] != null ? obj[28] : ""), String.valueOf(obj[29] != null ? obj[29] : ""),
						String.valueOf(obj[30] != null ? obj[30] : ""), String.valueOf(obj[31] != null ? obj[31] : ""),
						String.valueOf(obj[32] != null ? obj[32] : ""), String.valueOf(obj[33] != null ? obj[33] : ""),
						String.valueOf(obj[34] != null ? obj[34] : ""), String.valueOf(obj[35] != null ? obj[35] : ""),
						String.valueOf(obj[36] != null ? obj[36] : "")));
				nextIndex++;
			}

			// Compile the .jrxml template to a .jasper file
			InputStream jrxmlStream = AgywPrevController.class.getResourceAsStream(SUMMARY_REPORT_TEMPLATE);
			JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

			if (rows.size() > 0) {
				// Convert data to a JRBeanCollectionDataSource
				JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

				// Create a Map to store report parameters
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("date_start", formattedInitialDate);
				parameters.put("date_end", formattedFinalDate);

				// Generate the report
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//					configuration.setOnePagePerSheet(true);
				configuration.setDetectCellType(true);
				configuration.setAutoFitPageHeight(true);
				configuration.setIgnoreGraphics(false);
				// Set text wrapping
				configuration.setWhitePageBackground(false);
				configuration.setRemoveEmptySpaceBetweenColumns(true);
				configuration.setWrapText(true); // Enable text wrapping

				// Apply the configuration to the exporter
				JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();

				// Export the report to XLSX
				JRXlsxExporter exporter = new JRXlsxExporter(jasperReportsContext);
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(generatedFileName));
				exporter.setConfiguration(configuration);
				exporter.exportReport();
			}

			ObjectMapper objectMapper = new ObjectMapper();
			generatedReportResponse = objectMapper
					.writeValueAsString(new ReportResponse(generatedFileName, rows.size(), nextIndex));

			System.out.println(generatedFileName + ": generated and exported to XLSX with borders successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(generatedReportResponse, HttpStatus.OK);
	}

}