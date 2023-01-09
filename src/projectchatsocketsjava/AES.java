package projectchatsocketsjava;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

/**
 *
 * @author asus
 */
public class AES {
    KeyGenerator keygenerator;
	public static SecretKey secretkey;
	static Cipher cipher;
	private static Base64 base64; 
        static SecureRandom rnd = new SecureRandom();
        static IvParameterSpec iv = new IvParameterSpec(rnd.generateSeed(16));
        public AES() throws Exception {
	    
	    this.keygenerator = KeyGenerator.getInstance("AES");
             keygenerator.init(128);
	 
	    this.secretkey = keygenerator.generateKey();
		
		this.cipher = Cipher.getInstance("AES");
		
	}
        public static SecretKey getKey() {
		return secretkey;
	}
        public static String encryption(String inputText, SecretKey secretkey) throws Exception {
		
                System.out.println("\nStart encryption:");
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    
		    cipher.init(Cipher.ENCRYPT_MODE, secretkey,iv);
		   
		
		    byte[] encryptedmessage = cipher.doFinal(inputText.getBytes());
		  
		    return encode(encryptedmessage);
        }
        private  static String encode(byte[] data){
            return Base64.getEncoder().encodeToString(data);
        }
        
        public static String decryption(String encrypted, SecretKey secretkey) throws Exception {
		byte[] encryptedData = Base64.getDecoder().decode(encrypted);
		
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			
		
                
	    cipher.init(Cipher.DECRYPT_MODE, secretkey,iv);

	  
	    byte[] decrypted = cipher.doFinal(encryptedData);
            
		return new String(decrypted);
		
	}
        public static void main(String[] args) throws Exception {
	  AES encryption = new AES();
	  encryption.getKey();
	  System.out.println("KEY : " + encryption.getKey());
	  String line = "RA";
	  String x = encryption.encryption(line,encryption.secretkey);
	  String y = encryption.decryption(x,encryption.secretkey);
	  
	  System.out.println("crypté : "+ x);
	  System.out.println("décrypté : "+ y);
  }

       
}