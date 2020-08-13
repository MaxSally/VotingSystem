package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;

public class LoginController extends ScreenController {
    
    @FXML 
    private TextField txtUsername;
    
    @FXML 
    private TextField txtPassword;
    
    @FXML 
    private Text txtErrorText;

    public void createUser(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "create_user.fxml");
    }

    public void adminLogin(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "admin_login.fxml");
    }

    public void login(javafx.event.ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String ssn = encrypt(txtPassword.getText());

        if(!DataLogic.getInstance().isCurrentElectionActive()) {
        	txtErrorText.setText("Currently, there's no election going on.");
        	return;
        }
        
        if(DataLogic.getInstance().logIn(username, ssn)) {
            if(DataLogic.getInstance().checkIfVoted()){
                switchScreen(event, "user_info.fxml");
            }
            else {
                switchScreen(event, "voting_screen.fxml");
            }
            txtErrorText.setText("");
            DataLogic.getInstance().setCurrentVoter(ssn);
        }
        else {
            txtErrorText.setText("Error. Name or SSN must be wrong.");
        }
    }
}