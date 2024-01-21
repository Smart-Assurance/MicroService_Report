package ma.fstt.microservicereport.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Document(collection = "contracts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contract {
    @Id
    private String id;
    private String name;
    private double price;
    private String[] options;
    private String description;
    private boolean active;
    @JsonBackReference
    @OneToMany(mappedBy = "contract")
    @DBRef
    private List<Vehicule> assured_vehicules;

    public Contract(String name, double price, String[] options, String description, boolean active) {
        this.name = name;
        this.price = price;
        this.options = options;
        this.description = description;
        this.active = active;
    }

}
