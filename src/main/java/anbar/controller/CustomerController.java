package anbar.controller;

import anbar.controller.exception.InvalidPersonDataException;
import anbar.model.entity.Customer;
import anbar.model.entity.enums.Gender;
import anbar.model.repository.CustomerDA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Log4j2
public class CustomerController implements Initializable {

    @FXML
    private TextField idTxt, nationalIdTxt, nameTxt, familyTxt, phoneNumberTxt, usernameTxt, passwordTxt;
    @FXML
    private RadioButton womenRbtn, menRbtn;
    @FXML
    private ToggleGroup genderGroup;
    @FXML
    private DatePicker birthDatePick;

    @FXML
    private Button saveBtn, backBtn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resetForm();

        saveBtn.setOnAction(event -> {
            if (checkInputPassword(usernameTxt.getText()) && checkInputUsername(passwordTxt.getText())) {
                try (CustomerDA customerDA = new CustomerDA()) {
                    Gender genderRdb = menRbtn.isSelected() ? Gender.MALE : Gender.FEMALE;
                    Customer customer =
                            Customer.builder()
                                    .id(customerDA.nextId())
                                    .name(nameTxt.getText())
                                    .family(familyTxt.getText())
                                    .nationalId(nationalIdTxt.getText())
                                    .gender(genderRdb)
                                    .birthDate(birthDatePick.getValue())
                                    .phoneNumber(phoneNumberTxt.getText())
                                    .username(usernameTxt.getText())
                                    .password(passwordTxt.getText())
                                    .build();
                    customerDA.save(customer);
                    resetForm();
                } catch (Exception e) {
                    log.error("Error Saving Customer: {}", e.getMessage());
                    new Alert(Alert.AlertType.ERROR, "Error Saving Customer", ButtonType.OK).show();
                }
            }else log.info("Invalid Username or Password");
        });


    }

public boolean checkInputUsername(String username) {
    if(Pattern.matches("^[a-zA-Z0-9_]{4,20}$", username)) {
        return true;
    }else{
        return false;
    }
}
    public boolean checkInputPassword(String Password)  {
        if(Pattern.matches("^[a-zA-Z0-9@#$]{6,20}$", Password)) {
            return true;
        }else{
            return false;
        }
    }
    private void resetForm() {
        idTxt.clear();
        nameTxt.clear();
        familyTxt.clear();
        phoneNumberTxt.clear();
        usernameTxt.clear();
        passwordTxt.clear();
        birthDatePick.setValue(LocalDate.now());
        womenRbtn.setSelected(true);

    }
}

