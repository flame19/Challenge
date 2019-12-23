package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Challenge {

    private List<String> longestWords;
    private List<String> secondLongestWords;
    private int concatWordsCount = 0;

    public Challenge() {
        this.longestWords = new ArrayList<>();
        this.secondLongestWords = new ArrayList<>();
    }

    public static void main(String[] args) {

        Challenge challenge = new Challenge();
        challenge.solve();
        challenge.printResults();

    }

    public void solve() {

        List<String> concatenatedWords = getConcatenatedWords(getWords());

        if (!concatenatedWords.isEmpty()) {
            concatWordsCount = concatenatedWords.size();
            String longestWord = concatenatedWords.get(concatWordsCount - 1);

            int longestWordLength = longestWord.length();
            int secondLongestWordLength = 0;

            //filling the results
            for (int i = concatenatedWords.size() - 1; i >= 0; i--) {
                String word = concatenatedWords.get(i);

                if (word.length() == longestWordLength) {
                    longestWords.add(word);
                    concatenatedWords.remove(word);
                } else {
                    if (secondLongestWordLength == 0) {
                        secondLongestWordLength = word.length();
                        secondLongestWords.add(word);
                    } else if (word.length() == secondLongestWordLength) {
                        secondLongestWords.add(word);
                    } else {
                        break;
                    }
                }
            }
        }
    }

    public static List<String> getWords() {
        String line;
        List<String> inputList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/resources/words.txt"));
            while ((line = reader.readLine()) != null) {
                if (!line.isEmpty())
                    inputList.add(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return inputList;
    }

    public static List<String> getConcatenatedWords(List<String> words) {
        List<String> concatenatedWords = new ArrayList<>();
        Set<String> checkWords = new HashSet<>();
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.length() - s2.length();
            }
        });
        for (String word : words) {
            if (isConcatenated(word, checkWords)) {
                concatenatedWords.add(word);
            }
            checkWords.add(word);
        }
        return concatenatedWords;
    }

    private static boolean isConcatenated(String word, Set<String> checkWords) {
        if (checkWords.isEmpty()) return false;
        boolean[] concatMarkers = new boolean[word.length() + 1];
        concatMarkers[0] = true;
        for (int i = 1; i <= word.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (!concatMarkers[j]) continue;
                if (checkWords.contains(word.substring(j, i))) {
                    concatMarkers[i] = true;
                    break;
                }
            }
        }
        return concatMarkers[word.length()];
    }

    public void printResults() {
        if (!longestWords.isEmpty()) {

            //print longest word(s)
            if (longestWords.size() > 1) {
                System.out.println("The longest words are: ");
                for (String word : longestWords) {
                    System.out.println(word);
                }
            } else {
                String word = longestWords.get(0);
                System.out.println("The longest concatenated word is " + word);
            }

            //print 2nd longest word(s)
            if (!secondLongestWords.isEmpty()) {
                if (secondLongestWords.size() > 1) {
                    System.out.println("The second-longest words are: ");
                    for (String word : secondLongestWords) {
                        System.out.println(word);
                    }
                } else {
                    String word = secondLongestWords.get(0);
                    System.out.println("The second-longest concatenated word is " + word);
                }
            }

            //print count of all concatenated words in the file
            System.out.println("Count of all concatenated words in the file: " + concatWordsCount);
        } else {
            System.out.println("There are no concatenated words in the file");
        }

    }
}