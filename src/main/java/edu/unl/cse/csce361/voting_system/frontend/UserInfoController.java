package edu.unl.cse.csce361.car_rental.frontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class UserInfoController {
    @FXML private java.awt.TextArea txtVoteInfo;
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
        navigate(event, "login.fxml");
    }

    public void proceed(javafx.event.ActionEvent event) throws IOException {
        //this is not a final name for voting screen
        navigate(event, "voting_screen.fxml");
    }



    public void navigate(javafx.event.ActionEvent event, String screen) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(screen));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }


}