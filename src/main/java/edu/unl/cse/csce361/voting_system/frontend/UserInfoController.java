package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class UserInfoController extends ScreenController {
    @FXML private TextArea txtVoteInfo;
    @FXML private Button btnCancel;
    @FXML private Button btnProceedToVote;

    @FXML
    private void initialize(){
        //THIS IS JUST A SAMPLE

        // String voteInfo = "";
        // Answer<> answers = getUserAnswers();
        //iterate through each answer
        // for(int i = 0; i < answers.length; i++){
        //     voteInfo += getQuestionByAnswer(answers[i].id).getText() + ": \n\t";
        //     voteInfo += answers[i].getText() + " \n\n";
        // }
        // txtVoteInfo.setText(voteInfo);
    }

    public void login(javafx.event.ActionEvent event) throws IOException {
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }

    public void proceed(javafx.event.ActionEvent event) throws IOException {
        //this is not a final name for voting screen
        switchScreen(event, "voting_screen.fxml");
    }






}