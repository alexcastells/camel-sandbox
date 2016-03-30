package dev.axt.camel.ctemp.route;

import dev.axt.camel.ctemp.bean.GatewayConfig;
import dev.axt.camel.ctemp.processor.GatewayMatchProcessor;
import dev.axt.camel.ctemp.processor.MyDefaultErrorProcessor;
import dev.axt.camel.ctemp.processor.SampleProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author alextremp
 */
@Component
public class SampleGatewayRoute extends RouteBuilder {

	@Autowired
	private GatewayConfig gatewayConfig;
	@Autowired
	private GatewayMatchProcessor gatewayMatchProcessor;
	@Autowired
	private SampleProcessor sampleProcessor;
	@Autowired
	private MyDefaultErrorProcessor myDefaultErrorProcessor;

	@Override
	public void configure() throws Exception {

		onException(Exception.class)
				.handled(true)
				.process(myDefaultErrorProcessor);

		from("netty4-http:" + gatewayConfig.baseUrl() + "?matchOnUriPrefix=true")
				.to("activemq:queue:GatewayRequestsQueue");

		from("activemq:queue:GatewayRequestsQueue?maxConcurrentConsumers=25")
				.process(gatewayMatchProcessor)
				.recipientList(header(GatewayConfig.GW_REDIRECT_HEADER))
				.end();

		from("direct:test")
				.process(sampleProcessor);

	}

}
