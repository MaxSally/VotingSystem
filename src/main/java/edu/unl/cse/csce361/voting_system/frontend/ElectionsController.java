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

	@FXML
    private ComboBox comboElections;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        // temporary array for testing.
        List<String> answers = new ArrayList<String>();
        answers.add("a_A");
        answers.add("a_B");
        answers.add("a_C");
        Map<String,List<String>> election = new HashMap<String,List<String>>();
        election.put("q_1", answers);
        election.put("q_2", answers);
        election.put("q_3", answers);
        Map<String, Map<String,List<String>>> elections = new HashMap<String, Map<String,List<String>>>();
        elections.put("e_1", election);
        elections.put("e_2", election);
        elections.put("e_3", election);
        ///////////////////////DELETE THE ABOVE CODE!!!//////////////////////////////////////


        // foreach election add election name to lstElections
        // give each one a click event (edit election method) that assigns the election we're editing to a local variable and opens a view that pulls up that information.
        for(Map.Entry<String, Map<String,List<String>>> elec: elections.entrySet()){
            comboElections.getItems().add(elec.getKey());
        }
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
