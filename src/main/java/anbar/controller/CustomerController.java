package anbar.controller;

import anbar.controller.exception.InvalidPersonDataException;
import anbar.model.entity.Customer;
import anbar.model.entity.enums.Gender;
import anbar.model.repository.CustomerDA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
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
    @FXML
    private ImageView validNationalidImg,invalidNationalidImg,validNameImg,invalidNameImg,validFamilyImg,invalidFamilyImg,validUsernameImg,invalidUsernameImg,validPasswordImg,invalidPasswordImg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resetForm();

        saveBtn.setOnAction(event -> {
            if (isValidName(nameTxt.getText()) &
                    isValidFamily(familyTxt.getText()) &
                    isValidNationalId(nationalIdTxt.getText()) &
                    isValidUsername(usernameTxt.getText()) &
                    isValidPassword(passwordTxt.getText())) {

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
            } else {
                log.error("Invalid Data Input Error");
            }
        });
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

        validPasswordImg.setVisible(false);
        invalidPasswordImg.setVisible(false);
        validNationalidImg.setVisible(false);
        invalidNationalidImg.setVisible(false);
        validNameImg.setVisible(false);
        invalidNameImg.setVisible(false);
        validFamilyImg.setVisible(false);
        invalidFamilyImg.setVisible(false);
        validUsernameImg.setVisible(false);
        invalidUsernameImg.setVisible(false);



    }
    private boolean isValidName(String name) {
            if (Pattern.matches("^[a-zA-Z\\s]{3,30}$", name)) {
                validNameImg.setVisible(true);
                invalidNameImg.setVisible(false);
                return true;
            }else {
                validNameImg.setVisible(false);
                invalidNameImg.setVisible(true);
                return false;
            }
        }

    private boolean isValidFamily(String family) {
            if (Pattern.matches("^[a-zA-Z\\s]{3,30}$", family))
        {
            validFamilyImg.setVisible(true);
            invalidFamilyImg.setVisible(false);
            return true;
        }else {
            validFamilyImg.setVisible(false);
            invalidFamilyImg.setVisible(true);
            return false;
        }
        }

    private boolean isValidNationalId(String nationalId) {
            if (Pattern.matches("^\\d{10}$", nationalId))
            {
               validNationalidImg.setVisible(true);
                invalidNationalidImg.setVisible(false);
                return true;
            }else {
                validNationalidImg.setVisible(false);
                invalidNationalidImg.setVisible(true);
                return false;
            }
    }

    private boolean isValidUsername(String username) {
            if (Pattern.matches("^[a-zA-Z][a-zA-Z0-9_.]{4,19}$", username))
            {
                validUsernameImg.setVisible(true);
                invalidUsernameImg.setVisible(false);
                return true;
            }else {
                validUsernameImg.setVisible(false);
                invalidUsernameImg.setVisible(true);
                return false;
            }
        }

    private boolean isValidPassword(String password) {
            if (Pattern.matches("^(?=.*[a-zA-Z]a)(?=.*\\d)(?=.*[#$%@&]).{6,20}$", password))
            {
                validPasswordImg.setVisible(true);
                invalidPasswordImg.setVisible(false);
                return true;
            }else {
                validPasswordImg.setVisible(false);
                invalidPasswordImg.setVisible(true);
                return false;
            }
        }

}
