package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CreateUserController extends ScreenController {

	@FXML
    private TextField txtUsername;

    @FXML
    private TextField txtSSN;   
    
    public void createUser(javafx.event.ActionEvent event) throws IOException{
        String username = txtUsername.getText();
        String ssn = encryptSSN(txtSSN.getText());

        DataLogic.getInstance().registerNewVoter(username, ssn);
        switchScreen(event, "login.fxml");
    }

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        switchScreen(event, "login.fxml");
    }
}
