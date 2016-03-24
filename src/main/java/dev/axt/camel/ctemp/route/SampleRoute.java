package dev.axt.camel.ctemp.route;

import dev.axt.camel.ctemp.processor.SampleProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author acastells
 */
@Component
public class SampleRoute extends RouteBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(SampleRoute.class);

	@Autowired
	private SampleProcessor sampleProcessor;

	@Override
	public void configure() throws Exception {
		LOG.info("Configuring... " + getClass().getSimpleName());
		this.from("netty4-http:http://0.0.0.0:18080?matchOnUriPrefix=true")
				.process(sampleProcessor)
				.to("netty4-http:http://localhost:19001");
	}

}
