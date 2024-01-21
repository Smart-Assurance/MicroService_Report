package ma.fstt.microservicereport.payload.response;

import ma.fstt.microservicereport.entities.Examinater;
import ma.fstt.microservicereport.entities.Vehicule;


public interface ReportWithExaminaterAndVehicule {
    String getId();
    String getTitle();
    String getDescription();
    String getStatus();
    Vehicule getVehicule();
    Examinater getExaminater();
}
