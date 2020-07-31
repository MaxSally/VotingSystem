package edu.unl.cse.csce361.car_rental.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Pane;
import javafx.scene.control.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private Pane paneLogin;
    @FXML private java.awt.TextField txtUsername;
    @FXML private java.awt.TextField txtPassword;
    @FXML private java.awt.Text txtErrorText;

    @FXML private Button btnLogin;


    public void login(javafx.event.ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String pass = txtPassword.getText();

        //use login method
        if(login(userName, pass)){
            txtErrorText.setText("");
            navigate(event, "user_info.fxml");
        }
        else{
            txtErrorText.setText("Something is wrong. Please contact an administrator.");
        }
    }

    public void navigate(javafx.event.ActionEvent event, String screen) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(screen));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }


}