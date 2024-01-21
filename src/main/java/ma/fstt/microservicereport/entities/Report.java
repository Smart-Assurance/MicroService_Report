package ma.fstt.microservicereport.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Document(collection = "reports")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Report {
    @Id
    private String id;
    private String title;
    private String description;
    private String status;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "vehicule_id")
    @DBRef
    private Vehicule vehicule;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "examinater_id")
    @DBRef
    private Examinater examinater;

}
