package dlt.dltbackendmaster.reports;

import static dlt.dltbackendmaster.reports.utils.ReportsConstants.AGE_BANDS;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.COMPLETED_PRIMARY_PACKAGE;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.COMPLETED_PRIMARY_SERVICE;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.COMPLETED_SECONDARY_SERVICE;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.COMPLETED_VIOLENCE_SERVICE;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.COMPLETION_STATUSES;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.DISAGGREGATIONS;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.ENROLLMENT_TIMES;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.HAD_SCHOLL_ALLOWANCE;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.HAD_SOCIAL_ECONOMIC_APPROACHES;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.SERVICE_PACKAGES;
import static dlt.dltbackendmaster.reports.utils.ReportsConstants.STARTED_SERVICE;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedAvanteEstudante;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedAvanteEstudanteHivPrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedAvanteEstudanteSocialAssets;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedAvanteEstudanteViolencePrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedAvanteRapariga;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedAvanteRaparigaHivPrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedAvanteRaparigaSocialAssets;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedAvanteRaparigaViolencePrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedCombinedSocioEconomicApproaches;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedCondomsPromotionOrProvision;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedContraceptionsPromotionOrProvision;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedDisagCombinedSocioEconomicApproaches;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedFinancialLiteracy;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedFinancialLiteracyAflateen;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedFinancialLiteracyAflatoun;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedGbvSessions;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedGuiaFacilitacao;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedGuiaFacilitacaoHivPrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedGuiaFacilitacaoViolencePrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedHIVTestingServices;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedHivSessions;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedOtherSAAJServices;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedPostViolenceCare_CM;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedPostViolenceCare_US;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedPrep;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSAAJEducationSessions;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedAvanteRapariga;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedAvanteRaparigaHivPrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedAvanteRaparigaSocialAssets;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedAvanteRaparigaViolencePrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedGuiaFacilitacao;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedGuiaFacilitacaoHivPrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedGuiaFacilitacaoViolencePrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedSAAJEducationSessions;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSimplifiedViolencePrevention15Plus;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSiyakhaComprehensive;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSiyakhaLight;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedSocialAssetsOldCurriculum;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.completedViolencePrevention15Plus;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.hadSchoolAllowance;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedAvanteEstudante;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedAvanteEstudanteViolencePrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedAvanteRapariga;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedAvanteRaparigaViolencePrevention;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedFinancialLiteracyAflateen;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedFinancialLiteracyAflatoun;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedGuiaFacilitacao;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedGuiaFacilitacaoSocialAssets15Plus;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedPostViolenceCare_CM;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedPostViolenceCare_US;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedSAAJEducationSessions;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedSimplifiedAvanteRapariga;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedSimplifiedGuiaFacilitacao;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedSocialAssetsOldCurriculum;
import static dlt.dltbackendmaster.util.ServiceCompletionRules.startedViolencePrevention15Plus;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import dlt.dltbackendmaster.domain.AgywPrev;
import dlt.dltbackendmaster.reports.domain.BeneficiaryVulnerability;
import dlt.dltbackendmaster.reports.domain.PrimaryPackageRO;
import dlt.dltbackendmaster.reports.domain.ReportObject;
import dlt.dltbackendmaster.reports.domain.ResultObject;
import dlt.dltbackendmaster.service.BeneficiariyService;
import dlt.dltbackendmaster.service.DAOService;
import dlt.dltbackendmaster.util.Utility;

/**
 * Classe responsável pela manipulação e retorno dos dados para compor
 * relatórios
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class AgywPrevReport {

	private static final List<Integer> PP_COMPLETION_STATUSES = Arrays.asList(2, 3);

	private final DAOService service;

	private BeneficiariyService beneficiariyService;

	private List<Integer> beneficiariesIds = new ArrayList<>();

	public AgywPrevReport(DAOService service) {
		this.service = service;
	}

	public AgywPrevReport(DAOService service, BeneficiariyService beneficiariyService) {
		this.service = service;
		this.beneficiariyService = beneficiariyService;
	}

	private List<AgywPrev> getData(Integer[] districts, String startDate, String endDate, int reportType) {

		if (reportType == 1) {
			return service.GetAllEntityByNamedNativeQuery("AgywPrev.findByDistricts", Arrays.asList(districts),
					startDate, endDate);
		} else {
			return service.GetAllEntityByNamedNativeQuery("AgywPrev.findSimplifiedByDistricts",
					Arrays.asList(districts), startDate, endDate);
		}
	}

	public Map<Integer, Map<String, ResultObject>> getAgywPrevResultObject(Integer[] districts, String startDate,
			String endDate, int reportType, boolean isFlagWriter) {
		Map<Integer, Map<String, ResultObject>> agywPrevResultObject = new HashMap<>();

		ReportObject reportObject = reportType == 1 ? process(districts, startDate, endDate)
				: processSimplified(districts, startDate, endDate);

		for (Integer district : districts) {
			Map<String, ResultObject> districtAgywPrevResultObject = new HashMap<>();
			Map<String, Long> districtSummary = processDistrictSummary(district);
			ResultObject completedOnlyPrimaryPackage = computeDiggregationCompletedOnlyPrimaryPackage(reportObject,
					district, isFlagWriter);
			ResultObject completedPrimaryPackageAndSecondaryService = computeDiggregationCompletedPrimaryPackageAndSecondaryService(
					reportObject, district, isFlagWriter);
			ResultObject completedOnlyServiceNotPrimaryPackage = computeDiggregationCompletedOnlyServiceNotPrimaryPackage(
					reportObject, district, isFlagWriter);
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

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<AgywPrev> data = getData(districts, startDate, endDate, 1);

		LocalDate eDate = LocalDate.parse(endDate, formatter);

		for (AgywPrev agywPrev : data) {

			LocalDate enrollmentDate = agywPrev.getEnrollment_date().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate birthDate = agywPrev.getDate_of_birth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			int ageOnEndDate = Math.abs(Period.between(birthDate, eDate).getYears());
			int enrollmentTime = (int) ChronoUnit.MONTHS.between(enrollmentDate, eDate);

			int completionStatus = agywPrev.getCompletion_status();

			if (agywPrev.getCurrent_age_band() == 1) { // 9-14
				if (agywPrev.getVblt_is_student() == 1) { // AVANTE ESTUDANTE
					if (PP_COMPLETION_STATUSES.contains(completionStatus) || (completedAvanteEstudante(agywPrev)
							|| ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev))
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
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_PRIMARY_PACKAGE, agywPrev.getBeneficiary_id());
					}
					if ((completedAvanteEstudante(agywPrev) || ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev))
							|| completedSAAJEducationSessions(agywPrev)
							|| (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
									|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev))
							|| (agywPrev.getVblt_sexually_active() != null && agywPrev.getVblt_sexually_active() == 1
									&& (completedHIVTestingServices(agywPrev)
											|| completedCondomsPromotionOrProvision(agywPrev)))) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_PRIMARY_SERVICE, agywPrev.getBeneficiary_id());
					}
					if (startedAvanteEstudante(agywPrev) || startedSAAJEducationSessions(agywPrev)
							|| startedAvanteEstudanteViolencePrevention(agywPrev)
							|| startedPostViolenceCare_US(agywPrev) || startedPostViolenceCare_CM(agywPrev)
							|| startedFinancialLiteracyAflatoun(agywPrev)
							|| ageOnEndDate == 14 && (startedFinancialLiteracyAflateen(agywPrev)
									|| startedGuiaFacilitacao(agywPrev) || agywPrev.getHiv_gbv_sessions_prep() > 0)) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								STARTED_SERVICE, agywPrev.getBeneficiary_id());
					}
					if (completedAvanteEstudanteViolencePrevention(agywPrev)) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_VIOLENCE_SERVICE, agywPrev.getBeneficiary_id());
					}
				} else { // AVANTE RAPARIGA
					if (PP_COMPLETION_STATUSES.contains(completionStatus) || (completedAvanteRapariga(agywPrev)
							|| ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev))
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
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_PRIMARY_PACKAGE, agywPrev.getBeneficiary_id());
					}
					if ((completedAvanteRapariga(agywPrev) || ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev))
							|| completedSAAJEducationSessions(agywPrev)
							|| (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
									|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev))
							|| (agywPrev.getVblt_sexually_active() != null && agywPrev.getVblt_sexually_active() == 1
									&& (completedHIVTestingServices(agywPrev)
											|| completedCondomsPromotionOrProvision(agywPrev)))) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_PRIMARY_SERVICE, agywPrev.getBeneficiary_id());
					}
					if (startedAvanteRapariga(agywPrev) || startedSAAJEducationSessions(agywPrev)
							|| startedAvanteRaparigaViolencePrevention(agywPrev) || startedPostViolenceCare_US(agywPrev)
							|| startedPostViolenceCare_CM(agywPrev) || startedFinancialLiteracyAflatoun(agywPrev)
							|| ageOnEndDate == 14 && (startedFinancialLiteracyAflateen(agywPrev)
									|| startedGuiaFacilitacao(agywPrev) || agywPrev.getHiv_gbv_sessions_prep() > 0)) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								STARTED_SERVICE, agywPrev.getBeneficiary_id());
					}
					if (completedAvanteRaparigaViolencePrevention(agywPrev)) {
						addBeneficiary(reportObject, agywPrev.getDistrict_id(),
								getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
								COMPLETED_VIOLENCE_SERVICE, agywPrev.getBeneficiary_id());
					}
				}
				if (hadSchoolAllowance(agywPrev) || completedContraceptionsPromotionOrProvision(agywPrev)
						|| (agywPrev.getVblt_sexually_active() == null || agywPrev.getVblt_sexually_active() != null
								&& agywPrev.getVblt_sexually_active() == 0)
								&& (completedHIVTestingServices(agywPrev)
										|| completedCondomsPromotionOrProvision(agywPrev))
						|| completedPostViolenceCare_US(agywPrev) || completedPostViolenceCare_CM(agywPrev)
						|| completedOtherSAAJServices(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_SECONDARY_SERVICE, agywPrev.getBeneficiary_id());
				}

				// Old Curriculum
				if (completedSocialAssetsOldCurriculum(agywPrev) && completedSAAJEducationSessions(agywPrev)
						&& (agywPrev.getVblt_sexually_active() == null
								|| agywPrev.getVblt_sexually_active() != null && agywPrev.getVblt_sexually_active() == 0
								|| agywPrev.getVblt_sexually_active() != null && agywPrev.getVblt_sexually_active() == 1
										&& completedHIVTestingServices(agywPrev)
										&& completedCondomsPromotionOrProvision(agywPrev))) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_PACKAGE, agywPrev.getBeneficiary_id());
				}
				if (completedSocialAssetsOldCurriculum(agywPrev) || completedSAAJEducationSessions(agywPrev)
						|| (agywPrev.getVblt_sexually_active() != null && agywPrev.getVblt_sexually_active() == 1
								&& completedHIVTestingServices(agywPrev)
								&& completedCondomsPromotionOrProvision(agywPrev))) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (hadSchoolAllowance(agywPrev) || completedPostViolenceCare_US(agywPrev)
						|| completedPostViolenceCare_CM(agywPrev) || completedPostViolenceCare_US(agywPrev)
						|| completedOtherSAAJServices(agywPrev)
						|| (agywPrev.getVblt_sexually_active() != null && agywPrev.getVblt_sexually_active() == 0
								&& (completedHIVTestingServices(agywPrev)
										|| completedCondomsPromotionOrProvision(agywPrev)
										|| completedContraceptionsPromotionOrProvision(agywPrev)))) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_SECONDARY_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (startedSocialAssetsOldCurriculum(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							STARTED_SERVICE, agywPrev.getBeneficiary_id());
				}
			} else { // 15-24
				if (PP_COMPLETION_STATUSES.contains(completionStatus) || completedCondomsPromotionOrProvision(agywPrev)
						&& completedGuiaFacilitacao(agywPrev) && completedHIVTestingServices(agywPrev)
						&& (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflateen(agywPrev))) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_PACKAGE, agywPrev.getBeneficiary_id());
				}
				if (completedCondomsPromotionOrProvision(agywPrev) || completedGuiaFacilitacao(agywPrev)
						|| completedHIVTestingServices(agywPrev) || completedFinancialLiteracy(agywPrev)
						|| completedFinancialLiteracyAflateen(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (agywPrev.getCurrent_age_band() == 2
						&& (hadSchoolAllowance(agywPrev) || completedSocialAssetsOldCurriculum(agywPrev))
						|| completedPostViolenceCare_US(agywPrev) || completedPostViolenceCare_CM(agywPrev)
						|| completedCombinedSocioEconomicApproaches(agywPrev) || completedOtherSAAJServices(agywPrev)
						|| completedPrep(agywPrev) || completedContraceptionsPromotionOrProvision(agywPrev)
						|| completedSiyakhaLight(agywPrev) || completedSiyakhaComprehensive(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_SECONDARY_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (startedGuiaFacilitacaoSocialAssets15Plus(agywPrev) || startedGuiaFacilitacao(agywPrev)
						|| startedViolencePrevention15Plus(agywPrev)
						|| (agywPrev.getCurrent_age_band() == 2 && startedSocialAssetsOldCurriculum(agywPrev)
								|| startedPostViolenceCare_US(agywPrev) || startedPostViolenceCare_CM(agywPrev)
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

				// Old Curriculum
				if (completedCondomsPromotionOrProvision(agywPrev) && completedHIVTestingServices(agywPrev)
						&& completedHivSessions(agywPrev) && completedGbvSessions(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_PACKAGE, agywPrev.getBeneficiary_id());
				}
				if (completedHivSessions(agywPrev) || completedGbvSessions(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_SERVICE, agywPrev.getBeneficiary_id());
				}

				if (completedDisagCombinedSocioEconomicApproaches(agywPrev) && ageOnEndDate < 26) {
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
			if (hadSchoolAllowance(agywPrev)) {
				addBeneficiary(reportObject, agywPrev.getDistrict_id(), getAgeBandIndex(agywPrev.getCurrent_age_band()),
						getEnrollmentTimeIndex(enrollmentTime), HAD_SCHOLL_ALLOWANCE, agywPrev.getBeneficiary_id());
			}
		}

		return reportObject;
	}

	public ReportObject processSimplified(Integer[] districts, String startDate, String endDate) {

		ReportObject reportObject = new ReportObject(districts);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		List<AgywPrev> data = getData(districts, startDate, endDate, 2);

		LocalDate eDate = LocalDate.parse(endDate, formatter);

		for (AgywPrev agywPrev : data) {

			LocalDate enrollmentDate = agywPrev.getEnrollment_date().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate birthDate = agywPrev.getDate_of_birth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			int ageOnEndDate = Math.abs(Period.between(birthDate, eDate).getYears());
			int enrollmentTime = (int) ChronoUnit.MONTHS.between(enrollmentDate, eDate);

			int completionStatus = agywPrev.getCompletion_status();

			if (agywPrev.getCurrent_age_band() == 1) { // 9-14
				// AVANTE RAPARIGA
				if (PP_COMPLETION_STATUSES.contains(completionStatus) || completedSimplifiedAvanteRapariga(agywPrev)
						&& completedSimplifiedSAAJEducationSessions(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_PACKAGE, agywPrev.getBeneficiary_id());
				}
				if (completedSimplifiedAvanteRapariga(agywPrev) || completedSimplifiedSAAJEducationSessions(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (startedSimplifiedAvanteRapariga(agywPrev) || startedSAAJEducationSessions(agywPrev)
						|| startedAvanteRaparigaViolencePrevention(agywPrev) || startedPostViolenceCare_US(agywPrev)
						|| startedPostViolenceCare_CM(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							STARTED_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (completedSimplifiedAvanteRaparigaViolencePrevention(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_VIOLENCE_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (hadSchoolAllowance(agywPrev) || (agywPrev.getVblt_sexually_active() == null
						|| agywPrev.getVblt_sexually_active() != null && agywPrev.getVblt_sexually_active() == 0)
						&& (completedHIVTestingServices(agywPrev) || completedCondomsPromotionOrProvision(agywPrev))
						|| completedPostViolenceCare_CM(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_SECONDARY_SERVICE, agywPrev.getBeneficiary_id());
				}
			} else { // 15-24
				if (PP_COMPLETION_STATUSES.contains(completionStatus) || completedCondomsPromotionOrProvision(agywPrev)
						&& completedSimplifiedGuiaFacilitacao(agywPrev) && completedHIVTestingServices(agywPrev)
						&& completedFinancialLiteracyAflateen(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_PACKAGE, agywPrev.getBeneficiary_id());
				}
				if (completedCondomsPromotionOrProvision(agywPrev) || completedSimplifiedGuiaFacilitacao(agywPrev)
						|| completedHIVTestingServices(agywPrev) || completedFinancialLiteracyAflateen(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_PRIMARY_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (hadSchoolAllowance(agywPrev) || completedCombinedSocioEconomicApproaches(agywPrev)
						|| completedContraceptionsPromotionOrProvision(agywPrev) || completedSiyakhaLight(agywPrev)
						|| completedSiyakhaComprehensive(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_SECONDARY_SERVICE, agywPrev.getBeneficiary_id());
				}
				if (startedSimplifiedGuiaFacilitacao(agywPrev) || startedFinancialLiteracyAflateen(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							STARTED_SERVICE, agywPrev.getBeneficiary_id());

				}
				if (completedSimplifiedViolencePrevention15Plus(agywPrev)) {
					addBeneficiary(reportObject, agywPrev.getDistrict_id(),
							getAgeBandIndex(agywPrev.getCurrent_age_band()), getEnrollmentTimeIndex(enrollmentTime),
							COMPLETED_VIOLENCE_SERVICE, agywPrev.getBeneficiary_id());
				}

				if (completedDisagCombinedSocioEconomicApproaches(agywPrev) && ageOnEndDate < 26) {
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
			if (hadSchoolAllowance(agywPrev)) {
				addBeneficiary(reportObject, agywPrev.getDistrict_id(), getAgeBandIndex(agywPrev.getCurrent_age_band()),
						getEnrollmentTimeIndex(enrollmentTime), HAD_SCHOLL_ALLOWANCE, agywPrev.getBeneficiary_id());
			}
		}

		return reportObject;
	}

	public Map<Integer, PrimaryPackageRO> processPPCompletion(Integer[] districts, String startDate, String endDate) {

		Map<Integer, PrimaryPackageRO> reportObjects = new HashMap<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		List<AgywPrev> data = service.GetAllEntityByNamedNativeQuery("AgywPrev.findByDistricts",
				Arrays.asList(districts), startDate, endDate);

		LocalDate eDate = LocalDate.parse(endDate, formatter);

		for (AgywPrev agywPrev : data) {

			LocalDate birthDate = agywPrev.getDate_of_birth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			int ageOnEndDate = Math.abs(Period.between(birthDate, eDate).getYears());

			PrimaryPackageRO primaryPackageRO = null;

			Integer currentAgeBand = agywPrev.getCurrent_age_band();
			int ageBandIndex = getAgeBandIndex(currentAgeBand);
			if (currentAgeBand == 1) { // 9-14
				if (agywPrev.getVblt_is_student() == 1) { // AVANTE ESTUDANTE
					if (!((completedAvanteEstudante(agywPrev)
							|| ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev))
							&& completedSAAJEducationSessions(agywPrev)
							&& (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
									|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev))
							&& (agywPrev.getVblt_sexually_active() == null
									|| agywPrev.getVblt_sexually_active() != null
											&& agywPrev.getVblt_sexually_active() == 0
									|| agywPrev.getVblt_sexually_active() != null
											&& agywPrev.getVblt_sexually_active() == 1
											&& completedHIVTestingServices(agywPrev)
											&& completedCondomsPromotionOrProvision(agywPrev))) // Não completou o pacote primário
							&& (startedAvanteEstudante(agywPrev) || startedSAAJEducationSessions(agywPrev)
									|| startedAvanteEstudanteViolencePrevention(agywPrev)
									|| startedPostViolenceCare_US(agywPrev) || startedPostViolenceCare_CM(agywPrev)
									|| startedFinancialLiteracyAflatoun(agywPrev)
									|| ageOnEndDate == 14 && (startedFinancialLiteracyAflateen(agywPrev)
											|| startedGuiaFacilitacao(agywPrev)
											|| agywPrev.getHiv_gbv_sessions_prep() > 0))) { // Iniciou serviço
						primaryPackageRO = new PrimaryPackageRO(agywPrev.getBeneficiary_id(), ageOnEndDate,
								AGE_BANDS[ageBandIndex], agywPrev.getVulnerabilities(), SERVICE_PACKAGES[0]);
						if (!startedAvanteEstudante(agywPrev) && startedGuiaFacilitacao(agywPrev)) {
							primaryPackageRO.setServicePackage(SERVICE_PACKAGES[2]);
						}
						if (completedAvanteEstudanteSocialAssets(agywPrev)) {
							primaryPackageRO.setCompletedSocialAsset(COMPLETION_STATUSES[1]);
						}
						if (completedAvanteEstudanteHivPrevention(agywPrev)) {
							primaryPackageRO.setCompletedHivPrevention(COMPLETION_STATUSES[1]);
						}
						if (completedAvanteEstudanteViolencePrevention(agywPrev)) {
							primaryPackageRO.setCompletedViolencePrevention(COMPLETION_STATUSES[1]);
						}
						if (ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev)) {
							primaryPackageRO.setCompletedSocialAsset(COMPLETION_STATUSES[1]);
						}
						if (completedSAAJEducationSessions(agywPrev)) {
							primaryPackageRO.setCompletedSAAJ(COMPLETION_STATUSES[1]);
						}
						if (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
								|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev)) {
							primaryPackageRO.setCompletedFinancialLiteracy(COMPLETION_STATUSES[1]);
						}
						if (agywPrev.getVblt_sexually_active() == null || agywPrev.getVblt_sexually_active() != null
								&& agywPrev.getVblt_sexually_active() == 0) {
							primaryPackageRO.setCompletedHivTesting(COMPLETION_STATUSES[2]);
							primaryPackageRO.setCompletedCondoms(COMPLETION_STATUSES[2]);
						} else if (agywPrev.getVblt_sexually_active() != null
								&& agywPrev.getVblt_sexually_active() == 1) {
							if (completedHIVTestingServices(agywPrev)) {
								primaryPackageRO.setCompletedHivTesting(COMPLETION_STATUSES[1]);
							}
							if (completedCondomsPromotionOrProvision(agywPrev)) {
								primaryPackageRO.setCompletedCondoms(COMPLETION_STATUSES[1]);
							}
						}
					}
				} else { // AVANTE RAPARIGA
					if (!((completedAvanteRapariga(agywPrev)
							|| ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev))
							&& completedSAAJEducationSessions(agywPrev)
							&& (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
									|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev))
							&& (agywPrev.getVblt_sexually_active() == null
									|| agywPrev.getVblt_sexually_active() != null
											&& agywPrev.getVblt_sexually_active() == 0
									|| agywPrev.getVblt_sexually_active() != null
											&& agywPrev.getVblt_sexually_active() == 1
											&& completedHIVTestingServices(agywPrev)
											&& completedCondomsPromotionOrProvision(agywPrev))) // Não completou o pacote primário
							&& (startedAvanteRapariga(agywPrev) || startedSAAJEducationSessions(agywPrev)
									|| startedAvanteRaparigaViolencePrevention(agywPrev)
									|| startedPostViolenceCare_US(agywPrev) || startedPostViolenceCare_CM(agywPrev)
									|| startedFinancialLiteracyAflatoun(agywPrev)
									|| ageOnEndDate == 14 && (startedFinancialLiteracyAflateen(agywPrev)
											|| startedGuiaFacilitacao(agywPrev)
											|| agywPrev.getHiv_gbv_sessions_prep() > 0))) { // Iniciou serviço
						primaryPackageRO = new PrimaryPackageRO(agywPrev.getBeneficiary_id(), ageOnEndDate,
								AGE_BANDS[ageBandIndex], agywPrev.getVulnerabilities(), SERVICE_PACKAGES[1]);
						if (!startedAvanteRapariga(agywPrev) && startedGuiaFacilitacao(agywPrev)) {
							primaryPackageRO.setServicePackage(SERVICE_PACKAGES[2]);
						}
						if (completedAvanteRaparigaSocialAssets(agywPrev)) {
							primaryPackageRO.setCompletedSocialAsset(COMPLETION_STATUSES[1]);
						}
						if (completedAvanteRaparigaHivPrevention(agywPrev)) {
							primaryPackageRO.setCompletedHivPrevention(COMPLETION_STATUSES[1]);
						}
						if (completedAvanteRaparigaViolencePrevention(agywPrev)) {
							primaryPackageRO.setCompletedViolencePrevention(COMPLETION_STATUSES[1]);
						}
						if (ageOnEndDate == 14 && completedGuiaFacilitacao(agywPrev)) {
							primaryPackageRO.setCompletedSocialAsset(COMPLETION_STATUSES[1]);
						}
						if (completedSAAJEducationSessions(agywPrev)) {
							primaryPackageRO.setCompletedSAAJ(COMPLETION_STATUSES[1]);
						}
						if (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflatoun(agywPrev)
								|| ageOnEndDate == 14 && completedFinancialLiteracyAflateen(agywPrev)) {
							primaryPackageRO.setCompletedFinancialLiteracy(COMPLETION_STATUSES[1]);
						}
						if (agywPrev.getVblt_sexually_active() == null || agywPrev.getVblt_sexually_active() != null
								&& agywPrev.getVblt_sexually_active() == 0) {
							primaryPackageRO.setCompletedHivTesting(COMPLETION_STATUSES[2]);
							primaryPackageRO.setCompletedCondoms(COMPLETION_STATUSES[2]);
						} else if (agywPrev.getVblt_sexually_active() != null
								&& agywPrev.getVblt_sexually_active() == 1) {
							if (completedHIVTestingServices(agywPrev)) {
								primaryPackageRO.setCompletedHivTesting(COMPLETION_STATUSES[1]);
							}
							if (completedCondomsPromotionOrProvision(agywPrev)) {
								primaryPackageRO.setCompletedCondoms(COMPLETION_STATUSES[1]);
							}
						}
					}
				}
			} else { // 15-24
				if (!(completedCondomsPromotionOrProvision(agywPrev) && completedGuiaFacilitacao(agywPrev)
						&& completedHIVTestingServices(agywPrev)
						&& (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflateen(agywPrev))) // Não completou opacoteprimário
						&& (startedGuiaFacilitacaoSocialAssets15Plus(agywPrev) || startedGuiaFacilitacao(agywPrev)
								|| startedViolencePrevention15Plus(agywPrev)
								|| (currentAgeBand == 2 && startedSocialAssetsOldCurriculum(agywPrev)
										|| startedPostViolenceCare_US(agywPrev) || startedPostViolenceCare_CM(agywPrev)
										|| startedFinancialLiteracyAflateen(agywPrev)))) { // Iniciou serviço
					primaryPackageRO = new PrimaryPackageRO(agywPrev.getBeneficiary_id(), ageOnEndDate,
							AGE_BANDS[ageBandIndex], agywPrev.getVulnerabilities(), SERVICE_PACKAGES[2]);
					primaryPackageRO.setCompletedSAAJ(COMPLETION_STATUSES[2]);
					if (completedCondomsPromotionOrProvision(agywPrev)) {
						primaryPackageRO.setCompletedCondoms(COMPLETION_STATUSES[1]);
					}
					primaryPackageRO.setCompletedSocialAsset(COMPLETION_STATUSES[2]);
					if (completedGuiaFacilitacaoHivPrevention(agywPrev)) {
						primaryPackageRO.setCompletedHivPrevention(COMPLETION_STATUSES[1]);
					}
					if (completedGuiaFacilitacaoViolencePrevention(agywPrev)) {
						primaryPackageRO.setCompletedViolencePrevention(COMPLETION_STATUSES[1]);
					}
					if (completedHIVTestingServices(agywPrev)) {
						primaryPackageRO.setCompletedHivTesting(COMPLETION_STATUSES[1]);
					}
					if (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflateen(agywPrev)) {
						primaryPackageRO.setCompletedFinancialLiteracy(COMPLETION_STATUSES[1]);
					}
				}
			}
			if (primaryPackageRO != null) {
				reportObjects.put(primaryPackageRO.getBeneficiaryId(), primaryPackageRO);
			}
		}

		return reportObjects;
	}

	public Map<Integer, PrimaryPackageRO> processSimplifiedPPCompletion(Integer[] districts, String startDate,
			String endDate) {

		Map<Integer, PrimaryPackageRO> reportObjects = new HashMap<>();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		List<AgywPrev> data = service.GetAllEntityByNamedNativeQuery("AgywPrev.findSimplifiedByDistricts",
				Arrays.asList(districts), startDate, endDate);

		LocalDate eDate = LocalDate.parse(endDate, formatter);

		for (AgywPrev agywPrev : data) {

			LocalDate birthDate = agywPrev.getDate_of_birth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

			int ageOnEndDate = Math.abs(Period.between(birthDate, eDate).getYears());

			PrimaryPackageRO primaryPackageRO = null;

			Integer currentAgeBand = agywPrev.getCurrent_age_band();
			int ageBandIndex = getAgeBandIndex(currentAgeBand);
			if (currentAgeBand == 1) { // 9-14
				// AVANTE RAPARIGA
				if (!(completedSimplifiedAvanteRapariga(agywPrev) && completedSimplifiedSAAJEducationSessions(agywPrev)) // Não completou o pacote primário
						&& (startedSimplifiedAvanteRapariga(agywPrev) || startedSAAJEducationSessions(agywPrev)
								|| startedAvanteRaparigaViolencePrevention(agywPrev)
								|| startedPostViolenceCare_US(agywPrev) || startedPostViolenceCare_CM(agywPrev))) { // Iniciou serviço
					primaryPackageRO = new PrimaryPackageRO(agywPrev.getBeneficiary_id(), ageOnEndDate,
							AGE_BANDS[ageBandIndex], agywPrev.getVulnerabilities(), SERVICE_PACKAGES[1]);
					if (completedSimplifiedAvanteRaparigaSocialAssets(agywPrev)) {
						primaryPackageRO.setCompletedSocialAsset(COMPLETION_STATUSES[1]);
					}
					if (completedSimplifiedAvanteRaparigaHivPrevention(agywPrev)) {
						primaryPackageRO.setCompletedHivPrevention(COMPLETION_STATUSES[1]);
					}
					if (completedSimplifiedAvanteRaparigaViolencePrevention(agywPrev)) {
						primaryPackageRO.setCompletedViolencePrevention(COMPLETION_STATUSES[1]);
					}
					if (completedSimplifiedSAAJEducationSessions(agywPrev)) {
						primaryPackageRO.setCompletedSAAJ(COMPLETION_STATUSES[1]);
					}
					primaryPackageRO.setCompletedFinancialLiteracy(COMPLETION_STATUSES[2]);
					primaryPackageRO.setCompletedHivTesting(COMPLETION_STATUSES[2]);
					primaryPackageRO.setCompletedCondoms(COMPLETION_STATUSES[2]);
				}

			} else { // 15-24
				if (!(completedCondomsPromotionOrProvision(agywPrev) && completedSimplifiedGuiaFacilitacao(agywPrev)
						&& completedHIVTestingServices(agywPrev)) // Não completou o acote primário
						&& (startedGuiaFacilitacaoSocialAssets15Plus(agywPrev)
								|| startedSimplifiedGuiaFacilitacao(agywPrev)
								|| startedViolencePrevention15Plus(agywPrev)
								|| (currentAgeBand == 2 && startedSocialAssetsOldCurriculum(agywPrev)
										|| startedPostViolenceCare_US(agywPrev)
										|| startedPostViolenceCare_CM(agywPrev)))) { // Iniciou serviço
					primaryPackageRO = new PrimaryPackageRO(agywPrev.getBeneficiary_id(), ageOnEndDate,
							AGE_BANDS[ageBandIndex], agywPrev.getVulnerabilities(), SERVICE_PACKAGES[2]);
					primaryPackageRO.setCompletedSAAJ(COMPLETION_STATUSES[2]);
					if (completedCondomsPromotionOrProvision(agywPrev)) {
						primaryPackageRO.setCompletedCondoms(COMPLETION_STATUSES[1]);
					}
					primaryPackageRO.setCompletedSocialAsset(COMPLETION_STATUSES[2]);
					if (completedSimplifiedGuiaFacilitacaoHivPrevention(agywPrev)) {
						primaryPackageRO.setCompletedHivPrevention(COMPLETION_STATUSES[1]);
					}
					if (completedSimplifiedGuiaFacilitacaoViolencePrevention(agywPrev)) {
						primaryPackageRO.setCompletedViolencePrevention(COMPLETION_STATUSES[1]);
					}
					if (completedHIVTestingServices(agywPrev)) {
						primaryPackageRO.setCompletedHivTesting(COMPLETION_STATUSES[1]);
					}
					if (completedFinancialLiteracy(agywPrev) || completedFinancialLiteracyAflateen(agywPrev)) {
						primaryPackageRO.setCompletedFinancialLiteracy(COMPLETION_STATUSES[1]);
					}
				}
			}
			if (primaryPackageRO != null) {
				reportObjects.put(primaryPackageRO.getBeneficiaryId(), primaryPackageRO);
			}
		}

		return reportObjects;
	}

	private void addBeneficiary(ReportObject reportObject, Integer district, Integer ageBand, Integer enrollmentTime,
			Integer layering, Integer beneficiary) {
		if (!beneficiariesIds.contains(beneficiary)) {
			beneficiariesIds.add(beneficiary);
		}
		reportObject.getReportObject().get(district).get(AGE_BANDS[ageBand]).get(ENROLLMENT_TIMES[enrollmentTime])
				.get(DISAGGREGATIONS[layering]).add(beneficiary);
	}

	public ResultObject computeDiggregationCompletedOnlyPrimaryPackage(ReportObject reportObject, Integer district,
			boolean isFlagWriter) {
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
					if (!(completedSecondaryService.contains(beneficiaryId)
							|| completedOnlyPrimaryPackage.contains(beneficiaryId))) {
						completedOnlyPrimaryPackage.add(beneficiaryId);
						if (isFlagWriter)
							beneficiariyService.saveCompletionStatus(beneficiaryId, 2);
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
			Integer district, boolean isFlagWriter) {
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
					if (completedSecondaryService.contains(beneficiaryId)
							&& !completedPrimaryPackageAndSecondaryService.contains(beneficiaryId)) {
						completedPrimaryPackageAndSecondaryService.add(beneficiaryId);
						if (isFlagWriter)
							beneficiariyService.saveCompletionStatus(beneficiaryId, 3);
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
			Integer district, boolean isFlagWriter) {
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

				for (Integer beneficiaryId : completedService) {
					if (!(completedPrimaryPackage.contains(beneficiaryId)
							|| completedOnlyServiceNotPrimaryPackage.contains(beneficiaryId))) {
						completedOnlyServiceNotPrimaryPackage.add(beneficiaryId);
						if (isFlagWriter)
							beneficiariyService.saveCompletionStatus(beneficiaryId, 1);
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
							|| completedSecondaryService.contains(beneficiaryId)
							|| startedServiceDidNotComplete.contains(beneficiaryId))) {
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
				List<Integer> beneficiariesCompletedViolenceService = new ArrayList<>();
				List<Integer> completedViolenceService = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[COMPLETED_VIOLENCE_SERVICE]);

				for (Integer beneficiaryId : completedViolenceService) {
					if (!beneficiariesCompletedViolenceService.contains(beneficiaryId)) {
						beneficiariesCompletedViolenceService.add(beneficiaryId);
					}
				}

				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(beneficiariesCompletedViolenceService);
				int count = beneficiariesCompletedViolenceService.size();
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
				List<Integer> beneficiariesHadSchoolAllowance = new ArrayList<>();
				List<Integer> hadSchoolAllowancw = reportObject.getReportObject().get(district).get(AGE_BANDS[j])
						.get(ENROLLMENT_TIMES[i]).get(DISAGGREGATIONS[HAD_SCHOLL_ALLOWANCE]);

				for (Integer beneficiaryId : hadSchoolAllowancw) {
					if (!beneficiariesHadSchoolAllowance.contains(beneficiaryId)) {
						beneficiariesHadSchoolAllowance.add(beneficiaryId);
					}
				}

				int count = beneficiariesHadSchoolAllowance.size();
				subTotal += count;
				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(beneficiariesHadSchoolAllowance);
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
				List<Integer> beneficiariesHadSocialEconomicApproaches = new ArrayList<>();
				List<Integer> hadSocialEconomicApproaches = reportObject.getReportObject().get(district)
						.get(AGE_BANDS[j]).get(ENROLLMENT_TIMES[i])
						.get(DISAGGREGATIONS[HAD_SOCIAL_ECONOMIC_APPROACHES]);

				for (Integer beneficiaryId : hadSocialEconomicApproaches) {
					if (!beneficiariesHadSocialEconomicApproaches.contains(beneficiaryId)) {
						beneficiariesHadSocialEconomicApproaches.add(beneficiaryId);
					}
				}

				resultObject.getBeneficiaries().get(ENROLLMENT_TIMES[i]).get(AGE_BANDS[j])
						.addAll(beneficiariesHadSocialEconomicApproaches);
				int count = beneficiariesHadSocialEconomicApproaches.size();
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
			String searchNui = StringUtils.EMPTY;
			String searchName = StringUtils.EMPTY;
			Integer searchUserCreator = null;
			Integer searchDistrict = null;
			Long totalBeneficiaries = service.GetUniqueEntityByNamedQuery("Beneficiary.findCountByDistricts", searchNui,
					searchName, searchUserCreator, searchDistrict, Arrays.asList(district));
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
		ro.setBeneficiariesIds(beneficiariesIds);

		return ro;
	}

	public List<Object> getNewlyEnrolledAgywAndServices(Integer[] districts, Date startDate, Date endDate,
			int pageIndex, int pageSize) {
		List<Object> dataObjs = service.GetAllPagedEntityByNamedNativeQuery(
				"AgywPrev.findByNewlyEnrolledAgywAndServices", pageIndex, pageSize, Utility.atStartOfDay(startDate),
				Utility.atEndOfDay(endDate), Arrays.asList(districts));

		return dataObjs;
	}

	public List<Object> getNewlyEnrolledAgywAndServicesSummary(Integer[] districts, Date startDate, Date endDate,
			int pageIndex, int pageSize) {
		List<Object> dataObjs = service.GetAllPagedEntityByNamedNativeQuery(
				"AgywPrev.findByNewlyEnrolledAgywAndServicesSummary", pageIndex, pageSize,
				Utility.atStartOfDay(startDate), Utility.atEndOfDay(endDate), Arrays.asList(districts));

		return dataObjs;
	}

	public List<Object> getBeneficiariesVulnerabilitiesAndServices(Integer[] districts, String startDate,
			String endDate, int pageIndex, int pageSize) {
		List<Object> dataObjs = service.GetAllPagedEntityByNamedNativeQuery(
				"AgywPrev.findByBeneficiariesVulnerabilitiesAndServices", pageIndex, pageSize, startDate, endDate,
				Arrays.asList(districts));

		return dataObjs;
	}

	public List<Object> getBeneficiariesVulnerabilitiesAndServicesSummary(Integer district, String startDate,
			String endDate) {
		List<Object> dataObjs = service.GetByNamedNativeQuery(
				"AgywPrev.findByBeneficiariesVulnerabilitiesAndServicesSummary", district, startDate, endDate);
		return dataObjs;
	}

	public List<Object> countNewlyEnrolledAgywAndServices(Integer[] districts, Date date, Date date2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'countNewlyEnrolledAgywAndServices'");
	}

	public List<Object> getBeneficiariesNoVulnerabilities(Integer[] districts, Date startDate, Date endDate,
			int pageIndex, int pageSize) {
		List<Object> dataObjs = service.GetAllPagedEntityByNamedNativeQuery(
				"AgywPrev.findByBeneficiariesNoVulnerabilities", pageIndex, pageSize, Utility.atStartOfDay(startDate),
				Utility.atEndOfDay(endDate), Arrays.asList(districts));

		return dataObjs;
	}

	public List<Object> getAgywPrevBeneficiaries(Integer[] beneficiariesIds) {
		List<Object> dataObjs = service.GetAllEntityByNamedNativeQuery("AgywPrev.findAgywPrevBeneficiariesByIds",
				Arrays.asList(beneficiariesIds));

		return dataObjs;
	}

	public Map<Integer, Map<String, ResultObject>> getBeneficiariesNotCompletedPP(Integer[] districts, String startDate,
			String endDate, int reportType) {
		Map<Integer, Map<String, ResultObject>> agywPrevResultObject = new HashMap<>();

		ReportObject reportObject = reportType == 1 ? process(districts, startDate, endDate)
				: processSimplified(districts, startDate, endDate);

		for (Integer district : districts) {
			Map<String, ResultObject> districtAgywPrevResultObject = new HashMap<>();
			ResultObject completedOnlyServiceNotPrimaryPackage = computeDiggregationCompletedOnlyServiceNotPrimaryPackage(
					reportObject, district, false);
			ResultObject startedServiceDidNotComplete = computeDiggregationStartedServiceDidNotComplete(reportObject,
					district);
			districtAgywPrevResultObject.put("completed-service-not-primary-package",
					completedOnlyServiceNotPrimaryPackage);
			districtAgywPrevResultObject.put("started-service-did-not-complete", startedServiceDidNotComplete);

			agywPrevResultObject.put(district, districtAgywPrevResultObject);
		}

		return agywPrevResultObject;
	}

	public List<PrimaryPackageRO> getBeneficiariesWithoutPrimaryPackageCompleted(Integer[] districts, String startDate,
			String endDate, int reportType) {

		Map<Integer, PrimaryPackageRO> ppCompletion = reportType == 1
				? processPPCompletion(districts, startDate, endDate)
				: processSimplifiedPPCompletion(districts, startDate, endDate);
		List<PrimaryPackageRO> reportObjects = new ArrayList<>();

		List<Object> dataObjs = service.GetAllEntityByNamedNativeQuery("AgywPrev.findBeneficiariesByIds",
				ppCompletion.keySet());

		for (Object object : dataObjs) {
			Integer beneficiaryId = (Integer) getValueAtIndex(object, 0);
			PrimaryPackageRO primaryPackageRO = ppCompletion.get(beneficiaryId);
			primaryPackageRO.setNui((String) getValueAtIndex(object, 1));
			primaryPackageRO.setProvince((String) getValueAtIndex(object, 2));
			primaryPackageRO.setDistrict((String) getValueAtIndex(object, 3));
			primaryPackageRO.setNui((String) getValueAtIndex(object, 1));

			reportObjects.add(primaryPackageRO);
		}

		Comparator<PrimaryPackageRO> comparator = Comparator.comparing(PrimaryPackageRO::getDistrict)
				.thenComparing(PrimaryPackageRO::getNui);

		List<PrimaryPackageRO> sorterReportObjectList = reportObjects.stream().sorted(comparator)
				.collect(Collectors.toList());

		return sorterReportObjectList;
	}

	public List<Object> getBeneficiariesWithoutCommunityIntervention(Integer districts[], String startDate,
			String endDate) {
		// FIXME: Check why it is considering end date as first parameter... in shoulb
		// be disctricts
		List<Object> dataObjs = service.GetAllEntityByNamedNativeQuery(
				"AgywPrev.findBeneficiariesWithoutCommunityInterventions", endDate, Arrays.asList(districts),
				startDate);
		return dataObjs;
	}

	public List<BeneficiaryVulnerability> getBeneficiariesVulnerabilities(String startDate, String endDate) {
		List<BeneficiaryVulnerability> beneficiariesVulnerabilities = new ArrayList<>();
		List<Object> dataObjs = service.GetAllEntityByNamedNativeQuery("AgywPrev.findBeneficiariesVulnerabilities",
				endDate, startDate);

		for (Object object : dataObjs) {
			BeneficiaryVulnerability beneficiaryVulnerability = new BeneficiaryVulnerability();
			beneficiaryVulnerability.setProvincia((String) getValueAtIndex(object, 0));
			beneficiaryVulnerability.setDistrito((String) getValueAtIndex(object, 1));
			beneficiaryVulnerability.setOnde_mora((String) getValueAtIndex(object, 2));
			beneficiaryVulnerability.setPonto_entrada((String) getValueAtIndex(object, 3));
			beneficiaryVulnerability.setNome_ponto_entrada((String) getValueAtIndex(object, 4));
			beneficiaryVulnerability.setOrganizacao((String) getValueAtIndex(object, 5));
			beneficiaryVulnerability.setData_inscricao((String) getValueAtIndex(object, 6));
			beneficiaryVulnerability.setData_registo((String) getValueAtIndex(object, 7));
			beneficiaryVulnerability.setRegistado_por((String) getValueAtIndex(object, 8));
			beneficiaryVulnerability.setData_actualizacao((String) getValueAtIndex(object, 9));
			beneficiaryVulnerability.setActualizado_por((String) getValueAtIndex(object, 10));
			beneficiaryVulnerability.setNui((String) getValueAtIndex(object, 11));
			beneficiaryVulnerability.setSexo((String) getValueAtIndex(object, 12));
			beneficiaryVulnerability.setIdade_registo((Integer) getValueAtIndex(object, 13));
			beneficiaryVulnerability.setIdade_actual((Integer) getValueAtIndex(object, 14));
			beneficiaryVulnerability.setFaixa_registo((String) getValueAtIndex(object, 15));
			beneficiaryVulnerability.setFaixa_actual((String) getValueAtIndex(object, 16));
			beneficiaryVulnerability.setData_nascimento((String) getValueAtIndex(object, 17));
			beneficiaryVulnerability.setVulneravel((String) getValueAtIndex(object, 18));
			beneficiaryVulnerability.setCom_quem_mora((String) getValueAtIndex(object, 19));
			beneficiaryVulnerability.setSustenta_casa((String) getValueAtIndex(object, 20));
			beneficiaryVulnerability.setVai_escola((String) getValueAtIndex(object, 21));
			beneficiaryVulnerability.setTem_deficiencia((String) getValueAtIndex(object, 22));
			beneficiaryVulnerability.setTipo_deficiencia((String) getValueAtIndex(object, 23));
			beneficiaryVulnerability.setFoi_casada((String) getValueAtIndex(object, 24));
			beneficiaryVulnerability.setIdp((String) getValueAtIndex(object, 25));
			beneficiaryVulnerability.setTeste_hiv((String) getValueAtIndex(object, 26));
			beneficiaryVulnerability.setSexualmente_activa((String) getValueAtIndex(object, 27));
			beneficiaryVulnerability.setGravida_ou_tem_filhos((String) getValueAtIndex(object, 28));
			beneficiaryVulnerability.setVitima_exploracao_sexual_trafico((String) getValueAtIndex(object, 29));
			beneficiaryVulnerability.setTrabalhadora_sexo((String) getValueAtIndex(object, 30));
			beneficiaryVulnerability.setRelacoes_multiplas_concorrentes((String) getValueAtIndex(object, 31));
			beneficiaryVulnerability.setVitima_vbg((String) getValueAtIndex(object, 32));
			beneficiaryVulnerability.setTipo_vbg((String) getValueAtIndex(object, 33));
			beneficiaryVulnerability.setAbuso_alcool_drogas((String) getValueAtIndex(object, 34));
			beneficiaryVulnerability.setHistorico_its((String) getValueAtIndex(object, 35));

			beneficiariesVulnerabilities.add(beneficiaryVulnerability);
		}

		return beneficiariesVulnerabilities;
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
