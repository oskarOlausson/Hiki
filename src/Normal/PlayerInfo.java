package Normal;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by oskar on 2016-12-27.
 * This classes has some inputs and outputs
 */
public class PlayerInfo {
    private HashMap<LevelEnum, Integer>  levelsPlayed = new HashMap<>();
    private HashMap<PlayerModelEnum, Integer>  playerAttributes = new HashMap<>();
    private String name = "noName";
    private final int attributeMaximum = 5;
    private String key = "fantastic beasts and where to find them";
    private File file = null;

    private Tag nameTag = new Tag("name","namn", "person");
    private Tag cooperativeTag = new Tag("cooperative", "coop", "samarbete", "samarbetsvillig", "medgörlig");
    private Tag actionTag = new Tag("action", "snabb", "quick", "nimble");
    private Tag puzzleTag = new Tag("puzzle", "pussel", "klurig", "klurighet", "pusslig");
    private Tag talkativeTag = new Tag("talkative","talking", "talk", "communicative", "pratig", "kommunikativ", "pratande", "prat");

    public PlayerInfo(File file, boolean decode) {
        findAndRead(file, decode);
    }

    /**
     * This function is a function to locate a file, if it does not it creates a new file
     * @param name; the name of the new player
     */
    public PlayerInfo(String name) {
        changeName(name);
        file = new File(getPath());
        findAndRead(file, true);
    }

    public PlayerInfo(String name, boolean decode) {
        changeName(name);
        file = new File(getPath());
        findAndRead(file, decode);
    }

    private void findAndRead(File file, boolean decode) {
        this.file = file;
        String content = "";
        String line;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            while((line = br.readLine()) != null) {
                content += "\n" + line;
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found at: " + file.getPath());
            e.printStackTrace();
            this.name = "ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";
        } catch (IOException e) {
            System.err.println("Could not read line");
            e.printStackTrace();
        }
        finally {
            if (decode) {
                content = Chiffer.xorMessage(Chiffer.base64decode(content), key);
            }
            br = new BufferedReader(new StringReader(content));
            readFile(br);
        }
    }

    public void changeName(String name) {
        this.name = name;
    }

    /**
     * Sets a new value for the player attribute
     * @param value: Must be between 1 and 5
     */
    public void setAttributes(PlayerModelEnum attribute, int value) {
        if (value <= 0 || value > attributeMaximum) {
            System.err.println("Setting value of player attribute " + attribute.toString() + "\n" +
                    "\toutside range " + "1-" + Integer.toString(attributeMaximum) +
                    ", (value set to " + Integer.toString(value) + ")");
        }
        else playerAttributes.put(attribute, value);
    }

    public void deltaAttributes(PlayerModelEnum attribute, int delta) {
        int value;
        if (playerAttributes.containsKey(attribute)) {
            value = playerAttributes.get(attribute) + delta;
        }
        else {
            value = delta + attributeMaximum / 2;
        }

        value = Math.max(Math.min(value, attributeMaximum), 0);
        setAttributes(attribute, value);
    }

    /**
     * Reads the entire file and parses words and numbers
     * @param br, the file reader
     */
    private void readFile(BufferedReader br) {
        String line;
        Character c;
        List<String> words = new ArrayList<>();
        List<Integer> numbers = new ArrayList<>();
        String word;

        HashSet<Character> separators = new HashSet<>();
        char[] arr = {' ',',','.',';',':','>','<','&','|','='};
        for (int i = 0; i < arr.length; i++) {
            separators.add(arr[i]);
        }

        try {
            while((line = br.readLine()) != null) {
                words.clear();
                numbers.clear();
                word = "";
                line = line.toLowerCase();
                //we do not read lines that begins with # (those lines are for commenting purposes only)
                if (line.length() == 0 || line.charAt(0) == '#') continue;

                for (int i = 0; i < line.length(); i++) {
                    c = line.charAt(i);
                    if (separators.contains(c)) {
                        parseWord(words, numbers, word);
                        word = "";
                    }
                    else word += c;
                }
                //interpret last word
                parseWord(words, numbers, word);

                //interpret the whole line
                interpretLine(words, numbers);
            }
        } catch (IOException e) {
            System.err.println("Could not read the file");
            e.printStackTrace();
        }
    }

    private void parseWord(List<String> words, List<Integer> numbers, String word) {
        if (!word.isEmpty()) {

            Integer number = wordToInt(word);
            if (number == null) {

                word = getSynonym(word);

                words.add(word);
            }
            else {
                numbers.add(number);
            }
        }
    }

    private String getSynonym(String word) {
        if (nameTag.has(word))          return nameTag.toString();
        if (talkativeTag.has(word))     return talkativeTag.toString();
        if (cooperativeTag.has(word))   return cooperativeTag.toString();
        if (actionTag.has(word))        return actionTag.toString();
        if (puzzleTag.has(word))        return puzzleTag.toString();
        return word;
    }

    /**
     * Parses a line and sets the fields to the correct number
     * @param words: All names of variables
     * @param numbers: The numbers corresponding
     */
    private void interpretLine(List<String> words, List<Integer> numbers) {
        String word;
        if (words.size() == 0) return;

        if (numbers.size() == 0) {
            //the name of the player
            if (words.get(0).equals("name")) {
                name = "";
                for (int i = 1; i < words.size(); i++) {
                    //spaces between words but not at the start
                    if (name.length() > 0) name += " ";
                    name += words.get(i);
                }
            }
            return;
        }

        PlayerModelEnum playerModelEnum;
        LevelEnum levelEnum;
        int number;

        for(int i = 0; i < words.size(); i++) {
            word = words.get(i);

            if (i < numbers.size()) {
                number = numbers.get(i);
                playerModelEnum = PlayerModelEnum.fromString(word);

                if (playerModelEnum != null) {
                    if (number > attributeMaximum) {
                        System.err.println("Number outside range 0-" + Integer.toString(attributeMaximum) + " in attribute '" + word + "' (had value " + number + ")");
                        System.exit(1);
                    }
                    playerAttributes.put(playerModelEnum, number);
                } else {
                    levelEnum = LevelEnum.fromString(word);
                    if (levelEnum != null) {
                        levelsPlayed.put(levelEnum, number);
                    } else {
                        System.err.println("Didn´t find any match on word '" + word + "'");
                    }
                }
            }
            else {
                System.err.println("No number to match word: " + word);
            }

        }
    }

    public Integer wordToInt(String word) {
        try {
            int number = Integer.parseInt(word);
            return number;
        }
        catch( Exception e ) {
            return null;
        }
    }

    public boolean hasPlayed(LevelEnum levelEnum) {
        return (levelsPlayed.get(levelEnum) != null);
    }

    public int playedNumberOfTimes(LevelEnum levelEnum) {
        if (!hasPlayed(levelEnum)) return 0;
        else return levelsPlayed.get(levelEnum);
    }

    public int attributeCheck(PlayerModelEnum pME) {
        Integer value = playerAttributes.get(pME);
        if (value == null) {
            //if we don´t know, we assumes average
            return attributeMaximum / 2;
        }
        else {
            return value;
        }

    }

    public void tickPlayed(LevelEnum lE) {
        int numberOfTimes = playedNumberOfTimes(lE);
        levelsPlayed.remove(lE);
        levelsPlayed.put(lE, numberOfTimes + 1);
    }

    public void writeToFile() {
        String content = getPlayerInfoAsString();
        content = Chiffer.base64encode(Chiffer.xorMessage(content, key));
        writeToFile(content);
    }

    public void writeToFile(String fileContent) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(file.getPath());
            writer.print(fileContent);
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Could not open player file " + getPath());
        }

    }

    public String getPlayerInfoAsString() {
        String string = "";

        string += "Name: " + name + "\n";
        string += "#How many times a level has been played\n";
        for (LevelEnum levelEnum : LevelEnum.values()) {
            if (levelsPlayed.containsKey(levelEnum)) {
                string += levelEnum.toString().toLowerCase() + " " + Integer.toString(playedNumberOfTimes(levelEnum)) + " ";
            }
        }
        string += "\n";
        string += "#Different attributes of the player\n";
        boolean foundOne = false;

        for (PlayerModelEnum attribute : PlayerModelEnum.values()) {
            if (playerAttributes.containsKey(attribute)) {
                string += attribute.toString().toLowerCase() + " " + attributeCheck(attribute) + "\n";
                foundOne = true;
            }
        }

        if (!foundOne) {
            string += "#\tNo attributes yet (attributes are how good a player is at something\n" +
                    "#\trepresented as a number between 1 and 5 (5 is the best)";
        }

        return string;
    }

    public String getPath() {
        return "./PlayerFiles/" + name + ".plf";
    }

    public String getName() {
        return name;
    }

    public void decode() {

    }
}
