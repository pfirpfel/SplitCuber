package splitcuber.image;

import java.io.File;

import splitcuber.error.ImageError;

public class GathererSingleCardImage extends SingleCardImage {

    public GathererSingleCardImage(String name, File imagefile) throws ImageError {
        super(name, imagefile);
        super.crop(11, 12, 200, 285);
        super.rotateSideways();
    }

}
