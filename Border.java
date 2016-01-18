import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class Border extends ImageDN {
    private ArrayList<Integer> borderPos;
    private String color;

    public Border(BufferedImage brdr, String color) {
	super(brdr);
	this.color = color;
	borderPos = new ArrayList<Integer>();
	int r;
	int g;
	int b;
	for (int row = 0; row < getPixels().length; row++) {
	    for (int col = 0; col < getPixels()[row].length; col++) {
		r = getR(row,col);
		g = getG(row,col);
	    b = getB(row,col);
		if (r < 10 && g < 10 && b < 10) {
		    borderPos.add(row);
		    borderPos.add(col);
		}
	    }
	}
    }
	
    public ArrayList<Integer> getBorderPos() {
	return borderPos;
    }

    public String getColor() {
	return color;
    }
}