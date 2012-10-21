package splitcuber.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SplitCard {
    private SingleCardImage left;
    private SingleCardImage right;
    private BufferedImage splitImg;
    
    private static final int CENTER_PADDING = 10;
    
    public SplitCard(SingleCardImage leftCard, SingleCardImage rightCard){
        left = leftCard;
        right = rightCard;
        
        int width = left.getWidth() + CENTER_PADDING + right.getWidth();
        int height = Math.max(left.getHeight(), left.getHeight());
        
        splitImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = splitImg.getGraphics();
        // set background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        
        //adding split halves
        g.drawImage(left.getImage(), 0, 0, null);
        g.drawImage(right.getImage(), left.getWidth() + CENTER_PADDING, 0, null);   
    }
    
    public BufferedImage getSplitImage(){
        return splitImg;
    }

}
