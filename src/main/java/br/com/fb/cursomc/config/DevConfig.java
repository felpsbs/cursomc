package br.com.fb.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.fb.cursomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	// pegando o valor da chave que está la no properties
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instantiateTestDatabase() {
		if(!strategy.equals("create")) {
			return false;
		}
		
		dbService.instantiateTestDatabase();
		return true;
	}
}
