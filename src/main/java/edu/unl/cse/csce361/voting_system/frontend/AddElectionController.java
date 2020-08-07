package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;

public class AddElectionController extends ScreenController implements Initializable {

	@FXML
    private ListView<String> lstElectionData;

    public void addElection(javafx.event.ActionEvent event) throws IOException{
        //get election data from all procedurally generated form elements and create a new election

        switchScreen(event, "election_screen.fxml");
    }
    
    

    public void cancel(javafx.event.ActionEvent event) throws IOException{

        switchScreen(event, "election_screen.fxml");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
