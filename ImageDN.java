import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class ImageDN {
	private String[][] pixels;
	private int[][] pixelsARGB;
	private char[][] greypix;
	private int maxX;
	private int maxY;
	private BufferedImage img;

	/*Takes a BufferedImage to create an ImageDN that can be modified
	@param img is the BufferedImage created in the Driver that will be converted to an ImageDN
	*/
	public ImageDN(BufferedImage img) {
		this.img = img;
		maxX = img.getWidth();
		maxY = img.getHeight();
		pixels = new String[maxY][maxX];
		pixelsARGB = new int[maxY][maxX];
		Color c;
		String s;
		String get;
		for (int i = 0; i < maxY; i++) {
			for (int j = 0; j < maxX; j++) {
				c = new Color(img.getRGB(j,i));
				String[] colors = new String[4];
				for (int a = 0; a < 4; a++) {
					s = "";
					get = "";
					if (a == 0) {
						get = ""+c.getRed();
					}
					if (a == 1) {
						get = ""+c.getGreen();
					}
					if (a == 2) {
						get = ""+c.getBlue();
					}
					if (a == 3) {
						get = ""+c.getAlpha();
					}
					for (int b = 0; b < 3-(get).length(); b++) {
						s += "0";
					}
					s += get;
					colors[a] = s;
				}
				pixels[i][j] = colors[0]+colors[1]+colors[2]+colors[3];
			}
		}
		toTYPE_INT_ARGB();
	}

	/*Creates the pixelsARGB array
	*/
	public void toTYPE_INT_ARGB(){
		int r;
		int g;
		int b;
		int a;
		for (int row = 0; row < maxY; row++){
			for (int col = 0; col < maxX; col++){
				r = getR(row,col);
				g = getG(row,col);
				b = getB(row,col);
				a = getA(row,col);
				Color conv = new Color(r,g,b,a);
				pixelsARGB[row][col] = conv.getRGB();
			}
		}
	}
	
	/*Returns the array of pixels in the format RRRGGGBBBAAA
	@return the array of pixels in the format RRRGGGBBBAAA
	*/
	public String[][] getPixels() {
		return pixels;
	}

	/*Returns the array of pixels in the format TYPE_INT_ARGB
	@return the array of pixels in the format TYPE_INT_ARGB
	*/
	public int[][] getPixelsARGB() {
		return pixelsARGB;
	}
	
	/*Returns the MaxX value
	@return the number of pixels in the X direction
	*/
	public int getMaxX() {
		return maxX;
	}

	/*Returns the MaxY value
	@return the number of pixels in the Y direction
	*/
	public int getMaxY() {
		return maxY;
	}

	/*Returns the image to be modified
	@return the BufferedImage that created the ImageDN
	*/
	public BufferedImage getImage() {
		return img;
	}
	
	/*Returns the red component of a pixel's colors
	@param x is the x value of the pixel
	@param y is the y value of the pixel
	@return the red component of the pixel's color
	*/
	public int getR(int y, int x) {
		return Integer.parseInt(pixels[y][x].substring(0,3));
	}
	
	/*Returns the green component of a pixel's colors
	@param x is the x value of the pixel
	@param y is the y value of the pixel
	@return the green component of the pixel's color
	*/
	public int getG(int y, int x) {
		return Integer.parseInt(pixels[y][x].substring(3,6));
	}
	
	/*Returns the blue component of a pixel's colors
	@param x is the x value of the pixel
	@param y is the y value of the pixel
	@return the blue component of the pixel's color
	*/
	public int getB(int y, int x) {
		return Integer.parseInt(pixels[y][x].substring(6,9));
	}
	
	/*Returns the alpha component of a pixel's colors
	@param x is the x value of the pixel
	@param y is the y value of the pixel
	@return the alpha component of the pixel's color
	*/
	public int getA(int y, int x) {
		return Integer.parseInt(pixels[y][x].substring(9,12));
	}
	
	/*Returns a scaled version of an image
	@param input is the image to be scaled
	@param newWidth is the new width of the image
	@param newHeight is the new height of the image
	@return the new scaled BufferedImage
	*/
	public static BufferedImage scale(BufferedImage input, int newWidth, int newHeight) {
		int currentWidth = input.getWidth();
		int currentHeight = input.getHeight();
		
		double scaleX = (double)newWidth/currentWidth;
		double scaleY = (double)newHeight/currentHeight;
		AffineTransform scale = AffineTransform.getScaleInstance(scaleX,scaleY);
		AffineTransformOp scaleOp = new AffineTransformOp(scale,AffineTransformOp.TYPE_BILINEAR);
		if (input.getType() == 0) {
			return scaleOp.filter(input, new BufferedImage(newWidth,newHeight,5));
		}
		return scaleOp.filter(input, new BufferedImage(newWidth,newHeight,input.getType()));
	}
	
	/*Scales the image
	@param newWidth is the new width of the image
	@param newHeight is the new height of the image
	@return the new scaled ImageDN
	*/
	public ImageDN scale1(int newWidth, int newHeight) {
		ImageDN ret = new ImageDN(scale(img,newWidth,newHeight));
		return ret;
	}
	
	/*Applies the specified border to the image
	@param applied is the border to be applied to the image
	*/
	public void applyBorder(Border applied) {
		ArrayList<Integer> pos = applied.getBorderPos();
		int posX;
		int posY;
		int valX;
		int valY;
		int r;
		int g;
		int b;
		int a;
		Color c;
		for (int i = 0; i < pos.size(); i+=2) {
			posY = pos.get(i);
			posX = pos.get(i+1);
			valY = (int)pos.get(i);
			valX = (int)pos.get(i+1);
			img.setRGB(posX,posY,applied.getImage().getRGB(valX,valY));
		}
	}
	
	/*Applies the specified mood to the image
	@param mood is the mood to be applied to the image
	*/
	public void applyMood(String mood) {
		int rChange = 0;
		int gChange = 0;
		int bChange = 0;
		int r;
		int g;
		int b;
		int a;
		Color c;
		if (mood.equals("happy")) {
			rChange = 20;
			gChange = -20;
			bChange = -20;
		}
		if (mood.equals("sad")) {
			rChange = -20;
			gChange = -20;
			bChange = 20;
		}
		if (mood.equals("mad")) {
			rChange = -20;
			gChange = 20;
			bChange = -20;
		}
		
		for (int row = 0; row < maxY; row++) {
			for (int col = 0; col < maxX; col++) {
				r = fixBounds(getR(row,col)+rChange);
				g = fixBounds(getG(row,col)+gChange);
				b = fixBounds(getB(row,col)+bChange);
				a = getA(row,col);
				c = new Color(r,g,b,a);
				img.setRGB(col,row,c.getRGB());
			}
		}
	}
	
	/*Applies the specified transparency to the image from 0 (transparent) to 255 (opaque)
	@param percentage is the transparency % from 0 to 100 to be applied
	*/
	public void applyTransparency(int percentage) {
		int r;
		int g;
		int b;
		int a;
		Color c;
		for (int row = 0; row < maxY; row++) {
			for (int col = 0; col < maxX; col++) {
				r = getR(row,col);
				g = getG(row,col);
				b = getB(row,col);
				a = (int)(percentage*255.0/100.0);
				c = new Color(r,g,b,a);
				img.setRGB(col,row,c.getRGB());
			}
		}
	}
	
	/*Takes any number that may be outside the (0,255) range and puts it back in that range
	@param toFix is the number that needs to placed in the range
	@return the new int in the range (0,255)
	*/
	public int fixBounds(int toFix) {
		if (toFix > 255) {
			return 255;
		}
		else if (toFix < 0) {
			return 0;
		}
		else {
			return toFix;
		}
	}

	/*Creates a new file from the current, modified image with the specified name and extension
	@param filename is the name of the file (without the extension)
	@param extension is the extension to be used (without the period - e.g. jpg, png, etc)
	*/
	public void outputImage(String filename, String extension) {
		try {
			String s = filename+"."+extension;
			File output = new File(s);
			ImageIO.write(img, extension, output);
		}
		catch (IOException e) {
			System.out.println("Input/Output error. Please check that you have entered the correct parameters and try again.");
		}
	}

	public static void main(String[] args) {
		//Testing Border
		BufferedImage b = null;
		try {
			b = ImageIO.read(new File("whiteimage200x200.png"));
		}
		catch (IOException e) {
			System.out.println("IOException1");
		}
		ImageDN a = new ImageDN(b);
		
		BufferedImage brdr = null;
		Border test = null;
		try {
			brdr = ImageIO.read(new File("blackborder200x200.jpg"));
		}
		catch (IOException e) {
			System.out.println("IOException2");
		}
		test = new Border(brdr, "000000000");
		
		a.applyBorder(test);
		a.outputImage("whiteimageblackborder200x200","jpg");
		
		//Tesing Mood
		//Happy
		BufferedImage bHappy = null;
		try {
			bHappy = ImageIO.read(new File("test.jpg"));
		}
		catch (IOException e) {
			System.out.println("IOException3");
		}
		ImageDN aHappy = new ImageDN(bHappy);
		
		aHappy.applyMood("happy");
		aHappy.outputImage("happytest","jpg");
		//Sad
		BufferedImage bSad = null;
		try {
			bSad = ImageIO.read(new File("test.jpg"));
		}
		catch (IOException e) {
			System.out.println("IOException4");
		}
		ImageDN aSad = new ImageDN(bSad);
		
		aSad.applyMood("sad");
		aSad.outputImage("sadtest","jpg");
		//Mad
		BufferedImage bMad = null;
		try {
			bMad = ImageIO.read(new File("test.jpg"));
		}
		catch (IOException e) {
			System.out.println("IOException5");
		}
		ImageDN aMad = new ImageDN(bMad);
		
		aMad.applyMood("mad");
		aMad.outputImage("madtest","jpg");
		
		//Testing Scaling
		BufferedImage bScale = null;
		try {
			bScale = ImageIO.read(new File("beforeScale.jpg"));
		}
		catch (IOException e) {
			System.out.println("IOException6");
		}
		ImageDN aBeforeScale = new ImageDN(bScale);
		ImageDN aScale = aBeforeScale.scale1(200,200);
		
		aScale.outputImage("afterScale","jpg");
		
		//Testing Transparency
		BufferedImage bTransparency = null;
		try {
			bTransparency = ImageIO.read(new File("test.jpg"));
		}
		catch (IOException e) {
			System.out.println("IOException6");
		}
		ImageDN aTransparency = new ImageDN(bTransparency);
		
		aTransparency.applyTransparency(0);
		aTransparency.outputImage("transparencyTest","jpg");
	}
}