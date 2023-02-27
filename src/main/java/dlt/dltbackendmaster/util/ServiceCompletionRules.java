package dlt.dltbackendmaster.util;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dlt.dltbackendmaster.domain.Beneficiaries;
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
public class ServiceCompletionRules
{

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

    public static boolean startedAvanteEstudanteViolencePrevention(List<Integer> subServices) {
        return containsAny(ServicesConstants.ESTUDANTES_VIOLENCE_PREVENTION, subServices);
    }

    public static boolean completedAvanteRaparigaViolencePrevention(List<Integer> subServices) {
        return subServices.containsAll(ServicesConstants.RAPARIGAS_VIOLENCE_PREVENTION);
    }

    public static boolean startedAvanteRaparigaViolencePrevention(List<Integer> subServices) {
        return containsAny(ServicesConstants.RAPARIGAS_VIOLENCE_PREVENTION, subServices);
    }

    public static boolean completedGuiaFacilitacaoViolencePrevention(List<Integer> subServices) {
        return subServices.containsAll(ServicesConstants.GUIAO_FACILITACAO_VIOLENCE_PREVENTION);
    }

    public static boolean startedGuiaFacilitacaoViolencePrevention(List<Integer> subServices) {
        return containsAny(ServicesConstants.GUIAO_FACILITACAO_VIOLENCE_PREVENTION, subServices);
    }

    public static boolean completedAvanteEstudante(List<Integer> subServices) {
        return subServices.containsAll(ServicesConstants.ESTUDANTES_MANDATORY) && subServices.size() >= 24;
    }

    public static boolean startedAvanteEstudante(List<Integer> subServices) {
        return containsAny(ServicesConstants.ESTUDANTES_MANDATORY, subServices)
               || containsAny(ServicesConstants.ESTUDANTES_NON_MANDATORY, subServices);
    }

    public static boolean completedAvanteRapariga(List<Integer> subServices) {
        return subServices.containsAll(ServicesConstants.RAPARIGAS_MANDATORY) && subServices.size() >= 16;
    }

    public static boolean startedAvanteRapariga(List<Integer> subServices) {
        return containsAny(ServicesConstants.RAPARIGAS_MANDATORY, subServices)
               || containsAny(ServicesConstants.RAPARIGAS_NON_MANDATORY, subServices);
    }

    public static boolean completedGuiaFacilitacao(List<Integer> subServices) {
        return subServices.containsAll(ServicesConstants.GUIAO_FACILITACAO_MANDATORY);
    }

    public static boolean startedGuiaFacilitacao(List<Integer> subServices) {
        return containsAny(ServicesConstants.GUIAO_FACILITACAO_MANDATORY, subServices)
               || containsAny(ServicesConstants.GUIAO_FACILITACAO_NON_MANDATORY, subServices);
    }

    public static boolean completedSAAJEducationSessions(List<Integer> subServices) {
        return subServices.containsAll(ServicesConstants.SESSOES_EDUCATIVAS_SAAJ_MANDATORY);
    }

    public static boolean completedCondomsPromotionOrProvision(List<Integer> subServices) {
        return containsAny(ServicesConstants.PROMOCAO_PROVISAO_PRESERVATIVOS, subServices);
    }

    public static boolean completedContraceptionsPromotionOrProvision(List<Integer> subServices) {
        return containsAny(ServicesConstants.PROMOCAO_PROVISAO_CONTRACEPCAO, subServices);
    }

    public static boolean startedSAAJEducationSessions(List<Integer> subServices) {
        return containsAny(ServicesConstants.SESSOES_EDUCATIVAS_SAAJ_MANDATORY, subServices)
               || containsAny(ServicesConstants.SESSOES_EDUCATIVAS_SAAJ_NON_MANDATORY, subServices);
    }

    public static boolean completedPostViolenceCare_US(List<Integer> subServices) {
        return subServices.contains(ServicesConstants.APSS_US);
    }

    public static boolean startedPostViolenceCare_US(List<Integer> subServices) {
        return containsAny(ServicesConstants.CUIDADOS_POS_VIOLENCIA_US, subServices);
    }

    public static boolean completedPostViolenceCare_CM(List<Integer> subServices) {
        return subServices.contains(ServicesConstants.APSS_CM);
    }

    public static boolean startedPostViolenceCare_CM(List<Integer> subServices) {
        return containsAny(ServicesConstants.CUIDADOS_POS_VIOLENCIA_COMUNIDADE, subServices);
    }

    public static boolean completedEducationSubsidies(List<Integer> subServices) {
        return containsAny(ServicesConstants.SUBSIDIO_ESCOLAR, subServices);
    }

    public static boolean startedFinancialLiteracyAflatoun(List<Integer> subServices) {
        return containsAny(ServicesConstants.LITERACIA_FINANCEIRA_AFLATOUN, subServices);
    }

    public static boolean startedFinancialLiteracyAflateen(List<Integer> subServices) {
        return containsAny(ServicesConstants.LITERACIA_FINANCEIRA_AFLATEEN, subServices);
    }

    public static boolean completedFinancialLiteracy(List<Integer> subServices) {
        return subServices.contains(ServicesConstants.LITERACIA_FINANCEIRA);
    }

    public static boolean completedFinancialLiteracyAflatoun(List<Integer> subServices) {
        return subServices.containsAll(ServicesConstants.LITERACIA_FINANCEIRA_AFLATOUN);
    }

    public static boolean completedFinancialLiteracyAflateen(List<Integer> subServices) {
        return subServices.containsAll(ServicesConstants.LITERACIA_FINANCEIRA_AFLATEEN);
    }

    public static boolean completedHIVTestingServices(List<Integer> subServices) {
        return containsAny(ServicesConstants.ACONSELHAMENTO_TESTAGEM_SAUDE, subServices);
    }

    public static boolean completedOtherSAAJServices(List<Integer> subServices) {
        return containsAny(ServicesConstants.OUTROS_SERVICOS_SAAJ, subServices);
    }

    public static boolean completedPrep(List<Integer> subServices) {
        return subServices.contains(ServicesConstants.PROFILAXIA_PREP);
    }

    public static boolean completedCombinedSocioEconomicAprproaches(List<Integer> subServices) {
        return containsAny(ServicesConstants.ABORDAGENS_SOCIO_ECONOMICAS_COMBINADAS, subServices);
    }

    public static boolean hadScoolAllowance(List<Integer> subServices) {
        return containsAny(ServicesConstants.SUBSIDIO_ESCOLAR, subServices);
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

    public static Integer getReferenceServiceStatus(Beneficiaries beneficiary, Integer service) {
        List<Integer> subServices = beneficiary.getBeneficiariesInterventionses()
                                               .stream()
                                               .map(BeneficiariesInterventions::getSubServices)
                                               .collect(Collectors.toList())
                                               .stream()
                                               .map(SubServices::getId)
                                               .collect(Collectors.toList());

        switch (service) {
            case 1:
                return completedCondomsPromotionOrProvision(subServices) ? ServicesConstants.ATTENDED
                                : ServicesConstants.PENDING;
            case 2:
                return completedContraceptionsPromotionOrProvision(subServices) ? ServicesConstants.ATTENDED
                                : ServicesConstants.PENDING;
            case 3:
                return completedPostViolenceCare_US(subServices) ? ServicesConstants.ATTENDED
                                : startedPostViolenceCare_US(subServices) ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            // FIXME: Remove this option that stands only for testing purposes
            case 4:
                return completedPostViolenceCare_CM(subServices) ? ServicesConstants.ATTENDED
                                : startedPostViolenceCare_CM(subServices) ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 9:
                return completedHIVTestingServices(subServices) ? ServicesConstants.ATTENDED
                                : ServicesConstants.PENDING;
            case 18:
                return hadScoolAllowance(subServices) ? ServicesConstants.ATTENDED : ServicesConstants.PENDING;
            case 21:
                return completedCombinedSocioEconomicAprproaches(subServices) ? ServicesConstants.ATTENDED
                                : ServicesConstants.PENDING;
            case 36:
                return completedOtherSAAJServices(subServices) ? ServicesConstants.ATTENDED : ServicesConstants.PENDING;
            case 37:
                return completedSAAJEducationSessions(subServices) ? ServicesConstants.ATTENDED
                                : startedSAAJEducationSessions(subServices) ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 38:
                return completedCommunityMobilization(subServices) ? ServicesConstants.ATTENDED
                                : ServicesConstants.PENDING;
            case 40:
                return completedFamilyMatters(subServices) ? ServicesConstants.ATTENDED : ServicesConstants.PENDING;
            case 42:
                return completedPostViolenceCare_CM(subServices) ? ServicesConstants.ATTENDED
                                : startedPostViolenceCare_CM(subServices) ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 44:
                return completedAvanteRaparigaSocialAssets(subServices) ? ServicesConstants.ATTENDED
                                : startedAvanteRaparigaSocialAssets(subServices) ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 45:
                return completedAvanteEstudanteSocialAssets(subServices) ? ServicesConstants.ATTENDED
                                : startedAvanteEstudanteSocialAssets(subServices) ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 46:
                return completedGuiaFacilitacaoSocialAssets(subServices) ? ServicesConstants.ATTENDED
                                : startedGuiaFacilitacaoSocialAssets(subServices) ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 47:
                return completedAvanteRaparigaHIVPrevention(subServices) ? ServicesConstants.ATTENDED
                                : startedAvanteRaparigaHIVPrevention(subServices) ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 48:
                return completedAvanteEstudanteHIVPrevention(subServices) ? ServicesConstants.ATTENDED
                                : startedAvanteEstudanteHIVPrevention(subServices)
                                                ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 49:
                return completedGuiaFacilitacaoHIVPrevention(subServices) ? ServicesConstants.ATTENDED
                                : startedGuiaFacilitacaoHIVPrevention(subServices)
                                                ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 50:
                return completedAvanteRaparigaViolencePrevention(subServices) ? ServicesConstants.ATTENDED
                                : startedAvanteRaparigaViolencePrevention(subServices)
                                                ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 51:
                return completedAvanteEstudanteViolencePrevention(subServices) ? ServicesConstants.ATTENDED
                                : startedAvanteEstudanteViolencePrevention(subServices)
                                                ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 52:
                return completedGuiaFacilitacaoViolencePrevention(subServices) ? ServicesConstants.ATTENDED
                                : startedGuiaFacilitacaoViolencePrevention(subServices)
                                                ? ServicesConstants.PARTIALLY_ATTENDED
                                                : ServicesConstants.PENDING;
            case 53:
                return completedPartnerAddressedInvervention(subServices) ? ServicesConstants.ATTENDED
                                : ServicesConstants.PENDING;
            case 54:
                return completedPrep(subServices) ? ServicesConstants.ATTENDED : ServicesConstants.PENDING;
            case 55:
                return completedFinancialLiteracy(subServices) ? ServicesConstants.ATTENDED : ServicesConstants.PENDING;

            default:
                return ServicesConstants.PENDING;
        }
    }

    /**
     * Retorna o estado da referência 
     * 0 : Pendente 
     * 1 : Atendida Parcialmente 
     * 2 : Atendida
     * 
     * @param reference
     * @return
     */
    public static Integer getReferenceStatus(References reference) {
        int pendingServices = 0;
        int completedServices = 0;

        Set<ReferencesServices> referencesServiceses = reference.getReferencesServiceses();

        for (ReferencesServices referencesServices : referencesServiceses) {
            Integer referenceServiceStatus = getReferenceServiceStatus(reference.getBeneficiaries(),
                                                                       referencesServices.getId().getServiceId());

            if (referenceServiceStatus == 0) {
                pendingServices++;
            } else if (referenceServiceStatus != 1) {
                completedServices++;
            }
        }

        if (pendingServices == referencesServiceses.size()) {
            return 0;
        } else if (completedServices == referencesServiceses.size()) {
            return 2;
        } else {
            return 1;
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
