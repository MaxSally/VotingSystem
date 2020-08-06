package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ElectionsController extends ScreenController implements Initializable{

	@FXML
    private ListView<String> lstElections;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
        //foreach election add election name to lstElections
        //give each one a click event (edit election method) that assigns the election we're editing to a local variable and opens a view that pulls up that information.


    }

    public void editElection(){
        //current election equals the argument election
        //open edit_election.fxml
    }

    public void goToAdd(javafx.event.ActionEvent event) throws IOException{
        switchScreen(event, "add_election.fxml");
    }
    

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }


}
