package edu.unl.cse.csce361.voting_system.frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.util.List;

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
