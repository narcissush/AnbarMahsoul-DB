package anbar.model.repository;

import anbar.model.entity.Customer;
import anbar.model.entity.enums.Gender;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class CustomerDA implements AutoCloseable {
    private Connection connection;
    private PreparedStatement preparedStatement;

    public CustomerDA() throws SQLException {
        log.info("Connection DB Connected");
        connection = ConnectionProvider.getConnectionProvider().getconnection();
    }

    public int nextId() throws SQLException {
        preparedStatement = connection.prepareStatement("select product_seq.nextval from DUAL");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt("nextval");
    }

    public void save(Customer customer) throws SQLException {
        preparedStatement = connection.prepareStatement("insert into customer values(?,?,?,?,?,?,?,?,?)");
        preparedStatement.setInt(1, customer.getId());
        preparedStatement.setString(2, customer.getNationalId());
        preparedStatement.setString(3, customer.getName());
        preparedStatement.setString(4, customer.getFamily());
        preparedStatement.setString(5, customer.getGender().name());
        preparedStatement.setDate(6, Date.valueOf(customer.getBirthDate()));
        preparedStatement.setString(7, customer.getPhoneNumber());
        preparedStatement.setString(8, customer.getUsername());
        preparedStatement.setString(9, customer.getPassword());
        preparedStatement.execute();
        log.info("Customer saved: " + customer);
    }

    public void edit(Customer customer, int id) throws SQLException {
        preparedStatement = connection.prepareStatement("update customer set NATIONALID=?,NAME=?,FAMILY=?,GENDER=?,BIRTH_DATE=?,PHONE_NUMBER=?,USERNAME=?,PASSWORD=? where id = ?");
        preparedStatement.setString(1, customer.getNationalId());
        preparedStatement.setString(2, customer.getName());
        preparedStatement.setString(3, customer.getFamily());
        preparedStatement.setString(4, customer.getGender().name());
        preparedStatement.setDate(5, Date.valueOf(customer.getBirthDate()));
        preparedStatement.setString(6, customer.getPhoneNumber());
        preparedStatement.setString(7, customer.getUsername());
        preparedStatement.setString(8, customer.getPassword());
        preparedStatement.setInt(9, id);

        preparedStatement.execute();
        log.info("Customer update: " + customer);
    }

    public void remove(int i) throws SQLException {
        preparedStatement = connection.prepareStatement("delete from customer where id = ?");
        preparedStatement.setInt(1, i);
        preparedStatement.execute();

    }


    public List<Customer> getAllCustomers() throws SQLException {
        preparedStatement = connection.prepareStatement("select * from CUSTOMER");
        List<Customer> customerList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Customer customer = new Customer();
            customer.setId(resultSet.getInt("id"));
            customer.setNationalId(resultSet.getString("national_id"));
            customer.setName(resultSet.getString("name"));
            customer.setFamily(resultSet.getString("family"));
            customer.setGender(Gender.valueOf(resultSet.getString("gender")));
            customer.setBirthDate(resultSet.getDate("birthdate").toLocalDate());
            customer.setPhoneNumber(resultSet.getString("mobile"));
            customer.setUsername(resultSet.getString("username"));
            customer.setPassword(resultSet.getString("password"));
            customerList.add(customer);
        }
        return customerList;

    }

    public boolean userLogin(String username, String password) throws SQLException {
        preparedStatement = connection.prepareStatement("select * from customer where username=? and password=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public void close() throws Exception {
        log.info("Connection DB Closed");
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        connection.close();
    }
}








