import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public interface Hasher {
    public static String hashWithSHA256(byte[] inputBytes){
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            sha256.update(inputBytes);

            byte[] hashBytes = sha256.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static String hashWithSHA512(byte[] inputBytes){
        try {
            MessageDigest sha512 = MessageDigest.getInstance("SHA-512");

            sha512.update(inputBytes);

            byte[] hashBytes = sha512.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static String hashWithMD5(byte[] inputBytes){
        try {
            MessageDigest sha512 = MessageDigest.getInstance("MD5");

            sha512.update(inputBytes);

            byte[] hashBytes = sha512.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
