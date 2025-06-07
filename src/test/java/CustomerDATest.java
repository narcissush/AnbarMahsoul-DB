import anbar.controller.CustomerController;
import anbar.model.entity.Customer;
import anbar.model.entity.enums.Gender;
import anbar.model.repository.CustomerDA;

import java.time.LocalDate;

public class CustomerDATest {
    public static void main(String[] args) {
        try (CustomerDA customerDA=new CustomerDA()){
            Customer customer=
                    Customer.builder()
                            .id(customerDA.nextId())
                            .nationalId("0080386822")
                            .name("narges")
                            .family("hajizadeh")
                            .gender(Gender.MALE)
                            .birthDate(LocalDate.now())
                            .phoneNumber("09128179120")
                            .username("narci")
                            .password("Narci@123")
                            .build();
            customerDA.save(customer);

        }catch (Exception e){
            e.printStackTrace();
        }

        CustomerController customerController = new CustomerController();


    }
}
