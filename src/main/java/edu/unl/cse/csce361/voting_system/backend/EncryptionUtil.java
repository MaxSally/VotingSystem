package edu.unl.cse.csce361.voting_system.backend;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {
    public static String encrypt(String encryptedString) {
        //The other duplicate code happens
        String hashedString = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(encryptedString.getBytes());
            byte[] ssnBytes = messageDigest.digest();
            StringBuilder build = new StringBuilder();
            for(int i = 0; i < ssnBytes.length; i++) {
                build.append(Integer.toString((ssnBytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            hashedString = build.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedString;
    }
}
