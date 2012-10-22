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
        cardList = new String[tempList.size()];
        tempList.toArray(cardList);
        Arrays.sort(cardList, String.CASE_INSENSITIVE_ORDER);
    }
    
    private static CardDictionary getInstance() throws DictionaryError {
        if (instance == null) {
            instance = new CardDictionary();
        }
        return instance;
    }
    
    /**
     * Checks whether a card name is correctly spelled.
     * @param name card name
     * @return true if found in dictionary, false is not
     * @throws DictionaryError thrown if the creation of the dictionary went wrong
     */
    public static boolean isExactName(String name) throws DictionaryError{
        return Arrays.binarySearch(getInstance().cardList, name) >= 0;
    }
    
    /**
     * Returns a list of possible card names to a misspelled name.
     * @param query misspelled card name
     * @return String-array of possible matches
     * @throws DictionaryError thrown if the creation of the dictionary went wrong
     */
    public static String[] fuzzySearch(String query) throws DictionaryError{        
        ArrayList<String> suggestions = new ArrayList<String>();
        int suggestionsScore = Integer.MAX_VALUE;
        
        int currentScore;
        
        for(String s : getInstance().cardList){
            currentScore = StringUtils.getLevenshteinDistance(query, s);
            if(currentScore < suggestionsScore){
                suggestions.clear();
                suggestionsScore = currentScore;
            }
            if(currentScore == suggestionsScore){
                suggestions.add(s);
            }
        }
        return suggestions.toArray( new String[suggestions.size()]);
    }
}
