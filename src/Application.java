import java.util.Base64;

import static java.lang.System.exit;

public class Application {

    public void runNoAccount(){
        while (true){
            noAccountMenu();
        }
    }

    public void runTest() {
        int i;
        i = Integer.parseInt(InputDevice.keyboardTextInput());
        if(i==1){
            byte[] file = InputDevice.fileByteInput("readme.md");
            byte[] key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
            byte[] encrypted_file = Encryptor.encryptAES(file, key);
            OutputDevice.createFileFromBytes(encrypted_file, "readme.md.enc");
        }else{
            byte[] encrypted_file = InputDevice.fileByteInput("readme.md.enc");
            byte[] key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
            byte[] decrypted_file = Decryptor.decryptAES(encrypted_file, key);
            OutputDevice.createFileFromBytes(decrypted_file, "readme.md.dec");
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
            OutputDevice.display("Press enter to show menu");
            InputDevice.keyboardTextInput();
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
            OutputDevice.display("4. Decrypt text");
            OutputDevice.display("5. Encrypt file");
            OutputDevice.display("6. Decrypt file");
            OutputDevice.display("7. Create new account entry");
            OutputDevice.display("8. Show accounts");
            OutputDevice.display("0. Exit");
            try {
                choice = Integer.parseInt(InputDevice.keyboardTextInput());
            }catch (NumberFormatException e){
                OutputDevice.display("Please enter a number!");
            }
            if(choice>=0 && choice<=8){
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
                OutputDevice.display("Enter text to encrypt using AES256:");
                byte[] message = InputDevice.keyboardByteInput();
                OutputDevice.display("Enter key to encrypt using AES256:");
                byte[] key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
                byte[] encrypted_message = Encryptor.encryptAES(message, key);
                try {
                    OutputDevice.display(Base64.getEncoder().encodeToString(encrypted_message));
                }catch (Exception e){
                    OutputDevice.display("Error while encrypting message");
                }
                break;
            case 4:
                OutputDevice.display("Enter text to decrypt using AES256:");
                message = Base64.getDecoder().decode(InputDevice.keyboardTextInput());
                OutputDevice.display("Enter key to decrypt using AES256:");
                key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
                byte[] decrypted_message = Decryptor.decryptAES(message, key);
                try {
                    OutputDevice.display("Decrypted message: \n" + new String(decrypted_message));
                }catch (Exception e){
                    OutputDevice.display("Error while decrypting message");
                }
                break;
            case 5:
                try{
                    OutputDevice.display("Enter file path to encrypt using AES256:");
                    String filePath = InputDevice.keyboardTextInput();
                    byte[] file = InputDevice.fileByteInput(filePath);
                    OutputDevice.display("Enter key to encrypt using AES256:");
                    key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
                    byte[] encrypted_file = Encryptor.encryptAES(file, key);
                    OutputDevice.createFileFromBytes(encrypted_file, filePath + ".enc");
                    OutputDevice.display("Encrypted file created! You can find it at:");
                    OutputDevice.display(filePath + ".enc");
                    DataStorage.deleteFile(filePath);
                }catch (Exception e){
                    OutputDevice.display("Error while encrypting file");
                }
                break;
            case 6:
                try{
                    OutputDevice.display("Enter file path to decrypt using AES256:");
                    String filePath = InputDevice.keyboardTextInput();
                    byte[] file = InputDevice.fileByteInput(filePath);
                    OutputDevice.display("Enter key to decrypt using AES256:");
                    key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
                    byte[] decrypted_file = Decryptor.decryptAES(file, key);
                    DataStorage.deleteFile(filePath);
                    filePath = filePath.substring(0, filePath.length()-4);
                    OutputDevice.display("Decrypted file created! You can find it at:");
                    OutputDevice.display(filePath);
                    OutputDevice.createFileFromBytes(decrypted_file, filePath);
                }catch (Exception e){
                    OutputDevice.display("Error while decrypting file");
                }
                break;
            case 7:
                UserAccount.addAccountToUser(loggedInUser);
                break;
            case 8:
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
            OutputDevice.display("4. Decrypt text");
            OutputDevice.display("5. Encrypt file");
            OutputDevice.display("6. Decrypt file");
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
                OutputDevice.display("Enter text to encrypt using AES256:");
                byte[] message = InputDevice.keyboardByteInput();
                OutputDevice.display("Enter key to encrypt using AES256:");
                byte[] key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
                byte[] encrypted_message = Encryptor.encryptAES(message, key);
                try {
                    OutputDevice.display(Base64.getEncoder().encodeToString(encrypted_message));
                }catch (Exception e){
                    OutputDevice.display("Error while encrypting message");
                }
                break;
            case 4:
                OutputDevice.display("Enter text to decrypt using AES256:");
                message = Base64.getDecoder().decode(InputDevice.keyboardTextInput());
                OutputDevice.display("Enter key to decrypt using AES256:");
                key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
                byte[] decrypted_message = Decryptor.decryptAES(message, key);
                try {
                    OutputDevice.display("Decrypted message: \n" + new String(decrypted_message));
                }catch (Exception e){
                    OutputDevice.display("Error while decrypting message");
                }
                break;
            case 5:
                try{
                    OutputDevice.display("Enter file path to encrypt using AES256:");
                    String filePath = InputDevice.keyboardTextInput();
                    byte[] file = InputDevice.fileByteInput(filePath);
                    OutputDevice.display("Enter key to encrypt using AES256:");
                    key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
                    byte[] encrypted_file = Encryptor.encryptAES(file, key);
                    OutputDevice.createFileFromBytes(encrypted_file, filePath + ".enc");
                    OutputDevice.display("Encrypted file created! You can find it at:");
                    OutputDevice.display(filePath + ".enc");
                    DataStorage.deleteFile(filePath);
                }catch (Exception e){
                    OutputDevice.display("Error while encrypting file");
                }
                break;
            case 6:
                try{
                    OutputDevice.display("Enter file path to decrypt using AES256:");
                    String filePath = InputDevice.keyboardTextInput();
                    byte[] file = InputDevice.fileByteInput(filePath);
                    OutputDevice.display("Enter key to decrypt using AES256:");
                    key = Encryptor.make256Bit(InputDevice.keyboardByteInput());
                    byte[] decrypted_file = Decryptor.decryptAES(file, key);
                    DataStorage.deleteFile(filePath);
                    filePath = filePath.substring(0, filePath.length()-4);
                    OutputDevice.display("Decrypted file created! You can find it at:");
                    OutputDevice.display(filePath);
                    OutputDevice.createFileFromBytes(decrypted_file, filePath);
                }catch (Exception e){
                    OutputDevice.display("Error while decrypting file");
                }
                break;
        }
    }
}
