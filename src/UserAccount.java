public class UserAccount {
    private String username;
    private String password;

    private String accountName;
    private String email;
    private String website;
    UserAccount( String accountName, String username, String password, String email, String website){
        this.username = username;
        this.password = password;
        this.accountName = accountName;
        this.email = email;
        this.website = website;
    }

    public static void addAccountToUser(User user){
        OutputDevice.display("Enter account name:");
        String accountName = InputDevice.keyboardTextInput();
        OutputDevice.display("Enter username:(leave blank if none)");
        String username = InputDevice.keyboardTextInput();
        OutputDevice.display("Enter password:");
        String password = InputDevice.keyboardTextInput();
        OutputDevice.display("Enter email: (leave blank if none)");
        String email = InputDevice.keyboardTextInput();
        OutputDevice.display("Enter website: (leave blank if none)");
        String website = InputDevice.keyboardTextInput();
        UserAccount account = new UserAccount(accountName, username, password, email, website);
        user.addAccount(account);
    }

    public void printAccount(){
        OutputDevice.display("Account name: " + accountName);
        if(!username.isEmpty())
            OutputDevice.display("Username: " + username);
        OutputDevice.display("Password: " + password);
        if(!email.isEmpty())
            OutputDevice.display("Email: " + email);
        if(!website.isEmpty())
            OutputDevice.display("Website: " + website);
    }

    public String getAccountName(){
        return accountName;
    }
    public String getEmail(){
        return email;
    }
    public String getWebsite(){
        return website;
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
}
