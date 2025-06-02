package anbar.model.entity;
import anbar.model.entity.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@SuperBuilder
public class Customer implements java.io.Serializable {
    private int customerId;
    private String nationalId;
    private String name;
    private String family;
    private Gender gender;
    private LocalDate birthDate;
    private String mobile;
    private String username;
    private String password;

}
