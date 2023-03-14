package dlt.dltbackendmaster.reports.domain;

import java.util.HashMap;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nívele de faixas etárias (totais)
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class TotalsAgeBands {

	private Map<String, Integer> ageBands = new HashMap<>();

	public TotalsAgeBands() {
		for (int i = 0; i < ReportsConstants.AGE_BANDS.length; i++) {
			ageBands.put(ReportsConstants.AGE_BANDS[i], 0);
		}
	}

	public Map<String, Integer> getAgeBands() {
		return ageBands;
	}

}
