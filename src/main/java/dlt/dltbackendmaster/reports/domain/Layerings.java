package dlt.dltbackendmaster.reports.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.reports.utils.ReportsConstants;

/**
 * Nível do estágio no programa
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class Layerings {

	private Map<String, List<Integer>> result = new HashMap<>();

	public Layerings() {

		for (int i = 0; i < ReportsConstants.DISAGGREGATIONS.length; i++) {
			this.result.put(ReportsConstants.DISAGGREGATIONS[i], new ArrayList<>());
		}
	}

	public Map<String, List<Integer>> getResult() {
		return result;
	}

}
