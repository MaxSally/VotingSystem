package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javafx.scene.layout.HBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import edu.unl.cse.csce361.voting_system.logic.QuestionAnswer;

public class CreateUserController extends ScreenController {

	@FXML
    private TextView txtUsername;

    @FXML
    private TextView txtSSN;   
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
    }
    
    public void createUser(){
        String username = txtUsername.getText();
        //TODO encrypt ssn
        String ssn = txtSSN.getText();

        DataLogic.getInstance().createNewUser(username, ssn);
    }

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }


}
