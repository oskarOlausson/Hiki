package Normal;

import java.util.HashSet;
import java.util.Iterator;


/**
 * Created by oskar on 2017-01-01.
 * This classes has some inputs and outputs
 */
public class Tag {
    private HashSet<String> allowed = null;
    private String name;

    public Tag(String name, String... allowed) {
        this.name = name;
        this.allowed = new HashSet<>();
        this.allowed.add(name);
        for (int i = 0; i < allowed.length; i++) {
            this.allowed.add(allowed[i].toLowerCase());
        }
    }

    public String spellCheck(String word) {
        int lettersWrong;
        int least = 100000;

        String closest = null;

        for (String s : allowed) {
            lettersWrong = 0;
            for (int i = 0; i < s.length(); i++) {
                if (i >= word.length()) {
                    break;
                }
                else {
                    if (s.charAt(i) != word.charAt(i)) {
                        lettersWrong++;
                    }
                }
            }

            lettersWrong += Math.abs(s.length() - word.length());

            if (closest == null || lettersWrong < least) {
                closest = s;
                least = lettersWrong;
            }
        }

        if (least < 2) return closest;
        else return word;
    }

    public boolean has(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) {
            if (o.getClass() == String.class) {
                String input = o.toString().toLowerCase();
                return allowed.contains(input);
            }
            else return false;
        }

        Tag tag = (Tag) o;

        return allowed != null ? allowed.equals(tag.allowed) : tag.allowed == null;
    }

    @Override
    public String toString() {
        return name;
    }
}
