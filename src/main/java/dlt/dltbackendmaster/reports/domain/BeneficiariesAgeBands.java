package dlt.dltbackendmaster.reports.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nível de faixas etárias (beneficiárias)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class BeneficiariesAgeBands {

	private Map<String, List<Integer>> ageBands = new HashMap<>();

	public BeneficiariesAgeBands() {
		for (int i = 0; i < ReportsConstants.AGE_BANDS.length; i++) {
			ageBands.put(ReportsConstants.AGE_BANDS[i], new ArrayList<>());
		}
	}

	public Map<String, List<Integer>> getAgeBands() {
		return ageBands;
	}

}
