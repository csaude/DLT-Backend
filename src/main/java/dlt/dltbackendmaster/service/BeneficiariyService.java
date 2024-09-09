package dlt.dltbackendmaster.service;

/**
 * This interface holds the service implementations called by the API controller
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
public interface BeneficiariyService {

	public void markAsCompletedServices(Integer beneficiaryId,
			Integer serviceComplationLevel);
}
