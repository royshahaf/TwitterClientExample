package server;

public class SecurityParams {
	public boolean secure;
	public String keystoreFilePath;
	public String keystorePassword;
	public String truststoreFilePath;
	public String truststorePassword;

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
