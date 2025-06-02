package anbar.model.repository;

import anbar.model.entity.Customer;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomerDataFileManager {
    private final String FILE_NAME = "src/main/java/anbar/model/repository/datafile/customer.dat";
    private final File file = new File(FILE_NAME);
    private static List<Customer> customerList;

    @Getter
    private static final CustomerDataFileManager manager = new CustomerDataFileManager();

    private CustomerDataFileManager() {}

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void addCustomer(Customer customer) {
        if (customerList == null) {
            customerList = new ArrayList<>();
        }
        customerList.add(customer);
    }

    public int getNextId() {
        try {
            customerList = readFromFile();
            return customerList.size() + 1;
        } catch (Exception e) {
            log.error("Error getting next customer ID", e);
        }
        return 1;
    }

    public void saveToFile() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(customerList);

        objectOutputStream.close();
        fileOutputStream.close();
        log.info("Customer data saved to file.");
    }

    public List<Customer> readFromFile() throws IOException, ClassNotFoundException {
        if (!file.exists()) {
            customerList = new ArrayList<>();
            log.warn("Customer data file not found. New list created.");
            return customerList;
        }

        FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

        customerList = (ArrayList<Customer>) objectInputStream.readObject();

        objectInputStream.close();
        fileInputStream.close();
        log.info("Customer data read from file.");

        return customerList;
    }
}
