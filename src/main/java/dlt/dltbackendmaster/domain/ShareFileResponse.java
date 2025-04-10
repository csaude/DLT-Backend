package dlt.dltbackendmaster.domain;

public class ShareFileResponse {
	private String authToken;
	private String uploadLink;

	public ShareFileResponse(String uploadLink, String authToken) {
		this.uploadLink = uploadLink;
		this.authToken = authToken;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getUploadLink() {
		return uploadLink;
	}

	public void setUploadLink(String uploadLink) {
		this.uploadLink = uploadLink;
	}

}