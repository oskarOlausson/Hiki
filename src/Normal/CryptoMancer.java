package Normal;

import java.io.File;

/**
 * Created by oskar on 2017-01-07.
 * This classes has some inputs and outputs
 */
public class CryptoMancer {

    private void encryptAllUnEncrypted() {
        encryptAllUnEncrypted(new File("./PlayerFiles"));
    }

    private void encryptAllUnEncrypted(File folder) {
        File[] listOfFiles = folder.listFiles();
        String fileName;
        File fileAtIndex;
        PlayerInfo pi;

        for (int i = 0; i < listOfFiles.length; i++) {
            fileAtIndex = listOfFiles[i];
            if (fileAtIndex.isFile()) {
                fileName = listOfFiles[i].getName();
                if (fileName.length() > 4 || fileName.substring(fileName.length() - 4).equals(".plf")) {
                    pi = new PlayerInfo(fileAtIndex, true);


                    if (pi.getName().equals("noName")) {
                        //if not encrypted, we load as usual
                        pi = new PlayerInfo(fileAtIndex, false);

                        if (!pi.getName().equals("noName")) {
                            pi.writeToFile();
                        }
                    }
                }
                else {
                    System.err.println("Found file that is not .plf");
                }
            } else if (fileAtIndex.isDirectory()) {
                //RECURSION, reads the files from the subfolders as well
                encryptAllUnEncrypted(fileAtIndex);
            }
        }
    }

    public static void main(String[] args) {
        CryptoMancer crypt = new CryptoMancer();
        crypt.encryptAllUnEncrypted();
    }

}
