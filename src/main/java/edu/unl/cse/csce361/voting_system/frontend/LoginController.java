package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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

    public void login(javafx.event.ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String ssn = txtPassword.getText();

        if(DataLogic.getInstance().logIn(username, ssn)){
            txtErrorText.setText("");
            switchScreen(event, "user_info.fxml");
        }
        else{
            txtErrorText.setText("Something is wrong. Please contact an administrator.");
        }
    }
}