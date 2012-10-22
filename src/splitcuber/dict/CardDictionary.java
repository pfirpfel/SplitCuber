package splitcuber.dict;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import splitcuber.error.DictionaryError;

public class CardDictionary {
    private static CardDictionary instance = null;
    private static final String CARDLIST_PATH = "res/cardlist.txt";
    private String[] cardList;

    private CardDictionary() throws DictionaryError {
        ArrayList<String> tempList = new ArrayList<String>();
        FileReader fr = null;
        BufferedReader txtReader = null;

        try {
            fr = new FileReader(CARDLIST_PATH);
            txtReader = new BufferedReader(fr);
            String currentLine;
            while ((currentLine = txtReader.readLine()) != null && !currentLine.equals("")) {
                tempList.add(currentLine);
            }

        } catch (FileNotFoundException e) {
            throw new DictionaryError(e.getMessage());
        } catch (IOException e) {
            throw new DictionaryError(e.getMessage());
        } finally {
            try {
                if (txtReader != null) {
                    txtReader.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                throw new DictionaryError(e.getMessage());
            }
        }
        cardList = (String[]) tempList.toArray();
        Arrays.sort(cardList, String.CASE_INSENSITIVE_ORDER);
    }

    private static CardDictionary getInstance() throws DictionaryError {
        if (instance == null) {
            instance = new CardDictionary();
        }
        return instance;
    }
    
    public static boolean isExactName(String name) throws DictionaryError{
        return Arrays.binarySearch(getInstance().cardList, name) >= 0;
    }
    
    public static String[] fuzzySearch(String query) throws DictionaryError{        
        ArrayList<String> suggestions = new ArrayList<String>();
        int suggestionsScore = Integer.MAX_VALUE;
        
        int currentScore;
        
        for(String s : getInstance().cardList){
            currentScore = StringUtils.getLevenshteinDistance(query, s);
            if(currentScore < suggestionsScore){
                suggestions.clear();
                suggestions.add(s);
                suggestionsScore = currentScore;
            }
            if(currentScore == suggestionsScore){
                suggestions.add(s);
            }
        }
        return (String[]) suggestions.toArray();
    }
}
