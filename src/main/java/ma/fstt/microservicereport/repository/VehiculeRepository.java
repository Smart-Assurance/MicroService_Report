package ma.fstt.microservicereport.repository;

import ma.fstt.microservicereport.entities.Vehicule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculeRepository extends MongoRepository<Vehicule, String> {
    Optional<Vehicule> findByMatricule(String matricule);

}
