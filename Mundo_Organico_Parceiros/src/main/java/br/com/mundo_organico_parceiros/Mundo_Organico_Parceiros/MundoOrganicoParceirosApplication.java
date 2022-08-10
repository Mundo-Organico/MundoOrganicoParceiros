package br.com.mundo_organico_parceiros.Mundo_Organico_Parceiros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MundoOrganicoParceirosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MundoOrganicoParceirosApplication.class, args);
	}

}
