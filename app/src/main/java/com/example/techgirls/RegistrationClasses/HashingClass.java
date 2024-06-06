package com.example.techgirls.RegistrationClasses;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashingClass {
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException{
        MessageDigest md=MessageDigest.getInstance("SHA-256");

        // digest() method called to calculate message digest of an input and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    private static String toHexString(byte[] hash){
        // Convert byte array into signum representation
        BigInteger number=new BigInteger(1,hash);

        // Convert message digest into hex value
        StringBuilder hexString=new StringBuilder(number.toString(16));
        while(hexString.length()<64){
            hexString.insert(0,'0');
        }
        return hexString.toString();
    }
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        return toHexString(getSHA(password));
    }

    public static boolean verifyPassword(String inputPassword, String storedHash) throws NoSuchAlgorithmException {
        String hashedInputPassword = hashPassword(inputPassword);
        // Compare the hashed input password with the stored hash
        return hashedInputPassword.equals(storedHash);
    }
}
