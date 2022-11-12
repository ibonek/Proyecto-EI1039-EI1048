package com.ei10391048.project.controlador;

import com.ei10391048.project.modelo.Coordenadas;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CoordenadaController {
    private final CoordenadaRepository coordenadaRepository;

    public CoordenadaController(CoordenadaRepository coordenadaRepository) {
        this.coordenadaRepository = coordenadaRepository;
    }

    @GetMapping("/coordenadas")
    public List<Coordenadas>getCoordenadas(){
        return (List<Coordenadas>) coordenadaRepository.findAll();
    }

    @PostMapping("/coordenadas")
    public void addCoordenadas(@RequestBody Coordenadas coordenadas){
        coordenadaRepository.save(coordenadas);

        System.out.println(coordenadas);
    }
}
