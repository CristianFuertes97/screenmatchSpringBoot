package com.alura.pruebaScreenmatch;

import com.alura.pruebaScreenmatch.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PruebaScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(PruebaScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		Principal principal = new Principal();
		principal.muestraElMenu();
	}
}
