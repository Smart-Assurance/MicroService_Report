package ma.fstt.microservicereport.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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

import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Document(collection = "vehicules")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vehicule {
    @Id
    private String id;
    private String type;
    private String matricule;
    private double price;
    private String grey_card_number;
    private String model;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "client_id")
    @DBRef
    private Client owner;


    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "contract_id")
    @DBRef
    private Contract contract;

    @JsonBackReference
    @OneToMany(mappedBy = "examinater")
    @DBRef
    private List<Report> vehicules_reports;

    public Vehicule(String type, String matricule, double price, String greyCardNumber, String model, Client owner) {
        this.type = type;
        this.matricule = matricule;
        this.price = price;
        this.grey_card_number = greyCardNumber;
        this.model = model;
        this.owner = owner;
    }

    public Vehicule(String type, String matricule, double price, String greyCardNumber, String model, Client owner, Contract contract) {
        this.type = type;
        this.matricule = matricule;
        this.price = price;
        this.grey_card_number = greyCardNumber;
        this.model = model;
        this.owner = owner;
        this.contract = contract;
    }
}
