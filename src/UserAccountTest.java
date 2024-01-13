import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class UserAccountTest {

    @Test
    public void testConstructor() {
        UserAccount account = new UserAccount("TestAccount", "TestUsername", "TestPassword", "test@email.com", "http://example.com");

        assertEquals("TestAccount", account.getAccountName());
        assertEquals("TestUsername", account.getUsername());
        assertEquals("TestPassword", account.getPassword());
        assertEquals("test@email.com", account.getEmail());
        assertEquals("http://example.com", account.getWebsite());
    }
    @Test
    public void testPrintAccount() {
        UserAccount account = new UserAccount("TestAccount", "TestUsername", "TestPassword", "test@email.com", "http://example.com");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        account.printAccount();

        System.setOut(System.out);

        String expectedOutput = "Account name: TestAccount\nUsername: TestUsername\nPassword: TestPassword\nEmail: test@email.com\nWebsite: http://example.com\n";
        assertEquals(expectedOutput, outputStream.toString());
    }

    @Test
    public void testGetAccountName() {
        UserAccount account = new UserAccount("TestAccount", "TestUsername", "TestPassword", "  ", "  ");

        assertEquals("TestAccount", account.getAccountName());
    }

    @Test
    public void testGetUsername() {
        UserAccount account = new UserAccount("TestAccount", "TestUsername", "TestPassword", "  ", "  ");

        assertEquals("TestUsername", account.getUsername());
    }
}
