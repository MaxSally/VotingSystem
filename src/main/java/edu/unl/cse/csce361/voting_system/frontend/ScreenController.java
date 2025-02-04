package edu.unl.cse.csce361.voting_system.frontend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javafx.stage.Stage;
import java.io.IOException;

public abstract class ScreenController {
    public void switchScreen(javafx.event.ActionEvent event, String screen) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(screen));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public String encrypt(String rawInfo) {
        //using MD5 for this project for expediency 
        //actual production code could use a better encryption
        String hashed = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(rawInfo.getBytes());
            byte[] ssnBytes = messageDigest.digest();
            StringBuilder build = new StringBuilder();
            for(int i = 0; i < ssnBytes.length; i++) {
                build.append(Integer.toString((ssnBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashed = build.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashed;
    }
}
