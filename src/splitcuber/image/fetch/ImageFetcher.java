package splitcuber.image.fetch;

import java.io.File;

import splitcuber.error.ImageError;
import splitcuber.error.WebFetchError;
import splitcuber.image.SingleCardImage;

public interface ImageFetcher {
    public SingleCardImage fetchByName(String name, File file) throws WebFetchError, ImageError;
    public SingleCardImage fetchFromFile(String name, File imagefile) throws ImageError;
}
