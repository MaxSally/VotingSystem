package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import edu.unl.cse.csce361.voting_system.logic.DataLogic;

public class EditElectionController extends ScreenController implements Initializable{

	@FXML
    private ListView<String> lstElectionData;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
        //initially create a single question in a pane (textView)
        //below the question, in the same pane is a list of answers (one answer by default) with a plus button that adds a new TextView element that accepts an answer

        //at the end, we'll need a button that adds a new question to the listview. may want to make the question pane a component.
        //button to remove question in the pane (small x, link?)

        //this list will initially be populated by the current election

    }

    public void submitElectionEdit(javafx.event.ActionEvent event) throws IOException{
        //get information from dynamically created form elements
        //submit that information
        switchScreen(event, "election_screen.fxml");
    }    

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //TODO unassign selected election
        switchScreen(event, "election_screen.fxml");
    }


}
