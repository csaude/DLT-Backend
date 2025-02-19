package dlt.dltbackendmaster.reports.utils;

/**
 * Constantes do módulo de relatórios
 * 
 * @author Hamilton Mutaquiha
 *
 */
public class ReportsConstants {

	public static final int AGE_BAND_9_14 = 0;

	public static final int AGE_BAND_15_19 = 1;

	public static final int AGE_BAND_20_24 = 2;

	public static final int AGE_BAND_25_29 = 3;

	public static final int COMPLETED_PRIMARY_PACKAGE = 0;

	public static final int COMPLETED_PRIMARY_SERVICE = 1;

	public static final int COMPLETED_VIOLENCE_SERVICE = 2;

	public static final int STARTED_SERVICE = 3;

	public static final int COMPLETED_SECONDARY_SERVICE = 4;

	public static final int HAD_SCHOLL_ALLOWANCE = 5;

	public static final int HAD_SOCIAL_ECONOMIC_APPROACHES = 6;
	
	public static final int PACKAGE_AVANTE_ESTUDANTE = 0;
	
	public static final int PACKAGE_AVANTE_RAPARIGA = 1;
	
	public static final int PACKAGE_GUIAO_FACILITACAO = 2;
	
	public static final int SERVICE_COMPLETION_NO = 0;
	
	public static final int SERVICE_COMPLETION_YES = 1;
	
	public static final int SERVICE_COMPLETION_NA = 3;

	public static final String[] AGE_BANDS = { "9-14", "15-19", "20-24", "25-29", "Subtotal" };

	public static final String[] ENROLLMENT_TIMES = { "0-6", "7-12", "13-24", "25+" };

	public static final String[] DISAGGREGATIONS = { "completed-primary-package", "completed-primary-service",
			"completed-violence-service", "started-service", "completed-secondary-service", "had-school-allowance",
			"had-social-economic-approaches" };

	public static final String[] SERVICE_PACKAGES = { "Avante Estudante", "Avante Rapariga", "Guia de facilitação para clubes de jovens sobre a prevenção do HIV/Violência" };

	public static final String[] COMPLETION_STATUSES = { "Incompleto", "Completo", "N/A" };
}
