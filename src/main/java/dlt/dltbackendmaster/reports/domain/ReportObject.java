package dlt.dltbackendmaster.reports.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Nível do relatório
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class ReportObject {

	private Map<Integer, Map<String, Map<String, Map<String, List<Integer>>>>> reportObject = new HashMap<>();

	public ReportObject(Integer[] districts) {

		for (Integer district : districts) {
			reportObject.put(district, new AgeBands().getAgeBands());
		}
	}

	public Map<Integer, Map<String, Map<String, Map<String, List<Integer>>>>> getReportObject() {
		return reportObject;
	}

}
