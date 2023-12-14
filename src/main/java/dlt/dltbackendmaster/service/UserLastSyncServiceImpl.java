package dlt.dltbackendmaster.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dlt.dltbackendmaster.domain.UserLastSync;
import dlt.dltbackendmaster.domain.Users;

@Service
public class UserLastSyncServiceImpl implements UserLastSyncService {

	@Autowired
	private DAOService service;

	@Override
	public UserLastSync saveLastSyncDate(String username) {
		Users user = service.GetUniqueEntityByNamedQuery("Users.findByUsername", username);
		UserLastSync userSyncCOntrol = service.GetUniqueEntityByNamedQuery("UserLastSync.findByUsername", username);

		if (userSyncCOntrol != null) {
			userSyncCOntrol.setUser(user);
			userSyncCOntrol.setLastSyncDate(new Date());
			service.update(userSyncCOntrol);

			return userSyncCOntrol;
		} else {			
			UserLastSync userLastSync = new UserLastSync();
			userLastSync.setUsername(username);
			userLastSync.setUser(user);
			userLastSync.setLastSyncDate(new Date());
			service.Save(userLastSync);

			return userLastSync;
		}
	}
}
