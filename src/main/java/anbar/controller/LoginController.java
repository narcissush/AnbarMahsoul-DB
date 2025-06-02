package anbar.controller;

import anbar.model.repository.CustomerDataAccess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

import java.net.URL;
import java.util.ResourceBundle;
@Log4j2

public class LoginController implements Initializable {
    @FXML
    private TextField logInUserTxt,logInPasswordTxt;
    @FXML
    private Button signInBtn, signOutBtn;
    private CustomerDataAccess customerDataAccess = new CustomerDataAccess();
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        signInBtn.setOnAction(e -> {
            try {
                boolean success = customerDataAccess.loginUser(logInUserTxt.getText(), logInPasswordTxt.getText());
                if (success) {
                    Stage productStage = new Stage();
                    Scene productScene = new Scene(FXMLLoader.load(getClass().getResource("/view/ProductForm.fxml")));
                    productStage.setTitle("Products");
                    productStage.setScene(productScene);
                    productStage.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Invalid username or password!", ButtonType.OK).show();
                }
            } catch (Exception e1) {
                log.error("Login error: ", e1);
                new Alert(Alert.AlertType.ERROR, "An error occurred during login", ButtonType.OK).show();
            }
        });
        signOutBtn.setOnAction(e -> {
            try {
                Stage registerStage = new Stage();
                Scene registerScene = new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml")));
                registerStage.setTitle("Register Customer");
                registerStage.setScene(registerScene);
                registerStage.show();


            }catch (Exception e1) {
                log.error(e1.getMessage());
            }
        });

    }
}
