package anbar.model.repository;

import anbar.model.entity.Customer;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

@Log4j2
public class CustomerDA {

    public void saveCustomer(Customer customer) throws IOException {
        CustomerDataFileManager.getManager().addCustomer(customer);
        CustomerDataFileManager.getManager().saveToFile();
        log.info("Customer saved: " + customer);
    }

    public List<Customer> getAllCustomers() throws IOException, ClassNotFoundException {

        return CustomerDataFileManager.getManager().readFromFile();
    }

    public boolean loginUser(String username, String password) throws IOException, ClassNotFoundException {
        List<Customer> customers = CustomerDataFileManager.getManager().readFromFile();
        System.out.println(customers.toString());
        for (Customer customer : customers) {
            if (username.equals(customer.getUsername()) && password.equals(customer.getPassword())) {

                return true;
            }
        }
        return false;
    }

}





