package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class VoteController extends ScreenController{
    @FXML private Pane paneQuestions;
    @FXML private Button btnCancel;
    @FXML private Button btnSubmitVote;

    @FXML
    private void initialize(){
        //foreach question we'll dynamically add a text element and a dropdown (or radio buttons. dropdown might be easier) to the paneQuestions element (it scrolls)
    }

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }
    public void submitVotes(javafx.event.ActionEvent event) throws IOException{
        //cycle through each answer and save each vote into a list (we might want to do this when they change the vote answer, just in case the session messes up.)
        switchScreen(event, "confirm_screen.fxml");
    }
}
