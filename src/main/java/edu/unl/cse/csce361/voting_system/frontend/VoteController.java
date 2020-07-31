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
