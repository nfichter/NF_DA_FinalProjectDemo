import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Driver {
	public static void main(String[] args) {
		try {
			BufferedImage bIn = null;
			//Scaling
			if (args[1].equals("s")) {
				try {
					bIn = ImageIO.read(new File(args[0]));
					ImageDN in = new ImageDN(bIn);
					
					ImageDN out = in.scale1(Integer.parseInt(args[2]),Integer.parseInt(args[3]));
					out.outputImage(args[4],args[5]);
				}
				catch (Exception e) {
					System.out.println("Please read the README for instructions!");
				}
			}
			//Borders
			else if (args[1].equals("b")) {
				Border brdr = null;
				BufferedImage bBrdr = null;
				try {
					bIn = ImageIO.read(new File(args[0]));
					bBrdr = ImageIO.read(new File(args[2]));
					brdr = new Border(bBrdr,"000000000000");
					ImageDN in = new ImageDN(bIn);
					if ((brdr.getMaxX() != in.getMaxX()) || (brdr.getMaxY() != in.getMaxY())) {
						ImageDN inBrdr2 = brdr.scale1(in.getMaxX(),in.getMaxY());
						Border brdr2 = new Border(inBrdr2.getImage(),"000000000000");
						in.applyBorder(brdr2);
					}
					else {
						in.applyBorder(brdr);
					}
					
					in.outputImage(args[3],args[4]);
				}
				catch (Exception e) {
					System.out.println("Please read the README for instructions!");
				}
			}
			//Moods
			else if (args[1].equals("m")) {
				try {
					bIn = ImageIO.read(new File(args[0]));
					ImageDN in = new ImageDN(bIn);
					
					in.applyMood(args[2]);
					in.outputImage(args[3],args[4]);
				}
				catch (Exception e) {
					System.out.println("Please read the README for instructions!");
				}
			}
		}
		catch (Exception e) {
			System.out.println("Please read the README for instructions!");
		}
	}
}