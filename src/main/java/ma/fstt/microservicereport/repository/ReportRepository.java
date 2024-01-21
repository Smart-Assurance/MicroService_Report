package ma.fstt.microservicereport.repository;

import ma.fstt.microservicereport.entities.Report;
import ma.fstt.microservicereport.entities.Vehicule;
import ma.fstt.microservicereport.payload.response.ReportWithExaminaterAndVehicule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    @Query(value = "{ '_id': ?0 }")
    ReportWithExaminaterAndVehicule findReportWithExaminaterAndVehicule(String reportId);
}