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
	public UserLastSync saveLastSyncDate(String username, String appVersion) {
		Users user = service.GetUniqueEntityByNamedQuery("Users.findByUsername", username);
		UserLastSync userSyncControl = service.GetUniqueEntityByNamedQuery("UserLastSync.findByUsername", username);

		if (userSyncControl != null) {
			userSyncControl.setUser(user);
			userSyncControl.setLastSyncDate(new Date());
			userSyncControl.setAppVersion(appVersion);
			service.update(userSyncControl);

			return userSyncControl;
		} else {
			UserLastSync userLastSync = new UserLastSync();
			userLastSync.setUsername(username);
			userLastSync.setUser(user);
			userLastSync.setLastSyncDate(new Date());
			userLastSync.setAppVersion(appVersion);
			service.Save(userLastSync);

			return userLastSync;
		}
	}
}
