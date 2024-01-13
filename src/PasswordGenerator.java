import java.util.ArrayList;
import java.util.List;

public class PasswordGenerator {

    public static String generatePassword(int length) {
        List<Thread> threads = new ArrayList<>();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            Thread thread = new Thread(new CharacterGenerator(password));
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join(); // Wait for each thread to finish
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return password.toString();
    }

    private static class CharacterGenerator implements Runnable {

        private StringBuilder password;

        public CharacterGenerator(StringBuilder password) {
            this.password = password;
        }

        @Override
        public void run() {
            int random = (int) (Math.random() * 94) + 33;
            synchronized (password) {
                password.append((char) random);
            }
        }
    }
}