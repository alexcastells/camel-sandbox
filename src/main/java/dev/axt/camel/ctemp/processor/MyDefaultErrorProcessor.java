package dev.axt.camel.ctemp.processor;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.camel.Exchange;
import org.apache.camel.MessageHistory;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author alextremp
 */
@Component
public class MyDefaultErrorProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(MyDefaultErrorProcessor.class);

	private static final AtomicLong ERR_COUNT = new AtomicLong(0);

	@Override
	public void process(Exchange exchange) throws Exception {
		List<MessageHistory> list = exchange.getProperty(Exchange.MESSAGE_HISTORY, List.class);
		StringBuilder sb = new StringBuilder("Error [").append(ERR_COUNT.addAndGet(1)).append("] processing message with history:");
		for (MessageHistory mh : list) {
			sb.append("\n").append(String.format("\tRoute [%s]\t\tNode [%s]\t\t Elapsed [%s]", mh.getRouteId(), mh.getNode(), mh.getElapsed()));
		}
		Throwable th = exchange.getException();
		if (th == null) {
			th = exchange.getProperty("CamelExceptionCaught", Throwable.class);
		}
		exchange.getIn().setBody("Error" + (th != null ? " (" + th.getMessage() + ")" : ""));
		Integer ncode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class);
		int code = ncode == null || ncode < 400 ? 500 : ncode;
		exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, code);
		LOG.error(sb.toString(), th);
	}

}
