package com.ei10391048.project.controlador;
import com.ei10391048.project.modelo.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface FindingByNameRepository extends CrudRepository<Location,Long>{
}
