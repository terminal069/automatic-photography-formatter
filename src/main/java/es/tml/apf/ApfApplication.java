package es.tml.apf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import es.tml.apf.configuration.ApfConfigurator;

@SpringBootApplication
@Import({ ApfConfigurator.class })
public class ApfApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApfApplication.class, args);
	}
}
