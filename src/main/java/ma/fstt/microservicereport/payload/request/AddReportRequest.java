package ma.fstt.microservicereport.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class AddReportRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String description;

    @NotBlank
    private String matricule;
    @NotBlank
    private String examinaterId;
}