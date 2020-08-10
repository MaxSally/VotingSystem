package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import java.io.IOException;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;

public class AdminLoginController extends ScreenController {
    
    @FXML 
    private TextField txtUsername;
    
    @FXML 
    private TextField txtPassword;
    
    @FXML 
    private Text txtErrorText;

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        switchScreen(event, "login.fxml");
    }

    public void login(javafx.event.ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String ssn = txtPassword.getText();

        if(DataLogic.getInstance().adminLogIn(username, ssn)){

            if(DataLogic.getInstance().isElectionOfficial()){
                switchScreen(event, "election_screen.fxml");
            }
            else{
                switchScreen(event, "auditor.fxml");
            }
            txtErrorText.setText("");
        }
        else{
            txtErrorText.setText("Error. Username or Password must be wrong.");
        }
    }
}