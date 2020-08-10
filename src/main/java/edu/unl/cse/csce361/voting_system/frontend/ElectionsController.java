package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.util.ResourceBundle;

public class ElectionsController extends ScreenController implements Initializable{


	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        // temporary array for testing.
        
    }

    public void editElection(javafx.event.ActionEvent event) throws IOException{
        //current election equals the argument election
        //open edit_election.fxml
        //if(comboElections.getValue() != null || comboElections.getValue() != ""){DataLogic.getInstance.setEditElection(comboElections.getValue);switchScreen(event, "edit_election.fxml");}
        
    }

    public void goToAdd(javafx.event.ActionEvent event) throws IOException{
        switchScreen(event, "add_election.fxml");
    }

    public void goToAudit(javafx.event.ActionEvent event) throws IOException{
        switchScreen(event, "auditor.fxml");
    }
    

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }


}
