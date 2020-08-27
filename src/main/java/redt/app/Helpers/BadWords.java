package redt.app.Helpers;

import org.springframework.stereotype.Component;

@Component
public class BadWords {
    private static final String[] words = {"fuck", "dm"};
    public BadWords(){}

    public boolean contains(String text){
        for (String word : words){
            if (text.contains(word)){
                return true;
            }
        }
        return false;
    }

    public static String[] getWords() {
        return words;
    }
}
