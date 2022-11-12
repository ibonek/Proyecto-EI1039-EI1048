package com.ei10391048.project.controlador;

import com.ei10391048.project.modelo.Coordenadas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordenadaRepository extends CrudRepository<Coordenadas, Long> {



}
