package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class CreateUserController extends ScreenController {

	@FXML
    private TextView txtUsername;

    @FXML
    private TextView txtSSN;   
    
    public void createUser(){
        String username = txtUsername.getText();
        //TODO encrypt ssn
        String ssn = txtSSN.getText();

        DataLogic.getInstance().createNewUser(username, ssn);
        
        //might add message and sleep for 2 seconds before switching screens

        switchScreen(event, "login.fxml");
    }

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }


}
