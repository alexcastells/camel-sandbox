package dev.axt.camel.ctemp;

import org.apache.camel.spring.boot.CamelSpringBootApplicationController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author acastells
 */
@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args)
				.getBean(CamelSpringBootApplicationController.class)
				.blockMainThread();
	}

}
