package edu.unl.cse.csce361.voting_system.frontend;

import java.io.IOException;

public class ThankYouController extends ScreenController{

	public void logout(javafx.event.ActionEvent event) throws IOException {
        //logout method
        //logout();
        switchScreen(event, "login.fxml");
    }
}
