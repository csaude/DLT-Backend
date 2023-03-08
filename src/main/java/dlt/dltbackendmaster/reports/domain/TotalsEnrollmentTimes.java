package dlt.dltbackendmaster.reports.domain;

import java.util.HashMap;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nível de tempo de inscrição no programa (totais)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class TotalsEnrollmentTimes {

	private Map<String, Integer> time = new HashMap<>();

	public TotalsEnrollmentTimes() {
		for (int i = 0; i < ReportsConstants.ENROLLMENT_TIMES.length; i++) {
			time.put(ReportsConstants.ENROLLMENT_TIMES[i], 0);
		}
	}

	public Map<String, Integer> getTime() {
		return time;
	}

}
