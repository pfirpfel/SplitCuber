package splitcuber.image.fetch;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import splitcuber.error.ImageError;
import splitcuber.error.WebFetchError;
import splitcuber.image.SingleCardImage;

public class CachedFetch {
    
    //TODO change to properties file
    private static final String CACHE_PATH = "cache/";
    private static final String IMAGE_PATH = CACHE_PATH + "/singlecard/";
    
    public static SingleCardImage fetchByName(String name, FetchSource source, boolean useCache) throws WebFetchError, ImageError, IOException{
        SingleCardImage returnImage = null;
        File imageCache = getCacheFolder();
        String filename = name.replaceAll("[\\\\\\/]", "#") + ".jpg"; //split card fix
        File image = new File(imageCache.getPath() + "/" + filename);
        ImageFetcher fetcher;
        switch(source){
            case GATHERER:
                fetcher = new GathererFetcher();
            default:
                fetcher = new MagicCardsInfoFetcher();
        }
        if(image.exists() && useCache){
            returnImage = fetcher.fetchFromFile(name, image);
        } else {
            returnImage = fetcher.fetchByName(name, image);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Sleep not possible: " + e.getMessage());
            }
            ImageIO.write(returnImage.getImage(), "JPG", image);
        }
        return returnImage;
    }
    
    private static File getCacheFolder(){
        File cache = new File(IMAGE_PATH);
        if (!cache.exists()) {
            cache.mkdir();
        }
        File imageCache = new File(IMAGE_PATH);
        if (!imageCache.exists()) {
            imageCache.mkdir();
        }
        return imageCache;
    }

}
