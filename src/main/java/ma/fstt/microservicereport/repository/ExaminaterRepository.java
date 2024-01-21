package ma.fstt.microservicereport.repository;

import ma.fstt.microservicereport.entities.Examinater;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ExaminaterRepository extends MongoRepository<Examinater, String> {

}