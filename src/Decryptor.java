import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public interface Decryptor {
    static byte[] decryptAES(byte[] encryptedData, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(encryptedData);
        }catch (Exception e){
            OutputDevice.display("Wrong key!");
            return null;
        }
    }
    static byte[] decryptAESNoOutput(byte[] encryptedData, byte[] key) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return cipher.doFinal(encryptedData);
        }catch (Exception e){
            return null;
        }
    }
}
