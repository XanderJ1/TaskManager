package com.bash.taskmanager.Utilities;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * Generates key pairs using RSA Algorithm.
 */
public class KeyGeneratorUtility {
    public static KeyPair keyGeneratorPair(){

        KeyPair keyPair;

        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }catch (Exception e){
            throw new IllegalStateException();
        }
        return keyPair;
    }
}