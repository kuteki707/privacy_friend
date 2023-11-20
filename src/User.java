import java.util.ArrayList;

public class User  {
    private String username;
    private String password;

    private ArrayList<UserAccount> accounts = new ArrayList<UserAccount>();
    User(String username, String password){
        this.username = username;
        this.password = password;
        accounts = DataStorage.loadAccounts(this);
    }

    public ArrayList<UserAccount> getAccounts(){
        return accounts;
    }
    public void printAccounts(){
        int i = 1;
        for(UserAccount account: accounts){
            OutputDevice.display("********** Account " + i + " **********");
            account.printAccount();
            OutputDevice.display("");
            i++;
        }
    }
    public void addAccount(UserAccount account){
        this.accounts.add(account);
    }
    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
}
