package splitcuber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import au.com.bytecode.opencsv.CSVReader;

import splitcuber.dict.CardDictionary;
import splitcuber.error.DictionaryError;
import splitcuber.error.ImageError;
import splitcuber.error.WebFetchError;
import splitcuber.image.GathererSingleCardImage;
import splitcuber.image.MagicCardsInfoSingleCardImage;
import splitcuber.image.SingleCardImage;
import splitcuber.image.SplitCard;

public class SplitCuber {

    private static final String PATH = "cache/splitcards/";

    public static void main(String[] args) {

        if (args.length == 0 || args.length > 3 || (args.length == 1 && args[0].endsWith("help"))) {
            String usage = "Usage: java SplitCuber TSV_LIST [-g|-m] [-r]\n" + "TSV_LIST: card list, tab-separated\n"
                    + "(optional) source: -g Gatherer (default), -m MagicCards.info\n"
                    + "(optional) reload: if set, forces redownload of already downloaded images";
            System.out.println(usage);
            System.exit(0);
        }

        String TSVPath = args[0];
        boolean useGatherer = true;
        if ((args.length == 2 && args[1].equals("-m"))
                || (args.length == 3 && (args[1].equals("-m") || args[2].equals("-m")))) {
            useGatherer = false;
            System.out.println("Using MagicCards.info...");
        } else {
            System.out.println("Using Gatherer...");
        }

        boolean forceReload = false;
        if ((args.length == 2 && args[1].equals("-r"))
                || (args.length == 3 && (args[1].equals("-r") || args[2].equals("-r")))) {
            forceReload = true;
            System.out.println("Force re-downloading of all pictures...");
        }

        CSVReader reader;
        ArrayList<SplitPair<String, String>> splitCards = new ArrayList<SplitPair<String, String>>();
        try {
            reader = new CSVReader(new FileReader(TSVPath), '\t');
            System.out.println("Parsing card list...");
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                SplitPair<String, String> newPair = new SplitPair<String, String>(nextLine[0], nextLine[1]);
                splitCards.add(newPair);
            }

        } catch (FileNotFoundException e) {
            System.out.println(TSVPath + " not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error while reading " + TSVPath + ":\n" + e.getMessage());
            System.exit(0);
        }

        System.out.println("Generating " + splitCards.size() + " split cards...");
        for (SplitPair<String, String> pair : splitCards) {
            String leftName = checkName(pair.getLeft());
            String rightName = checkName(pair.getRight());
            String splitCardName = leftName + "#" + rightName;

            SingleCardImage left;
            SingleCardImage right;
            try {
                if (useGatherer) {
                    left = GathererSingleCardImage.fetchByName(leftName, forceReload);
                    right = GathererSingleCardImage.fetchByName(rightName, forceReload);
                } else {
                    left = MagicCardsInfoSingleCardImage.fetchByName(leftName, forceReload);
                    right = MagicCardsInfoSingleCardImage.fetchByName(rightName, forceReload);
                }
                SplitCard split = new SplitCard(left, right);
                File path = new File(PATH);
                if (!path.exists()) {
                    path.mkdir();
                }
                File splitOut = new File(PATH + splitCardName + ".jpg");
                ImageIO.write(split.getSplitImage(), "JPG", splitOut);

            } catch (ImageError | DictionaryError | WebFetchError | IOException e) {
                System.out.println("Error generating '" + splitCardName + "': " + e.getMessage());
            }
        }
        
        System.out.println("Done!");
    }

    private static String checkName(String name) {
        try {
            if (CardDictionary.isExactName(name)) {
                return name;
            } else {
                String[] possibilities = CardDictionary.fuzzySearch(name);
                if (possibilities.length == 0) {
                    return "";
                }
                if (possibilities.length == 1) {
                    return possibilities[0];
                } else {
                    Scanner in = new Scanner(System.in);
                    System.out.println(name + "could not be resolved, choose from suggestions:");
                    for (int i = 0; i < possibilities.length; i++) {
                        System.out.println("(" + (i + 1) + ") " + possibilities[i]);
                    }
                    int choice = in.nextInt();
                    in.close();
                    return possibilities[choice - 1];
                }

            }
        } catch (DictionaryError e) {
            System.out.println("Problem using card dictionary: " + e.getMessage());
            System.exit(-1);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("A problem occurred: " + e.getMessage());
            System.exit(-1);
        }
        return "";
    }
}
