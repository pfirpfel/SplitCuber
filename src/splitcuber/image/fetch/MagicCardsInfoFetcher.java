package splitcuber.image.fetch;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import splitcuber.error.ImageError;
import splitcuber.error.WebFetchError;
import splitcuber.image.MagicCardsInfoSingleCardImage;
import splitcuber.image.SingleCardImage;
import splitcuber.web.ContentType;
import splitcuber.web.FileFetcher;

public class MagicCardsInfoFetcher implements ImageFetcher {
    private static final String HOST = "magiccards.info";
    private static final String PATH = "/query";
    private static final String QUERY = "v=card&s=cname&q=!";

    @Override
    public SingleCardImage fetchByName(String name, File file) throws WebFetchError, ImageError{
        SingleCardImage returnImage = null;
        URI uri;
        try {
            uri = new URI("http", HOST, PATH, QUERY + name, null);
            // lim-dul fix
            String txtURL = uri.toString();
            txtURL = txtURL.replaceAll("\u00FB", "%C3%BB");
            uri = new URI(txtURL);
        } catch (URISyntaxException e) {
            throw new WebFetchError("URI Syntax: " + e.getMessage());
        }

        String textHTML = "";
        try {
            textHTML = FileFetcher.fetchText(uri.toURL());
        } catch (MalformedURLException e) {
            throw new WebFetchError("Malformed URL: " + e.getMessage());
        }
        String regex = "(http:\\/\\/magiccards\\.info\\/scans\\/[\\w\\/]+\\.jpg)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textHTML);

        String imageURLtxt = null;
        if (matcher.find()) {
            imageURLtxt = matcher.group(1);
        } else {
            throw new WebFetchError("Could not match image URL");
        }
        URL imageURL = null;
        try {
            imageURL = new URL(imageURLtxt);
        } catch (MalformedURLException e) {
            throw new WebFetchError("Malformed URL: " + e.getMessage());
        }
        FileFetcher.fetchFile(imageURL, ContentType.JPEG, file);
        returnImage = new MagicCardsInfoSingleCardImage(name, file, false);

        return returnImage;
    }

    @Override
    public SingleCardImage fetchFromFile(String name, File imagefile) throws ImageError {
        return new MagicCardsInfoSingleCardImage(name, imagefile, true);
    }

}
