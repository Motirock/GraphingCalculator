/*
Use this class to store methods used for images.
I currently have only one method used for checking rotating an image about a point.
*/

package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    //Rotates an image about a point
    public static void drawRotatedImage(Graphics2D g2, BufferedImage image, int degrees, int x, int y, int width, int height, int pivotX, int pivotY, double GS) {
        //Rotates
        g2.rotate(Math.toRadians(degrees), (int) (pivotX*GS), (int) (pivotY*GS));
        //Draws
        g2.drawImage(image, (int) (x*GS), (int) (y*GS), (int) (width*GS), (int) (height*GS), null);
        //Rotates back to the normal orientation
        g2.rotate(Math.toRadians(-degrees), (int) (pivotX*GS), (int) (pivotY*GS));
    }
}
