package com.ei10391048.project.controlador;

import com.ei10391048.project.modelo.Location;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FindingByNameController {

    private final FindingByNameRepository findingByNameRepository;

    public FindingByNameController(FindingByNameRepository findingByNameRepository){
        this.findingByNameRepository=findingByNameRepository;
    }

    @PostMapping("/")
    public void find(@RequestBody Location location){
        findingByNameRepository.save(location);
        System.out.println(location.toString());
    }
}
