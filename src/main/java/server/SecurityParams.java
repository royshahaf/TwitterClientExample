package server;

public class SecurityParams {
	private boolean secure;
	private String keystoreFilePath;
	private String keystorePassword;
	private String truststoreFilePath;
	private String truststorePassword;

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}

	public String getKeystoreFilePath() {
		return keystoreFilePath;
	}

	public void setKeystoreFilePath(String keystoreFilePath) {
		this.keystoreFilePath = keystoreFilePath;
	}

	public String getKeystorePassword() {
		return keystorePassword;
	}

	public void setKeystorePassword(String keystorePassword) {
		this.keystorePassword = keystorePassword;
	}

	public String getTruststoreFilePath() {
		return truststoreFilePath;
	}

	public void setTruststoreFilePath(String truststoreFilePath) {
		this.truststoreFilePath = truststoreFilePath;
	}

	public String getTruststorePassword() {
		return truststorePassword;
	}

	public void setTruststorePassword(String truststorePassword) {
		this.truststorePassword = truststorePassword;
	}

	public static SecurityParams getSecurityParams(String prefix) {
		SecurityParams params = new SecurityParams();
		String secureString = System.getenv(prefix + "secure");
		params.secure = Boolean.parseBoolean(secureString);
		if (params.secure) {
			params.keystoreFilePath = System.getenv(prefix + "keystoreFilePath");
			params.keystorePassword = System.getenv(prefix + "keystorePassword");
			params.truststoreFilePath = System.getenv(prefix + "truststoreFilePath");
			params.truststorePassword = System.getenv(prefix + "truststorePassword");
		}
		return params;
	}
}
