package splitcuber.image;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import splitcuber.dict.CardDictionary;
import splitcuber.error.DictionaryError;
import splitcuber.error.ImageError;
import splitcuber.error.WebFetchError;

public class MagicCardsInfoSingleCardImage extends SingleCardImage {

    public MagicCardsInfoSingleCardImage(String name, File imagefile, boolean alreadDone) throws ImageError {
        super(name, imagefile);
        if (!alreadDone) {
            crop(6, 9, 299, 426);
            resize(200, 285);
            rotateSideways();
        }
    }

    // http://magiccards.info/query?q=!Atog&v=card&s=cname
    // http://magiccards.info/query?q=!Zur%27s%20Weirding&v=card&s=cname
    //
    // in HTML:
    // http://magiccards.info/scans/en/9e/114.jpg
    // ->
    // http://magiccards.info/scans/*.jpg
    //
    // magiccards info skaliert proxies zu 222x319, wir mom zu 285x405 -> testen

    public static MagicCardsInfoSingleCardImage fetchByName(String name, boolean forceReload) throws ImageError,
            DictionaryError, MalformedURLException, WebFetchError, IOException {

        if (CardDictionary.isExactName(name)) {
            MagicCardsInfoSingleCardImage magicCardsInfoImage;
            File path = new File(IMAGE_PATH);
            if (!path.exists()) {
                path.mkdir();
            }
            File image = new File(IMAGE_PATH + name + ".jpg");
            if (image.exists() && !forceReload) {
                // aus 'cache'
                magicCardsInfoImage = new MagicCardsInfoSingleCardImage(name, image, true);
            } else {
                // TODO
                
                // URL imageURL = new URL(GATHERER_URL + name);
                // FileFetcher.fetchFile(imageURL, ContentType.JPEG, image);
                // magicCardsInfoImage = new MagicCardsInfoSingleCardImage(name, image, false);
                magicCardsInfoImage = null;
                // ImageIO.write(magicCardsInfoImage.image, "JPG", image);
            }
            return magicCardsInfoImage;
        } else {
            return null;
        }
    }
}
