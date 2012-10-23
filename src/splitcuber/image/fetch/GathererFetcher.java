package splitcuber.image.fetch;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import splitcuber.error.ImageError;
import splitcuber.error.WebFetchError;
import splitcuber.image.GathererSingleCardImage;
import splitcuber.image.SingleCardImage;
import splitcuber.web.ContentType;
import splitcuber.web.FileFetcher;

public class GathererFetcher implements ImageFetcher {
    
    private static final String GATHERER_URL = "http://gatherer.wizards.com/Handlers/Image.ashx?type=card&name=";

    @Override
    public SingleCardImage fetchByName(String name, File file) throws WebFetchError, ImageError {
        SingleCardImage returnImage = null;
        URL imageURL = null;
        try {
            imageURL = new URL(GATHERER_URL + name.replaceAll("\u00FB", "%C3%BB"));
        } catch (MalformedURLException e) {
            throw new WebFetchError("Malformed URL: " + e.getMessage());
        }
        FileFetcher.fetchFile(imageURL, ContentType.JPEG, file);
        returnImage = new GathererSingleCardImage(name, file, false);
        return returnImage;
    }

    @Override
    public SingleCardImage fetchFromFile(String name, File imagefile) throws ImageError {
        return new GathererSingleCardImage(name, imagefile, true);
    }

}
