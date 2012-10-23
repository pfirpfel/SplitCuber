package splitcuber.tools;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import splitcuber.error.ImageError;
import splitcuber.image.MagicCardsInfoSingleCardImage;

public class LocalResizer {

    public static void main(String[] args) {
        if (args.length != 1) {
            String usage = "Resizes and crops all .jpg-files from MagicCards.info in given folder\n"
                    + "Usage: <this> \"c:\\absolute\\path\"";
            System.out.println(usage);
            System.exit(0);
        }
        File dir = new File(args[0]);
        if (!dir.isDirectory()) {
            System.out.println("Input is not a directory");
            System.exit(0);
        }

        FilenameFilter filter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg");
            }
        };

        File[] files = dir.listFiles(filter);

        for (File f : files) {
            try {
                MagicCardsInfoSingleCardImage img = 
                        new MagicCardsInfoSingleCardImage(
                                f.getName().replaceAll(".jpg", ""),
                                f,
                                false);
                ImageIO.write(img.getImage(), "JPG", f);

            } catch (ImageError e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
