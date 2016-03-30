package dev.axt.camel.ctemp.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author alextremp
 */
@Component
public class GatewayConfigImpl implements GatewayConfig {

	private static final Logger LOG = LoggerFactory.getLogger(GatewayConfigImpl.class);

	private Properties properties;

	@Value("${gateway.url}")
	private String url;

	@Value("${gateway.contextPath:/}")
	private String contextPath;

	@Value("${gateway.configFile}")
	private String configFile;

	@PostConstruct
	public void load() {
		LOG.info("Loading gateway properties from " + configFile);
		File file = Paths.get(configFile).toFile();
		if (!file.isFile()) {
			LOG.error("No config file (" + configFile + ")");
		}
		properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		} catch (IOException ex) {
			LOG.error("Error reading properties file", ex);
		}
	}

	@Override
	public String match(String origin) {
		for (String k : properties.stringPropertyNames()) {
			if (origin != null && origin.startsWith(contextPath + k)) {
				return properties.getProperty(k);
			}
		}
		return null;
	}

	@Override
	public String baseUrl() {
		return url;
	}

	@Override
	public String contextPath() {
		return contextPath;
	}

}
