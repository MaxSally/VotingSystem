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

    private String encryptSSN(String ssn){
        //using MD5 for this project for expediency 
        //actual production code could use a better encryption
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(ssn.getBytes());
        byte[] ssnBytes = messageDigest.digest();
        StringBuilder build = new StringBuilder();
        for(int i=0; i< ssnBytes.length ;i++)
        {
            build.append(Integer.toString((ssnBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return build.toString();
    }


}
