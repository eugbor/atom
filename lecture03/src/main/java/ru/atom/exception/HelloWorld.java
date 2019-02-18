package ru.atom.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloWorld {

//    private static String getHelloWorld() throws NullPointerException {
//        try {
//            System.out.printf("Hello ");
//        } catch (NullPointerException e) {
//            throw new NullPointerException("Ой всё");
//        }
//        return "World!";
//    }
    private static final Logger log = LoggerFactory.getLogger(HelloWorld.class);

    public static void main(String[] args) {
        System.out.println(/*getHelloWorld()*/);
        /*-------------------------------------------------------------------------------*/
        List <String> wordList = getWordList("dictionary.txt");
        String secret = getRandomWord(wordList);
        System.out.printf("Welcome to Bulls and Cows game!\r\n");
        System.out.printf("I offered a " + secret.length() + "-letter word, your guess?\r\n");

        boolean flagAgainGame;
        int numberOfAttempts = 10;
        int oldNumberOfAttempts = numberOfAttempts;

        while (numberOfAttempts > 0){
            flagAgainGame = false;
            String guess = getValidString(secret.length());
            String result = getResult(secret, guess);
            System.out.printf(result);
            if (secret.length() == Character.getNumericValue(result.charAt(0))) {
                System.out.printf("You won!\r\n");
                flagAgainGame = true;
            }
            if (numberOfAttempts == 1) {
                System.out.printf("Ooh! You lose!\r\n");
                System.out.printf("This word is: " + secret + "\r\n"); //for testing
                flagAgainGame = true;
            }
            if (flagAgainGame) {
                System.out.printf("Wanna play again? Y/N:\r\n");
                while (true) {
                    String inputChar = getValidString(1);
                    if (inputChar.equalsIgnoreCase("y")) {
                        numberOfAttempts = oldNumberOfAttempts + 1;
                        break;
                    } else if (inputChar.equalsIgnoreCase("n")) {
                        numberOfAttempts = 1;
                        break;
                    } else {
                        System.out.printf("Sorry! Enter \"y\" or \"n\":\r\n");
                    }
                }
            }
            numberOfAttempts--;
        }

        System.out.printf("Thanks for playing! Bye Bye!\r\n");
    }

    /**
     *
     * @param fileName name of file
     * @return list of words
     */
    public static List<String> getWordList (String fileName){
        List<String> wordsList = null;
        try {
            wordsList = Files.readAllLines(Paths.get(fileName));
            return wordsList;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return wordsList;
    }

    /**
     *
     * @param wordList list of sting
     * @return random word
     */
    public static String getRandomWord(List <String> wordList) {
        Random rand = new Random();
        int randomIndex = rand.nextInt(wordList.size());
        String randomWord = wordList.get(randomIndex);
        return randomWord;
    }

    /**
     *
     * @param stringLength length of secret string
     * @return valid string
     */
    private static String getValidString( Integer stringLength) {
        Scanner in = new Scanner(System.in);
        String stringFromConsole = null;
        boolean flagAgainInput = true;

        while (flagAgainInput) {
            System.out.printf("Please, enter word:\r\n");
            stringFromConsole = in.nextLine();
            String regex = "^[a-zA-Z]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(stringFromConsole);

            if (stringFromConsole.isEmpty()) {
                log.warn("The string cannot be empty!");
            } else if (stringFromConsole.length() != stringLength ) {
                log.warn("The length of the input string does not match the requested!");
            } else if (!(m.matches())) {
                log.warn("The only letters must be entered!");
            } else { flagAgainInput = false; }
        }
        return stringFromConsole.toLowerCase();
    }

    /**
     *
     * @param secret random(secret) word
     * @param guess guess word
     * @return result - how many bulls and cows
     */
    private static String getResult(String secret, String guess) {
        int countBull = 0;
        int countCow = 0;

        Map<Character, Integer> hashmap = new HashMap<Character, Integer>();

        for (int i = 0; i < secret.length(); i++) {
            char charSecret = secret.charAt(i);
            char charGuess = guess.charAt(i);

            if (charSecret == charGuess) {
                countBull++;
            } else if (hashmap.containsKey(charSecret)) {
                int numberOfChar = hashmap.get(charSecret);
                numberOfChar++;
                hashmap.put(charSecret, numberOfChar);

            } else {
                hashmap.put(charSecret, 1);
            }
        }

        for (int i = 0; i < secret.length(); i++) {
            char charSecret = secret.charAt(i);
            char charGuess = guess.charAt(i);

            if (charSecret != charGuess) {
                if (hashmap.containsKey(charGuess)) {
                    countCow++;
                    if (hashmap.get(charGuess) == 1) {
                        hashmap.remove(charGuess);
                    } else {
                        int numberOfChar = hashmap.get(charGuess);
                        numberOfChar--;
                        hashmap.put(charGuess,numberOfChar);
                    }
                }
            }
        }
        return countBull + " - Bulls; " + countCow + " - Cows;\r\n";
    }
}

