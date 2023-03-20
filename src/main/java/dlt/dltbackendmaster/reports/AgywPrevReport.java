package dlt.dltbackendmaster.reports;

import static dlt.dltbackendmaster.reports.utils.ReportsConstants.*;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dlt.dltbackendmaster.domain.AgywPrev;
import dlt.dltbackendmaster.reports.domain.ReportObject;
import dlt.dltbackendmaster.reports.domain.ResultObject;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.util.Utility;

/**
 * Classe responsável pela manipulação e retorno dos dados para compor o
 * relatório
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class AgywPrevReport {

	private static final String COP_22_START_DATE = "20220921";
	private final DAOService service;

	public AgywPrevReport(DAOService service) {
		this.service = service;
	}

	public Map<Integer, Map<String, ResultObject>> getAgywPrevResultObject(Integer[] districts, String startDate,
			String endDate) {
		ReportObject reportObject = process(districts, startDate, endDate);
		Map<Integer, Map<String, ResultObject>> agywPrevResultObject = new HashMap<>();

		for (Integer district : districts) {
			Map<String, ResultObject> districtAgywPrevResultObject = new HashMap<>();
			Map<String, Long> districtSummary = processDistrictSummary(district);
			ResultObject completedOnlyPrimaryPackage = computeDiggregationCompletedOnlyPrimaryPackage(reportObject,
					district);
			ResultObject completedPrimaryPackageAndSecondaryService = computeDiggregationCompletedPrimaryPackageAndSecondaryService(
					reportObject, district);
			ResultObject completedOnlyServiceNotPrimaryPackage = computeDiggregationCompletedOnlyServiceNotPrimaryPackage(
					reportObject, district);
			ResultObject startedServiceDidNotComplete = computeDiggregationStartedServiceDidNotComplete(reportObject,
					district);
			districtAgywPrevResultObject.put("completed-only-primary-package", completedOnlyPrimaryPackage);
			districtAgywPrevResultObject.put("completed-primary-package-and-secondary-service",
					completedPrimaryPackageAndSecondaryService);
			districtAgywPrevResultObject.put("completed-service-not-primary-package",
					completedOnlyServiceNotPrimaryPackage);
			districtAgywPrevResultObject.put("started-service-did-not-complete", startedServiceDidNotComplete);
			districtAgywPrevResultObject.put("completed-violence-service",
					computeDiggregationCompletedViolenceService(reportObject, district));
			districtAgywPrevResultObject.put("had-school-allowance",
					computeDiggregationHasSchoolAllowance(reportObject, district));
			districtAgywPrevResultObject.put("completed-social-economic-approaches",
					computeDiggregationCompletedSocialEconomicAllowance(reportObject, district));

			// Process District Summary
			ResultObject ro = getTotalResultObject();
			ro.setTotal(completedOnlyPrimaryPackage.getTotal() + completedPrimaryPackageAndSecondaryService.getTotal()
					+ completedOnlyServiceNotPrimaryPackage.getTotal() + startedServiceDidNotComplete.getTotal());
			districtAgywPrevResultObject.put("all-disaggregations-total", ro);
			ro = getTotalResultObject();
			ro.setTotal(districtSummary.get("totalBeneficiaries").intValue());
			districtAgywPrevResultObject.put("total-beneficiaries", ro);
			ro = getTotalResultObject();
			ro.setTotal(districtSummary.get("maleBeneficiaries").intValue());
			districtAgywPrevResultObject.put("male-beneficiaries", ro);
			ro = getTotalResultObject();
			ro.setTotal(districtSummary.get("femaleBeneficiaries").intValue());
			districtAgywPrevResultObject.put("female-beneficiaries", ro);

			agywPrevResultObject.put(district, districtAgywPrevResultObject);
		}

		return agywPrevResultObject;
	}

	public ReportObject process(Integer[] districts, String startDate, String endDate) {

		ReportObject reportObject = new ReportObject(districts);

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

		try {
			List<AgywPrev> data = service.GetAllEntityByNamedNativeQuery("AgywPrev.findByDistricts",
					Arrays.asList(districts), startDate, endDate);

			Date eDate = df.parse(endDate);
			Date cop22StartDate = df.parse(COP_22_START_DATE);

			for (AgywPrev agywPrev : data) {

				int ageOnEndDate = Utility.dateDiffInYears(agywPrev.getDate_of_birth(), eDate);
				int enrollmentTime = Utility.dateDiffInYears(agywPrev.getEnrollment_date(), eDate) * 12;

				if (agywPrev.getCurrent_age_band() == 1) { // 9-14
					if (agywPrev.getVblt_is_student() == 1) { // AVANTE ESTUDANTE
						if ((completedAvanteEstudante(agywPrev)
								|| ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev, cop22StartDate))
								&& completedSAAJEducationSessions(agywPrev)
								&& (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
										|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev))
								&& (agywPrev.getVblt_sexually_active() == null
										|| agywPrev.getVblt_sexually_active() != null
												&& agywPrev.getVblt_sexually_active() == 0
										|| agywPrev.getVblt_sexually_active() != null
												&& agywPrev.getVblt_sexually_active() == 1
												&& completedHIVTestingServices(agywPrev)
												&& completedCondomsPromotionOrProvision(agywPrev))) {
							addBeneficiary(reportObject, agywPrev.getDistrict_id(),
									getAgeBandIndex(agywPrev.getCurrent_age_band()),
									getEnrollmentTimeIndex(enrollmentTime), COMPLETED_PRIMARY_PACKAGE,
									agywPrev.getBeneficiary_id());
						}
						if ((completedAvanteEstudante(agywPrev)
								|| ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev, cop22StartDate))
								|| completedSAAJEducationSessions(agywPrev)
								|| (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
										|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev))
								|| (agywPrev.getVblt_sexually_active() != null
										&& agywPrev.getVblt_sexually_active() == 1
										&& (completedHIVTestingServices(agywPrev)
												|| completedCondomsPromotionOrProvision(agywPrev)))) {
							addBeneficiary(reportObject, agywPrev.getDistrict_id(),
									getAgeBandIndex(agywPrev.getCurrent_age_band()),
									getEnrollmentTimeIndex(enrollmentTime), COMPLETED_PRIMARY_SERVICE,
									agywPrev.getBeneficiary_id());
						}
						if (startedAvanteEstudante(agywPrev) || startedSAAJEducationSessions(agywPrev)
								|| startedAvanteEstudanteViolencePrevention(agywPrev)
								|| startedPostViolenceCare_US(agywPrev) || startedPostViolenceCare_CM(agywPrev)
								|| startedFinancialLiteracyAflatoun(agywPrev)
								|| ageOnEndDate == 14 && (startedFinancialLiteracyAflateen(agywPrev)
										|| startedGuiaFacilitacao(agywPrev)
										|| agywPrev.getHiv_gbv_sessions_prep() > 0)) {
							addBeneficiary(reportObject, agywPrev.getDistrict_id(),
									getAgeBandIndex(agywPrev.getCurrent_age_band()),
									getEnrollmentTimeIndex(enrollmentTime), STARTED_SERVICE,
									agywPrev.getBeneficiary_id());
						}
						if (completedAvanteEstudanteViolencePrevention(agywPrev)) {
							addBeneficiary(reportObject, agywPrev.getDistrict_id(),
									getAgeBandIndex(agywPrev.getCurrent_age_band()),
									getEnrollmentTimeIndex(enrollmentTime), COMPLETED_VIOLENCE_SERVICE,
									agywPrev.getBeneficiary_id());
						}
					} else { // AVANTE RAPARIGA
						if ((completedAvanteRapariga(agywPrev)
								|| ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev, cop22StartDate))
								&& completedSAAJEducationSessions(agywPrev)
								&& (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
										|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev))
								&& (agywPrev.getVblt_sexually_active() == null
										|| agywPrev.getVblt_sexually_active() != null
												&& agywPrev.getVblt_sexually_active() == 0
										|| agywPrev.getVblt_sexually_active() != null
												&& agywPrev.getVblt_sexually_active() == 1
												&& completedHIVTestingServices(agywPrev)
												&& completedCondomsPromotionOrProvision(agywPrev))) {
							addBeneficiary(reportObject, agywPrev.getDistrict_id(),
									getAgeBandIndex(agywPrev.getCurrent_age_band()),
									getEnrollmentTimeIndex(enrollmentTime), COMPLETED_PRIMARY_PACKAGE,
									agywPrev.getBeneficiary_id());
						}
						if ((completedAvanteRapariga(agywPrev)
								|| ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev, cop22StartDate))
								|| completedSAAJEducationSessions(agywPrev)
								|| (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
										|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev))
								|| (agywPrev.getVblt_sexually_active() != null
										&& agywPrev.getVblt_sexually_active() == 1
										&& (completedHIVTestingServices(agywPrev)
												|| completedCondomsPromotionOrProvision(agywPrev)))) {
							addBeneficiary(reportObject, agywPrev.getDistrict_id(),
									getAgeBandIndex(agywPrev.getCurrent_age_band()),
									getEnrollmentTimeIndex(enrollmentTime), COMPLETED_PRIMARY_SERVICE,
									agywPrev.getBeneficiary_id());
						}
						if (startedAvanteRapariga(agywPrev) || startedSAAJEducationSessions(agywPrev)
								|| startedAvanteRaparigaViolencePrevention(agywPrev)
								|| startedPostViolenceCare_US(agywPrev) || startedPostViolenceCare_CM(agywPrev)
								|| startedFinancialLiteracyAflatoun(agywPrev)
								|| ageOnEndDate == 14 && (startedFinancialLiteracyAflateen(agywPrev)
										|| startedGuiaFacilitacao(agywPrev)
										|| agywPrev.getHiv_gbv_sessions_prep() > 0)) {
							addBeneficiary(reportObject, agywPrev.getDistrict_id(),
									getAgeBandIndex(agywPrev.getCurrent_age_band()),
									getEnrollmentTimeIndex(enrollmentTime), STARTED_SERVICE,
									agywPrev.getBeneficiary_id());
						}
						if (completedAvanteRaparigaViolencePrevention(agywPrev)) {
							addBeneficiary(reportObject, agywPrev.getDistrict_id(),
									getAgeBandIndex(agywPrev.getCurrent_age_band()),
									getEnrollmentTimeIndex(enrollmentTime), COMPLETED_VIOLENCE_SERVICE,
									agywPrev.getBeneficiary_id());
						}
					}
					if (hadScoolAllowance(agywPrev) || completedContraceptionsPromotionOrProvision(agywPrev)
							|| agywPrev.getVblt_sexually_active() != null && agywPrev.getVblt_sexually_active() == 0
									&& (completedHIVTestingServices(agywPrev)
											|| completedCondomsPromotionOrProvision(agywPrev))
							|| completedPostViolenceCare_US(agywPrev) || completedPostViolenceCare_CM(agywPrev)
							|| completedOtherSAAJServices(agywPrev)) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_SECONDARY_SERVICE, agywPrev.getBeneficiary_id());
					}
				} else { // 15-24
					if (completedCondomsPromotionOrProvision(agywPrev)
							&& completedGuiaFacilitacao(agywPrev, cop22StartDate)
							&& completedHIVTestingServices(agywPrev)
							&& (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflateen(agywPrev))) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_PRIMARY_PACKAGE, agywPrev.getBeneficiary_id());
					}
					if (completedCondomsPromotionOrProvision(agywPrev)
							|| completedGuiaFacilitacao(agywPrev, cop22StartDate)
							|| completedHIVTestingServices(agywPrev) || completedFinancialLiteracy(agywPrev)
							|| completedFinancialLiteracyAflateen(agywPrev)) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_PRIMARY_SERVICE, agywPrev.getBeneficiary_id());
					}
					if (agywPrev.getCurrent_age_band() == 2 && hadScoolAllowance(agywPrev)
							|| completedPostViolenceCare_US(agywPrev) || completedPostViolenceCare_CM(agywPrev)
							|| completedCombinedSocioEconomicApproaches(agywPrev)
							|| completedOtherSAAJServices(agywPrev)
							|| completedContraceptionsPromotionOrProvision(agywPrev)) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_SECONDARY_SERVICE, agywPrev.getBeneficiary_id());
					}
					if (startedGuiaFacilitacao(agywPrev) || startedViolencePrevention15Plus(agywPrev)
							|| (agywPrev.getCurrent_age_band() == 2 && startedPostViolenceCare_US(agywPrev)
									|| startedPostViolenceCare_CM(agywPrev)
									|| startedFinancialLiteracyAflateen(agywPrev))) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								STARTED_SERVICE, agywPrev.getBeneficiary_id());

					}
					if (completedViolencePrevention15Plus(agywPrev)) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_VIOLENCE_SERVICE, agywPrev.getBeneficiary_id());
					}
					if (completedCombinedSocioEconomicApproaches(agywPrev) && ageOnEndDate < 26) {
						if (ageOnEndDate == 25) {
							int ageOnServiceDate = Utility.dateDiffInYears(agywPrev.getDate_of_birth(),
									agywPrev.getApproaches_date());
							if (ageOnServiceDate == 25) {
								continue;
							}
						}
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								HAD_SOCIAL_ECONOMIC_APPROACHES, agywPrev.getBeneficiary_id());
					}
				}
				if (hadScoolAllowance(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							HAD_SCHOLL_ALLOWANCE, agywPrev.getBeneficiary_id());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return reportObject;
	}

	private void addBeneficiary(ReportObject reportObject, Integer district, Integer ageBand, Integer enrollmentTime,
			Integer layering, Integer beneficiary) {
		reportObject.getReportObject().get(district).get(AGE_BANDS[ageBand]).get(ENROLLMENT_TIMES[enrollmentTime])
				.get(DISAGGREGATIONS[layering]).add(beneficiary);
	}

	public ResultObject computeDiggregationCompletedOnlyPrimaryPackage(ReportObject reportObject, Integer district) {
		ResultObject resultObject = new ResultObject();

		int total = 0;
		for (int i = 0; i < ENROLLMENT_TIMES.length; i++) {
			int subTotal = 0;
			for (int j = 0; j < AGE_BANDS.length - 1; j++) {
				List<Integer> completedOnlyPrimaryPackage = new ArrayList<>();
				List<Integer> completedPrimaryPackage = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_PRIMARY_PACKAGE]);
				List<Integer> completedSecondaryService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_SECONDARY_SERVICE]);

				for (Integer beneficiaryId : completedPrimaryPackage) {
					if (!completedSecondaryService.contains(beneficiaryId)) {
						completedOnlyPrimaryPackage.add(beneficiaryId);
					}
				}
				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(completedOnlyPrimaryPackage);
				int count = completedOnlyPrimaryPackage.size();
				subTotal += count;
				resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[j], count);
			}
			resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[4], subTotal);
			total += subTotal;
		}
		resultObject.setTotal(total);
		return resultObject;
	}

	public ResultObject computeDiggregationCompletedPrimaryPackageAndSecondaryService(ReportObject reportObject,
			Integer district) {
		ResultObject resultObject = new ResultObject();

		int total = 0;
		for (int i = 0; i < ENROLLMENT_TIMES.length; i++) {
			int subTotal = 0;
			for (int j = 0; j < AGE_BANDS.length - 1; j++) {
				List<Integer> completedPrimaryPackageAndSecondaryService = new ArrayList<>();
				List<Integer> completedPrimaryPackage = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_PRIMARY_PACKAGE]);
				List<Integer> completedSecondaryService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_SECONDARY_SERVICE]);

				for (Integer beneficiaryId : completedPrimaryPackage) {
					if (completedSecondaryService.contains(beneficiaryId)) {
						completedPrimaryPackageAndSecondaryService.add(beneficiaryId);
					}
				}
				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(completedPrimaryPackageAndSecondaryService);
				int count = completedPrimaryPackageAndSecondaryService.size();
				subTotal += count;
				resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[j], count);
			}
			resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[4], subTotal);
			total += subTotal;
		}
		resultObject.setTotal(total);
		return resultObject;
	}

	public ResultObject computeDiggregationCompletedOnlyServiceNotPrimaryPackage(ReportObject reportObject,
			Integer district) {
		ResultObject resultObject = new ResultObject();

		int total = 0;
		for (int i = 0; i < ENROLLMENT_TIMES.length; i++) {
			int subTotal = 0;
			for (int j = 0; j < AGE_BANDS.length - 1; j++) {
				List<Integer> completedOnlyServiceNotPrimaryPackage = new ArrayList<>();
				List<Integer> completedService = new ArrayList<>();
				List<Integer> completedPrimaryPackage = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_PRIMARY_PACKAGE]);
				List<Integer> completedPrimaryService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_PRIMARY_SERVICE]);
				List<Integer> completedSecondaryService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_SECONDARY_SERVICE]);

				completedService.addAll(completedPrimaryService);

				for (Integer beneficiaryId : completedSecondaryService) {
					if (!completedService.contains(beneficiaryId)) {
						completedService.add(beneficiaryId);
					}
				}

				for (Integer beneficiaryId : completedPrimaryPackage) {
					if (!completedService.contains(beneficiaryId)) {
						completedOnlyServiceNotPrimaryPackage.add(beneficiaryId);
					}
				}
				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(completedOnlyServiceNotPrimaryPackage);
				int count = completedOnlyServiceNotPrimaryPackage.size();
				subTotal += count;
				resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[j], count);
			}
			resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[4], subTotal);
			total += subTotal;
		}
		resultObject.setTotal(total);
		return resultObject;
	}

	public ResultObject computeDiggregationStartedServiceDidNotComplete(ReportObject reportObject, Integer district) {
		ResultObject resultObject = new ResultObject();

		int total = 0;
		for (int i = 0; i < ENROLLMENT_TIMES.length; i++) {
			int subTotal = 0;
			for (int j = 0; j < AGE_BANDS.length - 1; j++) {
				List<Integer> startedServiceDidNotComplete = new ArrayList<>();
				List<Integer> startedService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[STARTED_SERVICE]);
				List<Integer> completedPrimaryService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_PRIMARY_SERVICE]);
				List<Integer> completedSecondaryService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_SECONDARY_SERVICE]);

				for (Integer beneficiaryId : startedService) {
					if (!(completedPrimaryService.contains(beneficiaryId)
							|| completedSecondaryService.contains(beneficiaryId))) {
						startedServiceDidNotComplete.add(beneficiaryId);
					}
				}
				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(startedServiceDidNotComplete);
				int count = startedServiceDidNotComplete.size();
				subTotal += count;
				resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[j], count);
			}
			resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[4], subTotal);
			total += subTotal;
		}
		resultObject.setTotal(total);
		return resultObject;
	}

	private ResultObject computeDiggregationCompletedViolenceService(ReportObject reportObject, Integer district) {
		ResultObject resultObject = new ResultObject();

		int total = 0;
		for (int i = 0; i < ENROLLMENT_TIMES.length; i++) {
			int subTotal = 0;
			for (int j = 0; j < AGE_BANDS.length - 1; j++) {
				List<Integer> completedViolenceService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_VIOLENCE_SERVICE]);

				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(completedViolenceService);
				int count = completedViolenceService.size();
				subTotal += count;
				resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[j], count);
			}
			resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[4], subTotal);
			total += subTotal;
		}
		resultObject.setTotal(total);
		return resultObject;
	}

	private ResultObject computeDiggregationHasSchoolAllowance(ReportObject reportObject, Integer district) {
		ResultObject resultObject = new ResultObject();

		int total = 0;
		for (int i = 0; i < ENROLLMENT_TIMES.length; i++) {
			int subTotal = 0;
			for (int j = 0; j < AGE_BANDS.length - 1; j++) {
				List<Integer> hadSchoolAllowancw = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[HAD_SCHOLL_ALLOWANCE]);

				int count = hadSchoolAllowancw.size();
				subTotal += count;
				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j]).addAll(hadSchoolAllowancw);
				resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[j], count);
			}
			resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[4], subTotal);
			total += subTotal;
		}
		resultObject.setTotal(total);
		return resultObject;
	}

	private ResultObject computeDiggregationCompletedSocialEconomicAllowance(ReportObject reportObject,
			Integer district) {
		ResultObject resultObject = new ResultObject();

		int total = 0;
		for (int i = 0; i < ENROLLMENT_TIMES.length; i++) {
			int subTotal = 0;
			for (int j = 0; j < AGE_BANDS.length - 1; j++) {
				List<Integer> hadSocialEconomicApproaches = reportObject.getReportObject().get(district)
						.get(AGE_BANDS[j]).get(ENROLLMENT_TIMES[i])
						.get(DISAGGREGATIONS[HAD_SOCIAL_ECONOMIC_APPROACHES]);

				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(hadSocialEconomicApproaches);
				int count = hadSocialEconomicApproaches.size();
				subTotal += count;
				resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[j], count);
			}
			resultObject.getTotals().get(ENROLLMENT_TIMES[i]).put(AGE_BANDS[4], subTotal);
			total += subTotal;
		}
		resultObject.setTotal(total);
		return resultObject;
	}

	private Map<String, Long> processDistrictSummary(Integer district) {
		Map<String, Long> districtSummary = new HashMap<>();

		try {
			Long totalBeneficiaries = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByDistricts",
					Arrays.asList(district));
			Long maleBeneficiaries = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByDistrictAndGender",
					'1', district);
			Long femaleBeneficiaries = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByDistrictAndGender",
					'2', district);

			districtSummary.put("totalBeneficiaries", totalBeneficiaries);
			districtSummary.put("maleBeneficiaries", maleBeneficiaries);
			districtSummary.put("femaleBeneficiaries", femaleBeneficiaries);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return districtSummary;
	}

	private int getEnrollmentTimeIndex(int enrollmentTime) {
		if (enrollmentTime < 7)
			return 0;
		else if (enrollmentTime < 13)
			return 1;
		else if (enrollmentTime < 25)
			return 2;
		else
			return 3;
	}

	private int getAgeBandIndex(int ageBand) {
		if (ageBand < 4) {
			return ageBand - 1;
		} else {
			return 3;
		}
	}

	private ResultObject getTotalResultObject() {
		ResultObject ro = new ResultObject();
		ro.setBeneficiaries(null);
		ro.setTotals(null);

		return ro;
	}
}
