package com.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class PasswordUtils {
	
	private static final int SALT_LENGTH = 16; //16 bytes
	private static final int HASH_LENGTH = 256; // 256 bits
    private static final int ITERATIONS = 65536;
    
    public static String generateSalt() throws NoSuchAlgorithmException {
    	SecureRandom random = new SecureRandom(); //crea una instancia de un generador de numeros aleatorios
    	byte [] salt = new byte[SALT_LENGTH]; //crea un array de bytes con la longitud específica del salt
    	random.nextBytes(salt); //llena el array de bytes con valores aleatorios
    	return Base64.getEncoder().encodeToString(salt); //codifica el array de bytes en una cadena Base64 para su almacenamiento y uso
    }
    // Este método genera un hash seguro de la contraseña utilizando el salt 
    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] saltBytes = Base64.getDecoder().decode(salt); //Decodifica el salt Base64 a su representación en bytes.
        KeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, ITERATIONS, HASH_LENGTH); //Crea una especificación de clave con la contraseña, el salt, el número de iteraciones y la longitud del hash.
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256"); //Obtiene una instancia de SecretKeyFactory para el algoritmo PBKDF2WithHmacSHA256.
        byte[] hash = factory.generateSecret(spec).getEncoded(); //Genera el hash utilizando la especificación de clave.
        return Base64.getEncoder().encodeToString(hash); //Codifica el hash en una cadena Base64 para su almacenamiento y uso.
    }
    
    //verifica si una contraseña proporcionada coincide con el hash almacenado utilizando el mismo salt.
    public static boolean verifyPassword(String password, String salt, String hash) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String generatedHash = hashPassword(password, salt); //Genera el hash de la contraseña proporcionada utilizando el salt almacenado.
        return generatedHash.equals(hash); //Compara el hash generado con el hash almacenado para verificar si coinciden.
    }
    
	

}
