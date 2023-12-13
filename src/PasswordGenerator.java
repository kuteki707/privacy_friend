public interface PasswordGenerator {
    static String generatePassword(int length){
        String password = "";
        for(int i = 0; i < length; i++){
            int random = (int) (Math.random() * 94) + 33;
            password += (char) random;
        }
        return password;
    }
}
