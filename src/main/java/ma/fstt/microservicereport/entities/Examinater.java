package ma.fstt.microservicereport.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@TypeAlias("EXAMINATER")
@Getter
@Setter
public class Examinater extends User {
    @NotBlank
    @Size(max = 12)
    private String cin;
    @NotBlank
    private Date date_of_birth;

    @JsonBackReference
    @OneToMany(mappedBy = "examinater")
    @DBRef
    private List<Report> examinater_reports;

    public Examinater(String l_name, String f_name, String username, String password, String email,
                      String phone, String city, String address, String cin, Date date_of_birth) {
        super(null, l_name, f_name, username, password, email, phone, city, address, "EXAMINATER");
        this.cin = cin;
        this.date_of_birth = date_of_birth;
    }
}
