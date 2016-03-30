package dev.axt.camel.ctemp.processor;

import dev.axt.camel.ctemp.bean.GatewayConfig;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alextremp
 */
@Component
public class GatewayMatchProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(GatewayMatchProcessor.class);

	@Autowired
	private GatewayConfig gatewayConfig;

	@Override
	public void process(Exchange exchange) throws Exception {
		String httpPath = exchange.getIn().getHeader(Exchange.HTTP_PATH, String.class);
		String endpoint = gatewayConfig.match(httpPath);
		if (endpoint == null) {
			throw new IllegalStateException("Not matched path: " + httpPath);
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug(String.format("[%s] => [%s]", httpPath, endpoint));
		}

		if (endpoint.startsWith("http")) {
			exchange.getIn().setHeader(Exchange.HTTP_PATH, httpPath.replaceFirst(gatewayConfig.contextPath(), ""));
		}

		exchange.getIn().setHeader(GatewayConfig.GW_REDIRECT_HEADER, endpoint);
	}

}
