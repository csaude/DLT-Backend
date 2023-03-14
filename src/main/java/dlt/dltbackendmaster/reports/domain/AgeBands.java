package dlt.dltbackendmaster.reports.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nível de faixas etárias
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class AgeBands {

	private Map<String, Map<String, Map<String, List<Integer>>>> ageBands = new HashMap<>();

	public AgeBands() {
		for (int i = 0; i < ReportsConstants.AGE_BANDS.length - 1; i++) {
			ageBands.put(ReportsConstants.AGE_BANDS[i], new EnrollmentTimes().getTime());
		}
	}

	public Map<String, Map<String, Map<String, List<Integer>>>> getAgeBands() {
		return ageBands;
	}

}
