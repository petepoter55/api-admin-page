package com.apiadminpage.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilityTools {
    public static boolean checkPassphrases(String phrases, String pass)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean status = true;

        String passwordRequest = hashSha256(pass);
        if (!passwordRequest.equals(phrases)) {
            status = false;
        }
        return status;
    }

    public static boolean checkOldPassword(String oldPass, String pass)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean status = true;

        String passwordRequest = hashSha256(pass);
        String passwordDatabase = hashSha256(oldPass);
        if (!passwordRequest.equals(passwordDatabase)) {
            status = false;
        }
        return status;
    }

    public static String hashSha256(String msg)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        return String.format("%064x", new java.math.BigInteger(1, digest));
    }
}
