package anbar.controller;

import anbar.model.entity.Customer;
import anbar.model.entity.enums.Gender;
import anbar.model.entity.enums.Os;
import anbar.model.repository.CustomerDataAccess;
import anbar.model.repository.CustomerDataFileManager;
import anbar.model.repository.ProductDataFileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

@Log4j2
public class CustomerController implements Initializable {

    @FXML private TextField customerIdTxt,nationalIdTxt, nameTxt, familyTxt, mobileTxt, userTxt, passwordTxt;
    @FXML private RadioButton womenRbtn, menRbtn;
    @FXML private ToggleGroup genderGroup;
    @FXML private DatePicker birthDatePick;

    @FXML private Button saveBtn,backBtn;

    private final CustomerDataAccess customerDataAccess = new CustomerDataAccess();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetForm();

        saveBtn.setOnAction(event -> {
            try {
                Customer customer = new Customer();
                customerDataAccess.saveCustomer(getCustomerFrom());
                log.info("Customer Saved: {}", customer);
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved", ButtonType.OK).show();
                resetForm();
            } catch (Exception e) {
                log.error("Error Saving Customer: {}", e.getMessage());
                new Alert(Alert.AlertType.ERROR, "Error Saving Customer", ButtonType.OK).show();
            }
        });
        backBtn.setOnAction(event -> {
            //
        });




    }

    private Customer getCustomerFrom() {
        Gender genderRdb = menRbtn.isSelected() ? Gender.MALE : Gender.FEMALE;
        Customer customer=
                Customer.builder()
                .customerId(Integer.parseInt(customerIdTxt.getText()))
                .name(nameTxt.getText())
                .family(familyTxt.getText())
                .nationalId(nationalIdTxt.getText())
                .gender(genderRdb)
                .birthDate(birthDatePick.getValue())
                .mobile(mobileTxt.getText())
                .username(userTxt.getText())
                .password(passwordTxt.getText())
                .build();
        return customer;

    }
    private void resetForm() {
        customerIdTxt.setText(String.valueOf(CustomerDataFileManager.getManager().getNextId()));
        nameTxt.clear();
        familyTxt.clear();
        mobileTxt.clear();
        userTxt.clear();
        passwordTxt.clear();
        birthDatePick.setValue(LocalDate.now());
        womenRbtn.setSelected(true);

       }
}

