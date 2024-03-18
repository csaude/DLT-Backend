package dlt.dltbackendmaster.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dlt.dltbackendmaster.domain.AgywPrev;
import dlt.dltbackendmaster.domain.BeneficiariesInterventions;
import dlt.dltbackendmaster.domain.References;
import dlt.dltbackendmaster.domain.ReferencesServices;
import dlt.dltbackendmaster.domain.SubServices;

/**
 * Rules for Services Completion
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class ServiceCompletionRules {

	private static final String COP_22_START_DATE = "20220921";

	public static boolean completedAvanteEstudanteSocialAssets(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.ESTUDANTES_RECURSOS_SOCIAIS_MANDATORY);
	}

	public static boolean startedAvanteEstudanteSocialAssets(List<Integer> subServices) {
		return containsAny(ServicesConstants.ESTUDANTES_RECURSOS_SOCIAIS_MANDATORY, subServices)
				|| containsAny(ServicesConstants.ESTUDANTES_RECURSOS_SOCIAIS_NON_MANDATORY, subServices);
	}

	public static boolean completedAvanteRaparigaSocialAssets(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.RAPARIGAS_RECURSOS_SOCIAIS_MANDATORY);
	}

	public static boolean startedAvanteRaparigaSocialAssets(List<Integer> subServices) {
		return containsAny(ServicesConstants.RAPARIGAS_RECURSOS_SOCIAIS_MANDATORY, subServices)
				|| containsAny(ServicesConstants.RAPARIGAS_RECURSOS_SOCIAIS_NON_MANDATORY, subServices);
	}

	public static boolean completedGuiaFacilitacaoSocialAssets(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.GUIAO_FACILITACAO_RECURSOS_SOCIAIS);
	}

	public static boolean startedGuiaFacilitacaoSocialAssets(List<Integer> subServices) {
		return containsAny(ServicesConstants.GUIAO_FACILITACAO_RECURSOS_SOCIAIS, subServices)
				|| containsAny(ServicesConstants.GUIAO_FACILITACAO_RECURSOS_SOCIAIS, subServices);
	}

	public static boolean startedGuiaFacilitacaoSocialAssets15Plus(AgywPrev agywPrev) {
		return agywPrev.getSocial_assets_15_plus() > 0;
	}

	public static boolean completedAvanteEstudanteHIVPrevention(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.ESTUDANTES_HIV_PREVENTION);
	}

	public static boolean startedAvanteEstudanteHIVPrevention(List<Integer> subServices) {
		return containsAny(ServicesConstants.ESTUDANTES_HIV_PREVENTION, subServices);
	}

	public static boolean completedAvanteRaparigaHIVPrevention(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.RAPARIGAS_HIV_PREVENTION);
	}

	public static boolean startedAvanteRaparigaHIVPrevention(List<Integer> subServices) {
		return containsAny(ServicesConstants.RAPARIGAS_HIV_PREVENTION, subServices);
	}

	public static boolean completedGuiaFacilitacaoHIVPrevention(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.GUIAO_FACILITACAO_HIV_PREVENTION_MANDATORY);
	}

	public static boolean startedGuiaFacilitacaoHIVPrevention(List<Integer> subServices) {
		return containsAny(ServicesConstants.GUIAO_FACILITACAO_HIV_PREVENTION_MANDATORY, subServices)
				|| containsAny(ServicesConstants.GUIAO_FACILITACAO_HIV_PREVENTION_NON_MANDATORY, subServices);
	}

	public static boolean completedAvanteEstudanteViolencePrevention(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.ESTUDANTES_VIOLENCE_PREVENTION);
	}

	public static boolean completedAvanteEstudanteViolencePrevention(AgywPrev agywPrev) {
		return agywPrev.getStudent_vilence_prevention() > 2;
	}

	public static boolean startedAvanteEstudanteViolencePrevention(List<Integer> subServices) {
		return containsAny(ServicesConstants.ESTUDANTES_VIOLENCE_PREVENTION, subServices);
	}

	public static boolean startedAvanteEstudanteViolencePrevention(AgywPrev agywPrev) {
		return agywPrev.getStudent_vilence_prevention() > 0;
	}

	public static boolean completedAvanteRaparigaViolencePrevention(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.RAPARIGAS_VIOLENCE_PREVENTION);
	}

	public static boolean completedAvanteRaparigaViolencePrevention(AgywPrev agywPrev) {
		return agywPrev.getGirl_violence_prevention() > 4;
	}

	public static boolean startedAvanteRaparigaViolencePrevention(List<Integer> subServices) {
		return containsAny(ServicesConstants.RAPARIGAS_VIOLENCE_PREVENTION, subServices);
	}

	public static boolean startedAvanteRaparigaViolencePrevention(AgywPrev agywPrev) {
		return agywPrev.getGirl_violence_prevention() > 0;
	}

	public static boolean completedGuiaFacilitacaoViolencePrevention(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.GUIAO_FACILITACAO_VIOLENCE_PREVENTION);
	}

	public static boolean startedGuiaFacilitacaoViolencePrevention(List<Integer> subServices) {
		return containsAny(ServicesConstants.GUIAO_FACILITACAO_VIOLENCE_PREVENTION, subServices);
	}

	public static boolean completedAvanteEstudante(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.ESTUDANTES_MANDATORY) && subServices.size() > 23;
	}

	public static boolean completedAvanteEstudante(AgywPrev agywPrev) {
		return agywPrev.getMandatory_social_assets() > 17 && agywPrev.getOther_social_assets() > 5;
	}

	public static boolean startedAvanteEstudante(List<Integer> subServices) {
		return containsAny(ServicesConstants.ESTUDANTES_MANDATORY, subServices)
				|| containsAny(ServicesConstants.ESTUDANTES_NON_MANDATORY, subServices);
	}

	public static boolean startedAvanteEstudante(AgywPrev agywPrev) {
		return agywPrev.getMandatory_social_assets() > 0 || agywPrev.getOther_social_assets() > 0;
	}

	public static boolean completedAvanteRapariga(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.RAPARIGAS_MANDATORY) && subServices.size() > 15;
	}

	public static boolean completedSimplifiedAvanteRapariga(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.SIMPLIFIED_RAPARIGAS_MANDATORY);
	}

	public static boolean completedAvanteRapariga(AgywPrev agywPrev) {
		return agywPrev.getMandatory_social_assets() > 10 && agywPrev.getOther_social_assets() > 4;
	}

	public static boolean completedSimplifiedAvanteRapariga(AgywPrev agywPrev) {
		return agywPrev.getMandatory_social_assets() >= 9;
	}

	public static boolean startedAvanteRapariga(List<Integer> subServices) {
		return containsAny(ServicesConstants.RAPARIGAS_MANDATORY, subServices)
				|| containsAny(ServicesConstants.RAPARIGAS_NON_MANDATORY, subServices);
	}

	public static boolean startedSimplifiedAvanteRapariga(List<Integer> subServices) {
		return containsAny(ServicesConstants.SIMPLIFIED_RAPARIGAS_MANDATORY, subServices);
	}

	public static boolean startedAvanteRapariga(AgywPrev agywPrev) {
		return agywPrev.getMandatory_social_assets() > 0 || agywPrev.getOther_social_assets() > 0;
	}

	public static boolean startedSimplifiedAvanteRapariga(AgywPrev agywPrev) {
		return agywPrev.getMandatory_social_assets() > 0;
	}

	public static boolean completedGuiaFacilitacao(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.GUIAO_FACILITACAO_MANDATORY);
	}

	public static boolean completedSimplifiedGuiaFacilitacao(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.SIMPLIFIED_GUIAO_FACILITACAO_MANDATORY);
	}

	public static boolean completedGuiaFacilitacao(AgywPrev agywPrev) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		try {
			Date cop22StartDate = df.parse(COP_22_START_DATE);
			return agywPrev.getHiv_gbv_sessions() >= 9 && (agywPrev.getDate_created().before(cop22StartDate)
					|| agywPrev.getDate_created().compareTo(cop22StartDate) >= 0
							&& agywPrev.getHiv_gbv_sessions_prep() > 0);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean completedSimplifiedGuiaFacilitacao(AgywPrev agywPrev) {
		return agywPrev.getHiv_gbv_sessions() >= 8;
	}

	public static boolean startedGuiaFacilitacao(List<Integer> subServices) {
		return containsAny(ServicesConstants.GUIAO_FACILITACAO_MANDATORY, subServices)
				|| containsAny(ServicesConstants.GUIAO_FACILITACAO_NON_MANDATORY, subServices);
	}

	public static boolean startedSimplifiedGuiaFacilitacao(List<Integer> subServices) {
		return containsAny(ServicesConstants.SIMPLIFIED_GUIAO_FACILITACAO_MANDATORY, subServices);
	}

	public static boolean startedGuiaFacilitacao(AgywPrev agywPrev) {
		return agywPrev.getHiv_gbv_sessions() > 0 || agywPrev.getHiv_gbv_sessions_prep() > 0;
	}

	public static boolean startedSimplifiedGuiaFacilitacao(AgywPrev agywPrev) {
		return agywPrev.getHiv_gbv_sessions() > 0;
	}

	public static boolean completedSAAJEducationSessions(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.SESSOES_EDUCATIVAS_SAAJ_MANDATORY);
	}

	public static boolean completedSimplifiedSAAJEducationSessions(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.SIMPLIFIED_SESSOES_EDUCATIVAS_SAAJ_MANDATORY);
	}

	public static boolean completedSAAJEducationSessions(AgywPrev agywPrev) {
		return agywPrev.getSaaj_educational_sessions() > 3;
	}

	public static boolean completedSimplifiedSAAJEducationSessions(AgywPrev agywPrev) {
		return agywPrev.getSaaj_educational_sessions() >= 2;
	}

	public static boolean completedCondomsPromotionOrProvision(List<Integer> subServices) {
		return containsAny(ServicesConstants.PROMOCAO_PROVISAO_PRESERVATIVOS, subServices);
	}

	public static boolean completedCondomsPromotionOrProvision(AgywPrev agywPrev) {
		return agywPrev.getCondoms() > 0;
	}

	public static boolean completedContraceptionsPromotionOrProvision(List<Integer> subServices) {
		return containsAny(ServicesConstants.PROMOCAO_PROVISAO_CONTRACEPCAO, subServices);
	}

	public static boolean completedContraceptionsPromotionOrProvision(AgywPrev agywPrev) {
		return agywPrev.getContraception() > 0;
	}

	public static boolean startedSAAJEducationSessions(List<Integer> subServices) {
		return containsAny(ServicesConstants.SESSOES_EDUCATIVAS_SAAJ_MANDATORY, subServices)
				|| containsAny(ServicesConstants.SESSOES_EDUCATIVAS_SAAJ_NON_MANDATORY, subServices);
	}

	public static boolean startedSimplifiedSAAJEducationSessions(List<Integer> subServices) {
		return containsAny(ServicesConstants.SIMPLIFIED_SESSOES_EDUCATIVAS_SAAJ_MANDATORY, subServices);
	}

	public static boolean startedSAAJEducationSessions(AgywPrev agywPrev) {
		return agywPrev.getSaaj_educational_sessions() > 0;
	}

	public static boolean completedPostViolenceCare_US(List<Integer> subServices) {
		return subServices.contains(ServicesConstants.APSS_US);
	}

	public static boolean completedPostViolenceCare_US(AgywPrev agywPrev) {
		return agywPrev.getPost_violence_care_us() > 0;
	}

	public static boolean startedPostViolenceCare_US(List<Integer> subServices) {
		return containsAny(ServicesConstants.CUIDADOS_POS_VIOLENCIA_US, subServices);
	}

	public static boolean startedPostViolenceCare_US(AgywPrev agywPrev) {
		return agywPrev.getPost_violence_care_us_others() > 0;
	}

	public static boolean completedPostViolenceCare_CM(List<Integer> subServices) {
		return subServices.contains(ServicesConstants.APSS_CM);
	}

	public static boolean completedSimplifiedPostViolenceCare_CM(List<Integer> subServices) {
		return subServices.contains(ServicesConstants.PROTECAO_CRIANCA);
	}

	public static boolean completedPostViolenceCare_CM(AgywPrev agywPrev) {
		return agywPrev.getPost_violence_care_comunity() > 0;
	}

	public static boolean startedPostViolenceCare_CM(List<Integer> subServices) {
		return containsAny(ServicesConstants.CUIDADOS_POS_VIOLENCIA_COMUNIDADE, subServices);
	}

	public static boolean startedPostViolenceCare_CM(AgywPrev agywPrev) {
		return agywPrev.getPost_violence_care_comunity_others() > 0;
	}

	public static boolean completedEducationSubsidies(List<Integer> subServices) {
		return containsAny(ServicesConstants.SUBSIDIO_ESCOLAR, subServices);
	}

	public static boolean startedFinancialLiteracyAflatoun(List<Integer> subServices) {
		return containsAny(ServicesConstants.LITERACIA_FINANCEIRA_AFLATOUN, subServices);
	}

	public static boolean startedFinancialLiteracyAflatoun(AgywPrev agywPrev) {
		return agywPrev.getFinancial_literacy_aflatoun() > 0;
	}

	public static boolean startedFinancialLiteracyAflateen(List<Integer> subServices) {
		return containsAny(ServicesConstants.LITERACIA_FINANCEIRA_AFLATEEN, subServices);
	}

	public static boolean startedSimplifiedFinancialLiteracyAflateen(List<Integer> subServices) {
		return containsAny(ServicesConstants.SIMPLIFIED_LITERACIA_FINANCEIRA_AFLATEEN, subServices);
	}

	public static boolean startedFinancialLiteracyAflateen(AgywPrev agywPrev) {
		return agywPrev.getFinancial_literacy_aflateen() > 0;
	}

	public static boolean completedFinancialLiteracy(List<Integer> subServices) {
		return subServices.contains(ServicesConstants.LITERACIA_FINANCEIRA);
	}

	public static boolean completedFinancialLiteracy(AgywPrev agywPrev) {
		return agywPrev.getFinancial_literacy() >= 1;
	}

	public static boolean completedFinancialLiteracyAflatoun(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.LITERACIA_FINANCEIRA_AFLATOUN);
	}

	public static boolean completedFinancialLiteracyAflatoun(AgywPrev agywPrev) {
		return agywPrev.getFinancial_literacy_aflatoun() >= 7;
	}

	public static boolean completedFinancialLiteracyAflateen(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.LITERACIA_FINANCEIRA_AFLATEEN);
	}

	public static boolean completedSimplifiedFinancialLiteracyAflateen(List<Integer> subServices) {
		return subServices.containsAll(ServicesConstants.SIMPLIFIED_LITERACIA_FINANCEIRA_AFLATEEN);
	}

	public static boolean completedFinancialLiteracyAflateen(AgywPrev agywPrev) {
		return agywPrev.getFinancial_literacy_aflateen() >= 9;
	}

	public static boolean completedSimplifiedFinancialLiteracyAflateen(AgywPrev agywPrev) {
		return agywPrev.getFinancial_literacy_aflateen() >= 2;
	}

	public static boolean completedHIVTestingServices(List<Integer> subServices) {
		return containsAny(ServicesConstants.ACONSELHAMENTO_TESTAGEM_SAUDE, subServices);
	}

	public static boolean completedHIVTestingServices(AgywPrev agywPrev) {
		return agywPrev.getHiv_testing() > 0;
	}

	public static boolean completedOtherSAAJServices(List<Integer> subServices) {
		return containsAny(ServicesConstants.OUTROS_SERVICOS_SAAJ, subServices);
	}

	public static boolean completedOtherSAAJServices(AgywPrev agywPrev) {
		return agywPrev.getOther_saaj_services() > 0;
	}

	public static boolean completedPrep(List<Integer> subServices) {
		return subServices.contains(ServicesConstants.PROVISAO_PREP);
	}

	public static boolean completedCombinedSocioEconomicApproaches(List<Integer> subServices) {
		return containsAny(ServicesConstants.ABORDAGENS_SOCIO_ECONOMICAS_COMBINADAS, subServices);
	}

	public static boolean completedDisagCombinedSocioEconomicApproaches(AgywPrev agywPrev) {
		return agywPrev.getDisag_social_economics_approaches() > 0;
	}

	public static boolean completedCombinedSocioEconomicApproaches(AgywPrev agywPrev) {
		return agywPrev.getSocial_economics_approaches() > 0;
	}

	public static boolean hadScoolAllowance(List<Integer> subServices) {
		return containsAny(ServicesConstants.SUBSIDIO_ESCOLAR, subServices);
	}

	public static boolean hadSchoolAllowance(AgywPrev agywPrev) {
		return agywPrev.getSchool_allowance() > 0;
	}

	public static boolean completedPartnerAddressedInvervention(List<Integer> subServices) {
		return subServices.contains(ServicesConstants.GRASSROOTS_SOCCER);
	}

	public static boolean completedCommunityMobilization(List<Integer> subServices) {
		return containsAny(ServicesConstants.MOBILIZACAO_COMUNITARIA_MUDANCA_NORMAS, subServices);
	}

	public static boolean completedFamilyMatters(List<Integer> subServices) {
		return subServices.contains(ServicesConstants.PAPO_FAMILIA);
	}

	public static boolean completedViolencePrevention15Plus(AgywPrev agywPrev) {
		return agywPrev.getViolence_prevention_15_plus() > 2;
	}

	public static boolean startedViolencePrevention15Plus(AgywPrev agywPrev) {
		return agywPrev.getViolence_prevention_15_plus() > 0;
	}

	// Old Curriculum
	public static boolean completedSocialAssetsOldCurriculum(AgywPrev agywPrev) {
		return agywPrev.getOld_social_assets() > 9;
	}

	public static boolean startedSocialAssetsOldCurriculum(AgywPrev agywPrev) {
		return agywPrev.getOld_social_assets() > 0;
	}

	public static boolean completedHivSessions(AgywPrev agywPrev) {
		return agywPrev.getHiv_sessions() > 0;
	}

	public static boolean completedGbvSessions(AgywPrev agywPrev) {
		return agywPrev.getGbv_sessions() > 0;
	}

	public static boolean completedPrep(AgywPrev agywPrev) {
		return agywPrev.getPrep() > 0;
	}

	public static Integer getReferenceServiceStatus(Collection<BeneficiariesInterventions> interventions,
			Integer service) {
		List<Integer> subServices = interventions.stream().map(BeneficiariesInterventions::getSubServices)
				.collect(Collectors.toList()).stream().map(SubServices::getId).collect(Collectors.toList());

		switch (service) {
		case 1:
			return completedCondomsPromotionOrProvision(subServices) ? ReferencesStatus.ADDRESSED
					: ReferencesStatus.PENDING;
		case 2:
			return completedContraceptionsPromotionOrProvision(subServices) ? ReferencesStatus.ADDRESSED
					: ReferencesStatus.PENDING;
		case 3:
			return completedPostViolenceCare_US(subServices) ? ReferencesStatus.ADDRESSED
					: startedPostViolenceCare_US(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		// FIXME: Remove this option that stands only for testing purposes
		case 4:
			return completedPostViolenceCare_CM(subServices) ? ReferencesStatus.ADDRESSED
					: startedPostViolenceCare_CM(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 9:
			return completedHIVTestingServices(subServices) ? ReferencesStatus.ADDRESSED : ReferencesStatus.PENDING;
		case 18:
			return hadScoolAllowance(subServices) ? ReferencesStatus.ADDRESSED : ReferencesStatus.PENDING;
		case 21:
			return completedCombinedSocioEconomicApproaches(subServices) ? ReferencesStatus.ADDRESSED
					: ReferencesStatus.PENDING;
		case 36:
			return completedOtherSAAJServices(subServices) ? ReferencesStatus.ADDRESSED : ReferencesStatus.PENDING;
		case 37:
			return completedSAAJEducationSessions(subServices) ? ReferencesStatus.ADDRESSED
					: startedSAAJEducationSessions(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 38:
			return completedCommunityMobilization(subServices) ? ReferencesStatus.ADDRESSED : ReferencesStatus.PENDING;
		case 40:
			return completedFamilyMatters(subServices) ? ReferencesStatus.ADDRESSED : ReferencesStatus.PENDING;
		case 42:
			return completedPostViolenceCare_CM(subServices) ? ReferencesStatus.ADDRESSED
					: startedPostViolenceCare_CM(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 44:
			return completedAvanteRaparigaSocialAssets(subServices) ? ReferencesStatus.ADDRESSED
					: startedAvanteRaparigaSocialAssets(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 45:
			return completedAvanteEstudanteSocialAssets(subServices) ? ReferencesStatus.ADDRESSED
					: startedAvanteEstudanteSocialAssets(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 46:
			return completedGuiaFacilitacaoSocialAssets(subServices) ? ReferencesStatus.ADDRESSED
					: startedGuiaFacilitacaoSocialAssets(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 47:
			return completedAvanteRaparigaHIVPrevention(subServices) ? ReferencesStatus.ADDRESSED
					: startedAvanteRaparigaHIVPrevention(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 48:
			return completedAvanteEstudanteHIVPrevention(subServices) ? ReferencesStatus.ADDRESSED
					: startedAvanteEstudanteHIVPrevention(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 49:
			return completedGuiaFacilitacaoHIVPrevention(subServices) ? ReferencesStatus.ADDRESSED
					: startedGuiaFacilitacaoHIVPrevention(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 50:
			return completedAvanteRaparigaViolencePrevention(subServices) ? ReferencesStatus.ADDRESSED
					: startedAvanteRaparigaViolencePrevention(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 51:
			return completedAvanteEstudanteViolencePrevention(subServices) ? ReferencesStatus.ADDRESSED
					: startedAvanteEstudanteViolencePrevention(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 52:
			return completedGuiaFacilitacaoViolencePrevention(subServices) ? ReferencesStatus.ADDRESSED
					: startedGuiaFacilitacaoViolencePrevention(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 53:
			return completedPartnerAddressedInvervention(subServices) ? ReferencesStatus.ADDRESSED
					: ReferencesStatus.PENDING;
		case 54:
			return completedPrep(subServices) ? ReferencesStatus.ADDRESSED : ReferencesStatus.PENDING;
		case 55:
			return completedFinancialLiteracy(subServices) ? ReferencesStatus.ADDRESSED : ReferencesStatus.PENDING;
		case 56:
			return completedFinancialLiteracyAflatoun(subServices) ? ReferencesStatus.ADDRESSED
					: startedFinancialLiteracyAflatoun(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;
		case 57:
			return completedFinancialLiteracyAflateen(subServices) ? ReferencesStatus.ADDRESSED
					: startedFinancialLiteracyAflateen(subServices) ? ReferencesStatus.PARTIALLY_ADDRESSED
							: ReferencesStatus.PENDING;

		default:
			return ReferencesStatus.PENDING;
		}
	}

	/**
	 * Retorna o estado da referência 0 : Pendente 1 : Atendida Parcialmente 2 :
	 * Atendida
	 * 
	 * @param reference
	 * @return
	 */
	public static Integer getReferenceStatus(References reference,
			Collection<BeneficiariesInterventions> interventions) {
		int pendingServices = 0;
		int completedServices = 0;

		Set<ReferencesServices> referencesServiceses = reference.getReferencesServiceses();

		for (ReferencesServices referencesServices : referencesServiceses) {
			Integer referenceServiceStatus = getReferenceServiceStatus(interventions,
					referencesServices.getServices().getId());

			if (referenceServiceStatus == ReferencesStatus.PENDING) {
				pendingServices++;
			} else if (referenceServiceStatus != ReferencesStatus.PARTIALLY_ADDRESSED) {
				completedServices++;
			}
		}

		if (pendingServices == referencesServiceses.size()) {
			return ReferencesStatus.PENDING;
		} else if (completedServices == referencesServiceses.size()) {
			return ReferencesStatus.ADDRESSED;
		} else {
			return ReferencesStatus.PARTIALLY_ADDRESSED;
		}
	}

	public static <T> boolean containsAny(List<T> l1, List<T> l2) {

		for (T elem : l1) {

			if (l2.contains(elem)) {
				return true;
			}
		}
		return false;
	}
}
