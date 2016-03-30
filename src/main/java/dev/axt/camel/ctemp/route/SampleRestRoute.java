package dev.axt.camel.ctemp.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 *
 * @author alextremp
 */
@Component
public class SampleRestRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		restConfiguration().component("netty4-http").host("localhost").port(10091).bindingMode(RestBindingMode.auto);

		rest("/say")
				.get("/hello").to("direct:hello")
				.get("/bye").to("direct:bye")
				.post("/bye").to("mock:update");

		from("direct:hello")
				.transform().constant("Hello World");
		from("direct:bye")
				.transform().constant("Bye World");
	}

}
