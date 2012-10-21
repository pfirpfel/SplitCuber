package splitcuber;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import splitcuber.error.WebFetchError;
import splitcuber.web.ContentType;
import splitcuber.web.FileFetcher;

public class SplitCuber {

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            FileOutputStream cardImg = new FileOutputStream("tmp/test.jpg");
            URL cardURL = new URL("http://gatherer.wizards.com/Handlers/Image.ashx?type=card&name=Clone");
            byte[] hash = FileFetcher.fetchFile(cardURL, ContentType.JPEG, cardImg);
            for(int i = 0; i < hash.length; i++){
                System.out.println(String.format("%02X", hash[i]) );
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (WebFetchError e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
