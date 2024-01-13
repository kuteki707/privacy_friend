import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    @Test
    public void testConstructor() {
        User user = new User("testUser", "testPassword");
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
        assertEquals(0, user.getAccounts().size());
    }

    @Test
    public void testAddAccount() {
        User user = new User("testUser", "testPassword");
        UserAccount account = new UserAccount("TestAccount", "TestUsername", "TestPassword", "test@email.com", "http://example.com");

        user.addAccount(account);

        assertEquals(1, user.getAccounts().size());
        assertEquals(account, user.getAccounts().getFirst());
    }

    @Test
    public void testGetUsername() {
        User user = new User("testUser", "testPassword");

        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testGetPassword() {
        User user = new User("testUser", "testPassword");

        assertEquals("testPassword", user.getPassword());
    }

}
