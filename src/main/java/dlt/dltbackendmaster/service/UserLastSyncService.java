package dlt.dltbackendmaster.service;

import dlt.dltbackendmaster.domain.UserLastSync;

public interface UserLastSyncService {

	UserLastSync saveLastSyncDate(String username, String appVersion);

}
