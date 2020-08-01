package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ConfirmController extends ScreenController{
    @FXML private Pane paneQuestions;
    @FXML private Button btnCancel;
    @FXML private Button btnSubmitVote;
    @FXML private Button btnGoBack;

    @FXML
    private void initialize(){
        //we'll dynamically add each question with answers the paneQuestions element (it scrolls)
    }

    public void goBack(javafx.event.ActionEvent event) throws IOException{
        //all we should have to do here is navigate them back to the voting screen.
        switchScreen(event, "voting_screen.fxml");
    }

    public void cancel(javafx.event.ActionEvent event) throws IOException{
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }
    public void submitVotes(javafx.event.ActionEvent event) throws IOException{
        //cycle through each answer and submit vote with *submitVote()? method
        switchScreen(event, "thank_you.fxml");
    }


}
