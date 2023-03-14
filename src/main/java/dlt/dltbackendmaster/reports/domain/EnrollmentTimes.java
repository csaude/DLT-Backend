package dlt.dltbackendmaster.reports.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nível de tempo de inscrição no programa
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class EnrollmentTimes {

	private Map<String, Map<String, List<Integer>>> time = new HashMap<>();

	public EnrollmentTimes() {
		for (int i = 0; i < ReportsConstants.ENROLLMENT_TIMES.length ; i++) {
			time.put(ReportsConstants.ENROLLMENT_TIMES[i], new Layerings().getResult());
		}
	}

	public Map<String, Map<String, List<Integer>>> getTime() {
		return time;
	}

}
