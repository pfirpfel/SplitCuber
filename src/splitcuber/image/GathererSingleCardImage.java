package splitcuber.image;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import splitcuber.dict.CardDictionary;
import splitcuber.error.DictionaryError;
import splitcuber.error.ImageError;
import splitcuber.error.WebFetchError;
import splitcuber.web.ContentType;
import splitcuber.web.FileFetcher;

public class GathererSingleCardImage extends SingleCardImage {

    private static final String GATHERER_URL = "http://gatherer.wizards.com/Handlers/Image.ashx?type=card&name=";

    public GathererSingleCardImage(String name, File imagefile) throws ImageError {
        super(name, imagefile);
        crop(12, 12, 200, 285);
        rotateSideways();
    }

    public static GathererSingleCardImage fetchByName(String name) throws ImageError, DictionaryError,
            MalformedURLException, WebFetchError, IOException {

        if (CardDictionary.isExactName(name)) {
            File path = new File(IMAGE_PATH);
            if(!path.exists()){
                path.mkdir();
            }
            File image = new File(IMAGE_PATH + name + ".jpg");
            URL imageURL = new URL(GATHERER_URL + name);
            FileFetcher.fetchFile(imageURL, ContentType.JPEG, image);
            GathererSingleCardImage gathererImage =  new GathererSingleCardImage(name, image);
            ImageIO.write(gathererImage.image, "JPG", image);
            return gathererImage;
        } else {
            return null;
        }
    }

}
