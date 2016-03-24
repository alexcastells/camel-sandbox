package dev.axt.camel.ctemp.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author acastells
 */
@Component
public class SampleProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(SampleProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		LOG.info(String.format("Received [%s] at [%s]", exchange.getIn().getBody(String.class), getClass().getSimpleName()));
		exchange.getIn().setBody("Hello at " + System.currentTimeMillis());
		exchange.getIn().setHeader("CamelHttpPath", exchange.getIn().getHeader("CamelHttpPath", String.class).replaceFirst("/centralapi", ""));
	}

}
