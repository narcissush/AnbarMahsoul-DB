package anbar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;

@Log4j2

public class AppForm extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {

            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml")));
            primaryStage.setTitle("anbar.AppForm");
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            log.error(e);
        }
    }


}
