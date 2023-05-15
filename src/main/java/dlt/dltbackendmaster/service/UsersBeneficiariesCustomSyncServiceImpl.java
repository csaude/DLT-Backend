package dlt.dltbackendmaster.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dlt.dltbackendmaster.domain.UsersBeneficiariesCustomSync;

/**
 * This class implements the Service interface
 * 
 * @author Francisco da Conceição Alberto Macuácua
 *
 */
@Service
public class UsersBeneficiariesCustomSyncServiceImpl implements UsersBeneficiariesCustomSyncService {
	@Autowired
	public DAOService daoService;

	@Override
	public List<UsersBeneficiariesCustomSync> getUserBeneficiariesSync(Integer userId, Date lastPulledAt) {

		List<UsersBeneficiariesCustomSync> usersBeneficiaries;

		if (lastPulledAt == null || lastPulledAt.equals(null)) {
			usersBeneficiaries = daoService.GetAllEntityByNamedQuery("UsersBeneficiariesCustomSync.findByUserId",
					userId);
		} else {
			usersBeneficiaries = daoService.GetAllEntityByNamedQuery("UsersBeneficiariesCustomSync.findByUserIdAndSyncDate",
					userId, lastPulledAt);
		}
		return usersBeneficiaries;
	}

	@Override
	public List<UsersBeneficiariesCustomSync> findByUserIdAndBeneficiaryId(Integer userId, Integer beneficiaryId) {
		List<UsersBeneficiariesCustomSync> usersBeneficiaries = daoService
				.GetAllEntityByNamedQuery("UsersBeneficiariesCustomSync.findByUserIdAndBeneficiaryId", userId, beneficiaryId);
		return usersBeneficiaries;
	}
}
