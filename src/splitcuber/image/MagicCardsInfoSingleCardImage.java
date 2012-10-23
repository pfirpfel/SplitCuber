package splitcuber.image;

import java.io.File;

import splitcuber.error.ImageError;

public class MagicCardsInfoSingleCardImage extends SingleCardImage {

    public MagicCardsInfoSingleCardImage(String name, File imagefile, boolean alreadDone) throws ImageError {
        super(name, imagefile);
        if (!alreadDone) {
            crop(6, 9, 299, 426);
            resize(200, 285);
        }
    }
}
