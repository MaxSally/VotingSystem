package edu.unl.cse.csce361.voting_system.frontend;

//import edu.unl.cse.csce361.voting_system.logic.DataLogic;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController extends ScreenController{
    @FXML private Pane paneLogin;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private Text txtErrorText;
    @FXML private Button btnLogin;


    public void login(javafx.event.ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String pass = txtPassword.getText();

        //use login method
        //Uncomment after DataLogic has been rebased/merged.
//        if(DataLogic.getInstance().logIn(username, pass)){
//            txtErrorText.setText("");
//            switchScreen(event, "user_info.fxml");
//        }
//        else{
//            txtErrorText.setText("Something is wrong. Please contact an administrator.");
//        }
    }
}