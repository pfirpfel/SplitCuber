package splitcuber.image;

import java.io.File;

import splitcuber.error.ImageError;

public class GathererSingleCardImage extends SingleCardImage {
    
    public GathererSingleCardImage(String name, File imagefile, boolean alreadyDone) throws ImageError {
        super(name, imagefile);
        if (!alreadyDone) {
            crop(12, 12, 200, 285);
        }
    }
}
