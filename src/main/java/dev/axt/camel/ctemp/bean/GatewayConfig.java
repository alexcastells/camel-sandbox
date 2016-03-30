package dev.axt.camel.ctemp.bean;

/**
 *
 * @author alextremp
 */
public interface GatewayConfig {

	public static final String GW_REDIRECT_HEADER = "GW_REDIRECT";

	String match(String origin);

	String baseUrl();
	
	String contextPath();

}
