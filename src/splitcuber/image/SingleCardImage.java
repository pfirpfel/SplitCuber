package splitcuber.image;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

//import static org.imgscalr.Scalr.*;

import splitcuber.error.ImageError;

public class SingleCardImage {
    protected String name;
    protected BufferedImage image;
    protected static final String IMAGE_PATH = "cache/singlecard/";

    public SingleCardImage(String name, File imagefile) throws ImageError {
        this.name = name;
        try {
            image = ImageIO.read(imagefile);
        } catch (IOException e) {
            throw new ImageError(e.getMessage());
        }
    }

    public void crop(int x, int y, int width, int height) throws ImageError {
        BufferedImageOp[] biop = new BufferedImageOp[0];
        try {
            image = Scalr.crop(image, x, y, width, height, biop);
        } catch (IllegalArgumentException | ImagingOpException e) {
            throw new ImageError(e.getMessage());
        }
    }

    public void rotateSideways() throws ImageError {
        BufferedImageOp[] biop = new BufferedImageOp[0];
        try {
            image =  Scalr.rotate(image, Scalr.Rotation.CW_90, biop);
        } catch (IllegalArgumentException | ImagingOpException e) {
            throw new ImageError(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }
    
    public int getWidth(){
        return image.getWidth();
    }
    
    public int getHeight(){
        return image.getHeight();
    }

    public BufferedImage getImage() {
        return image;
    }

}
