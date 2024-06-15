package ui;

public class LetterToInt {
    String upper = "ABCDEFGH";
    String lower = "abcdefgh";

    public static int convert(char ch){
        char littleChar = Character.toLowerCase(ch);
        return littleChar - 'a' + 1;
    }


}
