package dlt.dltbackendmaster.cfgs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

	@Value("${app.name}")
	private String appName;

	@Value("${app.version}")
	private String appVersion;

	@Value("${api.home}")
	private String apiHome;

	@Value("${api.origin}")
	private String apiOrigin;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getApiHome() {
		return apiHome;
	}

	public void setApiHome(String apiHome) {
		this.apiHome = apiHome;
	}

	public String getApiOrigin() {
		return apiOrigin;
	}

	public void setApiOrigin(String apiOrigin) {
		this.apiOrigin = apiOrigin;
	}

	public void printAppInfo() {
		System.out.println("App Name: " + appName);
		System.out.println("App Version: " + appVersion);
		System.out.println("Api Home: " + apiHome);
	}

}
