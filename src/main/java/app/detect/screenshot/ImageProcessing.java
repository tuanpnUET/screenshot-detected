package app.detect.screenshot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;

import nu.pattern.OpenCV;

public class ImageProcessing {

	public static String[] cropImage(String fileName) {
		OpenCV.loadLocally();
		Mat src = Imgcodecs.imread(fileName, Imgcodecs.IMREAD_COLOR);
        if( src.empty() ) {
            System.out.println("Error opening image!");
            String[] result = {};
            return result;
        } else {
        	Rect object = new Rect(340, 680, 420, 90);
            Mat cropped = new Mat(src, object);
            Mat output = cropped.clone();
            Imgcodecs.imwrite(fileName+"object1.png", output);
            
            object = new Rect(1280, 680, 420, 90);
            cropped = new Mat(src, object);
            output = cropped.clone();
            Imgcodecs.imwrite(fileName+"object2.png", output);
            
            object = new Rect(870, 580, 300, 300);
            cropped = new Mat(src, object);
            output = cropped.clone();
            Imgcodecs.imwrite(fileName+"object3.png", output);
            
            object = new Rect(1450, 990, 40, 40);
            cropped = new Mat(src, object);
            output = cropped.clone();
            Imgcodecs.imwrite(fileName+"object4.png", output);
            
            String[] result = {fileName+"object1.png", fileName+"object2.png", fileName+"object3.png", fileName+"object4.png"};
            return result;
        }
	}
	
	public static String getColor(String fileName) throws IOException {
		OpenCV.loadLocally();
		File file = new File(fileName);
		BufferedImage image = ImageIO.read(file);
		int height = image.getHeight();
		int width = image.getWidth();
		int clr, red, green, blue, sumred = 0, sumgreen = 0, sumblue = 0;
		for (int i = 0; i < width; i++) {
		   for (int j = 0; j < height; j++) {
		        clr = image.getRGB(i, j);
		        red = (clr & 0x00ff0000) >> 16;
		        green = (clr & 0x0000ff00) >> 8;
		        blue = clr & 0x000000ff;
		        sumred = red + sumred;
		        sumgreen = green + sumgreen; 
		        sumblue = blue + sumblue;
		       }
		    }
		sumred = sumred / (height * width); 
		sumblue = sumblue / (height * width); 
		sumgreen = sumgreen / (height * width);
		if (sumred < 100 && sumblue < 100 && sumgreen < 100) {
		       return "black";
		} else return "white";
	}
	
}
