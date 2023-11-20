import static java.lang.System.exit;

public class Application {

    public void runNoAccount(){
        while (true){
            noAccountMenu();
        }
    }
    public void run() {
        User loggedInUser = null;
        OutputDevice.display("This is your Privacy Friend!");
        if(Authentication.init().equals("y")){

            while (loggedInUser==null){
                loggedInUser = Authentication.Login();
                if(loggedInUser==null)
                    OutputDevice.display("Wrong username or password!");
            }
        } else {
            while(loggedInUser==null) {
                loggedInUser = Authentication.Register();
                if(loggedInUser==null)
                    OutputDevice.display("Something went wrong!");

            }
        }

        while(true){
            menu(loggedInUser);
            DataStorage.saveUsers(DataStorage.getUsers());
            DataStorage.saveAccounts(loggedInUser.getAccounts(), loggedInUser);
        }
    }
    private void menu(User loggedInUser){
        int choice = -1;
        while(true) {
            OutputDevice.display("What do you want to do?");
            OutputDevice.display("1. Hash text");
            OutputDevice.display("2. Hash file");
            OutputDevice.display("3. Encrypt text");
            OutputDevice.display("4. Encrypt file");
            OutputDevice.display("5. Store account");
            OutputDevice.display("6. Show accounts");
            OutputDevice.display("0. Exit");
            try {
                choice = Integer.parseInt(InputDevice.keyboardTextInput());
            }catch (NumberFormatException e){
                OutputDevice.display("Please enter a number!");
            }
            if(choice>=0 && choice<=6){
                break;
            }
        }
        switch (choice){
            case 0:
                OutputDevice.display("Bye!");
                exit(0);
            case 1:
                OutputDevice.display("Choose an algorithm:");
                OutputDevice.display("1. SHA-256");
                OutputDevice.display("2. SHA-512");
                OutputDevice.display("3. MD5");
                int algorithm = -1;
                while(true) {
                    try {
                        algorithm = Integer.parseInt(InputDevice.keyboardTextInput());
                    }catch (NumberFormatException e){
                        OutputDevice.display("Please enter a number!");
                    }
                    if(algorithm>=1 && algorithm<=3){
                        break;
                    }
                }
                OutputDevice.display("Enter text:");
                String text = InputDevice.keyboardTextInput();
                switch (algorithm){
                    case 1:
                        OutputDevice.display(Hasher.hashWithSHA256(text.getBytes()));
                        break;
                    case 2:
                        OutputDevice.display(Hasher.hashWithSHA512(text.getBytes()));
                        break;
                    case 3:
                        OutputDevice.display(Hasher.hashWithMD5(text.getBytes()));
                        break;
                }
                break;
            case 2:
                OutputDevice.display("Choose an algorithm:");
                OutputDevice.display("1. SHA-256");
                OutputDevice.display("2. SHA-512");
                OutputDevice.display("3. MD5");
                algorithm = -1;
                while(true) {
                    try {
                        algorithm = Integer.parseInt(InputDevice.keyboardTextInput());
                    }catch (NumberFormatException e){
                        OutputDevice.display("Please enter a number!");
                    }
                    if(algorithm>=1 && algorithm<=3){
                        break;
                    }
                }
                OutputDevice.display("Enter file path:");
                byte[] fileToHash = InputDevice.fileByteInput(InputDevice.keyboardTextInput());
                switch (algorithm){
                    case 1:
                        OutputDevice.display(Hasher.hashWithSHA256(fileToHash));
                        break;
                    case 2:
                        OutputDevice.display(Hasher.hashWithSHA512(fileToHash));
                        break;
                    case 3:
                        OutputDevice.display(Hasher.hashWithMD5(fileToHash));
                        break;
                }
                break;
            case 3:
                OutputDevice.display("TODO");
                break;
            case 4:
                OutputDevice.display("TODO");
                break;
            case 5:
                UserAccount.addAccountToUser(loggedInUser);
                break;
            case 6:
                loggedInUser.printAccounts();
                break;
        }
    }
    private void noAccountMenu(){
        int choice = -1;
        while(true) {
            OutputDevice.display("What do you want to do?");
            OutputDevice.display("1. Hash text");
            OutputDevice.display("2. Hash file");
            OutputDevice.display("3. Encrypt text");
            OutputDevice.display("4. Encrypt file");
            OutputDevice.display("0. Exit");
            try {
                choice = Integer.parseInt(InputDevice.keyboardTextInput());
            }catch (NumberFormatException e){
                OutputDevice.display("Please enter a number!");
            }
            if(choice>=0 && choice<=4){
                break;
            }
        }
        switch (choice){
            case 0:
                OutputDevice.display("Bye!");
                exit(0);
            case 1:
                OutputDevice.display("Choose an algorithm:");
                OutputDevice.display("1. SHA-256");
                OutputDevice.display("2. SHA-512");
                OutputDevice.display("3. MD5");
                int algorithm = -1;
                while(true) {
                    try {
                        algorithm = Integer.parseInt(InputDevice.keyboardTextInput());
                    }catch (NumberFormatException e){
                        OutputDevice.display("Please enter a number!");
                    }
                    if(algorithm>=1 && algorithm<=3){
                        break;
                    }
                }
                OutputDevice.display("Enter text:");
                String text = InputDevice.keyboardTextInput();
                switch (algorithm){
                    case 1:
                        OutputDevice.display(Hasher.hashWithSHA256(text.getBytes()));
                        break;
                    case 2:
                        OutputDevice.display(Hasher.hashWithSHA512(text.getBytes()));
                        break;
                    case 3:
                        OutputDevice.display(Hasher.hashWithMD5(text.getBytes()));
                        break;
                }
                break;
            case 2:
                OutputDevice.display("Choose an algorithm:");
                OutputDevice.display("1. SHA-256");
                OutputDevice.display("2. SHA-512");
                OutputDevice.display("3. MD5");
                algorithm = -1;
                while(true) {
                    try {
                        algorithm = Integer.parseInt(InputDevice.keyboardTextInput());
                    }catch (NumberFormatException e){
                        OutputDevice.display("Please enter a number!");
                    }
                    if(algorithm>=1 && algorithm<=3){
                        break;
                    }
                }
                OutputDevice.display("Enter file path:");
                byte[] fileToHash = InputDevice.fileByteInput(InputDevice.keyboardTextInput());
                switch (algorithm){
                    case 1:
                        OutputDevice.display(Hasher.hashWithSHA256(fileToHash));
                        break;
                    case 2:
                        OutputDevice.display(Hasher.hashWithSHA512(fileToHash));
                        break;
                    case 3:
                        OutputDevice.display(Hasher.hashWithMD5(fileToHash));
                        break;
                }
                break;
            case 3:
                OutputDevice.display("TODO");
                break;
            case 4:
                OutputDevice.display("TODO");
                break;
        }

    }
}
