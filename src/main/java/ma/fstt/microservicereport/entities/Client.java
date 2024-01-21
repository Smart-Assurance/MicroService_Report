package ma.fstt.microservicereport.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Document(collection = "users")
@TypeAlias("CLIENT")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Client extends User {

    @Size(max = 200)
    private String add_wallet_cli;

    @NotBlank
    @Size(max = 12)
    private String cin;

    @NotBlank
    private Date date_of_birth;


    @JsonBackReference
    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    @DBRef
    private List<Vehicule> vehicles;

    public Client(String l_name, String f_name, String username, String password, String email,
                  String phone, String city, String address,
                  String add_wallet_cli, String cin, Date date_of_birth) {
        super(null, l_name, f_name, username, password, email, phone, city, address, "CLIENT");
        this.add_wallet_cli = add_wallet_cli;
        this.cin = cin;
        this.date_of_birth = date_of_birth;
    }

    public Client(String l_name, String f_name, String email,
                  String phone, String city, String address,
                  String add_wallet_cli, String cin, Date date_of_birth) {
        super(null, l_name, f_name, l_name+"_"+f_name, date_of_birth.toString(), email, phone, city, address, "CLIENT");
        this.add_wallet_cli = add_wallet_cli;
        this.cin = cin;
        this.date_of_birth = date_of_birth;
    }


}