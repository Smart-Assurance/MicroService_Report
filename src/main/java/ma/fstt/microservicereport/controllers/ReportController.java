package ma.fstt.microservicereport.controllers;



import ma.fstt.microservicereport.entities.Examinater;
import ma.fstt.microservicereport.entities.Report;
import ma.fstt.microservicereport.entities.Vehicule;
import ma.fstt.microservicereport.payload.request.AddReportRequest;
import ma.fstt.microservicereport.payload.response.MessageResponse;
import ma.fstt.microservicereport.payload.response.ReportWithExaminaterAndVehicule;
import ma.fstt.microservicereport.repository.ExaminaterRepository;
import ma.fstt.microservicereport.repository.ReportRepository;
import ma.fstt.microservicereport.repository.VehiculeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/reports")

public class ReportController {


    @Autowired
    public ExaminaterRepository examinaterRepository;
    @Autowired
    public VehiculeRepository vehiculeRepository;
    @Autowired
    public ReportRepository reportRepository;

    private final AuthService authService;

    public ReportController(ExaminaterRepository examinaterRepository, VehiculeRepository vehiculeRepository, AuthService authService) {
        this.examinaterRepository = examinaterRepository;
        this.vehiculeRepository = vehiculeRepository;
        this.authService = authService;
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    @PostMapping("/add")
    public ResponseEntity<MessageResponse> addReport(@RequestBody AddReportRequest addReportRequest,@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the Authorization header
            String token = extractTokenFromHeader(authorizationHeader);
            if (!authService.isValidExaminaterToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(401, "Not authorized"));
            }
            Vehicule vehicule;
            Examinater examinater;
            //find if vehicule exist
            Optional<Vehicule> examinatedVehicule= vehiculeRepository.findByMatricule(addReportRequest.getMatricule());
            if (!examinatedVehicule.isPresent())
                return ResponseEntity.badRequest().body(new MessageResponse(404, "Vehicule not exist"));
            vehicule = examinatedVehicule.get();

            //find if examinater exist
            Optional<Examinater> examinaterOptional= examinaterRepository.findById(addReportRequest.getExaminaterId());
            if (!examinaterOptional.isPresent())
                return ResponseEntity.badRequest().body(new MessageResponse(404, "Examinater not exist"));
            examinater = examinaterOptional.get();

            //save report
            Report report = new Report(null,
                    addReportRequest.getTitle(),
                    addReportRequest.getDescription(),
                    "sent",
                    vehicule,
                    examinater
            );


           Report savedReport = reportRepository.save(report);

            // add report to vehicule
            List<Report> existantReports = vehicule.getVehicules_reports();
            if(existantReports==null)
                existantReports = new ArrayList<>();
            existantReports.add(savedReport);

            vehicule.setVehicules_reports(existantReports);

            vehiculeRepository.save(vehicule);


            //add report to examinater
            existantReports = examinater.getExaminater_reports();
            if(existantReports==null)
                existantReports = new ArrayList<>();
            existantReports.add(savedReport);
            examinater.setExaminater_reports(existantReports);

            examinaterRepository.save(examinater);


            return ResponseEntity.ok(new MessageResponse(201,"Report sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse(400,"Report doesn't sent "));

        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<Object> getAllReports(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the Authorization header
            String token = extractTokenFromHeader(authorizationHeader);
            if (!authService.isValidEmployeeToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(401, "Not authorized"));
            }
            List<Report> reports = reportRepository.findAll();

            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Erreur interne du serveur
        }
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<Object> getReportWithExaminaterAndVehicule(@PathVariable String reportId,@RequestHeader("Authorization") String authorizationHeader) {
        try {
            // Extract the token from the Authorization header
            String token = extractTokenFromHeader(authorizationHeader);
            if (!authService.isValidEmployeeToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(401, "Not authorized"));
            }
            ReportWithExaminaterAndVehicule reportWithExaminaterAndVehicule = reportRepository.findReportWithExaminaterAndVehicule(reportId);

            if (reportWithExaminaterAndVehicule != null) {
                //update state
                Optional<Report> reportOptional = reportRepository.findById(reportWithExaminaterAndVehicule.getId());
                Report report = reportOptional.get();
                report.setStatus("seen");
                reportRepository.save(report);
                return ResponseEntity.ok(reportWithExaminaterAndVehicule);
            } else {
                return ResponseEntity.status(404).build(); // Resource not found
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build(); // Internal server error
        }
    }

}