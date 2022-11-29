package com.ei10391048.project.controlador;

import com.ei10391048.project.modelo.Location;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FindingByNameController {


    @GetMapping("/")
    public void p(){
        System.out.println("aa");

    }

    @PostMapping("/")
    public void find(@RequestBody Location location){

        System.out.println(location.toString());
    }
}
