package edu.unl.cse.csce361.voting_system.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("edit_election.fxml"));
        primaryStage.setTitle("Voting!");
        primaryStage.setScene(new Scene(root, 400, 600));
        primaryStage.show();
    }
}
