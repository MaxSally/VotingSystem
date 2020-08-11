package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import edu.unl.cse.csce361.voting_system.logic.DataLogic;

public class CreateAdminController extends ScreenController {

	@FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword; 

    @FXML
    private CheckBox chkElectionOfficial;
    
    public void createUser(javafx.event.ActionEvent event) throws IOException {
        String username = txtUsername.getText();
        String password = encryptSSN(txtPassword.getText());
        boolean electionOfficial = chkElectionOfficial.selectedProperty().getValue();

        DataLogic.getInstance().registerNewAdmin(username, password, electionOfficial);
        switchScreen(event, "admin_login.fxml");
    }

    public void cancel(javafx.event.ActionEvent event) throws IOException {
        switchScreen(event, "admin_login.fxml");
    }
}
