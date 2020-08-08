package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditElectionController extends ScreenController implements Initializable {

	@FXML
    private ListView<String> lstElectionData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        ///////////////////////DELETE THE ABOVE CODE WHEN DONE TESTING OR HAVE LOGIC METHOD!!!//////////////////////////////////////


        //initially create a single question in a pane (textView)
        //below the question, in the same pane is a list of answers (one answer by default) with a plus button that adds a new TextView element that accepts an answer

        //at the end, we'll need a button that adds a new question to the listview. may want to make the question pane a component.
        //button to remove question in the pane (small x, link?)

        //this list will initially be populated by the current election
    }

    public void submitElectionEdit(javafx.event.ActionEvent event) throws IOException{
        //get information from dynamically created form elements
        //submit that information
        switchScreen(event, "elections_screen.fxml");
    }    

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //TODO unassign selected election
        switchScreen(event, "elections_screen.fxml");
    }



}
