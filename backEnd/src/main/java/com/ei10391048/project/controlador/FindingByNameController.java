package com.ei10391048.project.controlador;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FindingByNameController {

    private final FindingByNameRepository findingByNameRepository;

    public FindingByNameController(FindingByNameRepository findingByNameRepository) {
        this.findingByNameRepository = findingByNameRepository;
    }

    @GetMapping("/addLocation")
    public void test(){
        System.out.println("pepe");

    }

    @PostMapping("/addLocation")
    public void find(@RequestBody String location){

        System.out.println(location);
    }
}
