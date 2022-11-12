package com.ei10391048.project;

import com.ei10391048.project.controlador.CoordenadaRepository;
import com.ei10391048.project.modelo.Coordenadas;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CoordenadaRepository coordenadaRepository){
		return args -> {
			Stream.of("40.4167754,-3.7037902", "40.4167754,-3.7037902", "40.4167754,-3.7037902").forEach(latitudlongitud -> {
				Coordenadas coordenadas = new Coordenadas(latitudlongitud);
				coordenadaRepository.save(coordenadas);
			});
			coordenadaRepository.findAll().forEach(System.out::println);
		};
	}
}
