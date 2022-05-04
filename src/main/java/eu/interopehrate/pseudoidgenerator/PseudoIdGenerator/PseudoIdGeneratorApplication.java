package eu.interopehrate.pseudoidgenerator.PseudoIdGenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PseudoIdGeneratorApplication {

	public static void main(String[] args) {

		DatabaseHandler databaseHandler = new DatabaseHandler();
		databaseHandler.connectToMySQL();
		SpringApplication.run(PseudoIdGeneratorApplication.class, args);
	}

}
